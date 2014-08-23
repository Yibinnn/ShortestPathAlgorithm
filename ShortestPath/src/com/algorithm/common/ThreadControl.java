package com.algorithm.common;

public class ThreadControl
{
	public static void sleep(int time) 
	{
		try
		{
			Thread.sleep(time);
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
