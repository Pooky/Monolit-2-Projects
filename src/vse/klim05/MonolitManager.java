package vse.klim05;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MonolitManager {
	
	private ChapterManager chManager;
	private String monolitFolder;
	private String destinationFolder;
	private String[] extensions = {
		"txt", "java", "jsp", "html", "xml"
	};
	private TxtFileProcessor processor;
	

	public MonolitManager(String monolitFolder, String destinationFolder){
		
		this.monolitFolder = monolitFolder;
		this.destinationFolder = destinationFolder;
		
		
		JSONParser parser = new JSONParser();
		JSONArray a = null;
		
		String configFile = new File(this.monolitFolder, "chapters.json").toString();
		Tools.log("Loading config file: " + configFile);
		
		
		try {
			 a = (JSONArray) parser.parse(new FileReader(configFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chManager = new ChapterManager(a);
		this.processor = new TxtFileProcessor(chManager);
		
		
		Tools.log("chapters loaded");

		
	}
	/**
	 * Create chapter in specific folder
	 * 
	 * @param name
	 */
	public void CreateChapter(String name){
		
		Chapter chapter = chManager.getChapter(name);
		
		if(chapter == null){
			Main.error("Chapter not exists!");
			return;
		}
		
		// set chapter to processor
		this.processor.setChapter(chapter);
		
		Tools.log("Chapter: " + name + " - " + chapter.getFiles().toString());
		
		// for each files (folders
		for(String filePath: chapter.getFiles()){
			
			// check if is it file or folder
			File file = new File(monolitFolder, filePath);
			//Tools.log(file.toString());
			
			// check if it exits
			if(!file.exists()){
				Tools.log("File not found: " + file.toString());
				continue;
			}
			
			/*
			 * Copy file
			 */
			if(file.isFile()){
				
				File newFile = new File(destinationFolder, filePath);
				Tools.log("generating file " + newFile.getParentFile().toString());
				
				// create folder directory
				newFile.getParentFile().mkdirs();
				// copy file
				transportFile(file);
				
				
			}
			
			/**
			 * Copy folder
			 */
			if(file.isDirectory()){
				
				
				String relative = getRelative(file);
				new File(destinationFolder, relative).mkdir(); // create folder
				
				getFiles(file.listFiles());
				
				
			}
			
		}

	}
	/**
	 * Get files from folder recursive
	 * @param files
	 */
	public void getFiles(File[] files) {
		
	    for (File file : files) {
	        if (file.isDirectory()) {
	            //System.out.println("Directory: " + file.getName());       	
	        	
	        	String relative = getRelative(file);
	        	
	        	Tools.log("Creating folder: " + relative);
	        	// create new folder in destination
	        	new File(destinationFolder, relative).mkdir();
	            
	        	getFiles(file.listFiles()); // recursive
	        } else {
	        	
	            //Tools.log(file.toString());
	            transportFile(file);
	        
	        }
	    }
	    
	}

	/**
	 * Transport file
	 * @param file
	 */
	private void transportFile(File file){
	
		// construct relative path
		String relative = getRelative(file);
		//Tools.log("Relative: " + relative);
		
		String ext = FilenameUtils.getExtension(file.getName());
		
		// if its text file PARSE IT!
		if(Arrays.asList(extensions).contains(ext)){
			
			this.processor.processFile(file);
			try {
				this.processor.saveToFile(new File(destinationFolder, relative));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			
			// just copy it
		
			try {
				// copy file
				FileUtils.copyFile(new File(monolitFolder, relative), new File(destinationFolder, relative));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
	/**
	 * Create realive path
	 * @param file
	 * @return
	 */
	private String getRelative(File file){
		return new File(monolitFolder).toURI().relativize(file.toURI()).getPath();
	}

}
