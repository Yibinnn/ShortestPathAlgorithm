package test;

import java.util.Random;

public class data_80 {

	/*
	 	int []mgbb_n1={6,23,41,57,73,81,103,111,126,138,141,163,186,194,195,228,232,276,284,350};
		int []mgbb_n2={6,23,41,57,73,81,103,111,126,138,141,163,186,194,195,228,232,276,284,350};
    	int []mgbb_40={10,33,64,87,100,122,143,164,186,220,233,277,301,335,352,404,438,482,520,597};
    	
    	int []mgbb_1_80={6,21,42,56,65,80,90,106,117,145,146,182,186,221,218,258,284,313,322,394};
    	

    	int []bjs_x={5,15,27,42,62,65,90,91,106,111,124,132,149,163,205,218,237,254,261,265};
    	int []bjs_1_80={5,12,22,34,44,52,65,70,75,83,98,106,118,130,148,169,184,192,199,203};
    	int []bjs_2_80={4,11,21,32,43,50,63,68,72,81,96,102,115,126,145,163,180,186,195,198};
    	Random r = new Random();
    	*/
	public static void main(String[] args) {
		Random r = new Random();
		
		
		int mgbb_80[]=new int[41];
		for(int i=0;i<mgbb_80.length;i++)
		{
			mgbb_80[i]=i*10;
		}
		System.out.print("mgbb_80=[");
		for(int i=0;i<mgbb_80.length;i++)
    	{
    		if(i!=0)
    			System.out.print(",");
    		System.out.print(mgbb_80[i]);
    	}
		System.out.println("]");
		
		//mgbb_80_1
		int mgbb_80_1[] =new int[41];
		
		double a = 0.035;int b=27;int c=20;
		for(int i=0;i<mgbb_80_1.length;i++)
		{
			mgbb_80_1[i]=(int) ((-1)*a*(i-c)*(i-c)+30);
			int t = (int) (90/(3+Math.abs(i-c)));
			mgbb_80_1[i]=(int) (((100+t)/100.0)*mgbb_80_1[i]);
			mgbb_80_1[i]=(int) (((100+r.nextInt(30)-15)/100.0)*mgbb_80_1[i]);
		}
		
		int sum = 0;
		System.out.print("mgbb_80_1=[");
		for(int i=0;i<mgbb_80_1.length;i++)
    	{
    		if(i!=0)
    			System.out.print(",");
    		System.out.print(mgbb_80_1[i]);
    		sum+=mgbb_80_1[i];
    	}
		System.out.println("]");
		System.out.println("sum="+sum);
		
		//mgbb_80_2
		int mgbb_80_2[] =new int[41];
		 a = 0.035; b=27; c=10;
		for(int i=0;i<mgbb_80_2.length;i++)
		{
			mgbb_80_2[i]=(int) ((-1)*a*(i-c)*(i-c)+30);
			int t = (int) (90/(3+Math.abs(i-c)));
			mgbb_80_2[i]=(int) (((100+t)/100.0)*mgbb_80_2[i]);
			mgbb_80_2[i]=(int) (((100+r.nextInt(30)-15)/100.0)*mgbb_80_2[i]);
			if(i<=35)
				mgbb_80_2[i]=(int) (((100+30)/100.0)*mgbb_80_2[i]);
		}
		
		 sum = 0;
		System.out.print("mgbb_80_2=[");
		for(int i=0;i<mgbb_80_2.length;i++)
    	{
    		if(i!=0)
    			System.out.print(",");
    		System.out.print(mgbb_80_2[i]);
    		sum+=mgbb_80_2[i];
    	}
		System.out.println("]");
		System.out.println("sum="+sum);
		
	}

}
