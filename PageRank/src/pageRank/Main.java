package pageRank;
import matrixes.*;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
		Scanner in = new Scanner(System.in);
		
		double epslon = Math.pow(10, -4);
		double dampingFactor = 0.85;
		
		//Link matrix input
		double array[][] = new double[5][5];
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				array[i][j] = 1;
				//We will not consider loop links
				if(i == j) {
					array[i][j] = 0;
				}
			}
		}
		
		Matrix A = new Matrix(5, 5, array);
		
		//We will "apply weights" to the matrix, based on page's outgoing links
		for(int j = 0; j < A.getColumnDimension(); j++) {
			int sum = 0;
			for(int i = 0; i < A.getRowDimension(); i++) {
				sum += A.getArray()[i][j];
			}
			A.column_by_constat(j, Math.pow(sum, -1));
		}
		
		Matrix B = new Matrix(5, 5, 0.2);
		
		Matrix C = new Matrix(5,5);
		
		try {
			C = C.plus(B.timesConstant(dampingFactor)).plus(A.timesConstant(1-dampingFactor));
		} catch (Exception e) {
			System.out.println(e);;
		}
		
		Matrix v = new Matrix(5,1, 0.2);
		
		boolean stop = false;
		

		while (!stop) {
			Matrix previous = v;
			
			try {
				v = C.timesMatrix(v);
				v.normalize();
			
				if((v.minus(previous)).norm() < epslon) {
					stop = true;
				}
				
			}catch (Exception e) {
				System.out.println(e);;
			}
			
		}
		
		long endTime   = System.nanoTime();
		long totalTime = endTime - startTime;
		System.out.println(totalTime/100);
		
	}
}
