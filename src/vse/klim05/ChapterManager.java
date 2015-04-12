package vse.klim05;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ChapterManager {

	private List<Chapter> chapters;
	private JSONArray config;
	
	public ChapterManager(JSONArray config){
		
		this.config = config;
		
		chapters = new ArrayList<Chapter>();
		
		Chapter actualChapter;
		
		// populate chapter list
		for(int i = 0; i < config.size(); i++){
			
			actualChapter = new Chapter((JSONObject) config.get(i)); 
			
			// prev chapter
			if(i != 0){
				actualChapter.setPrevChapter(chapters.get(i - 1));
			}
			
			// next chapter
			if(i != (config.size() - 1) && i != 0){
				chapters.get(i - 1).setNextChapter(actualChapter);
			}
			
			chapters.add(actualChapter);
		}
		
	}
	/**
	 * Get chapter by name
	 * @param name
	 * @return
	 */
	public Chapter getChapter(String name){
		
		Chapter chapter = null;
		// search by name
		for(Chapter check : this.chapters){
			if(check.getName().equals(name)){
				chapter = check;
				break;
			}
		}
		
		return chapter;
		
	}
	
	public Chapter getChapter(Integer index){
		return this.chapters.get(index);
	}
	/**
	 * Get chapter position
	 * @param name
	 * @return
	 */
	public Integer getChapterPosition(String name){

		Integer position = -1;
		// search by name
		for(Integer i = 0; i < this.chapters.size(); i++){
			if(this.chapters.get(i).getName().equals(name)){
				position = i;
				break;
			}
		}
		
		return position;
		
	}
	
}
