package com.algorithm.ADA;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.algorithm.common.ThreadControl;
import com.algorithm.shortestpath.Key;
import com.algorithm.shortestpath.ShortestBasic;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

/**AnyTime D*算法
 * @author simin
 *
 */
public class ADA extends ShortestBasic<Key>
{
	double inflation; // 影响因子
	double decrease; // 每次减小的值
	int rhs[];
	List<Integer> incons;// 临时存放需要重新进行扩展的节点
	MapChangeManager mcm;// 地图变化管理器

	// 起点问题，实际用中 起点可以不停变化。 g rhs中的值都可以再利用 。只是估值函数变化而已
	public ADA(Map map, MapChangeManager mcm)
	{
		super(map,"ADA");
		this.mcm = mcm;
		int n = map.getN();// 节点个数
		rhs = new int[n];
		for (int i = 0; i < n; i++)
		{
			rhs[i] = INFINITY;
		}
		incons = new ArrayList<Integer>();
	}

	public int comp(Key k1, Key k2)
	{
		if (k1.a != k2.a)
			return k1.a - k2.a > 0 ? 1 : -1;
		else
			return k1.b - k2.b;
	}

	public Key getKey(int s)
	{
		double a;
		int b;
		if (g[s] > rhs[s])
		{
			b = rhs[s];
			a = getKeyValue(s);
		} else
		{
			b = g[s];
			a = Calc.add(b, map.h(start, s));
		}
		return new Key(s, a, b);
	}

	double getKeyValue(int s)
	{
		return Calc.add(rhs[s], inflation * map.h(start, s));
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
			// 如果没有访问过，加入open表，如果访问过，加入INCONS表
			if (!visited[s])
			{
				que.add(getKey(s));
			} else
			{
				incons.add(s);
			}
		}
	}

	// 这部分相对D-star好像没有改变
	void computeShortestPath()
	{
		while (!que.isEmpty())
		{
			Key temp = que.poll();
			int s = temp.s;

			// 判断是否需要扩展,如果当前key已经比start的key还要大了,就没有扩展必要了
			/*
			if (comp(temp, getKey(start)) >= 0)
			{
				if (rhs[start] == g[start])
				{
					que.clear();// 清空open表
					break;
				}
			}
			*/
			visited[s] = true; // 已经访问s
			if (g[s] > rhs[s])
			{
				g[s] = rhs[s];
				//终止条件是什么，放在哪，值得研究
				if(s==start)
					break;
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
		return 1;
	}

	@Override
	public int getEndPoint()
	{
		return 1002;
	}

	@Override
	public void init()
	{
		g[start] = rhs[start] = INFINITY;
		g[goal] = INFINITY;
		rhs[goal] = 0;
		que.add(getKey(goal));
		inflation = 2;
		decrease = 0.5;
	}

	@Override
	public void mainLoop()
	{
		while(true)
		{
			computeShortestPath();
			printPath(start);
			//边的改变
			if(!hasMapChange()&&!hasDecreaseInflation())
			{
				System.out.println();
				ThreadControl.sleep(10000);
			}
		}
	}

	boolean hasDecreaseInflation()
	{
		// 将不确定的重新加入open表
		for (Integer in : incons)
		{
			que.add(getKey(in));
		}
		incons.clear();// 清空不确定的表
		visited = new boolean[map.getN()];// 清空closed表
		
		if(inflation<=1)
			return false;
	
		// 影响因子更新
		inflation -= decrease;
		// 根据新的影响因子inflation更新open表
		for (Key k : que)
		{
			k.a = getKeyValue(k.s);
		}
		return true;
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
		}
		for (Edge e : changeEdges)
		{
			UpdateState(e.s);
		}
		changeEdges.clear();
		return true;
	}
	
	public static void main(String[] args)
	{
		AdjListMap map = new AdjListMap();
		MapChangeManager mcm = new MapChangeManager();
		ADA d = new ADA(map, mcm);
		d.run();

	}
}
