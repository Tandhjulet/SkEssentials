package dk.tandhjulet.elements.utils;

public class TopNode {
	Object index;
	Object value;
	int leaderboardLocation;

	public TopNode(Object index, Object value, int leaderboardLocation) {
		this.index = index;
		this.value = value;
		this.leaderboardLocation = leaderboardLocation;
	}

	public Object getIndex() {
		return index;
	}

	public Object getValue() {
		return value;
	}

	public int getLocation() {
		return leaderboardLocation;
	}

	@Override
	public String toString() {
		return "#" + (getLocation() + 1) + " " + getIndex() + ": " + getValue();
	}
}