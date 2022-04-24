package dk.tandhjulet.elements.utils;

public class CacheNode {
	private Object[] data;
	private String name;
	private boolean isArray;

	public CacheNode(String name, Object[] data, boolean isArray) {
		this.data = data;
		this.name = name;
		this.isArray = isArray;
	}

	public String getName() {
		return name;
	}

	public Object[] getData() {
		return data;
	}

	public Boolean isArray() {
		return isArray;
	}
}
