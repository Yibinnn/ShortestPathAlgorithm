package com.algorithm.APWA;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Edge;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

/**
 * @author simin
 *
 */
public class APWA implements NavigationAlgorithm{

	public static final int NUM_THREAD = 4;
	public static final double []epsilons = new double []{4.0,2.5,1.5,1.0};
	ExecutorService exec;
	Map map;
	List<WDStar> algorithmInstances;
	int start,end,timeLimit;
	List<Edge> changeEdges;
	public APWA(Map map)
	{
		exec = Executors.newFixedThreadPool(NUM_THREAD);
		this.map = map;
//		AdjListMap map = new AdjListMap("F:\\论文以及开题\\图数据\\USA-road-d.NE.gr\\USA-road-d.NE.gr","F:\\论文以及开题\\图数据\\USA-road-d.NE.co\\USA-road-d.NE.co");
		algorithmInstances = new ArrayList<WDStar>();
		for(int i=0;i<epsilons.length;i++)
		{
			WDStar instance = new WDStar(map, epsilons[i]);
			instance.init();
		}
	}
	public void setOtherParamter(int start,int end,int timeLimit,List<Edge> changeEdges)
	{
		this.start = start;
		this.end = end;
		this.timeLimit = timeLimit;
		this.changeEdges = changeEdges;
	}

	@Override
	public WDStarResult getNewPath() {
		for(WDStar wd:algorithmInstances)
		{
			wd.setOtherParamter(this.start, this.changeEdges);
		}
		WDStarResult result=null;
		List<Future<WDStarResult>> results = null;
		try {
			results = exec.invokeAll(algorithmInstances,timeLimit,TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for(Future<WDStarResult> r:results)
		{
			if(!r.isCancelled())
			{
				try {
					result = r.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

}

