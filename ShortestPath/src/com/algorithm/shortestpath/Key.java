package com.algorithm.shortestpath;

public class Key
{
	public int s;
	public double a;
	public int b;

	//s是节点编号 a是第一键值 b是第二键值
	public Key(int s, double a, int b)
	{
		this.s = s;
		this.a = a;
		this.b = b;
	}

	// 只是为了方便删除用，只需要填写一个参数
	public Key(int s)
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