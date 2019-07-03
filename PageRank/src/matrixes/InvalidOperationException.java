package matrixes;

public class InvalidOperationException extends Exception{

	//Some operations within the world of matrices are prohibited, this exception will show up in these cases
	public InvalidOperationException() {
		super("Invalid operation.");
	}
}
