package cz.vse._422.klim05.Klima;

public class Tools {
	
	public static Boolean debug = true;
	
	public static Integer showLevel = 1;
	
	
	public static void log(String message){
		
		if(debug)
			System.out.println("DEBUG: " + message);
	
	}
	
	public static void log(Integer number){
		
		if(debug)
			System.out.println("DEBUG: " + number.toString());
	
	}
		
	
	public static void log(String message, Integer level){
		
		if(debug && level <= showLevel)
			System.out.println("DEBUG: " + message);
	
		
	}

}
