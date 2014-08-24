package com.shortestpath.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MapChangeManager
{
	List<Edge> changeEdges = new ArrayList<Edge>();
	public List<Edge> getChangeEdge()
	{
		return changeEdges;
	}
	public void addChangeEdge(Edge edge)
	{
		synchronized (changeEdges)
		{
			changeEdges.add(edge);
		}
	}
	public void addChangeEdge(int start,int end,int w)
	{
		addChangeEdge(new Edge(start, end, w,1));
	}
	public void randomChangeEdge(int changeNumber,String map)
	{
		String pathname = "F:\\论文以及开题\\图数据\\changeEdge_"+map+"_"+changeNumber+".txt";
		try
		{
			Scanner s = new Scanner(new File(pathname));
			for(int i=0;i<changeNumber;i++)
			{
				addChangeEdge(new Edge(s.nextInt(), s.nextInt(), s.nextInt(),1));
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void write(List<Edge> edges)
	{
		try
		{
			System.setOut(new PrintStream(new FileOutputStream(new File("F:\\论文以及开题\\图数据\\changeEdge_NE_100.txt"))));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Edge edge:edges)
		{
			System.out.println(edge.s+" "+edge.e+" "+edge.w);
		}
	}
	public static void main(String []ss)
	{
		
		try{
			Random r = new Random();
			Scanner s = new Scanner(new File("F:\\论文以及开题\\图数据\\changeEdge_NE_100.txt"));
			List<Edge> temp = new ArrayList<Edge>();
			while(s.hasNext())
			{
				Edge edge = new Edge(s.nextInt(), s.nextInt(), r.nextInt(s.nextInt()),1);
				temp.add(edge);
			}
			write(temp);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		/*
		try
		{
			System.setOut(new PrintStream(new FileOutputStream(new File("F:\\论文以及开题\\图数据\\changeEdge_NE_100.txt"))));
			Random r = new Random();
			for(int i=0;i<100;i++)
			{
				int s = r.nextInt(1400000);
				int e = r.nextInt(1400000);
				int w = r.nextInt(10000)+20000;
				if(s==e)
				{
					i--;
					continue;
				}
				System.out.println(s+" "+e+" "+w);
				
			}
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		*/
	}
}
