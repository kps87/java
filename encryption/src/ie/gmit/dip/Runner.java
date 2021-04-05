package ie.gmit.dip;
import java.util.*;
import java.io.*;

// this class is designed execute the encryption/decryption 
// process.
//	The main sequence of execution is:
//		-- create a CommandLineInput (CLI) object, 
//		-- configure the CLI arguments
//		-- ensure the requested task and file are valid
//		-- create a Keyword object and initialize it from (a) user input or (b) with a default value
//		-- create a PolybiusSquare object and initialize from (a) user input or (b) with a default value
//		-- create an OutputDirectory object and initialize from (a) user input or (b) with a default value
//		-- if the task=encrypt, then create an Encryptor instance, and run the encryption process
//		-- if the task=decrypt, then create a  Decryptor instance, and run the decryption process
	
public class Runner {
	
	
	// verifies that the user has requested a valid encryption or decryption task
	private static void taskIsValid(String task){
		
		// get and initialize the task -- encryption or decryption
		boolean isValid = false;
		if(task.isEmpty()){
			isValid = false;
		}
		else if(task.equals("encrypt") || task.equals("decrypt")){
			isValid = true;
		}
		if(!isValid){
			System.out.println("-[fatal error] user must specify either an encryption or decryption task");
			System.out.println("-current task = [" + task +"]");
			System.out.println("\t-e.g. java ie.gmit.dip.Runner task=encrypt");
			System.out.println("\t-e.g. java ie.gmit.dip.Runner task=decrypt");
			System.exit(0);			
		}
	}
	
	// verifies that the user has provided a valid input file
	private static void fileIsValid(File f){
		
		// get and initialize the task -- encryption or decryption
		boolean isValid = false;
		if (f.isFile() && f.exists()){
			isValid = true;
		}
		if(!isValid){
			System.out.println("-[fatal error] user must provide a valid input file");
			System.out.println("-current file = [" + f +"]");
			System.out.println("\t-e.g. java ie.gmit.dip.Runner -i=example.txt");
			System.exit(0);			
		}
	}	
	
	// main method - takes command line arguments as input
	public static void main(String[] args){
		
		// get and set the command line arguments using the CommandLineInput
		// class and use them to set some local variables
		CommandLineInput cmdInput = new CommandLineInput(args);
		cmdInput.configure();
		String task    = cmdInput.getOption("task");
		File inputFile = new File(cmdInput.getOption("inputFile"));

		// ensure that the requested task and requested file are valid
		taskIsValid(task);
		fileIsValid(inputFile);
		
		// initialize the keyword, 
		// if the keyword is not defined, a default keyword will be used
		Keyword kw = new Keyword();
		kw.initialize(cmdInput.getOption("keyword"));
		
		// initialize the polybius square object
		// if a user requested file is not provided
		// it will initialize a default square,
		// or else it will [attempt] to initialize from 
		// a user specified file, if initialization from file fails,
		// a default square will be initialized automatically
		PolybiusSquare ps = new PolybiusSquare();
		if(cmdInput.getOption("polybiusSquareInputFile").isEmpty()){
			ps.initializeDefault();
		}
		else{
			File psf = new File(cmdInput.getOption("polybiusSquareInputFile"));
			ps.initializeFromFile(psf);
		}
		
		// create and initialize the default output directory
		OutputDirectory outputDirectory = new OutputDirectory();
		if(cmdInput.getOption("outputDirectory").isEmpty()){
			outputDirectory.setDefaultOutputDirectory();
			outputDirectory.createDir();
		}
		else{
			outputDirectory.setDir(cmdInput.getOption("outputDirectory"));
			outputDirectory.createDir();
		}
		
		// run the encryptor
		if(task.equals("encrypt")){
			
			Encryptor enc       = new Encryptor();
			try{
				String inp      = inputFile.getName();
				inp             = inp.replaceAll("decrypted_", "");
				inp             = inp.replaceAll("encrypted_", "");
				File outputFile = new File(outputDirectory.getDir(), "encrypted_" + inp);
				enc.runEncryptor(ps, kw, inputFile, outputFile);
			}
			catch(Exception e){
				System.out.println("-[fatal error] could not set output filename for encryption");
				System.exit(0);
			}
			
			
		}
		// run the decryptor
		else if(task.equals("decrypt")){
			
			Decryptor dec = new Decryptor();
			try{
				
				String inp      = inputFile.getName();
				inp             = inp.replaceAll("decrypted_", "");
				inp             = inp.replaceAll("encrypted_", "");
				File outputFile = new File(outputDirectory.getDir(), "decrypted_" + inp);
				dec.runDecryptor(ps, kw, inputFile, outputFile);
			}
			catch(Exception e){
				System.out.println("-[fatal error] could not set output filename for decryption");
				System.exit(0);
			}			
			
		}
		
	}

}
