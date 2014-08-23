package com.algorithm.ARA;


import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.algorithm.shortestpath.ShortestBasic;
import com.algorithm.shortestpath.SimpleKey;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;


/**AnyTime A*算法
 * @author simin
 *
 */
public class ARA extends ShortestBasic<SimpleKey>
{
	double inflation; // 影响因子
	double decrease; // 每次减小的值
	List<Integer> incons;//临时存放需要重新进行扩展的节点
	
	public ARA(Map map)
	{
		super(map,"ARA");
		incons = new ArrayList<Integer>();
	}

	public int comp(SimpleKey k1, SimpleKey k2)
	{
		return k1.a - k2.a>0?1:-1;
	}

	public SimpleKey getKey(int s)
	{
		double a = getKeyValue(s);
		return new SimpleKey(s, a);
	}

	double getKeyValue(int s)
	{
		return Calc.add(g[s], inflation*map.h(start, s));
	}
	
	void improvePath()
	{
		while (!que.isEmpty())
		{
			SimpleKey temp = que.poll();
			int s = temp.s;

			if(s==31774)
				System.out.println(s);
			// 判断是否需要扩展,如果当前key已经比start的key还要大了,就没有扩展必要了
			if (/*comp(temp, getKey(start)) >= 0 || // 可以证明，加上这个条件是达不到目的的，因为key并不是可容许的，很可能一条可以规划的路径因为满足不了这个条件而无法继续*/ s==start)
			{
				break;
			}
			visited[s] = true; // 已经访问s
			for (Edge temp2 : map.getPre(s))
			{
				int pre = temp2.s;
				int t = Calc.add(g[s],temp2.w);
				if(t<g[pre])
				{
					g[pre]=t;
					path[pre]=s;
					if(!visited[pre])
					{
						que.add(getKey(pre));
					}
					else
					{
						incons.add(pre);
					}
				}
			}
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
		for (SimpleKey k : que)
		{
			k.a = getKeyValue(k.s);
		}
		return true;
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
		g[start] = INFINITY;
		g[goal] = 0;
		que.add(getKey(goal));
		ps = System.out;
		inflation = 2; //检查这里
		decrease = 0.5;
	}

	@Override
	public void mainLoop()
	{
		do
		{
			System.out.println("启发函数影响因子为"+inflation);
			improvePath();
			//根据因子粒度输出
			printPath(start);
		}while(hasDecreaseInflation());
	}


	public static void main(String[] args)
	{
		AdjListMap map = new AdjListMap();
		ARA d = new ARA(map);
		d.run();
	}
}

