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

	// ֻ��Ϊ�˷���ɾ���ã�ֻ��Ҫ��дһ������
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
