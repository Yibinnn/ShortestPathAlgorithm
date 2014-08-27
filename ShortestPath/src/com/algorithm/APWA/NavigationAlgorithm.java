package com.algorithm.APWA;

import java.util.List;

import com.shortestpath.map.Edge;

public interface NavigationAlgorithm {
	public WDStarResult getNewPath();
	public void setOtherParamter(int start,int end,int timeLimit,List<Edge> changeEdges);
}
