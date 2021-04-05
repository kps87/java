package ie.gmit.dip;
import java.util.*;
import java.util.regex.Pattern;

// This class is designed to turn the command line
// ARGS into options which allow the user to specify
// input/output filenames, directories and encryption options
public class CommandLineInput {
	
	// instance variables
	private String[] args;
	private Map<String, String> options = new HashMap<String, String>();
	
	// constructor 
	public CommandLineInput(){
		
	}
	
	// constructor with command line ARGS directly provided
	public CommandLineInput(String[] args){
		this.setArgs(args);
	}
	
	// set the instance command line arguments array
	public void setArgs(String[] args){
		this.args = args;
	}
	
	// configure the options
	// this method could be moved outside of this class,
	// so that users could configure any amount/type of options
	// but this is the best place for it in the current project.
	public void configure(){
		
		// print to screen and set options individually based
		// on required switches.
		System.out.println("-Parsing and setting command line arguments");
		this.options.put("inputFile",               this.getOptionFromArgs("i"));
		this.options.put("keyword",                 this.getOptionFromArgs("kw"));
		this.options.put("outputDirectory",         this.getOptionFromArgs("o"));
		this.options.put("polybiusSquareInputFile", this.getOptionFromArgs("psf"));
		this.options.put("task",                    this.getOptionFromArgs("task"));
		this.printOptionsToScreen();
		
	}
	
	// return the map/dictionary of all keywords/options
	public Map<String, String> getOptionsMap(){
		return this.options;
	}
	
	// get a specific keyword from the options map/dictionary
	public String getOption(String option){
		return this.options.get(option);
	}

	// generic method to get an option
	// where option is defined by a -switch=option
	public String getOptionFromArgs(String cliSwitch){
	
		// pattern to search for
		String option  = new String();
		cliSwitch      = cliSwitch.toLowerCase();
		String pattern = "^-" + cliSwitch + "=";
	
		// loop through the args and attempt
		// to match the requested command line switch
		for (int i = 0; i < this.args.length; i++){
			if(Pattern.compile(pattern).matcher(args[i]).find()){
				String[] split = args[i].split("=", 2);
				return split[1].trim();
			}
		}
		
		return option;
	
	}
	
	// prints the map of key value pairs to screen
	// so that the user can see how the current run
	// has been configured
	public void printOptionsToScreen(){
		
		// loop through key-value pairs
		for (Map.Entry<String, String> entry : this.options.entrySet()) {
			if(entry.getValue().isEmpty())
			{
				System.out.printf(
				"\t-%-30s= %-1s %n", 
				entry.getKey(), 
				"[warning] option not set, will [try] to use a default option"
				);
			}
			else
			{
				System.out.printf(
				"\t-%-30s= %-1s %n", 
				entry.getKey(), 
				entry.getValue()
				);			
			}
		}
	}
	
	
	

}
