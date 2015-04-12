package vse.klim05;

public class Tools {
	
	public static Boolean debug = false;
	
	public static void log(String message){
		
		if(debug)
			System.out.println("DEBUG: " + message);
	
	}

}
