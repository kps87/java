package ie.gmit.dip;
import java.util.Arrays; 
import java.io.*;

// this class is designed specifically to handle
// the state and behaviour related to the keyword
// this includes keyword setters, getters, 
// and alphabetic sorting of keyword so that matrices
// can be transposed in a deterministic way
public class Keyword {
	
	// declare instance variables
	private String  keyword;
	private String  sortedKeyword;
	private int[]   unsortedToSortedMap;
	private int[]   sortedToUnsortedMap;
	public  boolean isInitialized  = false;
	
	// constructor
	public Keyword(){
		
	}
	
	// initializer for keywords
	// will try to initialize with a user-specified String
	// if this is deemed to be invalid, a default keyword
	// is employed
	public void initialize(String kw){
		
		System.out.println("-Initializing keyword");
		
		// if the keyword does not pass tests
		// then use the default keyword
		if(!this.isValid(kw)){
			kw = getDefaultKeyword();
			System.out.println("\t-[warning] initializing with default keyword = " + kw);
		}
		
		// set the keyword
		this.setKeyword(kw);
		this.setSortedKeyword(this.sortKeyword());
		this.mapUnsortedAndSortedIndices();
		this.isInitialized = true;
		System.out.println("\t-[success] keyword initialized");	
		
	}
	
	// public method for testing if keyword is valid
	public boolean isValid(String kw){

		if(kw.isEmpty()){
			System.out.println("\t-[warning] keyword is not defined.");
			return false;
		}
		else{
			
			char[] charArray  = kw.toCharArray();
			
			// test if keyword has even number of elements
			if(charArray.length % 2 > 0){
				System.out.println("\t-[warning] keyword has uneven number of elements: " + kw);
				return false;
			}
			// can add more tests of "validity" here, for now, only the
			// above two tests are employed.
			else{
				return true;
			}
		}
	}
	
	// public method for setting the polyibuys square keyword
	public void setKeyword(String s){
		this.keyword       = s;
	}
	
	// public method to set the sorted keyword
	public void setSortedKeyword(String s){
		this.sortedKeyword = s;
	}

	// method which maps the keyword to the unsorted keyword and vice versa
	private void mapUnsortedAndSortedIndices(){
		
		// initialize local variables
		char[] charArray                  = this.keyword.toCharArray();
		char[] sortedCharArray            = this.sortedKeyword.toCharArray();
		this.unsortedToSortedMap          = new int[this.keyword.toCharArray().length];
		this.sortedToUnsortedMap          = new int[this.keyword.toCharArray().length];
		boolean[] unsortedIndexWasMapped  = new boolean[this.keyword.toCharArray().length];
		boolean[] sortedIndexWasMapped    = new boolean[this.keyword.toCharArray().length];
		
		// populate an array which maps old unsorted indices to new sorted indices
		// the true statements are required at the end for the 
		// case that the keyword contains duplicate characters
		// and one should not map all old instances of a character
		// to the first instance of that character in the sortedCharArray
		// a dictionary/collection/linked list may be a better way to
		// map a->b and b->a but this also works for various keywords
		// that have been tested including "JAVA" and "J1V1"
		for(int unsortedIndex = 0; unsortedIndex < charArray.length; unsortedIndex++){
			for (int sortedIndex = 0; sortedIndex < sortedCharArray.length; sortedIndex++){
				if((charArray[unsortedIndex] == sortedCharArray[sortedIndex])){
					if(!unsortedIndexWasMapped[unsortedIndex] && !sortedIndexWasMapped[sortedIndex]){
						this.unsortedToSortedMap[unsortedIndex] = sortedIndex;
						this.sortedToUnsortedMap[sortedIndex]   = unsortedIndex;
						unsortedIndexWasMapped[unsortedIndex]   = true;
						sortedIndexWasMapped[sortedIndex]       = true;
					}
				}
			}
		}		
	}

	// method for getting keyword
	public String getKeyword(){
		return this.keyword;
	}
	
	// set the default keyword
	private String getDefaultKeyword(){
		return "JAVA";
	}
	
	// method for getting the sorted keyword
	public String getSortedKeyword(){
		return this.sortedKeyword;
	}
	
	// methods for returning arrays which maps 
	// sorted to unsorted keyword and vice versa
	public int[] getUnsortedToSortedMap(){
		return this.unsortedToSortedMap;
	}
	public int[] getSortedToUnsortedMap(){
		return this.sortedToUnsortedMap;
	}
	
	// sorts the keyword using Java's in-built
	// String sort command and returns a string
	private String sortKeyword(){
	
		// make a copy of the original keyword array
		// so that the the original keyword remains unsorted
		char[] charArray       = this.keyword.toCharArray();
		char[] sortedCharArray = new char[charArray.length];
		StringBuilder sb       = new StringBuilder();
		for(int i = 0; i < charArray.length; i++){
			sortedCharArray[i] = charArray[i];
		}
		
		// sort and append to StringBuilder
		Arrays.sort(sortedCharArray);
		for(int i = 0; i < sortedCharArray.length; i++){
			sb.append(sortedCharArray[i]);
		}
		return sb.toString();
		
	}
	

}
