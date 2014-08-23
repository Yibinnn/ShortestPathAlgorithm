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

/** D*动态图算法
 * @author simin
 *
 */
public class DStar extends ShortestBasic<Key>
{

	int rhs[];
	MapChangeManager mcm;// 地图变化管理器

	//起点问题，实际用中 起点不停变化
	public DStar(Map map, MapChangeManager mcm)
	{
		super(map,"D*");
		this.mcm = mcm;
		int n = map.getN();// 节点个数
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
		// 如果已经在open表里就删除,然后重新根据新的key值插入open表
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

			// 判断是否需要扩展,如果当前key已经比start的key还要大了,就没有扩展必要了
			if (comp(temp, getKey(start)) >= 0)
			{
				if (rhs[start] == g[start])
				{
					que.clear();// 清空open表
					break;
				}
			}
			visited[s] = true; // 已经访问s
			if (g[s] > rhs[s])
			{
				g[s] = rhs[s];
				// 更新s前驱的rhs值
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
			//边的改变
			if(!hasMapChange())
			{
				System.out.println("图没有改变");
				ThreadControl.sleep(10000);
			}
		}
	}

	boolean hasMapChange()
	{
		List<Edge> temp = mcm.getChangeEdge();//获得改变的边
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

