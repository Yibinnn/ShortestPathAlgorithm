package com.algorithm.APWA;

import java.util.List;

import com.shortestpath.map.Edge;

public interface NavigationAlgorithm {
	public boolean isDone();
	public List<Edge> getNewPath();
}
