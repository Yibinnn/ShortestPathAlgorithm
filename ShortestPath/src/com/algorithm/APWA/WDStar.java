package com.algorithm.APWA;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.algorithm.common.Calc;
import com.algorithm.common.ThreadControl;
import com.algorithm.shortestpath.AStar;
import com.algorithm.shortestpath.Key;
import com.algorithm.shortestpath.ShortestBasic;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

/** WD*��̬ͼ�㷨
 * @author simin
 *
 */
public class WDStar extends ShortestBasic<Key> implements Callable<WDStarResult>
{

	int rhs[];
	int info_w[];
	int info_ri[];
	double epsilon;
	List<Edge> changeEdges;
	//������⣬ʵ������ ��㲻ͣ�仯
	public WDStar(Map map,double epsilon)
	{
		super(map,"WD*");
		int n = map.getN();// �ڵ����
		rhs = new int[n];
		info_w = new int[n];
		info_ri = new int[n];
		for (int i = 0; i < n; i++)
		{
			rhs[i] = INFINITY;
		}
		this.epsilon = epsilon;
	}

	public int comp(Key k1, Key k2)
	{
		if (k1.a != k2.a)
			return k1.a - k2.a>0?1:-1;
		else
			return k1.b - k2.b;
	}

	public Key getKey(int s)
	{
		int a, b;
		b = Calc.min(g[s], rhs[s]);
		a = Calc.add(b, (int)(epsilon*map.h(start, s)));
		return new Key(s, a, b);
	}

	void UpdateState(int s)
	{
		if (!visited[s])
		{
			g[s] = INFINITY;
		}
		if (s!=goal)
		{
			for (Edge temp : map.getNext(s))
			{
				int t = Calc.add(g[temp.e], temp.w*temp.ri);
				if (t < rhs[s])
				{
					path[s] = temp.e;
					info_w[s] = temp.w;
					info_ri[s] = temp.ri;
					rhs[s] = t;
				}
			}
		}
		// ����Ѿ���open�����ɾ��,Ȼ�����¸����µ�keyֵ����open��
		que.remove(new Key(s));
		if (rhs[s] != g[s])
		{
			que.add(getKey(s));
		}
	}

	void computeShortestPath()
	{
		while (!que.isEmpty())
		{
			Key temp = que.poll();
			int s = temp.s;
			
			// �ж��Ƿ���Ҫ��չ,�����ǰkey�Ѿ���start��key��Ҫ����,��û����չ��Ҫ��
			if (comp(temp, getKey(start)) >= 0)
			{
				if (rhs[start] == g[start])
				{
					que.clear();// ���open��
					break;
				}
			}
			visited[s] = true; // �Ѿ�����s
			if (g[s] > rhs[s])
			{
				g[s] = rhs[s];
				// ����sǰ����rhsֵ
				for (Edge temp2 : map.getPre(s))
				{
					UpdateState(temp2.s);
				}
			} else
			{
				g[s] = INFINITY;
				for (Edge temp2 : map.getPre(s))
				{
					UpdateState(temp2.s);
				}
				UpdateState(s);
			}
		}
	}

	@Override
	public void init()
	{
		g[start] = rhs[start] = INFINITY;
		g[goal] = INFINITY;
		rhs[goal] = 0;
		que.add(getKey(goal));
	}

	

	void mapChange()
	{
		for (Edge e : changeEdges)
		{
			map.change(e);
			UpdateState(e.s);
		}
	}
	
	public void setOtherParamter(int start,List<Edge> changeEdges)
	{
		this.start = start;
		this.changeEdges = changeEdges;
	}
	@Override
	public WDStarResult call() throws Exception {
		mapChange();
		computeShortestPath();
		return new WDStarResult(path,info_w,info_ri);
	}

	@Override
	public int getStartPoint() {
		return map.getStart();
	}

	@Override
	public int getEndPoint() {
		return map.getGoal();
	}

	@Override
	public void mainLoop() {
		//unnecessary
	}
	
//	int k=0;
//	public static void main(String[] args)
//	{
//		AdjListMap map = new AdjListMap();
//		MapChangeManager mcm = new MapChangeManager();
//		WDStar d = new WDStar(map, mcm,1.0);
//		mcm.randomChangeEdge(10,"BJ");
//		d.run();
//	}

	
}

