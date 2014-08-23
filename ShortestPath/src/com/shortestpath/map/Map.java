package com.shortestpath.map;

import java.util.List;

public interface Map extends Cloneable
{
	int getN();
	int h(int start,int end);
	int getStart();
	int getGoal();
	List<Edge> getNext(int s);
	List<Edge> getPre(int s);
	void change(Edge edge);	
	//得到纬度
	 double getLat(int node);
	//得到经度
	 double getLon(int node);
	
}
