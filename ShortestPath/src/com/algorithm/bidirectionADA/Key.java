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

	// ֻ��Ϊ�˷���ɾ���ã�ֻ��Ҫ��дһ������
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