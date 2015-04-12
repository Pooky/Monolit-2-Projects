package vse.klim05;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TxtFileProcessor {

	
	private static final String blockBegin = "//@ USE_IN";
	private static final String blockEnd = "//@ USE_END";

	private ChapterManager chManager;
	private Chapter chapter; // actual chapter
	private List<String> resultFile; // output file
	
	public void setChapter(Chapter chapter){
		this.chapter = chapter;
	}
	
	public TxtFileProcessor(ChapterManager chManager){
		
		this.chManager = chManager;
		this.resultFile = new ArrayList();
	
	}
	
	public void processFile(File file){
		
		Scanner input = null;
		try {
			input = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		Boolean include = true;
		
		Pattern pattern = Pattern.compile("(.*?);");
		Matcher matcher = null;
		Integer position = 1;
		Integer UseInBegin = -1;
		String temp = null;
		
		// check all lines
		while(input.hasNext()){
			
			position = 0;
			// get next line
		    String line = input.nextLine();
		    
		    Tools.log("Analyzing: " + line);
		    
		    UseInBegin = line.indexOf(blockBegin);
		    
		    /**
		     * In this block, we analyze in which chapters we need add following text
		     */
		    if(UseInBegin != -1){
		    	
		    	Boolean res = false;
		    	include = false;
		    	
		    	temp = line.substring(UseInBegin + blockBegin.length());
		    	Tools.log("FOUND BLOCK");
		    	
		    	matcher = pattern.matcher(temp);
		    	while(matcher.find()){
		    		
		    		//Tools.log(matcher.group(position));
		    		res = evaluateExpression(matcher.group(position));
		    		if(res){
		    			include = true;
		    			break;
		    		}	
		    		
		    		//Tools.log(matcher.start() + " - " + matcher.end());
		    		position++;
		    	}
		    	
		    	// check for in-line syntax
		    	if(line.substring(0, UseInBegin).length() > blockBegin.length()){
		    		
		    		// add everything before expression
		    		if(res){
		    			resultFile.add(line.substring(0, UseInBegin));
		    		}
		    		include = true;
		    	}		    	

		    	
		    	
		    }else if(line.indexOf(blockEnd) != -1){
		    	
		    	// end of block - skip it
		    	include = true;
		    	
		    }else if(include){
		    	
		    	resultFile.add(line);
		    	
		    }
		    
		    Tools.log("INCLUDE:" + include.toString());
		}

		input.close();
		
		// save to file
		
		
	}
	
	/**
	 * Save to output file
	 * @param outputFile
	 * @throws IOException
	 */
	public void saveToFile(File outputFile) throws IOException{

		FileWriter writer = new FileWriter(outputFile);
		
		for(String line : this.resultFile){
			writer.write(line + System.getProperty("line.separator"));
		}
		writer.close();
	}
	
	/**
	 * Compare specific expression and return boolean
	 * if this expression pass 
	 * @return
	 */
	private Boolean evaluateExpression(String expression){
		
		Boolean result = false;
		
		expression = expression.replaceAll("\\s+", "").replaceAll(";$", "");
		Tools.log("\t evaluating: " + expression);
		
		// check if is it interval
		if(expression.contains(":")){
			
			String[] interval = expression.split(":");
			Integer chapterPosition = chManager.getChapterPosition(chapter.getName());
			Integer position1 = chManager.getChapterPosition(interval[0]);
			
			// check wild card
			if(interval[1].equals("?")){
				
				// compare interval begin and actual chapter
				if(position1 <= chapterPosition){
					result = true;
				}
				
			}else{
				
				if(position1 <= chapterPosition && chapterPosition <= chManager.getChapterPosition(interval[1])){
					result = true;
				}
				
			}			
			
		}else{
			// Single chapter expression
			result = this.chapter.getName().equals(expression);
			
		}
		
		return result;
	}

}
