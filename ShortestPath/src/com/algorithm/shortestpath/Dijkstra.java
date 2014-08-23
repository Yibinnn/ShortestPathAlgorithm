package com.algorithm.shortestpath;

import com.algorithm.common.Calc;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;

public class Dijkstra extends ShortestBasic<SimpleKey>
{

	public Dijkstra(Map map)
	{
		super(map,"Dijkstra");
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
	
	public static void main(String[] args)
	{
		Dijkstra a = new Dijkstra(new AdjListMap());
		a.run();
	
	}

	@Override
	public int comp(SimpleKey k1, SimpleKey k2)
	{
		return k1.a - k2.a>0?1:-1;
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
		g[start]=0;
		que.add(getKey(start));
	}

	@Override
	public void mainLoop()
	{
		computeShortestPath();
//		improvePath();
		printPath(goal);
	}

	@Override
	public SimpleKey getKey(int s)
	{
		return new SimpleKey(s, g[s]);
	}

}
