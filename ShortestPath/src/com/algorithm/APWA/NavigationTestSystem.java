package com.algorithm.APWA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ListModel;

import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;

public class NavigationTestSystem {
	int start,end,speed;
	int time=0;
	WDStarResult drivePath;
	NavigationAlgorithm na;
	Map map;
	int distance=0;
	int w_distance=0;
	static Random r = new Random();
	List<Edge> changeEdges = new ArrayList<Edge>();
	public NavigationTestSystem(int start,int end,int speed,NavigationAlgorithm na,Map map)
	{
		this.start = start;
		this.end = end;
		this.speed = speed;
		this.na = na;
		this.map = map;
	}
	
	public void changeRoadImpedance()
	{
		changeEdges.clear();
		List<Edge> allEdges = map.getAllEdge();
		for(Edge e:allEdges)
		{
			int t=1;
			if(r.nextInt(600)<100)
			{
				t=r.nextInt(15)+1;
			}
			if(t==e.ri)
			{
				changeEdges.add(e);
			}
		}
	}
	public void simulation()
	{
		//得到初始化路径
		na.setOtherParamter(this.start, this.end, Integer.MAX_VALUE, this.changeEdges);
		drivePath = na.getNewPath();
		while(start!=end)
		{
			System.out.println(start);
			int w = drivePath.info_w[start];
			int ww = drivePath.info_w[start]*drivePath.info_ri[start];
			distance+=w;
			w_distance+=ww;
			start = drivePath.path[start];
			System.out.println(start);
			int limit_time = (ww*60)/(speed);
			changeRoadImpedance();
			na.setOtherParamter(this.start, this.end, limit_time, this.changeEdges);
			WDStarResult t = na.getNewPath();
			if(t!=null)
				drivePath=t;
		}
		System.out.println("over");
	}
	
	public static void main(String[] args) {
		AdjListMap map = new AdjListMap("F:\\论文以及开题\\图数据\\USA-road-d.NE.gr\\USA-road-d.NE.gr","F:\\论文以及开题\\图数据\\USA-road-d.NE.co\\USA-road-d.NE.co");
		map.setStart(1);
		map.setGoal(1002);
		NavigationAlgorithm na  = new APWA(map);
		NavigationTestSystem ns = new NavigationTestSystem(1, 1002, 40, na, map);
		ns.simulation();
	}

}
