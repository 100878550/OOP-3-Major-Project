package Game;

import java.io.*;
import java.util.*;

public class JsonHelper {
	
	
	// Constants
	static final String STATS_JSON = "storage\\stats.json";
	
	
	// Reads the json File
	public static String ReadJSON() {
		
		// Initializes the file
		File stats = new File(STATS_JSON);
		
		// Creates the reader object
		Scanner input;
		
		//
		String readStats = "";
		
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
		
		// Reads stats from the file
		try {
			input = new Scanner(stats);
			
			// Loops until the entire file is read
			while(input.hasNextLine()) {
				readStats += input.nextLine() + "\n";
			}
		} catch (IOException error) {
			
		}
		return readStats;
	    
		
	}
	
	// Writes stats into the json file (creating it if necessary)
	public static void WriteJSON(int scores, int kills, int deaths) {
		
		// Initializes the file
		File stats = new File(STATS_JSON);
		
		// Creates the writer object
		FileWriter output;
		
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
			output = new FileWriter(stats);
			
			// Writes data into the file
			output.write(
					"{\n"
					+ "\"scores\":\"" + scores + "\",\n"
					+ "\"kills\":\"" + kills + "\",\n"
					+ "\"deaths\":\"" + deaths + "\"\n"
					+ "}"
					);
			
			// Closes the writer
			output.close();
			
		// If an error occurs
		} catch (IOException error) {
			
		}
	}

}
