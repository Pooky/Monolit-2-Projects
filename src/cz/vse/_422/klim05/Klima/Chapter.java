package cz.vse._422.klim05.Klima;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Chapter class
 * @author worker
 *
 */

public class Chapter {

	private List<String> addedFiles;
	private List<String> excludedFiles;
	private List<String> addedOnceFiles;
	private Boolean  useHistory;
	private Chapter nextChapter;
	private Chapter	prevChapter;
	private String  name;
	
	
	public void addFile(String path){
		addedFiles.add(path);
	}
	public void addOnceFile(String path){
		addedOnceFiles.add(path);
	}
	public void addExcludedFile(String path){
		excludedFiles.add(path);
	}
	public Boolean getUseHistory() {
		return useHistory;
	}
	public void setUseHistory(Boolean useHistory) {
		this.useHistory = useHistory;
	}
	public Chapter getNextChapter() {
		return nextChapter;
	}
	public void setNextChapter(Chapter nextChapter) {
		this.nextChapter = nextChapter;
	}
	public Chapter getPrevChapter() {
		return prevChapter;
	}
	public void setPrevChapter(Chapter prevChapter) {
		this.prevChapter = prevChapter;
	}
	public String getName(){
		return name;
	}
	
	
	/**
	 * Constructor
	 * 
	 * @param chapter JSONobject Chapter from config
	 */
	public Chapter(JSONObject chapter){
		
		
		this.addedFiles = new ArrayList<String>();
		this.excludedFiles = new ArrayList<String>();
		this.addedOnceFiles = new ArrayList<String>();
		this.prevChapter = null;
		this.nextChapter = null;
		this.useHistory = true;
		
		// name is required
		if(!chapter.containsKey("name")){
			System.out.println("Name is required");
		}
		
		this.name = (String) chapter.get("name");
		
		// Add files
		if(chapter.containsKey("add")){
			for(Object file : (JSONArray) chapter.get("add")){
				this.addFile((String) file);
				//System.out.println(file);
			}
		}
		
		// Add files only once
		if(chapter.containsKey("add-once")){
			for(Object file : (JSONArray) chapter.get("add-once")){
				this.addOnceFile((String) file);
				//System.out.println(file);
			}
		}
		
		// exclude files
		if(chapter.containsKey("exclude")){
			
			for(Object file : (JSONArray) chapter.get("exclude")){
				this.addExcludedFile((String) file);
				//System.out.println(file);
			}			
			
		}
		if(chapter.containsKey("history")){
			this.setUseHistory(Boolean.parseBoolean((String)chapter.get("history")));
		}
		
		
		
	}
	public ArrayList<String> getFiles(){
		
		ArrayList<String> output = new ArrayList<String>();
		
		output.addAll(addedOnceFiles);
		output.addAll(getFiles(true));
		
		return output;
	}
	
	/**
	 * Get all files, that this chapter contain
	 * @return ArrayList files
	 */
	public ArrayList<String> getFiles(Boolean secondCall){
		
		ArrayList<String> output = new ArrayList<String>();
		
		// use history and get all files from another chapters
		if(useHistory){
			
			Chapter actual = this.getPrevChapter();
			while(actual != null){
							
				for(String file : actual.getFiles()){
					// file exists?
					if(!output.contains(file)){
						// add file to result set
						output.add(file);
					}
				}
				// 
				if(!actual.useHistory)
					break; 
				
				// move to next chapter	
				actual = actual.getPrevChapter();
				
			}
		}
		
		// merge added files
		for(String file : this.addedFiles){
			// file exists?
			if(!output.contains(file)){
				// add file to result set
				output.add(file);
			}
		}	
		// exclude files
		for(String file: this.excludedFiles){
			// file exists?
			if(output.contains(file)){
				// remove file
				output.remove(file);
			}			
		}
		
		return output;
	}
	
	
	public String toString(){
		return this.getName();
	}
	
	

}
