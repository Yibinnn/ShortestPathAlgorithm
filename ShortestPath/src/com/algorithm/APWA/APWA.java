package com.algorithm.APWA;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.shortestpath.map.AdjListMap;
import com.shortestpath.map.Map;
import com.shortestpath.map.MapChangeManager;

/**
 * @author simin
 *
 */
public class APWA {

	public static final int NUM_THREAD = 4;
	public static final double []epsilons = new double []{1.0,1.5,2.5,4};
	public static void main(String[] args) {
		ExecutorService exec = Executors.newFixedThreadPool(NUM_THREAD);
		AdjListMap map = new AdjListMap();
		MapChangeManager mcm = new MapChangeManager();

		for(int i=0;i<epsilons.length;i++)
		{
			WDStarTask task = new WDStarTask(map,mcm,epsilons[i]);
			exec.execute(task);
		}
	}

}

class WDStarTask extends WDStar implements Runnable
{
	public WDStarTask(Map map, MapChangeManager mcm, double epsilon) {
		super(map, mcm, epsilon);
	}

	@Override
	public void run() {
		this.run();
	}
}
