package ie.gmit.dip;

/**
 * A simple Runner class to instantiate an
 * {@link InteractiveTextSimplifierSession}
 * 
 * @author Kieran P. Somers
 * @version 1.0
 * @since JavaSE-11
 * @see InteractiveTextSimplifierSession
 *
 */
public class Runner {

	/**
	 * Main method which is called via java ie.gmit.dip.Runner at the command line.
	 * 
	 * @param args command line arguments provided to main()
	 */
	public static void main(String[] args) {

		InteractiveTextSimplifierSession session = new InteractiveTextSimplifierSession();
		session.start();
	}

}
