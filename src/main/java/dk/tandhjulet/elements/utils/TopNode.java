package dk.tandhjulet.elements.utils;

public class TopNode {
	Object key;
	Object value;
	int leaderboardLocation;

	public TopNode(Object key, Object value, int leaderboardLocation) {
		this.key = key;
		this.value = value;
		this.leaderboardLocation = leaderboardLocation;
	}

	public Object getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public int getLocation() {
		return leaderboardLocation;
	}

	@Override
	public String toString() {
		return "#" + (getLocation()+1) + " " + getKey() + ": " + getValue();
	}
}