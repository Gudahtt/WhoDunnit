package clue.model;

import java.io.Serializable;

public enum Difficulty implements Serializable {
	EASY("Easy"), MEDIUM("Medium"), HARD("Hard");

	private String name;

	Difficulty(String name) {
		this.name = name;
	}

	private static Difficulty[] difficulties = new Difficulty[] { EASY, MEDIUM,
			HARD };

	public static Difficulty[] getDifficultyArray() {
		return difficulties;
	}

	public static Difficulty getDifficulty(String s) {
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
