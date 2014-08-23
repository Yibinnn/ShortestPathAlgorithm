package com.algorithm.shortestpath;

import com.algorithm.common.Calc;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

public class AStar extends ShortestBasic<SimpleKey>
{

	public AStar(Map map)
	{
		super(map,"A*");
	}

	
	
	void computeShortestPath()
	{
		
		while(!que.isEmpty())
		{
			SimpleKey temp = que.poll();
			int s = temp.s;
			visited[s] = true;
			if(s==goal)
				break;
			if(s==1049689)
			{
				System.out.println(s);
			}
			for(Edge edge : map.getNext(s))
			{
				int e = edge.e;
				int t = Calc.add(g[s], edge.w);
				if(!visited[e]&&t<g[e])
				{
					g[e]=t;
					path[e] = s;
					que.add(getKey(e));
				}
			}
		}
	}

	@Override
	public int comp(SimpleKey k1, SimpleKey k2)
	{
		return k1.a - k2.a>0?1:-1;
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
		g[start]=0;
		que.add(getKey(start));
	}

	@Override
	public void mainLoop()
	{

		computeShortestPath();
//		improvePath();
		System.out.println(g[goal]);
		printPath(goal);
	}

	@Override
	public SimpleKey getKey(int s)
	{
		return new SimpleKey(s, g[s]+map.h(s, goal));
	}

	public static void main(String[] args)
	{
//		AStar a = new AStar(new AdjListMap());
//		a.run();
		AdjListMap map = new AdjListMap();
//		MapChangeManager mcm = new MapChangeManager();
////		mcm.randomChangeEdge(100,"NE");
//		for(Edge e:mcm.getChangeEdge())
//		{
//			map.change(e);
//		}
//		
		AStar b = new AStar(map);
		b.run();
		
	}
}
