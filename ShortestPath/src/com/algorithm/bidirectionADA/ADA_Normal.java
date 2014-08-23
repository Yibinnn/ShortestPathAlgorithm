package com.algorithm.bidirectionADA;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

//从地图起点开始搜索
public class ADA_Normal extends ADA
{

	public ADA_Normal(String name,Map map, MapChangeManager mcm, BidirectionManager bm)
	{
		super(name,map, mcm, bm);
	}
	
	
	
	void reverseTraversalNeighbors(int s)
	{
//		System.out.println(s);
		for (Edge temp : map.getPre(s))
		{
			int t = Calc.add(g[temp.s], temp.w);
			if (t < rhs[s]) 
			{
				path[s] = temp.s;
				rhs[s] = t;
			}
		}
	}
	
	void traversalNeighbors(int s)
	{
		for (Edge temp : map.getNext(s))
		{
			//XXX这里错了 记得改 别的类似的地方也检查检查
			UpdateState(temp.e);
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
			UpdateState(e.e);
		}
		changeEdges.clear();
		return true;
	}
}

