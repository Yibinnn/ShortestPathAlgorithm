package com.algorithm.DStar;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.algorithm.common.ThreadControl;
import com.algorithm.shortestpath.AStar;
import com.algorithm.shortestpath.Key;
import com.algorithm.shortestpath.ShortestBasic;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

/** D*��̬ͼ�㷨
 * @author simin
 *
 */
public class DStar extends ShortestBasic<Key>
{

	int rhs[];
	MapChangeManager mcm;// ��ͼ�仯������

	//������⣬ʵ������ ��㲻ͣ�仯
	public DStar(Map map, MapChangeManager mcm)
	{
		super(map,"D*");
		this.mcm = mcm;
		int n = map.getN();// �ڵ����
		rhs = new int[n];
		for (int i = 0; i < n; i++)
		{
			rhs[i] = INFINITY;
		}
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
		a = Calc.add(b, map.h(start, s));
		return new Key(s, a, b);
	}

	void UpdateState(int s)
	{
		if (!visited[s])
		{
			g[s] = INFINITY;
		}
		if (s != goal)
		{
			for (Edge temp : map.getNext(s))
			{
				int t = Calc.add(g[temp.e], temp.w);
				if (t < rhs[s])
				{
					path[s] = temp.e;
					rhs[s] = t;
				}
			}
		}
		// ����Ѿ���open�����ɾ��,Ȼ�����¸����µ�keyֵ����open��
		que.remove(new Key(s));
		if (rhs[s] != g[s])
		{
			k++;
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
	public int getStartPoint()
	{
		return map.getStart();
	}

	@Override
	public int getEndPoint()
	{
		return map.getGoal();
	}

	@Override
	public void init()
	{
		g[start] = rhs[start] = INFINITY;
		g[goal] = INFINITY;
		rhs[goal] = 0;
		que.add(getKey(goal));
	}

	@Override
	public void mainLoop()
	{
		while (true)
		{		
			computeShortestPath();
			printPath(start);
			System.out.println("k="+k);
			//�ߵĸı�
			if(!hasMapChange())
			{
				System.out.println("ͼû�иı�");
				ThreadControl.sleep(10000);
			}
		}
	}

	boolean hasMapChange()
	{
		List<Edge> temp = mcm.getChangeEdge();//��øı�ı�
		List<Edge> changeEdges;
		synchronized (temp)
		{
			if (temp.size() == 0)
				return false;
			changeEdges = new ArrayList<Edge>(temp);
			temp.clear();
		}
	
		for (Edge e : changeEdges)
		{
			map.change(e);
		
			UpdateState(e.s);
		}
		changeEdges.clear();
		return true;
	}
	
	int k=0;
	public static void main(String[] args)
	{
		AdjListMap map = new AdjListMap();
		MapChangeManager mcm = new MapChangeManager();
		DStar d = new DStar(map, mcm);
//		mcm.randomChangeEdge(10,"BJ");
		d.run();
	}
}

