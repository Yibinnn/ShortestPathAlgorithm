package com.algorithm.bidirectionADA;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

/**
 * @author simin
 *
 */
public class ADA_Reverse extends ADA
{

	public ADA_Reverse(String name,Map map, MapChangeManager mcm, BidirectionManager bm)
	{
		super(name,map, mcm, bm);
	}
	
	
	void reverseTraversalNeighbors(int s)
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
	void traversalNeighbors(int s)
	{
		for (Edge temp : map.getPre(s))
		{
			UpdateState(temp.s);
		}
	}
	

	@Override
	public int getStartPoint()
	{
		return map.getGoal();
	}


	@Override
	public int getEndPoint()
	{
		return map.getStart();
	}


	@Override
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
}
