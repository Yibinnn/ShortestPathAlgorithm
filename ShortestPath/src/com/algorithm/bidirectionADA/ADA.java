package com.algorithm.bidirectionADA;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.algorithm.common.ThreadControl;
import com.algorithm.shortestpath.ShortestBasic;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

public abstract class ADA extends ShortestBasic<Key> implements Runnable
{
	int rhs[];
	double inflation; // 影响因子
	double decrease; // 每次减小的值
	List<Integer> incons;// 临时存放需要重新进行扩展的节点
	MapChangeManager mcm;// 地图变化管理器
	BidirectionManager bm;//双向搜索管理器
	// 起点问题，实际用中 起点可以不停变化。 g rhs中的值都可以再利用 。只是估值函数变化而已
	//初始化程序两者步骤相同，正反向程序各自初始化
	public ADA(String name,Map map, MapChangeManager mcm,BidirectionManager bm)
	{
		super(map,name);
		this.mcm = mcm;
		this.bm = bm;
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
			a = Calc.add(b, map.h(goal, s));
		}
		return new Key(s, a, b);
	}

	double getKeyValue(int s)
	{
		return Calc.add(rhs[s], inflation * map.h(goal, s));
	}

	void UpdateState(int s)
	{
		if (!visited[s])
		{
			g[s] = INFINITY;
		}
		//这里要改，对两者而言不一样，这个条件甚至可以考虑是否去掉
		if (s != start)
		{
			reverseTraversalNeighbors(s);
		}
		// 如果已经在open表里就删除,然后重新根据新的key值插入open表
		que.remove(new Key(s));
//		printDebugInfo("*"+s);
		if (rhs[s] != g[s])
		{
			// 如果没有访问过，加入open表，如果访问过，加入INCONS表
			if (!visited[s])
			{
				que.add(getKey(s));
			} else
			{
				//放到这表了
//				System.out.println(name+"here");
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
//				printDebugInfo("#"+s);
				synchronized (bm)
				{
					bm.judge(s,this);
				}
			
				//终止条件是什么，放在哪，值得研究
				//if(s==start)
				//	break;
				// 更新s前驱的rhs值
				traversalNeighbors(s);
			} else
			{
				g[s] = INFINITY;
				traversalNeighbors(s);
				UpdateState(s);
			}
		}
	}
	

	abstract void traversalNeighbors(int s);
	abstract void reverseTraversalNeighbors(int s);
	
	public void init()
	{
		inflation = 2;
		decrease = 0.5;
		rhs[start] = 0;
		que.add(getKey(start));
	}
	
	@Override
	public void mainLoop()
	{
		while(!bm.isOver())
		{
			//现在能简单计算出简单案例
			computeShortestPath();
			//边的改变
			if(!hasMapChange()&&!hasDecreaseInflation())
				ThreadControl.sleep(1000);
			
		}
	}

	boolean hasDecreaseInflation()
	{
		boolean isNoIcons = incons.isEmpty();
		System.out.println(isNoIcons);
		// 将不确定的重新加入open表
		for (Integer in : incons)
		{
			que.add(getKey(in));
		}
		incons.clear();// 清空不确定的表
		visited = new boolean[map.getN()];// 清空closed表
		
		if(inflation<=1 && isNoIcons)
			return false;
	
		// 影响因子更新
		if(inflation>1)
		inflation -= decrease;
		// 根据新的影响因子inflation更新open表
		for (Key k : que)
		{
			k.a = getKeyValue(k.s);
		}
		return true;
	}
	abstract boolean hasMapChange();

	public void printDebugInfo(String info)
	{
		if(name.equals("B"))
		{
			System.out.println("                   "+info+name);
		}
		else
			System.out.println(name+info);
	}

}
