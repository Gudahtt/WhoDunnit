package ca.mun.whodunnit.api.model;

import java.io.Serializable;

public enum PlayerType implements Serializable {
	EASY("Easy"), MEDIUM("Medium"), HARD("Hard"), HUMAN("Human");

	private String name;

	PlayerType(String name) {
		this.name = name;
	}

	private static PlayerType[] playerTypes = new PlayerType[] { EASY, MEDIUM,
			HARD, HUMAN };

	public static PlayerType[] getTypeArray() {
		return playerTypes;
	}

	private static PlayerType[] difficulties = new PlayerType[] { EASY, MEDIUM,
			HARD };

	public static PlayerType[] getDifficultyArray() {
		return difficulties;
	}

	public static PlayerType getType(String s) {
		if (s.equals("Easy"))
			return EASY;
		else if (s.equals("Medium"))
			return MEDIUM;
		else if (s.equals("Hard"))
			return HARD;

		return null;
	}

	@Override
	public String toString() {
		return name;
	}
}
