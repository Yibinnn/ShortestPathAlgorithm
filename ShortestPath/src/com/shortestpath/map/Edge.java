package com.shortestpath.map;

public class Edge
{
	public int s;
	public int e;
	public int w;
	//Â·×èÏµÊı
	public int ri;
	public Edge(int s, int e, int w,int ri)
	{
		this.s = s;
		this.e = e;
		this.w = w;
		this.ri = ri;
	}
	@Override
	public boolean equals(Object obj)
	{
		Edge temp = (Edge)obj;
		return s==temp.s && e==temp.e;
	}
	
}
