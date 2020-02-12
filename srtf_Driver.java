import java.util.*;

public class Driver {

	
	
	public static void SRTF(int []AT, int []BT, int []RT, int []FT, int []WT, int n){
		int TT = 0; // total burst time required

		for (int i = 0; i < n; i++) {
			TT += BT[i];
		}
		System.out.println(":::PROCESS-DATA:::");
		System.out.println("Process		Arrival_Time	Burst_Time");
		for (int i = 0; i < n; i++) {
			System.out.println("P" + (i + 1) + "         	 " + AT[i]
					+ "   	 	    " + BT[i]);
		}

		System.out.println("\nTotal_Burst_Time: " + TT + " (Time Units)\n\n");

		int timer = 0;
		int present=0;
		int smallest=0;
		int prev_smallest=0;

		System.out.println(":::Time_Line:::");
		while(timer < TT){

				System.out.print("<"+(timer)+">");	
			
			//1. Finding total processes that have been enqueued
			int i=0;
			while(i<AT.length && i<=timer){
				if(AT[i]==timer){
					present++;
				}
				i++;
			}
			
			//2. Finding the one with the Shortest Remaining Time amongst the enqueued processes
			
			smallest=0;
			for(int j=0;j<present;j++){

				//ones who's time is greater than zero are considered
				if( RT[j]>0 && (RT[j]<=RT[smallest]) ){	
					smallest=j;
				}
			}

			System.out.print("(P"+(smallest+1)+")");
			
			//3. Consumption of the present smallest by one unit time
			RT[smallest]--;
			
			if(RT[smallest]==0){	//if that process has been consumed
				FT[smallest]=timer+1;//+1;		//1 is being added since the timer starts counting on 0
				
			}
			timer++;
		}
		
		System.out.print("<"+(timer)+">");
		
		System.out.println("\n\n:::FINISHING TIMES:::");
		
		for(int t=0;t<n;t++){
			System.out.println("Process "+(t+1)+" : "+FT[t]);
		}
		
		
		int total_waiting_time=0;
		for(int t=0;t<n;t++){
			WT[t]=FT[t]-(BT[t]+AT[t]);		//waiting time formula for Pre-emptive Scheduling
			total_waiting_time+=WT[t];
		}
		
		
		System.out.println("\n:::WAITING TIMES:::");
		for(int i=0;i<WT.length;i++){
			System.out.println("Process-"+(i+1)+" : "+WT[i]);
		}
		
		double temp_ave=total_waiting_time/n;
		
		System.out.println("Average waiting time: "+temp_ave);

	}
	
	
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter the number of Processes: ");
		int n = scan.nextInt();

		int[] AT = new int[n];	//Arrival Times
		int[] BT = new int[n];	//Burst Times
		int[] RT = new int[n];	//Remaining Time
		int[] FT = new int[n];	//Finish Time
		int[] WT = new int[n];	//Waiting Times  

		for (int i = 0; i < n; i++) {// initialization of FT array
			FT[i] = 0;
		}

		for (int i = 0; i < n; i++) {

			
			
			System.out.println("Process " + (i + 1));
			System.out.print("Enter the Arrival Time: ");
			AT[i] = scan.nextInt();

			System.out.print("Enter the CPU Burst Time: ");
			BT[i] = scan.nextInt();

			RT[i]=BT[i];	//Assignment of the initial Remaining Times
			
			System.out.println();
		}
		
		SRTF( AT, BT, RT, FT, WT, n);		
	}

}
