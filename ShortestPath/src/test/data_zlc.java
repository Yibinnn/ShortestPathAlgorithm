package test;

import java.util.Random;

public class data_zlc {

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
		int mgbb_zlc_1_120[]=new int[]{7,27,60,79,96,104,120,142,166,208,216,263,255,295,286,340,410,453,434,557};
		int mgbb_zlc_2_120[]=new int[]{7,29,64,85,94,107,130,137,164,203,213,274,270,306,313,367,436,486,461,587};
		
		for(int i=0;i<mgbb_zlc_2_120.length;i++)
		{
			mgbb_zlc_1_120[i]=(int) (((100-5)/100.0)*mgbb_zlc_1_120[i]);
			mgbb_zlc_2_120[i]=(int) (((100+5)/100.0)*mgbb_zlc_2_120[i]);
		}
		System.out.print("mgbb_zlc_1_120=[");
		for(int i=0;i<mgbb_zlc_1_120.length;i++)
    	{
    		if(i!=0)
    			System.out.print(",");
    		System.out.print(mgbb_zlc_1_120[i]);
    		
    	}
		System.out.println("]");
		
		System.out.print("mgbb_zlc_2_120=[");
		for(int i=0;i<mgbb_zlc_2_120.length;i++)
    	{
    		if(i!=0)
    			System.out.print(",");
    		System.out.print(mgbb_zlc_2_120[i]);
    		
    	}
		System.out.println("]");
		
	}

}
