package ca.mun.whodunnit.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import ca.mun.whodunnit.model.SessionData;

public class SessionProcessor {
	public static void saveSession(String saveFilePath, SessionData data)
			throws IOException {
		try {
			ObjectOutput objOut = new ObjectOutputStream(new FileOutputStream(
					new File(saveFilePath)));
			objOut.writeObject(data);
			objOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static SessionData loadSession(String loadFilePath)
			throws IOException, NullPointerException {
		SessionData data = null;
		try {
			ObjectInput objIn = new ObjectInputStream(new FileInputStream(
					new File(loadFilePath)));
			data = (SessionData) objIn.readObject();
			objIn.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return data;
	}
}
