package com.algorithm.APWA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ListModel;

import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;

public class NavigationTestSystem {
	int start,end,speed;
	int time=0;
	List<Edge> drivePath;
	NavigationAlgorithm na;
	Map map;
	int distance=0;
	int w_distance=0;
	static Random r = new Random();
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
		List<Edge> allEdges = map.getAllEdge();
		for(Edge e:allEdges)
		{
			if(r.nextInt(600)<100)
			{
				e.ri=r.nextInt(15)+1;
			}else
			{
				e.ri=1;
			}
		}
	}
	public void simulation()
	{
		//得到初始化路径
		drivePath = na.getNewPath();
		while(start!=end)
		{
			Edge e = drivePath.remove(0);
			distance+=e.w;
			w_distance+=e.w*e.ri;
			start = e.e;
			int limit_time = (e.w*e.ri*3600)/(speed);
			changeRoadImpedance();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
