package com.algorithm.common;

import com.shortestpath.map.AdjListMap;

public class AlgorithmLaunch
{

	public static void main(String[] args)
	{
		AdjListMap.path = "F:\\�����Լ�����\\ͼ����\\USA-road-d.BAY.gr\\USA-road-d.BAY.gr";
		AdjListMap.coordinate_path = "F:\\�����Լ�����\\ͼ����\\USA-road-d.BAY.co\\USA-road-d.BAY.co";
		AdjListMap map = new AdjListMap();
		map.setGoal(1002);
		map.setStart(1);

		
	}

}
