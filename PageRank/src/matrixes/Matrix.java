package matrixes;

public class Matrix {
	private int n;
	private int m;
	private double [][] array;

	
	//Creates an n-by-m matrix given a certain array
	public Matrix(int n, int m, double[][] array) {
		this.n = n;
		this.m = m;
		this.array = array;
	}
	
	//Creates an n-by-m matrix of zeros
	public Matrix(int n, int m) {
		this.n = n;
		this.m = m;
		this.array = new double[n][m];
	}
	
	//Creates an n-by-m matrix filled with a scalar h
	public Matrix(int n, int m, double h) {
		this.n = n;
		this.m = m;
		
		double[][] arrayEqual = new double [n][m];
		
		for(int i = 0; i < n;i++) {
			for(int j = 0; j < m; j++) {
				arrayEqual[i][j] = h;
			}
		}
		
		this.array = arrayEqual;
	}
	

	public int getRowDimension() {
		return this.n;
	}

	public int getColumnDimension() {
		return this.m;
	}

	public double[][] getArray() {
		return this.array;
	}

	public void setArray(double [][] array) {
		this.array = array;
	}

	//Now we define a few matrixes operations
	
	//Sum
	public Matrix plus(Matrix B) throws InvalidOperationException {
		if(this.m == B.getColumnDimension() && this.n == B.getRowDimension()) {

			double[][] arrayC = new double[this.n][this.m];

			for(int i = 0; i < this.n; i++) {
				for(int j = 0; j< this.m; j++) {
					arrayC[i][j] = this.array[i][j] + B.getArray()[i][j];
				}
			}

			Matrix C = new Matrix(this.n, this.m, arrayC);

			return C;

		}else {
			throw new InvalidOperationException();
		}
	}

	//Subtraction
	public Matrix minus(Matrix B) throws InvalidOperationException {
		if(this.m == B.getColumnDimension() && this.n == B.getRowDimension()) {

			double[][] arrayC = new double[this.n][this.m];

			for(int i = 0; i < this.n; i++) {
				for(int j = 0; j< this.m; j++) {
					arrayC[i][j] = this.array[i][j] - B.getArray()[i][j];
				}
			}

			Matrix C = new Matrix(this.n, this.m, arrayC);

			return C;

		}else {
			throw new InvalidOperationException();
		}
	}

	//Multiplication by constant
	public Matrix timesConstant(double k) {

		double[][] arrayC = new double[this.n][this.m];

		for(int i = 0; i < this.n; i++) {
			for(int j = 0; j< this.m; j++) {
				arrayC[i][j] = k*this.array[i][j];
			}
		}

		Matrix C = new Matrix(this.n, this.m, arrayC);

		return C;
	}

	//Matrix multiplication
	public Matrix timesMatrix(Matrix B) throws InvalidOperationException {

		if(this.m == B.getRowDimension()) {
			double[][] arrayC = new double[this.n][B.getColumnDimension()];

			for(int i = 0; i < this.n; i++) {
				for(int j = 0; j< this.m; j++) {

					double sumCk = 0;

					for(int k = 0; k <this.m; k++) {
						sumCk = sumCk + this.array[i][k]*B.getArray()[k][j];
					}

					arrayC[i][j] = sumCk;
				}
			}

			Matrix C = new Matrix(this.n, this.m, arrayC);

			return C;

		} else {
			throw new InvalidOperationException();
		}
	}
	
	//Other useful functions
	
	//Takes the vector norm
	public double module() throws InvalidOperationException {
		if (this.m == 1) {
			double sum = 0;
			for (int i = 0; i < this.n; i++) {
				sum += Math.pow(array[i][1], 2);
			}
			return Math.sqrt(sum);
		}else {
			throw new InvalidOperationException();
		}
	}
	
	//Normalizes the vector
	public void normalize() throws InvalidOperationException {
		for (int i = 0; i < this.n; i++) {
			array[i][1] /= this.module();
		}
	}
}
