package ie.gmit.dip;
import java.util.*;
import java.io.*;

// this class is designed to handle output 
// directory operations/configuration
// it has methods for setting user-defined directories,
// creating directories, and implementing a default
// directory should the user directory be invalid
public class OutputDirectory {
	
	// class variables, private, not static,
	// can specify different directories for different
	// instances
	private File defaultDir = new File("output");
	private File name;
	
	// constructor
	public OutputDirectory(){
		
	}
	
	// constructor with directory specified as a String
	public OutputDirectory(String s){
		this.setDir(s);
	}
	
	// public method set the directory
	public void setDir(String s){

		System.out.println("-Setting output directory to: [" + s + "]");
		if(s.isEmpty()){
			System.out.println("\t-No user defined output directory");
			this.setDefaultOutputDirectory();
		}
		else{
			this.name = new File(s);
		}
	}

	// sets a default directory
	public void setDefaultOutputDirectory(){
		System.out.println("-Setting default output directory to: [" + this.defaultDir + "]");
		this.name = this.defaultDir;
	}
	
	// get the name of the directory
	public File getDir(){
		return this.name;
	}
	
	// method which trys to make a directory
	public void createDir(){
		
		System.out.println("-Creating output directory");
		
		// if directory already exists, confirm and do nothing
		if (this.name.exists() && this.name.isDirectory()) {
			System.out.println("\t-Output dir: [" + this.name + "] already exists");
		}
		// else, try to make the directory
		else{

			// try to create the directory, or a default directory
			try{
				
				System.out.println("\t-Creating output directory: [" + this.name + "]");
				
				// if directory can be made successfully
				if(this.name.mkdirs()){
					System.out.println("\t\t-[success] output directory created");
				}
				// else set a default directory and recursively try to create
				// the default directory
				else{
					System.out.println("\t\t-[error] could not create output directory");
					this.setDefaultOutputDirectory();
					this.createDir();
				}
			}
			
			// catch an exception if directory cannot be created
			catch(Exception e){
				System.out.println("\t-[error] creating output directory: [" + this.name + "]");
				System.exit(0);
			}

		}	
	}
	
	

}