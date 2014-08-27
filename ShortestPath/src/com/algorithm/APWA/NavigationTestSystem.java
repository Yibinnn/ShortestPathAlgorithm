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
		drivePath = na.getNewPath();
		while(start!=end)
		{
			int w = drivePath.info_w[start];
			int ww = drivePath.info_w[start]*drivePath.info_ri[start];
			distance+=w;
			w_distance+=ww;
			start = drivePath.path[start];
			int limit_time = (ww*3600)/(speed);
			changeRoadImpedance();
			na.setOtherParamter(this.start, this.end, limit_time, this.changeEdges);
			WDStarResult t = na.getNewPath();
			if(t!=null)
				drivePath=t;
		}
	}
	
	public static void main(String[] args) {
		AdjListMap map = new AdjListMap("F:\\论文以及开题\\图数据\\USA-road-d.NE.gr\\USA-road-d.NE.gr","F:\\论文以及开题\\图数据\\USA-road-d.NE.co\\USA-road-d.NE.co");
		NavigationAlgorithm na  = new APWA(map);
		NavigationTestSystem ns = new NavigationTestSystem(0, 100, 40, na, map);
		ns.simulation();
	}

}
