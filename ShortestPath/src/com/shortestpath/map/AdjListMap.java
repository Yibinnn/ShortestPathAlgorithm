package com.shortestpath.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.algorithm.common.Calc;

/**
 * @author simin
 *
 */
public class AdjListMap implements Map
{
//	public static final String path = "F:\\�����Լ�����\\ͼ����\\newyork\\USA-road-d.NY.gr";
//	public static final String coordinate_path = "F:\\�����Լ�����\\ͼ����\\newyork\\USA-road-d.NY.co";
//	public static  String path = "F:\\�����Լ�����\\ͼ����\\USA-road-d.BAY.gr\\USA-road-d.BAY.gr";
//	public static  String coordinate_path = "F:\\�����Լ�����\\ͼ����\\USA-road-d.BAY.co\\USA-road-d.BAY.co";
	public static  String path = "F:\\�����Լ�����\\ͼ����\\USA-road-d.NE.gr\\USA-road-d.NE.gr";
	public static  String coordinate_path = "F:\\�����Լ�����\\ͼ����\\USA-road-d.NE.co\\USA-road-d.NE.co";
//	public static  String path = "F:\\�����Լ�����\\ͼ����\\�����е�ͼ\\edges.txt";
//	public static  String coordinate_path = "F:\\�����Լ�����\\ͼ����\\�����е�ͼ\\vertices.txt";

	public static int N = 171504;
	public static int NUM_EDGE = 0;
	public ArrayList<ArrayList<Edge>> nexts;
	public ArrayList<ArrayList<Edge>> pres;
	public ArrayList<Edge> edges;

	//���������꣬���ڹ�ֵ����
	 double x[];
	 double y[];
	int start;
	int goal;
	
	public AdjListMap()
	{
		this(path,coordinate_path);
	}
	public AdjListMap(String path,String coordinate_path)
	{
		long time = System.currentTimeMillis();
		Scanner s = null;
		//��ȡȨֵ
		try
		{
			s = new Scanner(new File(path));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		nexts = new ArrayList<ArrayList<Edge>>(N);
		pres = new ArrayList<ArrayList<Edge>>(N);
		for(int i=0;i<N;i++)
		{
			nexts.add(new ArrayList<Edge>());
			pres.add(new ArrayList<Edge>());
		}
		while(s.hasNextInt())
		{
			int w = s.nextInt();
			int a = s.nextInt()-1;
			int b = s.nextInt()-1;

			Edge e = new Edge(a, b, w,1);
			nexts.get(a).add(e);
			pres.get(b).add(e);
			edges.add(e);
		}
		//��ȡ����
		x = new double[N];
		y = new double[N];
		try
		{
			s = new Scanner(new File(coordinate_path));
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		while(s.hasNextInt())
		{
			int node = s.nextInt()-1;
			x[node] = s.nextDouble();
			y[node] = s.nextDouble();
		}
//		this.start=0;
//		this.goal=171503;
		this.start=59300;
		this.goal=117598;
		long time2 = System.currentTimeMillis();
		System.out.println(time2-time);
	}
	@Override
	public int getN()
	{
		return N;
	}
	//�õ�γ��
	public double getLat(int node)
	{
		return y[node];
	}
	//�õ�����
	public double getLon(int node)
	{
		return x[node];
	}
	@Override
	public int h(int start, int end)
	{
//		return 0;
		
		double a = x[start]-x[end];
		double b = y[start]-y[end];
		BigInteger t1 = new BigInteger(String.valueOf(a));
		t1 = t1.multiply(t1);
		BigInteger t2 = new BigInteger(String.valueOf(b));
		t2 = t2.multiply(t2);
		t1 = t1.add(t2);
		double temp = Math.sqrt(t1.doubleValue());
		return  (int) temp;
//		
	}

	@Override
	public int getStart()
	{
		return start;
	}

	@Override
	public int getGoal()
	{
		return goal;
	}
	public void setGoal(int goal)
	{
		this.goal = goal;
	}
	public void setStart(int start)
	{
		this.start = start;
	}
	@Override
	public List<Edge> getNext(int s)
	{
		return nexts.get(s);
	}

	@Override
	public List<Edge> getPre(int s)
	{
		return pres.get(s);
	}
	@Override
	public void change(Edge edge)
	{
		nexts.get(edge.s).remove(edge);
		pres.get(edge.e).remove(edge);
		if(edge.w!=Calc.INFINITY) //û������������
		{
			nexts.get(edge.s).add(edge);
			pres.get(edge.e).add(edge);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public AdjListMap clone() 
	{
		AdjListMap other ;
		try
		{
			other = (AdjListMap) super.clone();
			other.nexts = new ArrayList<ArrayList<Edge>>();
			for(int i=0;i<nexts.size();i++)
			{
				ArrayList<Edge> temp = nexts.get(i);
				other.nexts.add((ArrayList<Edge>) temp.clone());
			}
			
			return other;
		} catch (CloneNotSupportedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public int getEdgeNum() {
		// TODO Auto-generated method stub
		return NUM_EDGE;
	}
	@Override
	public List<Edge> getAllEdge() {
		// TODO Auto-generated method stub
		return edges;
	}
	
	
	
//	public  Object clone() throws CloneNotSupportedException
//	{
//		AdjListMap o = (AdjListMap)super.clone();
//		o.nexts = new ArrayList<ArrayList<Edge>>();
//		for(int i=0;i<nexts.size();i++)
//		{
//			ArrayList<Edge> temp1 = new ArrayList<Edge>();
//			for(int j=0;j<nexts.get(i);j++)
//				temp1.add(e)
//		}
//		return super.clone();
//	}
	
	
}
