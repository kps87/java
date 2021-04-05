package ie.gmit.dip;
import java.io.*;

// this class is designed to print output files
public class PrintFile {
	
	// no class/instance variables
	
	// constructor
	public PrintFile(){
		
	}
	
	// print a file from a character array
	// could use overloaded methods with the same
	// name but different parameter types but this is
	// explicit enough for the current project.
	public void fromCharArray(char[] charArray, File f){
	
		System.out.println("\t-printing file [" + f +"]" + " from character array");
		
		// try to write the file,
		// ensuring to flush any buffered lines and close the file
		try{
			
			FileWriter fw = new FileWriter(f);
			for(int i = 0; i < charArray.length; i++){
				fw.write(charArray[i]);
			}			
			fw.flush();
			fw.close();
			
		}
		// if an exception occurs let the user know
		// and print a stack trace
		// this error is not "fatal" for the current project
		// as file-writing is the terminal operation of
		// encryption/decryption
		catch(Exception e){
			System.out.println("[error] could not print file [" + f + "]");
			e.printStackTrace();
		}
	}
	
}
