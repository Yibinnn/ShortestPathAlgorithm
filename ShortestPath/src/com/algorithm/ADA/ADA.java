package com.algorithm.ADA;

import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.algorithm.common.ThreadControl;
import com.algorithm.shortestpath.Key;
import com.algorithm.shortestpath.ShortestBasic;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

/**AnyTime D*�㷨
 * @author simin
 *
 */
public class ADA extends ShortestBasic<Key>
{
	double inflation; // Ӱ������
	double decrease; // ÿ�μ�С��ֵ
	int rhs[];
	List<Integer> incons;// ��ʱ�����Ҫ���½�����չ�Ľڵ�
	MapChangeManager mcm;// ��ͼ�仯������

	// ������⣬ʵ������ �����Բ�ͣ�仯�� g rhs�е�ֵ������������ ��ֻ�ǹ�ֵ�����仯����
	public ADA(Map map, MapChangeManager mcm)
	{
		super(map,"ADA");
		this.mcm = mcm;
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
			a = Calc.add(b, map.h(start, s));
		}
		return new Key(s, a, b);
	}

	double getKeyValue(int s)
	{
		return Calc.add(rhs[s], inflation * map.h(start, s));
	}

	void UpdateState(int s)
	{
		if (!visited[s])
		{
			g[s] = INFINITY;
		}
		if (s != goal)
		{
			for (Edge temp : map.getNext(s))
			{
				int t = Calc.add(g[temp.e], temp.w);
				if (t < rhs[s])
				{
					path[s] = temp.e;
					rhs[s] = t;
				}
			}
		}
		// ����Ѿ���open�����ɾ��,Ȼ�����¸����µ�keyֵ����open��
		que.remove(new Key(s));
		if (rhs[s] != g[s])
		{
			// ���û�з��ʹ�������open��������ʹ�������INCONS��
			if (!visited[s])
			{
				que.add(getKey(s));
			} else
			{
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
			if(visited[s])
				System.out.println("����~");
			visited[s] = true; // �Ѿ�����s
			if (g[s] > rhs[s])
			{
				g[s] = rhs[s];
				//��ֹ������ʲô�������ģ�ֵ���о�
				if(s==start)
					break;
				// ����sǰ����rhsֵ
				for (Edge temp2 : map.getPre(s))
				{
					UpdateState(temp2.s);
				}
			} else
			{
				g[s] = INFINITY;
				for (Edge temp2 : map.getPre(s))
				{
					UpdateState(temp2.s);
				}
				UpdateState(s);
			}
		}
	}

	@Override
	public int getStartPoint()
	{
		return 1;
	}

	@Override
	public int getEndPoint()
	{
		return 1002;
	}

	@Override
	public void init()
	{
		g[start] = rhs[start] = INFINITY;
		g[goal] = INFINITY;
		rhs[goal] = 0;
		que.add(getKey(goal));
		inflation = 2;
		decrease = 0.5;
	}

	@Override
	public void mainLoop()
	{
		while(true)
		{
			computeShortestPath();
			printPath(start);
			//�ߵĸı�
			if(!hasMapChange()&&!hasDecreaseInflation())
			{
				System.out.println();
				ThreadControl.sleep(10000);
			}
		}
	}

	boolean hasDecreaseInflation()
	{
		// ����ȷ�������¼���open��
		for (Integer in : incons)
		{
			que.add(getKey(in));
		}
		incons.clear();// ��ղ�ȷ���ı�
		visited = new boolean[map.getN()];// ���closed��
		
		if(inflation<=1)
			return false;
	
		// Ӱ�����Ӹ���
		inflation -= decrease;
		// �����µ�Ӱ������inflation����open��
		for (Key k : que)
		{
			k.a = getKeyValue(k.s);
		}
		return true;
	}
	boolean hasMapChange()
	{
		List<Edge> temp = mcm.getChangeEdge();//��øı�ı�
		List<Edge> changeEdges;
		synchronized (temp)
		{
			if (temp.size() == 0)
				return false;
			changeEdges = new ArrayList<Edge>(temp);
			temp.clear();
		}
	
		for (Edge e : changeEdges)
		{
			map.change(e);
		}
		for (Edge e : changeEdges)
		{
			UpdateState(e.s);
		}
		changeEdges.clear();
		return true;
	}
	
	public static void main(String[] args)
	{
		AdjListMap map = new AdjListMap();
		MapChangeManager mcm = new MapChangeManager();
		ADA d = new ADA(map, mcm);
		d.run();

	}
}
