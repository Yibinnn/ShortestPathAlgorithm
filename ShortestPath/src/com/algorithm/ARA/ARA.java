package com.algorithm.ARA;


import java.util.ArrayList;
import java.util.List;

import com.algorithm.common.Calc;
import com.algorithm.shortestpath.ShortestBasic;
import com.algorithm.shortestpath.SimpleKey;
import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;


/**AnyTime A*�㷨
 * @author simin
 *
 */
public class ARA extends ShortestBasic<SimpleKey>
{
	double inflation; // Ӱ������
	double decrease; // ÿ�μ�С��ֵ
	List<Integer> incons;//��ʱ�����Ҫ���½�����չ�Ľڵ�
	
	public ARA(Map map)
	{
		super(map,"ARA");
		incons = new ArrayList<Integer>();
	}

	public int comp(SimpleKey k1, SimpleKey k2)
	{
		return k1.a - k2.a>0?1:-1;
	}

	public SimpleKey getKey(int s)
	{
		double a = getKeyValue(s);
		return new SimpleKey(s, a);
	}

	double getKeyValue(int s)
	{
		return Calc.add(g[s], inflation*map.h(start, s));
	}
	
	void improvePath()
	{
		while (!que.isEmpty())
		{
			SimpleKey temp = que.poll();
			int s = temp.s;

			if(s==31774)
				System.out.println(s);
			// �ж��Ƿ���Ҫ��չ,�����ǰkey�Ѿ���start��key��Ҫ����,��û����չ��Ҫ��
			if (/*comp(temp, getKey(start)) >= 0 || // ����֤����������������Ǵﲻ��Ŀ�ĵģ���Ϊkey�����ǿ�����ģ��ܿ���һ�����Թ滮��·����Ϊ���㲻������������޷�����*/ s==start)
			{
				break;
			}
			visited[s] = true; // �Ѿ�����s
			for (Edge temp2 : map.getPre(s))
			{
				int pre = temp2.s;
				int t = Calc.add(g[s],temp2.w);
				if(t<g[pre])
				{
					g[pre]=t;
					path[pre]=s;
					if(!visited[pre])
					{
						que.add(getKey(pre));
					}
					else
					{
						incons.add(pre);
					}
				}
			}
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
		for (SimpleKey k : que)
		{
			k.a = getKeyValue(k.s);
		}
		return true;
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
		g[start] = INFINITY;
		g[goal] = 0;
		que.add(getKey(goal));
		ps = System.out;
		inflation = 2; //�������
		decrease = 0.5;
	}

	@Override
	public void mainLoop()
	{
		do
		{
			System.out.println("��������Ӱ������Ϊ"+inflation);
			improvePath();
			//���������������
			printPath(start);
		}while(hasDecreaseInflation());
	}


	public static void main(String[] args)
	{
		AdjListMap map = new AdjListMap();
		ARA d = new ARA(map);
		d.run();
	}
}

