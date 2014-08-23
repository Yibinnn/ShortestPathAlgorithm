package com.algorithm.bidirectionADA;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.algorithm.common.ThreadControl;
import com.algorithm.shortestpath.ShortestBasic;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

public abstract class ADA extends ShortestBasic<Key> implements Runnable
{
	int rhs[];
	double inflation; // Ӱ������
	double decrease; // ÿ�μ�С��ֵ
	List<Integer> incons;// ��ʱ�����Ҫ���½�����չ�Ľڵ�
	MapChangeManager mcm;// ��ͼ�仯������
	BidirectionManager bm;//˫������������
	// ������⣬ʵ������ �����Բ�ͣ�仯�� g rhs�е�ֵ������������ ��ֻ�ǹ�ֵ�����仯����
	//��ʼ���������߲�����ͬ�������������Գ�ʼ��
	public ADA(String name,Map map, MapChangeManager mcm,BidirectionManager bm)
	{
		super(map,name);
		this.mcm = mcm;
		this.bm = bm;
		int n = map.getN();// �ڵ����	
		rhs = new int[n];
		for (int i = 0; i < n; i++)
		{
			rhs[i] = INFINITY;
		}
		incons = new ArrayList<Integer>();
	}

	public int comp(Key k1, Key k2)
	{
		if (k1.a != k2.a)
			return k1.a - k2.a > 0 ? 1 : -1;
		else
			return k1.b - k2.b;
	}

	public Key getKey(int s)
	{
		double a;
		int b;
		if (g[s] > rhs[s])
		{
			b = rhs[s];
			a = getKeyValue(s);
		} else
		{
			b = g[s];
			a = Calc.add(b, map.h(goal, s));
		}
		return new Key(s, a, b);
	}

	double getKeyValue(int s)
	{
		return Calc.add(rhs[s], inflation * map.h(goal, s));
	}

	void UpdateState(int s)
	{
		if (!visited[s])
		{
			g[s] = INFINITY;
		}
		//����Ҫ�ģ������߶��Բ�һ������������������Կ����Ƿ�ȥ��
		if (s != start)
		{
			reverseTraversalNeighbors(s);
		}
		// ����Ѿ���open�����ɾ��,Ȼ�����¸����µ�keyֵ����open��
		que.remove(new Key(s));
//		printDebugInfo("*"+s);
		if (rhs[s] != g[s])
		{
			// ���û�з��ʹ�������open��������ʹ�������INCONS��
			if (!visited[s])
			{
				que.add(getKey(s));
			} else
			{
				//�ŵ������
//				System.out.println(name+"here");
				incons.add(s);
			}
		}
	}
	
	// �ⲿ�����D-star����û�иı�
	void computeShortestPath()
	{
		while (!que.isEmpty())
		{
			Key temp = que.poll();
			int s = temp.s;
			// �ж��Ƿ���Ҫ��չ,�����ǰkey�Ѿ���start��key��Ҫ����,��û����չ��Ҫ��
			/*
			if (comp(temp, getKey(start)) >= 0)
			{
				if (rhs[start] == g[start])
				{
					que.clear();// ���open��
					break;
				}
			}
			*/
			visited[s] = true; // �Ѿ�����s
			if (g[s] > rhs[s])
			{
				g[s] = rhs[s];
//				printDebugInfo("#"+s);
				synchronized (bm)
				{
					bm.judge(s,this);
				}
			
				//��ֹ������ʲô�������ģ�ֵ���о�
				//if(s==start)
				//	break;
				// ����sǰ����rhsֵ
				traversalNeighbors(s);
			} else
			{
				g[s] = INFINITY;
				traversalNeighbors(s);
				UpdateState(s);
			}
		}
	}
	

	abstract void traversalNeighbors(int s);
	abstract void reverseTraversalNeighbors(int s);
	
	public void init()
	{
		inflation = 2;
		decrease = 0.5;
		rhs[start] = 0;
		que.add(getKey(start));
	}
	
	@Override
	public void mainLoop()
	{
		while(!bm.isOver())
		{
			//�����ܼ򵥼�����򵥰���
			computeShortestPath();
			//�ߵĸı�
			if(!hasMapChange()&&!hasDecreaseInflation())
				ThreadControl.sleep(1000);
			
		}
	}

	boolean hasDecreaseInflation()
	{
		boolean isNoIcons = incons.isEmpty();
		System.out.println(isNoIcons);
		// ����ȷ�������¼���open��
		for (Integer in : incons)
		{
			que.add(getKey(in));
		}
		incons.clear();// ��ղ�ȷ���ı�
		visited = new boolean[map.getN()];// ���closed��
		
		if(inflation<=1 && isNoIcons)
			return false;
	
		// Ӱ�����Ӹ���
		if(inflation>1)
		inflation -= decrease;
		// �����µ�Ӱ������inflation����open��
		for (Key k : que)
		{
			k.a = getKeyValue(k.s);
		}
		return true;
	}
	abstract boolean hasMapChange();

	public void printDebugInfo(String info)
	{
		if(name.equals("B"))
		{
			System.out.println("                   "+info+name);
		}
		else
			System.out.println(name+info);
	}

}
