package vse.klim05;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

import joptsimple.OptionParser;
import joptsimple.OptionSet;


public class Main {

	/**
	 * @param args
	 * @throws CmdLineException 
	 */
	public static void main(String[] args) {
		
		Tools.debug = true;
		
		//MonolitManager manager = new MonolitManager(
		//		"/home/worker/Dokumente/VSE/Arch_Navrh/Test",
		//		"/home/worker/Dokumente/VSE/Arch_Navrh/Output"
		//	);
		
		//manager.CreateChapter("Kapitola1");
		//manager.CreateChapter("Kapitola2");
		//manager.CreateChapter("Kapitola3");
		//manager.CreateChapter("Kapitola3-clean");
		//manager.CreateChapter("Kapitola4");
		
		OptionParser parser = new OptionParser();
		
		parser.accepts("m").withRequiredArg(); // -m path to monolit
		parser.accepts("o").withRequiredArg(); // -o output folder
		parser.accepts("ch").withRequiredArg(); // -ch chapter which should be generated
		parser.accepts("d"); // allow debug
		Tools.log(Arrays.asList(args).toString());
		
		OptionSet options = parser.parse(args);
		
		File monolitFolder;
		File outputFolder;
		String chapterName;
		
		if(options.hasArgument("d")){
			Tools.debug = true;
		}
		if(!options.hasArgument("m")){
			error("-m monolit argument missing!");
			return;
		}else{
			monolitFolder = new File((String) options.valueOf("m"));
			
			if(!monolitFolder.exists()) error("Monolit folder not exists!");
			if(!monolitFolder.isDirectory()) error("Monolit folder is not directory!");
			if(!monolitFolder.canRead()) error("Monolit folder is not readible");
			
			if(!new File(monolitFolder, "chapters.json").exists())
				error("Config file 'chapters.json' is not in root folder!");
			
		}
		if(!options.hasArgument("o")){
			error("-o output folder argument missing!");
			return;
		}else{
			outputFolder = new File((String) options.valueOf("o"));
			
			if(!outputFolder.exists()) error("Output folder not exists!");
			if(!outputFolder.isDirectory()) error("Output folder is not directory!");
			if(!outputFolder.canRead()) error("Output folder is not readible");
			if(!outputFolder.canWrite()) error("Output folder is not writeable");
		
		}
		
		if(!options.hasArgument("ch")){
			error("-ch chapter argument is missing");
			return;
		}else{
			chapterName = (String) options.valueOf("ch");
		}
		// create manager
		MonolitManager manager = new MonolitManager(
			monolitFolder.getAbsolutePath(),
			outputFolder.getAbsolutePath()
		);
		// create chapter
		manager.CreateChapter(chapterName);
		
		
		Tools.log("DONE");
		

	}

	private void printUsage(){
		System.out.println("" +
				"");
	}
	
	public static void error(String message){
		System.out.println("ERROR: " + message);
		System.exit(1);
	}
	
	
}
