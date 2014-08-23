package com.algorithm.bidirectionADA;

public class Key
{
	int s;
	double a;
	int b;

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