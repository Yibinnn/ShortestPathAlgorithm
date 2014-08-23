package com.algorithm.common;

import com.shortestpath.map.AdjListMap;

public class AlgorithmLaunch
{

	public static void main(String[] args)
	{
		AdjListMap.path = "F:\\论文以及开题\\图数据\\USA-road-d.BAY.gr\\USA-road-d.BAY.gr";
		AdjListMap.coordinate_path = "F:\\论文以及开题\\图数据\\USA-road-d.BAY.co\\USA-road-d.BAY.co";
		AdjListMap map = new AdjListMap();
		map.setGoal(1002);
		map.setStart(1);

		
	}

}
