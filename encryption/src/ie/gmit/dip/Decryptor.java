package ie.gmit.dip;
import java.io.*;

// this class is designed to handle
// all tasks associated with file decryption
// and reverses the operations of the encryption class
// the two classes are therefore dependent in some ways.
public class Decryptor {
	
	// declare instance variables
	private char[] plainText;
	private char[] cipherText;
	private char[] encryptedText;
	
	// constructor for menu object
	public Decryptor(){
		
	}
	
	// method to set the plainText after decryption
	public void setPlainText(char[] c){
		this.plainText = c;
	}
	
	// method to set the cipherText after columnar transposition
	// is undone
	public void setCipherText(char[] c){
		this.cipherText = c;
	}
	
	// method to set the encrypted text
	// which has been read in
	public void setEncryptedText(char[] c){
		this.encryptedText = c;
	}
	
	// public method to decrypt a batch of files
	public void runDecryptor(PolybiusSquare ps, Keyword kw, File inputFile, File outputFile){
		
		// print a header
		System.out.println("-Decrypting file: [" + inputFile + "]");
		System.out.println("-Output file    : [" + outputFile + "]");		
		
		if(kw.isInitialized && ps.dimensionIsInitialized && inputFile.isFile() && inputFile.exists()){
			
			// parse in the file and convert to upper case
			// and use it to set the plainText to be encrypted
			boolean toUpperCase = true;
			ParseFile parseFile = new ParseFile();
			this.setEncryptedText(parseFile.toCharArray(inputFile, toUpperCase));

			// decryption steps
			//	-convert the encrypted text to a matrix
			//	-undo the columnar transposition
			//	-decrypt the matrix and set the plainText
			CharacterMatrix matrix          = this.convertEncryptedTextToMatrix(kw.getKeyword());
			CharacterMatrix tranposedMatrix = matrix.transposeColumns(kw.getSortedToUnsortedMap());
			this.setPlainText(this.decryptMatrix(tranposedMatrix, ps));
			
			// print the output to a file
			PrintFile printFile = new PrintFile();
			printFile.fromCharArray(this.plainText, outputFile);
	
		}
		else{
			
			// print a specific error, only the first error is printed
			System.out.println("\t-[error] cannot run decryption");
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
	
	// private method that converts a character array
	// to a matrix given the character array and keyword as input
	private CharacterMatrix convertEncryptedTextToMatrix(String keyword){
		
		System.out.println("\t-convertEncryptedTextToMatrix");
		CharacterMatrix encipheredMatrix = new CharacterMatrix();
		
		// set the matrix dimensions
		try{
			
			// print a title/section to screen
			System.out.println("\t\t-setting Matrix dimensions");
			System.out.println("\t\t\t-encryptedText length = " + this.encryptedText.length);
			System.out.println("\t\t\t-keyword       length = " + keyword.toCharArray().length);
			
			// get the row dimension
			// divide the cipher text length by the keyword length
			// if there is a remainder, then an extra row is required
			// to store the entire cipherText in the keyword matrix
			int rowDimension = 0;
			if(this.encryptedText.length % keyword.toCharArray().length > 0){
				float a = (float) this.encryptedText.length;
				float b = (float) keyword.toCharArray().length;
				rowDimension = (int) Math.ceil(a/b);
			}
			else{
				rowDimension = this.encryptedText.length/keyword.toCharArray().length;	
			}
			encipheredMatrix.setColumnDimension(keyword.toCharArray().length);
			encipheredMatrix.setRowDimension(rowDimension);
			encipheredMatrix.setMatrixDimensions();
			
			// print some diagnostics to screen
			System.out.println("\t\t\t-column dimension  = " + encipheredMatrix.getColumnDimension());
			System.out.println("\t\t\t-row    dimension  = " + encipheredMatrix.getRowDimension());
			System.out.println("\t\t\t-matrix cells      = " + encipheredMatrix.getRowDimension()*encipheredMatrix.getColumnDimension());
		

		}
		catch(Exception e){
			System.out.println("\t\t\t-[error] setting matrix dimensions");
			e.printStackTrace();
			System.exit(0);
		}
		
		// if the encipheredMatrix could be constructed:
		// loop over the matrix columns, then rows,
		// assigning an index based on a cumulative sum over
		// rows and columns
		// if the encrypted text character exists
		// for that index, then set the matrix value at row i, column j
		int index = 0;
		for (int j = 0; j < encipheredMatrix.getColumnDimension(); j++){
			for (int i = 0; i < encipheredMatrix.getRowDimension(); i++){
				if(index < encryptedText.length){
					encipheredMatrix.setMatrixValue(i, j, encryptedText[index]);
				}
				index++;
			}
		}	
		
		return encipheredMatrix;		
		
		
	}
	
	// private method which converts the pairs of letters to their original
	// characters via the polybius square
	private char[] decryptMatrix(CharacterMatrix cm, PolybiusSquare ps){
		
		System.out.println("\t-decryptMatrix");
		StringBuilder sb = new StringBuilder();
		
		// loop over the matrix rows
		for (int i = 0; i < cm.getRowDimension(); i+=1){
			
			// loop over the matrix columns two at a time
			for (int j = 0; j < cm.getColumnDimension(); j+=2){
				
				// for row i, get the two characters at indices j and j+1
				// this form a row-column pair in the polybius square
				// then get the indicies of the keys
				char psRowKey   = cm.getMatrixValue(i, j);
				char psColKey   = cm.getMatrixValue(i, j+1);
				int rowIndex      = ps.getIndexOfKey(psRowKey, ps.getRowKeys());
				int colIndex      = ps.getIndexOfKey(psColKey, ps.getColumnKeys());
				
				// if these indices are greater than 0
				// we can get the character that was originally encoded 
				// by plugging these indices back into the polybius square
				// via the getMatrixValue method
				if(rowIndex >= 0 && colIndex >= 0){
					sb.append(ps.getMatrixValue(rowIndex, colIndex));
				}
				// if one of the indices isn't defined or is less than 0
				// it means the character that was encoded
				// was not in the original polybius square
				// For cases where a character could not be encoded during encryption
				// The original character was duplicated and so we should append
				// this character here to get the original character back
				else{
					sb.append(psRowKey);
				}
			}
		}
		
		return sb.toString().toCharArray();

	}

}
