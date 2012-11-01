package phase10.gui;

import java.io.File;
import java.util.Scanner;

public class Language {

	public static final String DEFAULT_LANG = "lang/en.txt";

	private String name;
	private String[] entries;

	Language() {
		setLanguage(DEFAULT_LANG);
	}
	/**
	 * sets the language to the specified file
	 * 
	 * @param fileName
	 *            the file (usually something like "lang/en.txt"
	 * @return true if it was successful, false if there was a problem
	 */
	boolean setLanguage(String fileName) {
		try {
			Scanner file = new Scanner(new File(fileName));
			name = file.next();
			int max = file.nextInt();
			entries = new String[max + 1];

			while (file.hasNext()) {
				int id = file.nextInt();
				String entry = file.nextLine();
				System.out.println("id: " + id + " entry: " + entry);
				entries[id] = entry;
			}

			file.close();
		} catch (Exception e) {
			System.out.println("Error setting language: " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Gets the String entry from the language file
	 * 
	 * @param id
	 *            the id of the entry
	 * @return the entry
	 */
	String getEntry(int id) {
		return entries[id];
	}

	/**
	 * 
	 * @return the name of the language as specified in the file: "English"
	 */
	String getName() {
		return name;
	}
}