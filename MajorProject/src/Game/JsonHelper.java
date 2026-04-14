package Game;

import java.io.*;

public class JsonHelper {
	
	
	// Constants
	static final String STATS_JSON = "storage\\stats.json";
	
	
	// Reads the json File
	public static void ReadJSON() {
		
		File stats = new File(STATS_JSON);
		
		// Checks if the file exists
		if (!stats.exists()) {
			
			try {
				stats.createNewFile();
			} catch (IOException error) {
				System.out.println("File does not exist and can't be created");
			}
		}
		
	}
	
	// Writes stats into the json file (creating it if necessary)
	public static void WriteJSON(int scores, int kills, int deaths) {
		
		// Creates the writer object
		File stats = new File(STATS_JSON);
		FileWriter out;
		BufferedWriter writeFile;
		
		// Check if the file exist
		if (!stats.exists()) {
			
			// If file does not exist
			try {
				stats.createNewFile();
			} 
			
			// If file could not be made
			catch (IOException error) {
				System.out.println(error);
			}
		
		}
		
		// Writes stats into the file
		try {
			
			// Initializes the writer object
			out = new FileWriter(stats);
			writeFile = new BufferedWriter(out);
			
			// Writes data into the file
			writeFile.write(
					"{\n"
					+ "\"scores\":\"" + scores + "\",\n"
					+ "\"kills\":\"" + kills + "\",\n"
					+ "\"deaths\":\"" + deaths + "\"\n"
					+ "}"
					);
			
			// Closes the writer
			out.close();
			writeFile.close();
			
		// If an error occurs
		} catch (IOException error) {
			
		}
	}

}
