package com.shortestpath.map;

public class Edge
{
	public int s;
	public int e;
	public int w;
	public Edge(int s, int e, int w)
	{
		this.s = s;
		this.e = e;
		this.w = w;
	}
	@Override
	public boolean equals(Object obj)
	{
		Edge temp = (Edge)obj;
		return s==temp.s && e==temp.e;
	}
	
}
