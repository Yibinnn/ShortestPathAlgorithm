package com.algorithm.shortestpath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.algorithm.common.Calc;
import com.shortestpath.map.Map;

public abstract class ShortestBasic<K>
{
	public final static int INFINITY = Calc.INFINITY;
	public Map map;
	public int g[];
	public int path[]; // 记录路径
	// 起点、终点
	public int start, goal;
	public boolean visited[]; // 记录有没有被访问过,相当于closed吧？
	public PriorityQueue<K> que;
//	double inflation; // 影响因子
//	List<Integer> incons;//临时存放需要重新进行扩展的节点
	public long oldTime;
	public PrintStream ps = System.out;
	public String name;
	public ShortestBasic(Map map,String name)
	{
		this.map = map;
		this.name = name;
		int n = map.getN();// 节点个数
		start = getStartPoint();// 起点
		goal = getEndPoint();// 终点
		g = new int[n];
		path = new int[n];
		visited = new boolean[n];

		for (int i = 0; i < n; i++)
		{
			g[i] = INFINITY;
			path[i] = -1;
//			visited[i] = false;//后面会重新生成
		}
		que = new PriorityQueue<K>(map.getN(), new Comparator<K>()
		{
			@Override
			public int compare(K k1, K k2)
			{
				return comp(k1, k2);
			}
		});
	}
	public void print(Object info)
	{
		ps.println(info); 
	}
	public void setPrintStreamFile(String filePath)
	{
		try
		{
			this.ps = new PrintStream(new File(filePath));
		} catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void printPath(int start)
	{
		long nowTime = System.currentTimeMillis();
		print("耗时"+(nowTime-oldTime)+"ms");
		StringBuilder pathBuilder = new StringBuilder();
		StringBuilder jsPathBuilder = new StringBuilder("var s=[");
		if (g[start] < INFINITY)
		{
			print("路径长为"+g[start]);
			int i = start;
			pathBuilder.append(i);
			jsPathBuilder.append("[").append(this.map.getLon(start)).append(",").append(this.map.getLat(start)).append("]");
			while (path[i] != -1)
			{
				pathBuilder.append("-"+path[i]);
				jsPathBuilder.append(",[").append(this.map.getLon(path[i])).append(",").append(this.map.getLat(path[i])).append("]");
				i = path[i];
			}
		} else
		{
			pathBuilder.append("不可达");
		}
		jsPathBuilder.append("];");
		//写入js文件供googlemap使用
		try {
			PrintStream ps = new PrintStream(new File("C:\\Users\\simin\\Desktop\\GoogleMapJS\\data.js"));
			ps.print(jsPathBuilder.toString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		print(pathBuilder);
	}
	public abstract int comp(K k1, K k2);
	public abstract int getStartPoint();
	public abstract int getEndPoint();
	public abstract void init();
	public abstract void mainLoop();
	public abstract K getKey(int s);
	public  void run()
	{
		init();
		print(name+"开始运行,起点是"+start+",终点是"+goal);
		oldTime = System.currentTimeMillis();
		mainLoop();
	}
	
	
}
