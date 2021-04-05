/**
 * ****************************************************************************
 * -Author: Kieran P. Somers
 * -E-mail: kieransomers87@gmail.com
 * ****************************************************************************
 * 
 * ****************************************************************************
 * Table of Contents
 * ****************************************************************************
 * (1) Compiling the source code
 * (2) Running the code
 * (3) UML Diagram Overview
 * (4) JavaDocs
 * (5) API Structure, OOP Design Principles and Key Features of Code
 * ****************************************************************************
 * 
 * ****************************************************************************
 * (1) Compiling the source code
 * ****************************************************************************
 * 
 * This code was primarily written and tested using Linux Mint and Eclipse
 * 2019-09 R (4.13.0) with the latter running the following compiler/jdk and 
 * jre:
 * 
 * openjdk 11.0.4 2019-07-16
 * 
 * OpenJDK Runtime Environment (build 11.0.4+11-post-Ubuntu-1ubuntu218.04.3)
 * 
 * OpenJDK 64-Bit Server VM (build 11.0.4+11-post-Ubuntu-1ubuntu218.04.3, mixed
 * mode, sharing)
 * 
 * This code has not been tested on a Windows machine - although this should not
 * influence the operation as no windows specific statements are included i.e.
 * filenames and paths are handled internally in Java using the in-built File
 * package.
 * 
 * -To compile, navigate to the /src/ directory in a command window and type:
 * -javac ie/gmit/dip/*.java
 * 
 * To create a .jar file from the source code compiled above, navigate to the
 * /bin/ directory and type:
 * 
 * jar -cf simplifier.jar ie/gmit/dip/*.class
 * 
 * No external libraries are necessary for code compilation. All classes are
 * either part of the Java standard library, or are user-defined abstract data
 * types located in src/ie/gmit/dip directory
 * 
 * ****************************************************************************
 * (2) Running the program
 * ****************************************************************************
 * 
 * If running from the compiled source code, open a terminal or command prompt
 * in the /bin/ path and at the command line type and enter:
 * 
 * java ie.gmit.dip.Runner
 * 
 * This will launch an interactive text simplification session.
 * 
 * If running from a compiled jar file (i.e. the simplifer.jar compiled above)
 * open a command line terminal in the path where the simplifier.jar file is
 * located and type:
 * 
 * java -Xmx1G -cp ./simplifier.jar ie.gmit.dip.Runner.
 * 
 * This will also launch an interactive text simplification session.
 *
 * Once an interactive text simplification session is started, the user will
 * be presented with a list of options which they can choose from by provided
 * an integer value to the terminal. Once an option is chosen, they may be
 * prompted to enter some text (either as a single line or multi line) input.
 * For multi line input, the user will be able to enter multiple lines when
 * the hit the enter key. To finalise multi line input, they should terminate
 * their input by entering a line that says "END INPUT".
 *
 * The interactive menu has been designed to accommodate user mistakes in input
 * and should provide the user with ample information for carrying out
 * text simplification, or other options.
 * 
 * ****************************************************************************
 * (3) UML Diagram Overview
 * ****************************************************************************
 * 
 * An overview of virtually all user-developed classes is provided in the
 * accompanying UML diagram which was constructed using ArgoUML:
 * https://en.wikipedia.org/wiki/ArgoUML.
 * 
 * The usual symbols apply for an interface, a class, an abstract class, and for
 * a composition, aggregation, and dependency, as well as for specialisation
 * inheritance and specification inheritance.
 * 
 * However, note that specification (implementation) inheritance is accompaneid
 * by a <<realize>> statement.
 * 
 * Packages imported and used from standard java libraries are not shown in this
 * UML diagram (e.g. the Collections framework), it shows only the key
 * interactions between the interfaces and classes developed as part of the
 * novel API.
 * 
 * ****************************************************************************
 * (4) JavaDocs
 * ****************************************************************************
 * A complete JavaDocs description of all packages and methods is available in
 * the /docs/ folder. Please consult this for a detailed overview of each
 * interface/class, and method. Every interface and class contains an synopsised
 * description of its role (SRP), functionality, their cohesion and coupling to
 * other packages/interfaces/classes. Every method is further described in
 * detail in terms of its function and role. Appropriate comments on Big-O
 * notation are provided for virtually every method, although detailed
 * descriptions are only provided for key and complex methods.
 * 
 * For the specifics on how each type and method is implemented, the java docs
 * and original source code contain exhaustive information and are the
 * definitive source of information, this document aims to supplement that.
 * 
 * ****************************************************************************
 * (5) API Structure, OOP Design Principles and Key Features of Code
 * ****************************************************************************
 * 
 * ----------------------------------------------------------------------------
 * API Structure: Cohesiveness and Coupling
 * ----------------------------------------------------------------------------
 * 
 * In total there are 28 classes and interfaces in the current API. These
 * classes were originally sub-packaged as follows, although, this structure was
 * removed in order to comply with the project specification that a jar file
 * should be compilable using the following command:
 * 
 * jar -cf simplifier.jar ie/gmit/dip/*.class
 * 
 * Compilation of a jar is not possible with this command alone if the the
 * following code structure is employed, although the following package
 * structure was capable of being converted to a jar file via automatic build
 * tools embedded in Eclipse (Ant). The reason for containing this description
 * is to illustrate how the API developed in this project is inteded to be
 * structured when the above restriction does not apply, to illustrate how
 * interfaces and classes were grouped together in cohesive sub packages, which
 * were largely stand-alone and which could be re-used independent of one
 * another.
 * 
 * These were sub-packaged as follows:
 * 
 * The ie.gmit.dip base directory contains a standalone Runner class with a
 * main() method which can be used to launch the software. The Runners only
 * function is to instantiate and start an InteractiveTextSimplifierSession
 * type.
 * 
 * The ie.gmit.dip.converters package contained a cohesive and coupled set of
 * abstract interfaces and classes with responsibility for defining convertible
 * and converter types, with a specific emphasis for the current project on
 * String conversion operations, although the core abstract interfaces are
 * extensible beyond this. The relevant interfaces and classes are:
 * 
 * -AbstractConveter -AbstractConvertible -AbstractStringConverter
 * -ConvertibleString -StringColorConverter -StringCaseConverter
 * -StringSynonymConverter
 * 
 * These classes are completely independent of all others in the current API.
 * 
 * The ie.gmit.dip.parsers package contained a cohesive and coupled set of
 * abstract interfaces and classes with responsibility for defining parsable
 * types, and their accompaning parsers with a specific emphasis for the current
 * project on File and String parsing, although the core abstract interfaces
 * should be extensible beyond this. The relevant interfaces and classes are:
 * 
 * -AbstractParsable -AbstractParser -AbstractFileParser -SimpleFileParser
 * -ParsableFile -IterativeStringSplitter
 * 
 * These classes are completely independent of all others in the current API.
 * 
 * The ie.gmit.dip.terminalIO packaged contains a cohesive and coupled set of
 * abstract interfaces and classes with responsibility for parsing terminal
 * input, and writing terminal output, with additional functionality for
 * defining ANSI colors codes of printed text. The relevant interfaces and
 * classes are:
 * 
 * -TerminalInputParser -TerminalTitleWriter -DefaultAnsiColorCodeMap
 * -ConfigurableAnsiColorCodeMap
 * 
 * These classes are completely independent of all others in the current API.
 * 
 * The ie.gmit.dip.menu package contained a cohesive and coupled set of abstract
 * interfaces and classes with responsibility for defining console menus and the
 * options of which they are composed. The emphasis on this project is the
 * definition of a command line menu of options which can be run by a user from
 * the terminal. This package is coupled loosely to the ie.gmit.dip.terminalIO
 * package for parsing command line arguments and some basic writing operations.
 * The relevant interfaces and classes are:
 * 
 * -Menuable -Optionable -RunnableCommandLineOption
 * -RunnableCommandLineOptionMenu
 * 
 * The ie.gmit.dip.thesaurus package contained a cohesive and coupled set of
 * abstract interfaces and classes with responsibility for constructing and
 * mapping a thesaurus from a thesaurus input file, and a file containing a list
 * of the Google Top 1000 words. This package is coupled as loosely as possible
 * with the ie.gmit.dip.parsers package for file parsing operations. However,
 * dependencies on the ie.gmit.dip.parsers package are fully encapsulated within
 * the package and are not visible to an external user of this package who
 * interacts with it via standard java types. The relevant interfaces and
 * classes are:
 * 
 * -Thesauruable -OneToOneMappedThesaurus -OneToOneMappedThesaurusBuilder
 * 
 * The ie.gmit.dip.textSimplifier contains three cohesive/coupled interfaces and
 * classes which are composed of, and coupled with, all of the previous above
 * types. This package has responsibility for performing a range of text
 * simplification and conversion operations from the command line. The classes
 * in this package are by definition highly composite types, relying heavily
 * upon and delegating frequently to, the functionality in the previous packages
 * to carry out text simplification. This package allows the user to carry out a
 * broad range of tasks associated with the current API, including text
 * simplification, text coloring, and selection of input files. The relevant
 * interfaces and classes are:
 * 
 * -AbstractTextSimplifier -ThesaurusMapTextSimplifier
 * -InteractiveTextSimplifierSession
 * 
 * ----------------------------------------------------------------------------
 * OOP Design principles
 * ----------------------------------------------------------------------------
 * ----------------------------
 * Polymorphism and Liskov Substitutability Principle
 * ----------------------------
 * These are illustrated in the ie.gmit.dip.converters package where a hierarchy
 * of convertible types, and converters have been designed ranging from the
 * abstract to the concrete.
 * 
 * A specific example is the design of the ConvertibleString type, which
 * implements the AbstractConvertible interface by taking a String as input, and
 * returning a String as output, with the conversion taking place by any number
 * of types which extend the AbstractStringConverter abstract base type. Three
 * specific StringConverters have been designed which implement the
 * AbstractStringConverter interface, and therefore the requisite convert()
 * method. These concrete StringConverters are used to carry out the important
 * synonym conversion, and color conversions, but the specifics of the convert()
 * procedure embedded in the ConvertibleString type are not known until run-time
 * depending on the specific specialised type of AbstractStringConverter which
 * is being run.
 * ----------------------------
 * Specification inheritance (interfaces):
 * ----------------------------
 * There are many examples of different classes implementing the same, and
 * different interfaces, in some cases, implementing many interfaces:
 * 
 * The ConvertibleString type implements the AbstractConvertible interface
 * 
 * The AbstractStringConverter type implements the AbstractConverter interface
 * 
 * The AbstractFileParser type implements the AbstractParser interface
 * 
 * The IterativeStringSplitter type implements the AbstractParser interface
 * 
 * The ParsableFile type implements the AbstractParsable interface
 * 
 * The RunnableCommandLineOption type implements the Optionable, Comparable, and
 * Runnable interfaces
 * 
 * The RunnableCommandLineOptionMenu type implements the Menuable interface
 * 
 * The OneToOneMappedThesaurus type implements the Thesaurable interface
 * 
 * The ThesaurusMapTextSimplifier implements the AbstractTextSimplifier
 * interface
 * ----------------------------
 * Specialisation inheritance:
 * ----------------------------
 * There are many examples of specialisation inheritance in the current project:
 * 
 * The StringCaseConverter type extends the AbstractStringConverter abstract
 * base class
 * 
 * The StringColourConverter type extends the AbstractStringConverter abstract
 * base class
 * 
 * The StringSynonymConverter type extends the AbstractStringConverter abstract
 * base class
 * 
 * The SimpleFileParser type extends the AbstractFileParser abstract base class
 * ----------------------------
 * Functional interfaces:
 * ----------------------------
 * The DefaultAnsiColorCodeMap interfaces is a functional interface - it has
 * only a single method which can be called statically without trying to
 * instantiate an uninstantiable abstract interface, see:
 * -DefaultAnsiColorCodeMap.getDefaultColorMap()
 * ----------------------------
 * Abstraction
 * ----------------------------
 * Overall, the functionality required to implement the current project has been
 * abstracted in the form of approximately 28 new interfaces and classes which
 * have been previously described in terms of relevant packages. These
 * interfaces and classes and packaged were designed keeping in mind core OOP
 * principles such as the SRP coupling and cohesion in mind.
 * 
 * The large majority of the abstraction in the current API has taken place at
 * the level of the string converters, file parsers and menu options, which have
 * been abstracted into a series of interfaces, abstract classes, and concrete
 * types which further refine and extend the abstract classes. These classes
 * were designed to be universal, minimalist, and extensible beyond the scope of
 * the current project. The present author is employed as a computational
 * chemist working in domains of protein (molecular dynamics) and combustion
 * modelling (chemical reaction engineering, and handling and processing large
 * amounts of (10-100s GB) of file IO data. Historically, this author has relied
 * heavily upon scripting tools such as perl, with python employed as both an
 * OOP and scripting tool, for developing workflows and pipelines. In order to
 * put their learning of Java and advanced OOP into practice beyond the current
 * project, and to put in place foundational elements of Java code that could be
 * of use outside the scope of this project the abstraction of the parsers and
 * menus into potentialy useful libraries was a focus of this work.
 * 
 * As responsibilities become more specialised, as in the case of the thesaurus
 * map construction, and text simplification, abstraction into a set of
 * extensible libraries becomes more difficult given the highly specific nature
 * of these types, the specific operations involved, and level of coupling
 * between them.
 * 
 * The thesaurus map is abstracted in the form of a thesauruable interface,
 * which defines basic operations one would expect from a thesaurus including
 * the ability to add and remove words, define synonyms etc. In practice, much
 * of this functionality has been delegated to Javas Collections framework in
 * the form of HashMaps and HashSets, owing to their speed for key based
 * operations, with some SortedSet/TreeSet employed where lists of unique and
 * sorted elements were required.
 * 
 * The ThesaurusMapTextSimplifier is abstracted in the form of an
 * AbstractTextSimplifier interface, which defines the basic functionality
 * expected of a text simplifier, in highly generic terms. Ultimately, the
 * ThesaurusMapTextSimplifier is a highly composite object, which delegates much
 * of its responsibility for String simplification and conversion to the types
 * defined in the ie.gmit.dip.converters package, among others.
 * ----------------------------
 * Encapsulation
 * ----------------------------
 * An example of encapsulation is provided in the OneToOneMappedThesaurusBuilder
 * class. Internally, this class is coupled quite strongly with the
 * ie.gmit.dip.ParsableFile and ie.gmit.dip.SimpleFileParser classes. This
 * internal behavior is not visible to the user of the
 * OneToOneMappedThesaurusBuilder class, with users interacting with this class
 * by providing standard Java.io.File objects to setters and getters, with these
 * File objects internally converted to ParsableFile types. Whilst internally,
 * the behaviour of the OneToOneMappedThesaurusBuilder is therefore dependent on
 * the ie.gmit.dip.parsers library, externally the user is insulated from this
 * and changes to these underlying libraries should not influence any code that
 * use the OneToOneMappedThesaurusBuilder, as they cannot depend on the parsers
 * library at present.
 * ----------------------------
 * Composition
 * ----------------------------
 * The ThesaurusMapTextSimplifier is a highly composite object, which was
 * written to be, insofar as possible, fully composed of the following private
 * class-level types, none of which are directly accessible externally, and with
 * those having getter and/or setter methods making copies of any ArrayLists and
 * HashMaps before setting, or returning data.
 * 
 * private ArrayList<String> textToSimplify;
 * 
 * private ArrayList<String> simplifiedText;
 * 
 * private HashMap<String, String> defaultColors;
 * 
 * private OneToOneMappedThesaurusBuilder thesaurusMapBuilder;
 * 
 * private TerminalInputParser inputParser;
 * 
 * private ConfigurableAnsiColorCodeMap colorMap;
 * 
 * Similarly, as described above, the OneToOneMappedThesaurusBuilder is composed
 * of several class-level ParsableFile types, which are externally inaccessible
 * to the user as they are fully encapsulated.
 * ----------------------------
 * Aggregation
 * ----------------------------
 * A simple example of an aggregation in the current project is that of the
 * AbstractFileParser, which is aggregated with a class-level ParsableFile type.
 * This ParsableFile is provided to the AbstractFileParser via the method
 * signature of its AbstractFileParser.setInput() method. The ParsableFile is
 * likely to outlive its container in this case. Similarly, The
 * ConvertibleString type is aggregated with an ArrayList of
 * AbstractStringConverters, with the latter likely to outlive the
 * ConvertibleString which requires them.
 * ----------------------------
 * Association
 * ----------------------------
 * Oddly, there are no obvious forms of association in the current project, with
 * objects tending to be fully composed with, aggregated with, or dependent on
 * other classes, rather than being associated with them (i.e. there are no
 * instances of an object being passed as part of a method signature, without
 * being declared as a class-level variable in the receiving object).
 * ----------------------------
 * Dependency
 * ----------------------------
 * There are many instances of dependency in the current project. As part of its
 * convertString() method. The ThesaurusMapTextSimplifier has dependencies on
 * ConvertibleString types, and many different types which extend the
 * AbstractStringConverter abstract base class. The
 * OneToOneMappedThesaurusBuilder, the TerminalInputParser and the
 * RunnableCommandLineOptionMenu all have weak dependency on the
 * TerminalTitleWriter class.
 * ----------------------------
 * Cohesion and Coupling:
 * ----------------------------
 * See notes on the intended API structure above, with classes arranged in
 * packages which are highly cohesive, and loosely coupled.
 * 
 * ----------------------------------------------------------------------------
 * Key features of the code and relevant methods that work
 * ----------------------------------------------------------------------------
 * 
 * The code is extensively documented in the javadocs /docs/ folder, and the
 * following is not intended to replace that. Rather the following is to state
 * as succinctly as possible how the key features of the project have been
 * implemented, how the basic functionality requested has been met, and
 * elaborated upon, and to delineate some important features of the code and to
 * point to some of the important method calls.
 * 
 * File and text Parsing is an important feature of the code that is essential
 * to correctly build thesaurus maps. The ie.gmit.dip.parsers package has been
 * specifically written to abstract and implement state and behavior associated
 * with file parsing. The ParsableFile and SimpleFileParser are cohesive in this
 * regard.
 * 
 * The ParsableFile type has been written to extend the base Java.io.File type
 * and implement the AbstractParsable interface, and it allows parsed data to be
 * stored alongside all of the usual Java.io.File functionality. The
 * SimpleFileParser class is designed to read in a ParsableFile and return the
 * contents as a String, whilst retaining the whitespace characters that allow
 * for it to be subsequently split by newline characters and for more complex
 * parsing operations to occur, for this functionality see the following
 * methods:
 * 
 * -AbstractFileParser.setInput()
 * 
 * -SimpleFileParser.parse()
 * 
 * -ParsableFile.setParsedData()
 * 
 * -ParsableFile.getParsedData()
 * 
 * The OneToOneMappedThesaurusBuilder makes good use of the functionality in the
 * ie.gmit.dip.parsers package via the following methods which delegate
 * responsibility:
 * 
 * -OneToOneMappedThesaurusBuilder.readThesaurusFile()
 * 
 * -OneToOneMappedThesaurusBuilder.readPriorityWordSetFile()
 * 
 * The contents of the google-1000.txt file (the priorityWordSetFile) are
 * further processed and converted into a HashSet of strings in the following
 * method:
 * 
 * -OneToOneMappedThesaurusBuilder.setPriorityWords()
 * 
 * and the MobyThesaurus2.txt (thesaurusFile) is converted into a HashMap, that
 * maps Strings to Strings, in the following method:
 * 
 * -OneToOneMappedThesaurusBuilder.buildMappedThesaurus()
 * 
 * As words that are contained in google-1000.txt file appear more than once in
 * the MobyThesaurus2.txt file, there is ambiguity over which word should be
 * chosen to map to, and there is simple solution to this problem. One approach
 * would be to map each word to all possible synonyms (and synonyms of synontms)
 * and choose one at random from the list, however this approach has not been
 * adopted for as one is still dependent on arbitrary criteria for choosing a
 * synonym. Rather than implementing such an approach, a simple one to one
 * mapping is carried out, and an important feature of the
 * OneToOneMappedThesaurusBuilder.buildMappedThesaurus() method is that it is
 * deterministic - once a word in the MobyThesaurus2.txt file has been mapped to
 * a word in the google-1000.txt file, this mapping is not over-written. So
 * rather than allowing the last instance to be mapped, the code has been
 * written to give priority to the first instance of a google 1000 word
 * encountered in the thesaurus file. This ensure that the code produces the
 * same converted text when given a list of input words to convert, and the
 * repeatable output is therefore generated for a given input.
 * 
 * The combination of the above classes and methods therefore allows for an
 * important specification in the current project to be fulfilled, that is, a
 * mapping of the words in the thesaurus file to a Google 1000 word. Once these
 * maps, and sets, have been built, they are readily accessible from the
 * ThesaurusMapTextSimplifier via getter methods defined in the
 * OneToOneMappedThesaurusBuilder and OneToOneMappedThesaurus classes. See:
 * 
 * -OneToOneMappedThesaurusBuilder.getPriorityWordSet()
 * 
 * -OneToOneMappedThesaurusBuilder.getMappedThesaurus().getAllWords(), which is
 * a delegated call to the following method
 * 
 * -OneToOneMappedThesaurus.getAllWords()
 * 
 * Note that if a word in the google 1000 list does not appear in the thesaurus
 * file, the google 1000 word does not get mapped explicitly to the same HashMap
 * that is returned by the OneToOneMappedThesaurus.getAllWords() method i.e. the
 * method that returns the words in the thesaurus that have been mapped. It may
 * seem therefore that not all words in the google 1000 list get mapped and that
 * some core functionality is missing. However, this is not an oversight and
 * does not mean the project specification has not been met. Rather this feature
 * was specifically designed so that one could distinguish readily between the
 * following types of words, each of which receives a unique color upon printing
 * to the terminal:
 * 
 * -default text (including the general terminal contents) receive one color
 * 
 * -words that are in the google 1000 list but not in the thesaurus file are
 * given another color
 * 
 * -words that are in the google 1000 list and are in thesaurus file are given
 * another color
 * 
 * -words that are in the thesaurus that were mapped to a google 1000 word are
 * given another color
 * 
 * -and words that are in the thesaurus that do not have an equivalent in the
 * google 1000 list are given another color.
 * 
 * This behavior of assigning explicit colors to different words is programmed
 * in the ie.gmit.dip.ThesaurusMapTextSimplifier class, specifically in:
 * 
 * -ThesaurusMapTextSimplifier.simplify() and subsequently in
 * 
 * -ThesaurusMapTextSimplifier.convertString()
 * 
 * Much of the behaviour for text conversion is delegated to the the
 * AbstractConvertible and AbstractConverter hierarchy of interfaces and
 * classes, as follows. ThesaurusMapTextSimplifier.convertString() instantiates
 * a new ConvertibleString type and configures it with an ArrayList of concrete
 * types which extend the AbstractStringConverters class, which itself
 * implements the AbstractConverter interface.
 * 
 * Color conversions are handled by the StringColourConverter class, which
 * allows one to prepend and append a String to be colored, with two other
 * String types which assign the starting color and ending color via the
 * methods:
 * 
 * -StringColourConverter.convert() and the
 * 
 * -StringColourConverter.setStartColor() and
 * 
 * -StringColourConverter.setEndColor() methods which allow one to set the
 * colors, or more simply, via an overloaded constructor:
 * 
 * -StringColourConverter(String startColor, String endColor)
 * 
 * The appending of and endColor is important so that the general terminal
 * contents can be colored with a default or user defined color.
 * 
 * Similarly, as part of the conversions carried out, words are converted to
 * their synonyms by the same ConvertibleString, via the StringSynonymConverter
 * class and its StringSynonymConverter.convert() method. As stated previously,
 * these conversions are implemented via the AbstractConverter, with
 * polymorphism and the LSP in mind. If one consults the ConvertibleString
 * class, they will find methods including:
 * 
 * -ConvertibleString.setConverters(ArrayList<AbstractStringConverter>
 * converters)
 * 
 * -ConvertibleString.convert()
 * 
 * Note that the setConverters method takes as input an ArrayList of
 * AbstractStringConverter types, meaning that any type that extends the
 * AbstractStringConverter type, and therefore implements its own convert()
 * method, can be added to this ArrayList. In principle, any number of String
 * conversions could therefore be configured, added to this list, and carried
 * out, in an ordered sequence of operations. Importantly, for the current
 * application, this approach is capable of performing the necessary synonym and
 * color conversions to meet the requirements. Further to this, an attempt has
 * been made to preserve string case, via the StringCaseConverter, which is
 * effectively a means to ensure that more superficial text elements (i.e. if
 * the word started with a capital letter) are retained post-conversion. It is
 * not entirely possible to preserve the entire case of a word, as the number of
 * letters likely changes when converted to a synonym, so only title case is
 * conserved. Most importantly, this was implemented to illustrate the use of
 * polymorphism in the current API.
 * 
 * In terms of the definition of colors in the StringColourConverter, the
 * coupling with other important classes is relatively loose. Colors are
 * specified and implemented in the ie.gmit.dip.terminalIO package, in the
 * DefaultAnsiColorCodeMap and ConfigurableAnsiColorCodeMap classes. The latter
 * calls on the former to instantiate a default HashMap of colors, which was
 * originally provided as part of the project guidelines - see the the
 * DefaultAnsiColorCodeMap.getDefaultColorMap() method.
 * 
 * Whilst these color codes were originally provided as an enumuration, this
 * approach has been replaced with a HashMap implementation for the following
 * reasons. The first is that it still allows users to access colors (ANSI code)
 * via a simple natural language String assigned to their name, and the
 * implementation as a HashMap is very straight forward. In principle, this can
 * have advantages, in that one can readily extend the HashMap and the colors
 * therein. In this case, the HashMap is used as the basis of a simple command
 * line interface which prints the colors names to screen and allows the user to
 * choose the colors they would like for terminal printing, and so it allows for
 * easy coupling with the TerminalInputParser packaged.
 * 
 * The ThesaurusMapTextSimplifier.updateColorFromCommandLine() affords the user
 * this option, by delegating behavior to the
 * TerminalInputParser.chooseStringFromList() method, with the keys of the
 * ConfigurableAnsiColorCodeMap.colorMap used as options in a menu, which the
 * user can then select to override the default colors which have been
 * implemented. Therefore, an important and unique feature of the current API,
 * is not just that it prints words in different colors, but that it allows the
 * user to configure the colors as they see fit.
 * 
 * The final critical feature of the current code is the implementation of a
 * command line interface from which the user can select various options or
 * tasks to be performed. This behavior is implemented via a pair of abstract
 * interfaces, namely the Optionable and Menuable interfaces, which are
 * implemented by the RunnableCommandLineOption and
 * RunnableCommandLineOptionMenu classes.
 * 
 * The RunnableCommandLineOption class allows one to configure individual
 * options, which the RunnableCommandLineOptionMenu class is further composed
 * of. RunnableCommandLineOption implements two other important interfaces, the
 * Comparable and Runnable interfaces, indeed, the name
 * RunnableCommandLineOption stems from the fact the options therein must be
 * Runnable types.
 * 
 * The Comparable interface is implemented such that an object composed of
 * RunnableCommandLineOption types can logically/sensibly sort the individual
 * RunnableCommandLineOption types. In this regard, the
 * RunnableCommandLineOption.compareTo() method, which is required of a class
 * implementing the Comparable interface, simply delegates responsibility for
 * comparing options to the int RunnableCommandLineOption.number variable, and
 * the pre-existing compareTo method for Integer types in the java library is
 * employed for this comparison. The Runnable interface is implemented via the
 * RunnableCommandLineOption.run() method, with responsibility for running
 * further delegated to the Runnable type of which RunnableCommandLineOption is
 * composed via its own .run() method. Aside from this the
 * RunnableCommandLineOption type is effectively a simple Bean class, with
 * getter and setter methods to designate the option number, the option text (to
 * be displayed to the user), the option help text (which further embellishes
 * the previous variable), and the Runnable which will be executed.
 * 
 * The RunnableCommandLineOptionMenu is subsequently responsible for a number of
 * tasks, including storing a Collection of RunnableCommandLineOption types (in
 * this case, in a SortedSet/TreeSet), providing a list of these options to the
 * user (RunnableCommandLineOptionMenu.showOptions()), allowing the user to
 * select an option via terminal input
 * (RunnableCommandLineOptionMenu.setOptionToRunFromCommandLine()), and running
 * the option (RunnableCommandLineOptionMenu.runSetOption()). Note that the
 * RunnableCommandLineOptionMenu.setOptionToRunFromCommandLine() method
 * delegates responsibility for command line input parsing to the
 * TerminalInputParser.getIntegerInput() method.
 * 
 * In terms of configuring the RunnableCommandLineOptionMenu, the
 * InteractiveTextSimplifierSession class, which is instantiated from
 * ie.gmit.dip.Runner, has this responsibility. The important method in this
 * class is the InteractiveTextSimplifierSession.buildMenu() method. Therein, a
 * series of RunnableCommandLineOption types are instantiated, configured and
 * added to a RunnableCommandLineOptionMenu. Each Runnable is implemented via
 * Lambda expressions of the form:
 * 
 * Runnable runnableName = () -> { ThesaurusMapTextSimplifier.methodName(); };
 * 
 * before subsequently having their option number, text, and help text
 * specified. The use of these Runnable types, coupled with lambda expressions,
 * offers an extremely convenient method for the current interface to be built
 * and options to be run via simple calls to pre defined methods in the
 * ThesaurusMapTextSimplifier class.
 * 
 * Aside from its responsibility for building the RunnableCommandLineOptionMenu,
 * the InteractiveTextSimplifierSession contains the important
 * InteractiveTextSimplifierSession.start() method which is called by
 * ie.gmit.dip.Runner to initialize a command line session for text
 * simplification.
 * 
 * The above gives some appreciation for how the project requirements have been
 * met.
 * 
 * Further to this, functionality has been added which allows the user to change
 * the thesaurus and google-1000 words, if they wish. Such an option would
 * support the extension of the API to different thesaurus and lists of
 * "priority" words (imagine an anyontm conversion API), or even different
 * languages (e.g. French, L33T SP34K). It also insulates the API from the user
 * misplacing the input files, allowing them to be specific about where such
 * files are located.
 * 
 * The ThesaurusMapTextSimplifier.setPriorityWordSetFileFromCommandLine() method
 * allows the user to provide the path to an input file which should be used in
 * lieu of the 'google-1000.txt' file. Responsibility is delegated to the
 * TerminalInputParser.getSingleLineTextInput() method, the
 * OneToOneMappedThesaurusBuilder.setPriorityWordSetFile() and ultimately to the
 * OneToOneMappedThesaurusBuilder.buildThesaurusMap() method. Note that should
 * the user decide to specify a new file the thesaurus is automatically
 * reconstructed.
 * 
 * Similarly, should the user wish the replace the thesaurus file with one in a
 * different location, the
 * ThesaurusMapTextSimplifier.setThesaurusFileFromCommandLine() method can be
 * called, which subsequently delegates responsibility to the
 * TerminalInputParser.getSingleLineTextInput() and
 * OneToOneMappedThesaurusBuilder.setThesaurusFile() file methods, before the
 * OneToOneMappedThesaurusBuilder.buildThesaurusMap() method is called to
 * re-build the thesaurus.
 * 
 * A comment on the data structures and core java API employed is wortwhile. The
 * current API makes extensive use of the in built java libraries and types. The
 * most important of these is the Collections framework has been extensively
 * employed for list and mapping operations, with Collection, HashMap, HashSet,
 * SortedSet (TreeSet), List, ArrayList all employed to some degree depending on
 * the functionality required. with standard String[] arrays typically used for
 * string parsing before conversion to a List<>. HashMaps and HashSets have
 * specifically being used where possible owing to their O(c) running time for
 * key based operations. Lists and ArrayList are employed when order of
 * insertion is important, and are extensively used for menu operations.
 * TreeSets were used when sorting of objects (which implement the comparable)
 * interface was required, and it was specifically used in lieu of a HashMap
 * when sorting of menu items was required.
 * 
 * Finally, the following command line operations are available to the user, and
 * a skeletal overview of the sequence of classes and methods that are employed
 * to implement these options are shown, albeit, without parameters included in
 * the method signature. The methods below are the key ones which form the basis
 * of user interaction and control of the text simplification API and are
 * therefore worth inspecting in further detail, and a user can follow the flow
 * of control from these methods through the API. Whilst some of these methods
 * are composites of others, the menu was designed to illustrate various
 * different parts of the API in use, both in isolation, and in tandem with one
 * another. The level of nesting is indicative of the flow of control within
 * nested loops/blocks of code.
 * 
 * ------------------------------------------------------------------------------------
 * 1) Enter text, run converter and print old and new text
 * ------------------------------------------------------------------------------------
 * -ThesaurusMapTextSimplifier.getTextRunConverterAndPrintToScreen()
 * 		-ThesaurusMapTextSimplifier.getTextFromCommandLine();
 * 		-TerminalInputParser.getMultiLineInput() 
 * 		-IterativeStringSplitter.setInput()
 * 		-IterativeStringSplitter.parse()
 * -ThesaurusMapTextSimplifier.runConverterAndPrintToScreen();
 * 		-ThesaurusMapTextSimplifier.simplify();
 * 		-ThesaurusMapTextSimplifier.convertString();
 * 		-ThesaurusMapTextSimplifier.printTextToConvertToScreen();
 *		-ThesaurusMapTextSimplifier.printConvertedTextToScreen();
 * 
 * ------------------------------------------------------------------------------------
 * 2) Re-build the thesaurus
 * ------------------------------------------------------------------------------------
 * -ThesaurusMapTextSimplifier.buildThesaurusMap()
 * 		-OneToOneMappedThesaurusBuilder.build()
 * 			-OneToOneMappedThesaurusBuilder.readPriorityWordSetFile();
 * 			-OneToOneMappedThesaurusBuilder.readThesaurusFile();
 * 			-OneToOneMappedThesaurusBuilder.setPriorityWords();
 * 			-OneToOneMappedThesaurusBuilder.buildMappedThesaurus();
 * ------------------------------------------------------------------------------------
 * 3) Enter new text for simplification
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.getTextFromCommandLine();
 * 		-TerminalInputParser.getMultiLineInput() 
 * 		-IterativeStringSplitter.setInput()
 * 		-IterativeStringSplitter.parse()
 * ------------------------------------------------------------------------------------
 * 4) Print user input text to screen
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.printTextToConvertToScreen()
 * 		-TerminalTitleWriter.writeTitle() 
 * 		-TerminalTitleWriter.writeLine()
 * ------------------------------------------------------------------------------------
 * 5) Print converted text to screen
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.printConvertedTextToScreen()
 * 		-TerminalTitleWriter.writeTitle() 
 * 		-TerminalTitleWriter.writeLine()
 * ------------------------------------------------------------------------------------
 * 6) Run the converter
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.runConverterAndPrintToScreen();
 * 		-ThesaurusMapTextSimplifier.simplify();
 * 			-ThesaurusMapTextSimplifier.convertString(); -
 * 		-ThesaurusMapTextSimplifier.printTextToConvertToScreen();
 *		-ThesaurusMapTextSimplifier.printConvertedTextToScreen();
 * ------------------------------------------------------------------------------------
 * 7) Provide path to new thesaurus file
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.setThesaurusFileFromCommandLine()
 * 		-TerminalInputParser.getSingleLineTextInput()
 * 		-ThesaurusMapTextSimplifier.setThesaurusFile()
 * 			-OneToOneMappedThesaurusBuilder.setThesaurusFile()
 * 			-ThesaurusMapTextSimplifier.buildThesaurusMap()
 * 				-OneToOneMappedThesaurusBuilder.build()
 * 					-OneToOneMappedThesaurusBuilder.readPriorityWordSetFile();
 * 					-OneToOneMappedThesaurusBuilder.readThesaurusFile();
 * 					-OneToOneMappedThesaurusBuilder.setPriorityWords();
 * 					-OneToOneMappedThesaurusBuilder.buildMappedThesaurus();
 * ------------------------------------------------------------------------------------
 * 8) Provide path to new file containing most popular words
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.setPriorityWordSetFileFromCommandLine()
 * 		-TerminalInputParser.getSingleLineTextInput()
 * 		-ThesaurusMapTextSimplifier.setPriorityWordSetFile()
 * 			-OneToOneMappedThesaurusBuilder.setPriorityWordSetFile()
 * 			-ThesaurusMapTextSimplifier.buildThesaurusMap()
 * 				-OneToOneMappedThesaurusBuilder.build()
 * 					-OneToOneMappedThesaurusBuilder.readPriorityWordSetFile();
 * 					-OneToOneMappedThesaurusBuilder.readThesaurusFile();
 * 					-OneToOneMappedThesaurusBuilder.setPriorityWords();
 * 					-OneToOneMappedThesaurusBuilder.buildMappedThesaurus();
 * ------------------------------------------------------------------------------------
 * 
 * 9) Change default color of printed words (this includes menu font)
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.updateColorFromCommandLine()
 * 		-TerminalInputParser.chooseStringFromList()
 * 		-TerminalInputParser.chooseStringFromList()
 * ------------------------------------------------------------------------------------
 * 10) Check if a word or phrase is in the thesaurus (case insensitive matching)
 * ------------------------------------------------------------------------------------
 * 	-ThesaurusMapTextSimplifier.queryThesaurusMap()
 * 		-TerminalInputParser.getSingleLineTextInput()
 * 		-ThesaurusMapTextSimplifier.convertString();
 * 
 */