import java.util.*;

public class Bankers {

	int proc;
	int res;
	int[][] maximum;
	int[][] need;
	int[][] allocated;
	int[][] available;
	int[][] request;
	boolean[] safetyLog;

	void display_Present_State() {
		System.out.println("-----PRESENT_SYSTEM_STATE-----");

		System.out.println("Processes	|Maximum	|Allocated");

		for (int i = 0; i < proc; i++) {
			System.out.print("P" + i + "		");
			for (int j = 0; j < res; j++) {
				System.out.print(maximum[i][j] + "  ");
			}
			System.out.print("	");
			for (int j = 0; j < res; j++) {
				System.out.print(+allocated[i][j] + "  ");
			}
			System.out.println();
		}

		System.out.println("\nAvailable Resources in the OS");

		for (int i = 0; i < res; i++) {
			System.out.print(available[0][i] + " ");
		}

		System.out.println();

		System.out.println("Need Matrix");

		for (int i = 0; i < proc; i++) {
			System.out.print("P" + i + "  ");
			for (int j = 0; j < res; j++) {
				System.out.print(need[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void isSafe() {
		// this function determines the processes for which it is unsafe to
		// start with
		safetyLog = new boolean[proc];

		for (int i = 0; i < proc; i++) {
			safetyLog[i] = true;
		}

		for (int i = 0; i < proc; i++) {
			for (int j = 0; j < res; j++) {
				if (need[i][j] > available[0][j]) {
					safetyLog[i] = false;
					break;
				}
			}
		}

		/*System.out
				.println("Following processes are safe and unsafe to start with");

		for (int i = 0; i < proc; i++) {
			if (safetyLog[i]) {
				System.out.println("P" + i + ": Safe");
			} else {
				System.out.println("P" + i + ": Un-Safe");
			}
		}*/

	}

	void safetyAlgo(int[][] request, int n) {

		boolean[] done = new boolean[proc];

		for (int i = 0; i < res; i++) {
			need[n][i] += request[0][i];
		}

		//changing the safety status after updating the need matrix
		isSafe();
		
		for (int i = 0; i < proc; i++) {
			done[i] = false;
		}

		int count = 0;
		int count_safe_alloc = 0;

		System.out.println("Processes will start in the following sequence:");

		while (count < proc) {

			for (int i = 0; i < proc; i++) {
				if (safetyLog[i] && !done[i]) { // if safe and has not been done
					count_safe_alloc++;
												// then do it
					for (int j = 0; j < res; j++) {
						available[0][j] = available[0][j] + allocated[i][j];
						//System.out.print("P" + i + ", ");
						done[i] = true;
						
						//**********this part is just to update the safe or unsafe status
	
						isSafe();
						
						//************
						
						// must change the safety status here

						// safetyLog[i]=false;

					}
					
					System.out.print("P" + i + ", ");
				}
			}
			count++;

		}

		System.out.println("\n "+count_safe_alloc);

		if (count_safe_alloc < proc ) {

			System.out
					.println("\nThere will be a dead lock. So Can't allocate");
		} else {
			System.out.println("There will be No dead lock.");
		}
		
		System.out.println("Final Available Matrix:");
		
		for(int i=0;i<res;i++){
			System.out.print(available[0][i]+" ");
		}

	}

	void input() {
		Scanner scan = new Scanner(System.in);

		System.out.print("Enter the no of Resources: ");
		res = scan.nextInt();
		System.out.print("Enter the no of Processes: ");
		proc = scan.nextInt();

		// allocated_Resources
		System.out.println("Enter the allocated resources:");
		allocated = new int[proc][res];

		for (int i = 0; i < proc; i++) {
			System.out.print("Process-" + i + ": ");
			for (int j = 0; j < res; j++) {
				allocated[i][j] = scan.nextInt();
			}
			System.out.println();
		}

		// maximum resources required
		System.out.println("Enter the maximum required resources: ");
		maximum = new int[proc][res];

		for (int i = 0; i < proc; i++) {
			System.out.print("Process-" + i + ": ");
			for (int j = 0; j < res; j++) {
				maximum[i][j] = scan.nextInt();
			}
			System.out.println();
		}

		// available resources
		available = new int[1][res];
		System.out.println("Enter the resources present at the instance:");
		for (int i = 0; i < res; i++) {
			available[0][i] = scan.nextInt();
		}

		// need matrix calculation::

		need = new int[proc][res];

		for (int i = 0; i < proc; i++) {
			for (int j = 0; j < res; j++) {
				need[i][j] = maximum[i][j] - allocated[i][j];
			}
		}

		display_Present_State();
		isSafe();

		request = new int[1][res];

		System.out.print("Enter the process and request the resources: \n");
		System.out.print("Process: ");
		int n = scan.nextInt();
		System.out.print("Enter the resources: ");
		for (int i = 0; i < res; i++) {
			request[0][i] = scan.nextInt();
		}

		int[][] temp = new int[1][res];

		for (int i = 0; i < res; i++) {
			temp[0][i] = available[0][i];
		}

		safetyAlgo(request, n); // passing on the requested resources and the
								// process no
	}

	public static void main(String[] args) {

		new Bankers().input();
		// input();

	}

}
