package dk.tandhjulet.elements.utils;

public class TopNode {
	String key;
	Number value;
	int leaderboardLocation;

	public TopNode(String key, Number value, int leaderboardLocation) {
		this.key = key;
		this.value = value;
		this.leaderboardLocation = leaderboardLocation;
	}

	public String getKey() {
		return key;
	}

	public Number getValue() {
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