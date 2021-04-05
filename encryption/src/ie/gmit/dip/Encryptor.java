package ie.gmit.dip;
import java.io.*;

// this class is designed to handle
// all tasks associated with file encryption
// including the conversion of plainText to cipherText
// managing construction and transposition of matrices
// in tandem with the CharacterMatrix class
// and writing encrypted files
public class Encryptor {
	
	// declare global variables
	private char[] plainText;
	private char[] cipherText;
	private char[] encryptedText;

	// constructor for menu object
	public Encryptor(){

	}
	
	// method to set the plainText to be encrypted
	public void setPlainText(char[] c){
		this.plainText = c;
	}
	
	// method to set the cipherText post-encryption
	public void setCipherText(char[] c){
		this.cipherText = c;
	}
	
	// method to set the final encrypted charArray
	// which has been tranposed, and read-off column by column
	public void setEncryptedText(char[] c){
		this.encryptedText = c;
	}
	
	
	// public method to encrypt a file
	// requires as input:
	// 	-a keyword object
	//	-a polybius square object
	//	-an input file name
	//	-an output file name (to be written to)
	// Foreach file it:
	//	-parses the file
	//	-encodes each character in the file
	//	-creates an encryption matrix based on the encoded character set
	//		and the polybius square keyword
	//	-it performs a columnar transposition of the resulting matrix
	//	-it converts the columns to a single character array
	//	-it prints the encrypted character array to a user requested file
	public void runEncryptor(PolybiusSquare ps, Keyword kw, File inputFile, File outputFile){
		
		// print a header
		System.out.println("-Encrypting file: [" + inputFile + "]");
		System.out.println("-Output file    : [" + outputFile + "]");
		
		if(kw.isInitialized && ps.dimensionIsInitialized && inputFile.isFile() && inputFile.exists()){
			
			// parse in the file and convert to upper case
			// and use it to set the plainText to be encrypted
			boolean toUpperCase = true;
			ParseFile parseFile = new ParseFile();
			this.setPlainText(parseFile.toCharArray(inputFile, toUpperCase));
			
			// encryption steps
			//	-convert the plain text using the Polybius Square
			//	-construct a square matrix based on the keyword,
			//		and the cipherText
			//	-tranpose the enciphered matrix based on
			//		an alphabetic sort of the keyword
			//	-take the tranposed matrix, and convert it to a character array
			//		and push the output to a character array
			//	-then print the file
			this.setCipherText(this.encryptPlainText(ps));
			CharacterMatrix encipheredMatrix = this.buildEncipheredMatrixFromKeyword(kw.getKeyword());
			CharacterMatrix transposedMatrix = encipheredMatrix.transposeColumns(kw.getUnsortedToSortedMap());
			this.setEncryptedText(this.convertMatrixColumnsToCharArray(transposedMatrix));
			
			// print the output to a file
			PrintFile printFile = new PrintFile();
			printFile.fromCharArray(this.encryptedText, outputFile);
		
		}
		else{
			
			// print reasons for errors
			System.out.println("\t-[error] cannot run encryption");
			if(!kw.isInitialized){
				System.out.println("\t-[error] keyword is not initialized");
			}
			else if (!ps.dimensionIsInitialized){
				System.out.println("\t-[error] polybius square is not initialized");				
			}
			else if (!inputFile.isFile() || !inputFile.exists()){
				System.out.println("\t-[error] file to encrypt + [" + inputFile + "] is not a file or cannot be found");
			}
			else{
				System.out.println("\t-[error] unknown");
			}
		}
	
	}
	
	// encode each character in the character array
	// based on the polybius square and return a char array
	private char[] encryptPlainText(PolybiusSquare ps){
		
		// for every character in the charArray
		// search the polybius square, for the character
		// if found, append it to the String Builder
		System.out.println("\t-encryptPlainText");
		System.out.println("\t\t-plainText length = " + this.plainText.length);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < this.plainText.length; i++){
			
			// search the matrix and get the
			// row and column of the polybius square if it matches
			// the plainText character
			boolean foundChar = false;
			for (int j = 0; j < ps.getRowDimension(); j++){
				for (int k = 0; k < ps.getColumnDimension(); k++){
					
					if(ps.getMatrixValue(j,k) == this.plainText[i]){
						
						// append the row-column value pair to the string builder
						sb.append(ps.getRowKeys()[j]);
						sb.append(ps.getColumnKeys()[k]);

						// if the character was found,
						foundChar = true;
					}
					
					// terminate the loop
					if (foundChar){
						break;
					}
				}
				// terminate the loop
				if (foundChar){
					break;
				}
			}
			
			// if character was not found in the Matrix
			// append the unencrypted text twice.
			if(!foundChar){
				sb.append(this.plainText[i]);
				sb.append(this.plainText[i]);				
			}
		}	


		return sb.toString().toCharArray();

	}
	
	// builds the enciphered matrix from a keyword
	// and the enciphered text
	private CharacterMatrix buildEncipheredMatrixFromKeyword(String keyword){
		
		// create a new matrix object
		System.out.println("\t-buildEncipheredMatrixFromKeyword");
		CharacterMatrix encipheredMatrix = new CharacterMatrix();
		
		// try to set the matrix dimensions
		try{
			
			// print a header to screen
			System.out.println("\t\t-setting Matrix dimensions");
			System.out.println("\t\t\t-cipherText length = " + this.cipherText.length);
			System.out.println("\t\t\t-keyword    length = " + keyword.toCharArray().length);
			
			// get the row dimension
			// divide the cipher text length by the keyword length
			// if there is a remainder, then an extra row is required
			// to store the entire cipherText in the matrix
			int rowDimension = 0;
			if(this.cipherText.length % keyword.toCharArray().length > 0){
				float a = (float) this.cipherText.length;
				float b = (float) keyword.toCharArray().length;
				rowDimension = (int) Math.ceil(a/b);
			}
			else{
				rowDimension = this.cipherText.length/keyword.toCharArray().length;	
			}
			encipheredMatrix.setColumnDimension(keyword.toCharArray().length);
			encipheredMatrix.setRowDimension(rowDimension);
			encipheredMatrix.setMatrixDimensions();
			
			// print some diagnostics to screen
			System.out.println("\t\t\t-column dimension  = " + encipheredMatrix.getColumnDimension());
			System.out.println("\t\t\t-row    dimension  = " + encipheredMatrix.getRowDimension());
			System.out.println("\t\t\t-matrix cells      = " + encipheredMatrix.getRowDimension()*encipheredMatrix.getColumnDimension());
		
			// populate the encipheredMatrix with the appropriate values
			// only do this while the index is less the the length of
			// the cipherText chararray
			int cipherIndex = 0;
			for (int i = 0; i < encipheredMatrix.getRowDimension(); i++){
				for (int j = 0; j < encipheredMatrix.getColumnDimension(); j++){
					if(cipherIndex < this.cipherText.length){
						encipheredMatrix.setMatrixValue(i,j,this.cipherText[cipherIndex]);
					}
					cipherIndex++;
				}
			}
		}
		catch(Exception e){
			System.out.println("\t\t\t-Error setting matrix dimensions");
			e.printStackTrace();
			System.exit(0);
		}
		
		return encipheredMatrix;
		
	}

	// this method turns the columns of a matrix into a character
	// which is the last part of encryption before file printing
	public char[] convertMatrixColumnsToCharArray(CharacterMatrix charMatrix){
		
		System.out.println("\t-convertMatrixColumnsToCharArray");
		
		// foreach column->foreach row, append char to a string
		// builder, matrix is a square matrix
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < charMatrix.getColumnDimension(); j++){
			for (int i = 0; i < charMatrix.getRowDimension(); i++){
				sb.append(charMatrix.getMatrixValue(i,j));
			}
		}
		return sb.toString().toCharArray();
	}

	
	



}
