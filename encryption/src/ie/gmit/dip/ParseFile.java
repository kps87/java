package ie.gmit.dip;
import java.io.*;

// this class is designed to parse files
// to string and character arrays
public class ParseFile {
	
	// no class/instance variables
	
	// constructor
	public ParseFile(){
		
	}
	
	// reads a file to string array for later processing
	public String[] toStringArray(File f){
		
		String[] string = new String[0];
		
		// try to parse the file and append each file to a StringBuilder
		try{
			
			System.out.println("\t-attempting to parse file to a string array: [" + f + "]");
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = null;
			
			while((line = br.readLine()) != null){
				sb.append(line).append("\n");
			}		
			
			br.close(); // close the reader
			string = sb.toString().split("\\n");
			
		}
		// else catch the exception, warn the user and exit
		catch(Exception e){
			System.out.println("\t-[error] parsing file to a string array: [" + f + "]");
			System.out.println("\t-make sure file exists and that it is not empty");
			System.exit(0);
		}	
		
		return string;

	}
	
	// read a file to char array for later processing
	public char[] toCharArray(File f, boolean toUpperCase){
		
		// local variable
		StringBuilder sb  = new StringBuilder();

		// try to open the file append each line to the StringBuilder	
		try{
			System.out.println("\t-attempting to parse file to a character array: [" + f + "]");
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String line = null;
			while((line = br.readLine()) != null){
				sb.append(line).append("\n");
			}		
			
			br.close(); // close the reader
			
		}
		// else catch the exception, warn the user and exit
		catch(Exception e){
			System.out.println("\t-[error] parsing file to a character array: [" + f + "]");
			System.out.println("\t-make sure file exists and that it is not empty");
			System.exit(0);
		}		
		
		// trim the last character if it is just a new line
		char[] testCharArray = new char[sb.length()];
		testCharArray = sb.toString().toCharArray();
		if(testCharArray[testCharArray.length-1] == '\n'){
			sb.setLength(sb.length() - 1);
		}
		
		// convert to uppercase if user has requested that option
		char[] returnChar = new char[sb.length()];
		if(toUpperCase){
			returnChar = sb.toString().toUpperCase().toCharArray();
		}
		else{
			returnChar = sb.toString().toCharArray();
		}
		
		return returnChar;
		
	}
	
}
