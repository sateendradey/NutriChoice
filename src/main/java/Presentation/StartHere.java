package Presentation;
import java.util.logging.Level;
import java.util.logging.Logger;



public class StartHere {

	public static void main(String[] args) {
		
		Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
		mongoLogger.setLevel(Level.OFF);
		UIForm ui = new UIForm();
		ui.entry();

	}

}
