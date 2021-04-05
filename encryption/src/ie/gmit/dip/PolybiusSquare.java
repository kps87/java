package ie.gmit.dip;
import java.io.*;
import java.util.regex.Pattern;

// this class is designed specifically to handle
// all setters, getters and methods associated with the Polybius Square
// the Polybius Square consists of a vector of keys for the 
// rows and columns, and an n*n matrix of characters
// this class has been written to extend a generic
// CharacterMatrix class, with the CharacterMatrix
// handling all generic matrix state/behaviour/
public class PolybiusSquare extends CharacterMatrix {
	
	// these fields are unique to the Polybius Square class
	// they are not generated for a matrix which can simply be accessed
	// by integer indices for each row and column
	private char[] rowKeys;
	private char[] columnKeys;
	
	// constructor for PolybiusSquare object
	public PolybiusSquare(){
	}
	
	// method which initializes the 
	// Polybius Square directly from a file
	public void initializeFromFile(File f){

		// read the data from the input file
		System.out.println("-Initializing polybius square from file: [" + f + "]");
		ParseFile parseFile = new ParseFile();
		String[] sarray     = parseFile.toStringArray(f);
		
		// if file contains some data,
		// try to initialize keys and matrix
		if(sarray.length > 0){
			
			// get the keys
			String[] rowKeys    = this.getKeysFromParsedFile("matrix_row_keys", sarray);
			String[] columnKeys = this.getKeysFromParsedFile("matrix_column_keys", sarray);
			
			// if the keys are valid,
			// set them, initialize the matrix dimensions
			// and try to parse the matrix out of the file
			if(keysAreValid(rowKeys, columnKeys)){

                this.setColumnKeys(columnKeys);
                this.setRowKeys(rowKeys);
				this.setMatrixDimensions();

				System.out.println("\t-user provided polybius square row/column keys are valid");
				System.out.println("\t-polybius Square has dimesions of " + this.getRowDimension() + "x" + this.getColumnDimension());
				
				// try to parse the square matrix
				// from the input file
				boolean buildSuccess = this.buildMatrixFromParsedFile(sarray);
				if(!buildSuccess){
					System.out.println("\t-[error] initializing polybius square from file");
					this.initializeDefault();					
				}
			}
			else{
				System.out.println("\t-[error] user provided polybius square row/column keys are not valid");
				this.initializeDefault();
			}
			
		}
		else{
			System.out.println("\t-[error] parsing file: [" + f + "] file is empty");
			this.initializeDefault();
		}
	}
	
	// default method for setting the polybius square in the event
	// that parsing it from a file fails
	public void initializeDefault(){
		
		System.out.println("-Initializing default polybius square");
		
		// default keys and matrix
		String[] keys   = new String[]{"A", "D", "F", "G", "V", "X"};
		char[][] matrix ={
							{'P', 'H', '0', 'Q', 'G', '6'},
							{'4', 'M', 'E', 'A', '1', 'Y'},
							{'L', '2', 'N', 'O', 'F', 'D'},
							{'X', 'K', 'R', '3', 'C', 'V'},
							{'S', '5', 'Z', 'W', '7', 'B'},
							{'J', '9', 'U', 'T', 'I', '8'},
						};
		
		// setters
		this.setColumnKeys(keys);	
		this.setRowKeys(keys);
		this.setMatrixDimensions();
		this.setMatrix(matrix);
		System.out.println("\t-[success] polybius square initialized");
		
		
	}
	
	// public method to set the column keys
	public void setColumnKeys(String[] s){
		if(this.getColumnDimension() == 0){
			this.setColumnDimension(s.length);
		}
		this.columnKeys   = new char[this.getColumnDimension()];	
		for (int i = 0; i < s.length; i++){
			this.columnKeys[i] = s[i].charAt(0);	
		}		
	}
	
	// public method to set the row keys
	public void setRowKeys(String[] s){
		if(this.getRowDimension() == 0){
			this.setRowDimension(s.length);
		}
		this.rowKeys   = new char[this.getRowDimension()];	
		for (int i = 0; i < s.length; i++){
			this.rowKeys[i] = s[i].charAt(0);	
		}	
	}
	
	// make sure the matrix keys are valid
	private boolean keysAreValid(String[] rowKeys, String[] columnKeys){
		
		boolean areValid = false;
		if(rowKeys.length == columnKeys.length){
			areValid = true;
		}
		// can add other conditions here
		
		return areValid;
		
	}
	
	// method for getting the column keys
	public char[] getColumnKeys(){
		return this.columnKeys;
	}
	
	// method for getting the row keys
	public char[] getRowKeys(){
		return this.rowKeys;
	}
	
	// method which gets the index of a given key
	// *warning* if the same key appears twice
	// only the first instance will ever be returned
	public int getIndexOfKey(char c, char[] charArray){
		int index = -1;
		for (int i = 0; i < charArray.length; i++){
			if(charArray[i] == c){
				index = i;
				return index;
			}
		}
		return index;
	}
	
	// public method to print the square to the screen
	public void printPolybiusSquareToScreen(){

		System.out.println("-Printing Polybius Square to Screen");

		if(this.dimensionIsInitialized){
			
			// print the combined rows/columns matrix
			System.out.print("\t");
			System.out.print("\t|   | ");
			
			for (int i = 0; i < this.getRowDimension(); i++){
				System.out.print(this.rowKeys[i] + " | ");
			}		
			
			System.out.println();
			System.out.print("\t");
			
			for (int i = 0; i < this.getColumnDimension(); i++){
				
				System.out.print("\t| " + this.columnKeys[i] + " | ");
				for(int j = 0; j < this.getMatrixValue()[i].length; j++){
					System.out.print(this.getMatrixValue()[i][j] + " | ");
				}
				
				System.out.println();
				System.out.print("\t");
			}
			
		}
		else{
			System.out.println("\t-[error] cannot print Polybius Square to screen");
		}
	}
	
	// method which gets the row keys from a file
	private String[] getKeysFromParsedFile(String pattern, String[] stringArray){
		
		String[] parsedStringArray = new String[0];
		
		// loop through the input String array
		for (int i = 0; i < stringArray.length; i++){

			// set the line and get the matrix keys if pattern is matched
			String line = stringArray[i];
			if (line.startsWith(pattern)) {
				
				// split the line, and trim whitespace
				System.out.println("\t-found " + pattern);
				String[] splitMatchedLine = line.split(":", 2);
				for (int j = 0; j < splitMatchedLine.length; j++){
					splitMatchedLine[j] = splitMatchedLine[j].trim();
				}
				
				// split the second element of the array
				parsedStringArray = splitMatchedLine[1].split("\\s+");
				for(int j = 0; j < parsedStringArray.length; j++){
					parsedStringArray[j] = parsedStringArray[j].trim().toUpperCase();
				}
				// return the StringArray if pattern is matched		
				return parsedStringArray;
			}
		}

		// return the parsedStringArray
		return parsedStringArray;
	}
	
	// get the polybius matrix from file
	private boolean buildMatrixFromParsedFile(String[] stringArray){
		
		System.out.println("\t-attempting to build polybius square from input file contents...");
	
		boolean buildSuccess = true;
		int matrixRowIndex = 0;
		
		// loop through the input String array
		for (int i = 0; i < stringArray.length; i++){
    
			// set the line and get the matrix keys if pattern is matched
			String line = stringArray[i];
			if (
				Pattern.compile("^[A-Z0-9]").matcher(line).find() 
				&& 
				!Pattern.compile("^matrix").matcher(line).find()
				&& 
				this.dimensionIsInitialized
				) 
			{
				
				// split the line, and trim whitespace
				line = line.trim();
				String[] splitMatchedLine = line.split("\\s+");			
				int matrixColIndex = 0;
				
				// loop over the split line and add values to matrix
				while(matrixColIndex < splitMatchedLine.length){
					
					// print an error/warning if row or column dimension are out of bounds
					if(matrixRowIndex > this.getRowDimension()-1){
						System.out.println("\t-[error] polybius square row dimensions greater than allowed");
						System.out.println("\t\t-polybius square should have a maximum of " + this.getRowDimension() + " entries");
						System.out.println("\t\t-line         = " + line);
						System.out.println("\t\t-row index    = " + (matrixRowIndex+1));
						buildSuccess = false;
						break;
					}
					else if(matrixColIndex > this.getColumnDimension()-1){
						System.out.println("\t-[error] polybius square column dimensions are greater than allowed");
						System.out.println("\t\t-polybius square should have a maximum of " + this.getColumnDimension() + " entries");
						System.out.println("\t\t-line         = " + line);
						System.out.println("\t\t-row index    = " + (matrixRowIndex+1));
						System.out.println("\t\t-column index = " + (matrixColIndex+1));
						System.out.println("\t\t\t-character  = " + splitMatchedLine[matrixColIndex]);
						break;
					}
					// else add these to the matrix
					else{
						this.setMatrixValue(matrixRowIndex, matrixColIndex, splitMatchedLine[matrixColIndex].charAt(0));
					}
					matrixColIndex++;
				}
				
				// if col index is less than the matrix column dimension
				// then print an error and set the return value to false
				if(!(matrixRowIndex > this.getRowDimension()-1) && matrixColIndex < this.getColumnDimension()){
					System.out.println("\t-[error] polybius square column dimension less than allowed");
					System.out.println("\t\t-line: " + line + " has only " + (matrixColIndex) + " entries");		
					System.out.println("\t\t-should have " + this.getColumnDimension() + " entries");
					buildSuccess = false;					
				}
				
				// increment the row index
				matrixRowIndex++;
			}
		}
		
		// if row index is less than the matrix row dimension
		// then print an error and set the return value to false
		if(matrixRowIndex < this.getRowDimension()){
			System.out.println("\t-[error] polybius square row dimension less than allowed");
			System.out.println("\t\t-polybius square should have " + this.getRowDimension() + " entries");
			System.out.println("\t\t-polybius square only has has " + (matrixRowIndex) + " entries");
			buildSuccess = false;					
		}		
		if(buildSuccess){
			System.out.println("\t-[success] initialized polybius square with dimensions of " + this.getRowDimension() + "x" + this.getColumnDimension());
		}
    
		// return whether the build was succesful
		return buildSuccess;
	
	}
	

}
