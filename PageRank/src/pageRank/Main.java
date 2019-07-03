package pageRank;
import matrixes.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		
		//This value will be the threshold for our power method loop
		double epslon = Math.pow(10, -4);
		//Initial ranking factor
		double dampingFactor = 0.85;
		
		//Link Matrix size input
		System.out.println("How many sites would you like to rank?");
		int n = in.nextInt();
		System.out.println();
		//Creates a associated name matrix
		String pageNames[] = new String[n]; 
		for (int i = 1; i <= n; i++) {
			pageNames[i-1] = "P"+i; 
		}
		
		//Link Matrix actual input
		System.out.println("Give me the adjacency matrix (in-going and out-going links relation, lines represent ingoing"
				+ " links and columns the out-going ones):");
		double array[][] = new double[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				array[i][j] = in.nextDouble();
				//We will not consider loop links (links that come from itself)
				if(i == j) {
					array[i][j] = 0;
				}
			}
		}
		
		//Initializing Matrix
		long startTime = System.nanoTime();
		Matrix A = new Matrix(n, n, array);
		
		//We will "apply weights" to the matrix, based on page's out-going links, pages witch have more out-going
		//links will be less valuable, so the sites that have links coming from those will score less
		for(int j = 0; j < A.getColumnDimension(); j++) {
			int sum = 0;
			for(int i = 0; i < A.getRowDimension(); i++) {
				sum += A.getArray()[i][j];
			}
			A.column_by_constat(j, Math.pow(sum, -1));
		}
		
		//Support matrices for power method
		Matrix B = new Matrix(n, n, 1.0/n);
		
		Matrix C = new Matrix(n, n);
		
		//Matrix preparation for the power method execution
		try {
			C = C.plus(B.timesConstant(dampingFactor)).plus(A.timesConstant(1-dampingFactor));
		} catch (Exception e) {
			System.out.println(e);;
		}
		
		//Pre-iteration eigenvector
		Matrix v = new Matrix(n, 1, 1.0/n);
		
		//Actual power method
		boolean done = false;
		while (!done) {
			Matrix previous = v;
			
			try {
				v = C.timesMatrix(v);
				v.normalize();
			
			//Finding out threshold has been achieved
				if((v.minus(previous)).norm() < epslon) {
					done = true;
				}
				
			}catch (Exception e) {
				System.out.println(e);;
			}
			
		}
		
		//Bubble-sorting the PageRank
		for(int i = 0; i < v.getRowDimension(); i++){
	        for(int j = 0; j < v.getRowDimension()-1; j++){
	            if(v.getArray()[j][0] < v.getArray()[j + 1][0]){
	                double aux = v.getArray()[j][0];
	                String auxS = pageNames[j];
	                v.getArray()[j][0] = v.getArray()[j + 1][0];
	                pageNames[j] = pageNames[j + 1];
	                v.getArray()[j + 1][0] = aux;
	                pageNames[j + 1] = auxS;
	            }
	        }
	    }
		
		//Output
		System.out.println("\nPage Rank:");
		for(int i = 0; i < v.getRowDimension(); i++) {
			System.out.println(pageNames[i]+": "+v.getArray()[i][0]);
		}
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println("\nExececution time(nanoseconds): " + totalTime);
	}
}