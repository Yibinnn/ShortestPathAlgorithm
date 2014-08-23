 package com.algorithm.common;

public class Calc
{
	public static int INFINITY = 0x7fffffff;
	public static double add(int a,double b)
	{
		if(a>=INFINITY || b>=INFINITY)
			return INFINITY;
		else
		{
			if(a+b<0)
				System.out.println("超过了最大值");
			return a+b;
		}
	}
	public static int add(int a,int b)
	{
		if(a>=INFINITY || b>=INFINITY)
			return INFINITY;
		else
		{
			if(a+b<0)
				System.out.println("超过了最大值");
			return a+b;
		}
	}
	public static int min(int a,int b)
	{
		return a>b?b:a;
	}
	public static void main(String []asd)
	{
	
	}
}
