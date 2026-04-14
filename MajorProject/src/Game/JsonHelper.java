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
	
	// Writes onto the JSON file
	public static void WriteJSON(int scores, int kills, int deaths) {
		
		File stats = new File(STATS_JSON);
		FileWriter out;
		BufferedWriter writeFile;
		
		if (!stats.exists()) {
			
			try {
				stats.createNewFile();
			} catch (IOException error) {
				System.out.println(error);
			}
		
		}
		
		try {
			
			out = new FileWriter(stats);
			writeFile = new BufferedWriter(out);
			
			
			writeFile.write(
					"{\n"
					+ "\"scores\":\"" + scores + "\",\n"
					+ "\"kills\":\"" + kills + "\",\n"
					+ "\"deaths\":\"" + deaths + "\"\n"
					+ "}"
					);
			
			writeFile.close();
			
		} catch (IOException error) {
			
		}
	}

}
