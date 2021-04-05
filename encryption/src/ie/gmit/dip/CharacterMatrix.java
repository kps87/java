package ie.gmit.dip;

// this class is designed specifically to handle
// all setters/getters and methods associated 
// with n*m matrices OF CHARACTERS
// there are several matrix elements to this project
// and so a generic class is wortwhile, with a seperate
// class for PolybiusSquares which extend this base class
public class CharacterMatrix {
	
	// declare global and instance variables
	private int rowDimension;
	private int columnDimension;
	private char[][] matrix;
	public boolean dimensionIsInitialized = false;
	
	// constructor for matrix object
	public CharacterMatrix(){
		
	}
	
	// overloaded constructor for matrix object
	// when dimensions are known in advance
	public CharacterMatrix(int i, int j){
		
		this.setRowDimension(i);
		this.setColumnDimension(j);
		this.setMatrixDimensions();
		
	}
	
	// public method to set the row dimension
	public void setRowDimension(int i){
		this.rowDimension = i;
	}
	
	// public method to set the column dimension
	public void setColumnDimension(int i){
		this.columnDimension = i;
	}
	
	// public method to set the matrix dimensions
	public void setMatrixDimensions(){
		this.matrix = new char[this.rowDimension][this.columnDimension];
		this.dimensionIsInitialized = true;
	}
	
	// public method to set the values of a
	// matrix given the full matrix as a char array
	public void setMatrix(char[][] matrix){
		this.matrix = matrix;
		this.dimensionIsInitialized = true;
	}
	
	// public method to set the values of a
	// given row index/column index with String value = s
	public void setMatrixValue(int irow, int icol, char c){
		this.matrix[irow][icol] = c;
	}
	
	// getters for row dimensions
	public int getRowDimension(){
		return this.rowDimension;
	}
	
	// getters for  column dimensions
	public int getColumnDimension(){
		return this.columnDimension;
	}

	// method which returns the entire character matrix 
	// if no irow or icol are provided
	public char[][] getMatrixValue(){
		return this.matrix;
	}

	// overloaded method for getting a given matrix
	// value for a given row/column
	public char getMatrixValue(int irow, int icol){
		return this.matrix[irow][icol];
	}
	
	
	// public method for print matrices to screen
	// comes with a max row variable to stop
	// printing at a certain level
	public void printMatrixToScreen(int maxRowIndex){
		
		// make sure maxRowIndex is in bounds
		System.out.println("\t-printMatrixToScreen");
		if(maxRowIndex > this.rowDimension){
			maxRowIndex = this.rowDimension;
		}
		else if(maxRowIndex < 0){
			maxRowIndex = 20;
		}
		
		// do the printing up to the specified max row index
		for(int i = 0; i < maxRowIndex; i++){
			System.out.print("\t\t row = " + (i) + " = ");
			for (int j = 0; j < this.columnDimension; j++){
				System.out.print(this.getMatrixValue(i,j) + " ");
			}
			System.out.print("\n");
		}	
		
	}
		
	// public method which performs a columnar transposition
	// given a matrix and a set of indices to sort the columns
	// does not perform transposition directly on matrix
	// instead it returns a copy of the original matrix
	public CharacterMatrix transposeColumns(int[] tranpositionArray){
		
		// create a matrix of same dimension as original matrix
		System.out.println("\t-transposeColumns");
		CharacterMatrix transposedMatrix = new CharacterMatrix(this.rowDimension, this.columnDimension);
		
		for(int i = 0; i < this.rowDimension; i++){
			for (int j = 0; j < this.columnDimension; j++){
				transposedMatrix.setMatrixValue(i, tranpositionArray[j], this.getMatrixValue(i, j));
			}
		}
		
		return transposedMatrix;
		
	}
	

	
}
