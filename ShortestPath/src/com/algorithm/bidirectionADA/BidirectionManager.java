package com.algorithm.bidirectionADA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.algorithm.common.Calc;
import com.algorithm.common.ThreadControl;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.MapChangeManager;

public class BidirectionManager
{
	ADA_Normal A;
	ADA_Reverse B;
	MapChangeManager mcmA = new MapChangeManager();
	MapChangeManager mcmB = new MapChangeManager();
	
	PrintStream ps = System.out;
	long oldTime;
	int encounter;//相遇的节点
	int min;//当前路径最小值
	public boolean isNew;
	AdjListMap map;
	public BidirectionManager(AdjListMap map)
	{
		this.map = map;
		A = new ADA_Normal("双向ADA正向",map, mcmA,this);
		B = new ADA_Reverse("双向ADA反向",map.clone(), mcmB, this);
		min = Calc.INFINITY;
	}
	
	public boolean judge(int s,ADA user)
	{
		if(s==user.goal)
		{
			System.out.println(user.name+"自己到达了终点");
			user.printPath(user.goal);
		}
		if(A.g[s]!=Calc.INFINITY && B.g[s]!=Calc.INFINITY)
		{
			int t = A.g[s]+B.g[s];
//			System.out.println("进入了");
			if(t<min)
			{
				min = t;
				encounter = s;
				printPath();
				return true;
			}
		}
		return false;
	}
	
	void printPath()
	{
		long nowTime = System.currentTimeMillis();
		print("耗时"+(nowTime-oldTime)+"ms");
		print("相遇在点"+encounter);
		print("路径长为"+min);
		int i = encounter;
		String path = "";
		while (A.path[i] != -1)
		{
			path = A.path[i]+"-"+path;
			i = A.path[i];
		}
		i = encounter;
		path += i;
		while (B.path[i] != -1)
		{
			path = path+"-"+B.path[i];
			i = B.path[i];
		}
		path = "路径为"+path;
		print(path);
		
	}
	public boolean isOver()
	{
		return false;
	}
	
	public void run()
	{
		ExecutorService exec = Executors.newCachedThreadPool();
		oldTime = System.currentTimeMillis();
		
		
		
//		ThreadControl.sleep(14000);
		
		int changenum = 10;
		String pathname = "F:\\论文以及开题\\图数据\\changeEdge_NE_"+changenum+".txt";
		
		try
		{
			Scanner	s = new Scanner(new File(pathname));
			ArrayList<Edge> temp = new ArrayList<Edge>();
			for(int i=0;i<changenum;i++)
			{
				temp.add(new Edge(s.nextInt(),s.nextInt(),s.nextInt()));
			}
			edgeChange(temp);
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		exec.execute(B);
		exec.execute(A);
		exec.shutdown();
	}
	
	void edgeChange(List<Edge> edges)
	{
		for(Edge edge :edges)
		{
			print("改变了边"+edge.s+"到"+edge.e+" 权为"+edge.w);
			mcmA.addChangeEdge(edge);
			mcmB.addChangeEdge(edge);
		}
		//边改变后重新计算最优路径
//		min = Calc.INFINITY;
	}
	void print(Object info)
	{
		ps.println(info); 
	}
	public static void main(String []args) throws CloneNotSupportedException
	{
		final AdjListMap map = new AdjListMap();
		final AdjListMap map2 =  (AdjListMap) map.clone();
//		System.out.println(map.nexts);
//		System.out.println(map2.nexts);
//		MapChangeManager mcm = new MapChangeManager();

		BidirectionManager bm = new BidirectionManager(map);
		bm.run();
		
		/*
		new Thread(new Runnable()
		{
			public void run()
			{
				while(true)
				{
					for(Edge edge:map.getNext(0))
					{System.out.println("   "+edge);}
//					ThreadControl.sleep(20);
				}
			}
		}).start();
		new Thread(new Runnable()
		{
			public void run()
			{
				while(true)
				{
				map2.getNext(0).add(new Edge(0, 0, 0));
				map2.getNext(0).remove(0);
				System.out.println(map2.getNext(0));
				ThreadControl.sleep(100);
				}
			}
		}).start();
		*/
	}
}
