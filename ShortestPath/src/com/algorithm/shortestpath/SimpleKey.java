package com.algorithm.shortestpath;

public class SimpleKey
{
	public int s;
	public double a;

	public SimpleKey(int s, double a)
	{
		this.s = s;
		this.a = a;
	}

	// 只是为了方便删除用，只需要填写一个参数
	public SimpleKey(int s)
	{
		this.s = s;
	}

	@Override
	public boolean equals(Object obj)
	{
		Key other = (Key) obj;
		return this.s == other.s;
	}

}
