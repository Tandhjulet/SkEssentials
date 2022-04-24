package dk.tandhjulet.elements.utils;

import java.util.HashMap;

public class CacheManager {
	
	public HashMap<String, CacheNode> map = new HashMap<String, CacheNode>();

	public HashMap<String, CacheNode> getMap() {
		return map;
	}

}
