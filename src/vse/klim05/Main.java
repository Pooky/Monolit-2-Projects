package vse.klim05;

import java.io.File;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MonolitManager manager = new MonolitManager(
			"/home/worker/Dokumente/VSE/Arch_Navrh/Test",
			"/home/worker/Dokumente/VSE/Arch_Navrh/Output"
		);
		
		//manager.CreateChapter("Kapitola1");
		manager.CreateChapter("Kapitola2");
		//manager.CreateChapter("Kapitola3");
		//manager.CreateChapter("Kapitola3-clean");
		//manager.CreateChapter("Kapitola4");
		

		
		Tools.log("DONE");
		

	}

}
