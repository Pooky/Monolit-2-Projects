package cz.vse._422.klim05.Klima;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class MonolitManager {
	
	private ChapterManager chManager;
	private String monolitFolder;
	private String destinationFolder;
	private String actualDestinationFolder;
	
	private String[] extensions = {
		"txt", "java", "jsp", "html", "xml"
	};
	private TxtFileProcessor processor;
	
	public MonolitManager(){
		this("/home/worker/Projects/Java/Projects-2-Monolit/Monolit", "");
	}	
	
	public MonolitManager(String destinationFolder){
		this("/home/worker/Projects/Java/Projects-2-Monolit/Monolit", destinationFolder);
	}
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
	
	public void createAllChapters(){
		for(Chapter chapter : chManager.getChapters()){
			CreateChapter(chapter.getName());
		}
	}
	
	public List<Chapter> getAllChapters(){
		return chManager.getChapters();
	}
	
	public void setDestinationFolder(String folder){
		this.destinationFolder = folder;
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
		
		// creating chapter folder
		new File(destinationFolder, chapter.getName()).mkdir();
		
		actualDestinationFolder = destinationFolder + "/" + chapter.getName();
		
		String destinationFilePath;
		Boolean useRelative;
		
		// for each files (folders
		for(String filePath: chapter.getFiles()){
				
			useRelative = true;
			destinationFilePath = filePath; // root folder
			
			// Check if file has destination path
			if(filePath.contains("::")){
				String[] sourceDestination = filePath.split("::");
				filePath = sourceDestination[0];
				destinationFilePath = sourceDestination[1];
				useRelative = false;
			}

			// check if is it file or folder
			File file = new File(monolitFolder, filePath);
			//Tools.log("Real file:" + file.toString());
			
			// check if it exits
			if(!file.exists()){
				Tools.log("File not found: " + file.toString());
				continue;
			}
			
			File newFile = new File(actualDestinationFolder, destinationFilePath);
			//Tools.log("New file: " + newFile.toString());
			/*
			 * Copy file
			 */
			if(file.isFile()){
				
				//Tools.log("generating file " + newFile.getParentFile().toString());
				
				// create folder directory
				newFile.getParentFile().mkdirs();
				// copy file
				transportFile(file, newFile);
				
				
			}
			
			/**
			 * Copy folder
			 */
			if(file.isDirectory()){
				
				Tools.log("parsing directory: " + file.toString() );
				
				String relative = destinationFilePath;
				if(useRelative){
					relative = getRelative(newFile);
				}
				
				//Tools.log("get files");
				getFiles(file.listFiles(), destinationFilePath);
				
				
			}
			
		}

	}
	/**
	 * Get files from folder recursive
	 * @param files
	 */
	public void getFiles(File[] files, String relative) {
		
		new File(actualDestinationFolder, relative).mkdir(); // create folder
		
	    for (File file : files) {
	        if (file.isDirectory()) {
	            
	        	Tools.log("Creating folder: " + relative);
	        	// create new folder in destination
	        	new File(actualDestinationFolder, relative).mkdir();
	            
	        	getFiles(file.listFiles(), relative + "/" + file.getName()); // recursive
	        } else {
	        	
	           // Tools.log("File:" + file.toString() + ": " + relative);	 
	            
	            File destinationFile = new File(actualDestinationFolder, relative + "/" + file.getName());
	            
	            transportFile(file, destinationFile);
	        
	        }
	    }
	    
	}

	/**
	 * Transport file
	 * @param file
	 */
	private void transportFile(File file, File outputFile){
	
		//Tools.log("Transport file: " + file.toString() + " - to - " + outputFile.toString());
		
		String ext = FilenameUtils.getExtension(file.getName());
		
		// if its text file PARSE IT!
		if(Arrays.asList(extensions).contains(ext)){
			
			this.processor.processFile(file);
			try {
				this.processor.saveToFile(outputFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			
			// just copy it
			try {
				// copy file
				FileUtils.copyFile(file, outputFile);
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
