****************************************************************
-Author:		Kieran P. Somers
-Email:			kieransomers87@gmail.com	
****************************************************************

****************************************************************
Table of Contents
****************************************************************
(1) Compiling the source code
(2) Running the code
	(2.1) General command line input, and input options
	(2.2) Quick Start Guide
		(2.2.1) Encrypting a file with default output directory, 
				default keyword and default polybius square
		(2.2.2) Decrypting a file with default output directory, 
				default keyword and default polybius square
	(2.3) Advanced Options/User-Specified Configuration/Batch-Mode
		(2.3.1) Specifying the output directory at the command line
		(2.3.2) Specifying a keyword at the command line
		(2.3.3) Configuring/providing a user-specified polybius 
				square input file from the command line
		(2.3.4) Windows/Shell batch file examples
(3)  An Overview of classes and their features
	(3.1.1)  Runner.java
	(3.1.2)  CommandLineInput.java
	(3.1.3)  OutputDirectory.java
	(3.1.4)  ParseFile.java
	(3.1.5)  PrintFile.java
	(3.1.6)  Keyword.java
	(3.1.7)  CharacterMatrix.java
	(3.1.8)  PolybiusSquare.java
	(3.1.9)  Encryptor.java
	(3.1.10) Decryptor.java
****************************************************************

****************************************************************
(1) Compiling the source code
****************************************************************

This code was primarily written and tested using Windows 10 
and notepad++ using:
-Java Compiler/Development Kit Version:
	-java 11.0.2 2019-01-15 LTS
	-Java(TM) SE Runtime Environment 18.9 (build 11.0.2+9-LTS)
	-Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.2+9-LTS, mixed mode)

This program was also tested on a virtual machine running Ubuntu.
-Java Compiler/Development Kit Version:
	-javac 1.8.0_171
	-openjdk version "1.8.0_171"
	-OpenJDK Runtime Environment (build 1.8.0_171-8u171-b11-0ubuntu0.16.04.1-b11)
	-OpenJDK 64-Bit Server VM (build 25.171-b11, mixed mode) 
	
-To compile, navigate to the \src\ directory in a command window and type:
	-javac ie\gmit\dip\*.java
	
-If using a unix/linux type machine the follow command applies:
	-javac ie/gmit/dip/*.java
	
No external libraries are necessary for code compilation.
All classes are either part of the Java standard library,
or are user-defined.

A windows batch file is provided ("compile.bat") which 
compiles the source code.

A bash file is provided ("compile.sh") which compiles 
the source code on unix/linux operating systems.
		
****************************************************************
(2) Running the program
****************************************************************

All paths below refer to Windows/DOS type paths where directories
are forward slash-separated. 

If using this program on a unix/linux other machine, pay heed to
your operating system conventions for filepaths.

----------------------------------------------------------------
(2.1) General command line input, and input options
----------------------------------------------------------------

This program is designed to be run at the command line via a 
series of input keywords/switches of the form "-x=input", where:

	"-x=" is the "switch", and 
	"input" is the user-specified input for that "switch"

Currently available options include:
	
	------------------------------------------------------------
	Mandatory Keywords/Switches
	------------------------------------------------------------
	-i=[input]		specifies the name of the input file to 
					encrypt/decrypt the filename must be 
					absolute, or relative to a subfolder in 
					the current directory
	-task=[input]	specifies the task to perform 
					(i.e. encryption or decryption)
	------------------------------------------------------------
	
	------------------------------------------------------------
	Optional Keywords/Switches
	------------------------------------------------------------
	-o=[input]		specifies a path to the output directory 
					where output files are to be written
					the path must be absolute, or relative 
					to a subfolder in the current working 
					directory
	-kw=[input]		specifies a user provided keyword
	-psf=[input]	species the name of a polybius square file 
					to be used for encryption/decryption
	------------------------------------------------------------
	
The -i= and -task= options MUST be provided by the user or the 
program will terminate.

The -o=, -kw= and -psf= options are not required, and if no 
option is provided, default options are used.

The default output directory is a relative directory named 
"output", which will be created in the current working 
directory (\src\output)

The default keyword is "JAVA".

The default polybius square is the one provided in the project 
description.

----------------------------------------------------------------
(2.2) Quick Start Guide
----------------------------------------------------------------

Example input files are provided in "\example_unencrypted_files".
Example encrypted files are provided in "\example_encrypted_files".
Example decrypted files are provided in "\example_decrypted_files".

The above examples were generated using a Windows 10 OS 
using the "run_examples.bat" file, and were also succesfully 
generated on a Ubuntu machine using the "run_examples.sh" file.

See section 2.3.4 for an overview of the batch-processing examples 
provided.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(2.2.1) Encrypting a file with default output directory, 
		default keyword and default polybius square
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

Navigate to the \src\ directory in a command window and type: 
	
	>java ie.gmit.dip.Runner -i=example_unencrypted_files\DeBelloGallico-Caesar.txt -task=encrypt

The program will then encrypt the 
"\example_unencrypted_files\DeBelloGallico-Caesar.txt" file, 
using the "JAVA" keyword, the default polybius square, 
and the output will be written to a file called 
"\src\output\encrypted_DeBelloGallico-Caesar.txt".

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(2.2.2) Decrypting a file with default output directory, 
		default keyword and default polybius square
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

Navigate to the \src\ directory in a command window and type: 
	
	>java ie.gmit.dip.Runner -i=example_encrypted_files\encrypted_DeBelloGallico-Caesar.txt -task=decrypt

The program will then decrypt the 
"\example_encrypted_files\encrypted_DeBelloGallico-Caesar.txt" 
file, using the "JAVA" keyword, the default polybius square, 
and the output will be written to a file called 
"\src\output\decrypted_encrypted_DeBelloGallico-Caesar.txt.
	
----------------------------------------------------------------
(2.3) Advanced Options/User-Specified Configuration/Batch-Mode
----------------------------------------------------------------

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(2.3.1) Specifying the output directory at the command line
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

The user can easily specify the output directory where files will be written via
the -o=\path\to\output\ switch:

	java ie.gmit.dip.Runner -i=file_to_encrypt_decrypt -task=[encrypt|decrypt] -o=\path\to\output\
	
The output directory provided can be absolute, or relative to the current working directory.
The program will create the output directory if it does not exist.
If the output directory cannot be created, a default will be used.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(2.3.2) Specifying a keyword at the command line
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

The user can provide their own keyword via the -kw=string switch.

	>java ie.gmit.dip.Runner -i=file_to_encrypt_decrypt -task=[encrypt|decrypt] -kw=string

The keyword must have an even number of alphabetic characters.
If the keyword is deemed to be invalid, the program will default 
to the "JAVA" keyword.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(2.3.3) Configuring/providing a user-specified polybius 
		square input file from the command line
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

The user can supply a user-specified polybius square via an 
input file specified with the -psf=filename switch.
	
	>java ie.gmit.dip.Runner -i=file_to_encrypt_decrypt -task=[encrypt|decrypt] -psf=filename

In this file the user must specify: 
	-the row keys 
	-the column keys
	-the square matrix for the polybius square.
Below is an example input file for the "default" polybius square
provided as part of the project description:

!---------------------------------------
! Lines that start with a ! are comments
!---------------------------------------
matrix_column_keys: A D F G V X
matrix_row_keys: A D F G V X
!---------------------------------------
!start square
!---------------------------------------
P H 0 Q G 6
4 M E A 1 Y
L 2 N O F D
X K R 3 C V
S 5 Z W 7 B
J 9 U T I 8
!---------------------------------------
!end square
!---------------------------------------

The "matrix_column_keys:" and "matrix_row_keys:" keywords 
are essential, and they specify the matrix row/column keys, 
and also, these lines define the dimensionality of the 
subsequent polybius square.

Any other non-comment line that starts with an alphanumeric 
character is assumed to be a row of the Polybius square, 
with corresponding columns for that row being space-separated.

Only square n*n matrices are allowed, 
as a Polybius "Square" should have equal column and row dimensions, 
that is, if it is really "square".

In the event that the program cannot find the Polybius square 
input file defined by the -psf= switch, or if the Polybius Square 
that is used is deemed invalid i.e. if the dimensionality is 
ill-defined, then a default Polybius Square will be employed.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(2.3.4) Windows/Shell batch file examples
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

A set of windows batch files are provided which illustrate
how to encrypt and decrypt files with various user inputs.
An identical set of bash/shell scripts are provided.

run_examples.bat and/or run_examples.sh will run examples:

	--	with the default polybius square
	--	with a user specified keyword (-kw=JAVA)
	--	with a user specified output directory 
		(-o=varies depending on whether encrypting/decrypting)
	
run_examples_with_user_polybius.bat/run_examples_with_user_polybius.sh will run examples:
 
	--	with the polybius square defined in "polybius_square_a.inp"
	--	with a user specified keyword (-kw=JAVA)
	--	with a user specified output directory 
		(-o=varies depending on whether encrypting/decrypting)

****************************************************************
(3) An Overview of program goals, program classes and their features
****************************************************************

The program consists of 74 functions/methods contained in 9 
core classes and a seperate "Runner" class.

Each class is designed to be in compliance with the 
"Single Responsibility" and "Encapsulation" principles insofar 
as possible. 

Class/instance variables are designed to be private and
can generally only be accessed through public 
mutator/accessor/setter/getter methods.

The program was designed to produce as accurate an 
encryption/decryption as possible. To this end, if a character appears 
in a file, which is not included in the polybius square, that 
character will be "duplicated" during the encryption process 
and these unencryptable characters are then "deduplicated" during
the decryption process. Hence certain whitespace and non-whitespace
characters (e.g. commas, semi-colons, dashes), are preserved
during the encryption/decryption process.

The program was also designed with batch processing 
specifically in mind, and with the option of allowing the user 
to change key features related to running encryption/decryption, 
including the keyword, the polybius square, the input file, 
and the output directory.

A simple and robust way to implement batch processing was 
with a 'one at a time' approach whereby the Runner will 
only process one file at a time, but the user can configure 
the options for that file with some simple command line 
arguments rather than using complex input files which declare 
all of the options for processing  batches of files. An 
interactive menu system was designed initially, 
but this led to many violations of the SRP, and the current 
approach is more straight forward in terms of 
maintenance of the core-classes.

With the current approach, if the user has a basic knowledge of 
scripting (e.g. perl, python, bash), and a pressing need to 
encrypt/decrypt many files, then it would be trivial to 
encrypt/decrypt multiple files, with multiple different keywords, 
multiple different polybius squares, with input/output being 
piped from/to various desired directories.

All diagnostic information is printed to the command window, which can
be re-directed to output files using bash/DOS scripts if required.

Each class contains comments embedded in the source code,
and the descriptions below elaborate upon those comments where required.

Not all methods in the core classes are discussed below, only
the most important features of each class.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.1) Runner.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	
	
This is the "main" class designed to execute the 
encryption/decryption processes. It is a relatively straight 
forward class with no constructor methods which leverages the 
other classes to run a single encryption/decryption process.

There are no class/instance variables.

There is a single main method which takes the command line arguments as
input:

	main(String[] args)
		-- this method handles the main flow of control 
		-- by instantiating and employing CommandLineInput, 
		-- Keyword, PolybiusSquare, OutputDirectory,
		-- and Encryptor/Decryptor objects/classes
	
There are two methods which ensure that a valid file and valid "task"
are specified:

	private static void taskIsValid(String task)
		-- the task must be set to either "encrypt" or "decrypt"
		-- the program will terminate if this is not the case
	private static void fileIsValid(File f)
		-- the file specified must be a valid file that exists
		-- the program will terminate if this is not the case
		
The main sequence/flow of execution is:

	-- create a CommandLineInput (CLI) object, 
	-- configure the CLI arguments using the String[] args piped to main()
	-- ensure the requested task and file are valid
	-- create a Keyword object and initialize it from (a) user input or (b) with a default value
	-- create a PolybiusSquare object and initialize from (a) user input or (b) with a default value
	-- create an OutputDirectory object and initialize from (a) user input or (b) with a default value
	-- if the task=encrypt, then instantiate an Encryptor, and run the encryption process
	-- if the task=decrypt, then instantiate a  Decryptor, and run the decryption process

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.2) CommandLineInput.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
This class is designed to turn the command line ARGS into options 
which allow the user to specify input/output filenames, 
input/output directories, and encryption options.

These options are specified via "Switches" and "options"
for example:
	>java ie.gmit.dip.Runner -switch1=option1 -switch2=option2

The class is designed to be extensible to any number of arguments/switches,
although for the current project, only a handful of options are
"configured"

There are two instance variables:
	String[] args;
		-- a String array which defines the command line 
		-- arguments before processing/parsing
	Map<String, String> options;
		-- a dictionary of option-value pairs which are 
		-- set via the configure() method
		
This class contains two overloaded constructor methods. 

The configure() method configures all keywords of relevance to 
running encryption and decryption.

The getOptionFromArgs(String cliSwitch) method is the key method
which when provided with a switch, searches the args array 
for that switch to retrieve the corresponding 
option via the Java regular expressions library.

There are three mutator/accessor/setter/getter methods:

	public void setArgs(String[] args) 
		-- sets the args array for the instance of the class
	public String getOption(String option) 
		-- returns the value of an option, give the keyword
	public Map<String, String> getOptionsMap() 
		-- returns a map/dictionary of keywords and corresponding options
		
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.3) OutputDirectory.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

This class is designed to handle output directory 
operations/configuration.

There are two instance variables:
	
	File defaultDir = new File("output");
		-- used to set a default directory relative to the 
		-- current working directory
	File name;
		-- a variable name for the directory which the user can set

Contains two overloaded constructor methods.

There are three mutator/accessor/setter/getter methods:
	public void setDir(String s) 
		-- sets the output directory name when provided with a string
	public void setDefaultOutputDirectory() 
		-- sets a default output directory if user-specified directory cannot be created
	public File getDir() 
		-- returns the current output directory as a "File" type
	
The key user-defined method is the createDir() method which tries 
to create the user specified directory and ensures that a 
valid directory exists to which output can be written.

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.4) ParseFile.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
		
This class is designed to parse files to string and character arrays.

There are no class or instance variables.

This class has a single constructor method and two user-defined
methods for parsing files:

	public String[] toStringArray(File f) 
		-- reads a file to a String array and returns the String 
		-- array for processing
	public char[] toCharArray(File f, boolean toUpperCase) 
		-- reads a file to a char array and returns character array 
		-- for processing with an option to convert the char 
		-- array to upper case

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.5) PrintFile.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
This class is designed to print output files given the desired 
output filename and arrays to be printed.

There are no class or instance variables.

This class has one constructor method and one user-defined method
for printing/writing files from character arrays:

	public void fromCharArray(char[] charArray, File f)
		-- prints a character array to a file when 
		-- both the character array and filename are provided
		
The "fromCharArray()" method could be given a more generic
name (e.g. write()), and overloaded to take Strings, String[]
arrays, char[] arrays etc, but this approach is suitable 
for the current project.
 
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.6) Keyword.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

This class is designed specifically to handle the state and behaviour 
related to the encryption keyword, and includes 
mutator/accessor/setter/getter methods as well as user-defined 
methods related to alphabetic sorting of the keyword so that 
matrices can be transposed in a deterministic way.

Instance variables include:

	String  keyword;
		-- a String to store the keyword
	String  sortedKeyword;
		-- a String to store the sorted keyword
	boolean isInitialized  = false;
		-- a boolean to test/ensure that the keyword has been initialized
	int[] unsortedToSortedMap;
		-- an integer array which maps the indices of the 
		-- characters in the keyword to the corresponding
		-- index in the sortedKeyword
	int[] sortedToUnsortedMap;
		-- an integer array which maps the indices of the 
		-- characters in the sortedKeyword to the corresponding
		-- index in the keyword
		
There is a single constructor method.

This class four methods associated with properly initializing a valid keyword:
	
	public void initialize(String kw)
		-- initializes a valid keyword, 
		-- either by confirming the user provided String kw is valid
		-- or by setting a default keyword
	public boolean isValid(String kw)
		-- tests whether the keyword is valid based on some basic rules
		-- i.e. it must have an even number of elements
	public void setKeyword(String s)
		-- sets the keyword variable
	private String getDefaultKeyword()
		-- gets the default keyword should the user-provided keyword be invalid

There are five methods associated with sorting the keyword alphabetically,
and creating "transposition" maps which allow for a degree of diffusion 
to be introduced to encryption matrices:
	
	private String sortKeyword()
		-- sorts the keyword alphabetically and returns the sorted string
	public void setSortedKeyword(String s)
		-- setter method to set the instance sortedKeyword variable
	private void mapUnsortedAndSortedIndices(), getUnsortedToSortedMap(), getSortedToUnsortedMap()
		-- based on the keyword and sorted keyword, sets the maps
		-- which map the unsorted keyword to the sorted keyword and vice versa

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.7) CharacterMatrix.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

This class is designed to handle all setters,
getters and methods associated with n*m matrices of characters.
Matrices of any dimension are allowed, they do not have to be 
square. There are several matrix elements to this project and 
so a generic class is wortwhile. A seperate class for 
PolybiusSquares is employed which extends this base class.

There are four instance variables:

	int rowDimension;
		-- integer which specifies the matrix row dimension
	int columnDimension;
		-- integer which specifies the matrix column dimension
	char[][] matrix;
		-- character array which stores the matrix
	boolean dimensionIsInitialized;
		-- boolean which can be used to determine if matrix has been
		-- succesfully initialized

There are two overloaded constructor methods.

There are five methods associated with setting matrix dimensions and values:

	public void setRowDimension(int i)
		-- sets the matrix row dimension
	public void setColumnDimension(int i)
		-- sets the matrix column dimension
	public void setMatrixDimensions()
		-- sets the overall n*m matrix dimension 
		-- based on the row and column dimensions
	public void setMatrix(char[][] matrix)
		-- sets all of the matrix values
		-- when provided with a complete matrix
	public void setMatrixValue(int irow, int icol, char c)
		-- sets the value of a given row/column

There are four methods associated with getting/returning matrix values:

	public int getRowDimension()
		-- gets the matrix row dimension
	public int getColumnDimension()
		-- gets the matrix column dimension
	public char[][] getMatrixValue()
		-- gets the entire matrix/all values
	public char getMatrixValue(int irow, int icol)
		-- gets the value of a given row/column

There is a single method for columnar transposition:

	public CharacterMatrix transposeColumns(int[] tranpositionArray)
		-- transposes the columns given a transposition array 
		-- where tranpositionArray[untransposed_index] = transposed_index
		-- returns a new instance of a CharacterMatrix

++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.8) PolybiusSquare.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	
This class is designed specifically to handle all setters, getters 
and methods associated with the Polybius Square
The Polybius Square consists of arrays of keys for the 
rows and columns, and an n*n matrix of characters.

This class has been written to extend a generic
CharacterMatrix class with the CharacterMatrix
handling all generic matrix state/behaviour

There are two instance variables:

	char[] rowKeys;
		-- an array of characters which defines the row keys
	char[] columnKeys;
		-- an array of characters which defines the column keys

There is a single constructor method.

There are five methods associated with properly initializing a valid polybius square:

	public void initializeFromFile(File f)
		-- tries to initialize the polybius square from a user-defined file
	private String[] getKeysFromParsedFile(String pattern, String[] stringArray)
		-- gets the matrix column or row keys from a parsed file
		-- where "pattern" is a regex pattern to search the file for
	private boolean buildMatrixFromParsedFile(String[] stringArray)
		-- gets the matrix values from a parsed file
	public void initializeDefault()
		-- initializes a default polybius square if above methods fail
	private boolean keysAreValid(String[] rowKeys, String[] columnKeys)
		-- tests whether matrix keys are valid
	public void setColumnKeys(String[] s)
		-- sets the polybius square column keys
	public void setRowKeys(String[] s)
		-- sets the polybius square row keys

There are three methods associated with getting keys and their indices:

	public char[] getColumnKeys()
		-- gets all of the column keys as a char array
	public char[] getRowKeys()
		-- gets all of the row keys as a char array
	public int getIndexOfKey(char c, char[] charArray)
		-- gets the index of a given character in a character array
		-- which is required to interpolate a row-column key pair
		-- to corresponding values and vice versa
		
All other methods associated with setting/getting matrix values
are implemented in the CharacterMatrix class.
	
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.9) Encryptor.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

This class is designed to handle all tasks associated with file 
encryption including the conversion of plainText to cipherText
and encryptedText, the management of the 
construction and transposition of matrices in tandem with the 
CharacterMatrix class, and writing encrypted files.

There are three instance variables:

	char[] plainText;
		-- a character array which stores the unencrypted text
		-- before enciphering with polybius square
	char[] cipherText;
		-- a character array which stores the cipher text
		-- before columunar transposition via keyword
	char[] encryptedText;
		-- a character array which stores the encrypted text
		-- after columunar transposition via keyword

There is a single constructor method.

There are three methods associated with setting plain and encrypted text variables/state:

	public void setPlainText(char[] c)
		-- sets the input plainText
	public void setCipherText(char[] c)
		-- sets the cipherText before columnar transpositions have taken place
	public void setEncryptedText(char[] c)
		-- sets the final encrypted text after the columnar transpositions have taken place
		
There are four methods associated with the encryption process:

	public void runEncryptor(PolybiusSquare ps, Keyword kw, File inputFile, File outputFile)
		-- runs the encryption/handles the flow of control when provided
		-- a valid polybius square, a keyword, an input file name, and an output file name
	private char[] encryptPlainText(PolybiusSquare ps)
		-- converts the plaintext to cipherText given the polybius square
	private CharacterMatrix buildEncipheredMatrixFromKeyword(String keyword)
		-- builds an enciphered matrix from the cipherText in tandem with the
		-- keyword (the keyword enables the columnar transposition via its own methods)
	public char[] convertMatrixColumnsToCharArray(CharacterMatrix charMatrix)
		-- convert the matrix columns to a single character array which can be printed
		-- to an output file


++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
(3.10) Decryptor.java
++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++	

This class is designed to handle all tasks associated with file 
decryption and reverses the operations of the encryption class.

There are three instance variables:

	char[] encryptedText;
		-- a character array which stores the encrypted text
		-- before columunar transposition are undone via the keyword
	char[] cipherText;
		-- a character array which stores the cipher text
		-- after columunar transposition are undone via the keyword
	char[] plainText;
		-- a character array which stores the decrypted text
		-- after cipherText is converted via the polybius square

There is a single constructor method.

There are three methods associated with setting plain and encrypted text:

	public void setEncryptedText(char[] c)
		-- sets the encrypted text which is to be decrypted 
	public void setCipherText(char[] c)
		-- sets the cipherText after columnar transpositions of the encrypted text have been reversed
	public void setPlainText(char[] c)
		-- sets the plainText (i.e. the decrypted text) after the cipherText has been decrypted

There are three methods associated with the decryption process:

	public void runDecryptor(PolybiusSquare ps, Keyword kw, File inputFile, File outputFile)
		-- runs the decryption/handles the flow of control when provided
		-- a valid polybius square, a keyword, an input file, and an output filename
	private CharacterMatrix convertEncryptedTextToMatrix(String keyword)
		-- converts the encrypted text to a matrix based on the keyword dimensions
		-- and encrypted text dimension/length
	private char[] decryptMatrix(CharacterMatrix cm, PolybiusSquare ps)
		-- converts the matrix to a plainText character array 
		-- given an encrypted text matrix which has already 
		-- had columnar transposition "undone" via the CharacterMatrix class.
	











		