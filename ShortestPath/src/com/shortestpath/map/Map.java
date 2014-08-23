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
	//�õ�γ��
	 double getLat(int node);
	//�õ�����
	 double getLon(int node);
	
}
