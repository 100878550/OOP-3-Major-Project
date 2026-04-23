package Game;

public class Stats {

    private static int enemiesKilled;
    private static int deaths;
    private static int roomsCleared;

    public Stats() {
       
    	// when created, it loads from the json file.
        load();
    }

    // load function
    // loads, naturally. uses methods from the jsonhelper class.
    public static void load() {
        String json = JsonHelper.ReadJSON();
        if (json == null || json.isEmpty()) return;

        try {
            enemiesKilled = Integer.parseInt(parseJsonValue(json, "kills"));
            deaths = Integer.parseInt(parseJsonValue(json, "deaths"));
            roomsCleared = Integer.parseInt(parseJsonValue(json, "rooms"));
        } catch (Exception e) {
            System.err.println("Error parsing stats: " + e.getMessage());
        }
    }

    // saves the changes to the JSON file
    public static void save() {
        JsonHelper.WriteJSON(enemiesKilled, deaths, roomsCleared);
    }

    // grabs values between quotes
    private static String parseJsonValue(String json, String key) {
        // Looks for "kills":"
        String search = "\"" + key + "\":\""; 
        int start = json.indexOf(search) + search.length();
        // Finds the next " after the value
        int end = json.indexOf("\"", start); 
        return json.substring(start, end);
    }

    
    public int getEnemiesKilled() { return enemiesKilled; }
    public int getDeaths() { return deaths; }
    public int getRoomsCleared() { return roomsCleared; }

    // methods to increment
    public static void addEnemyKilled() { enemiesKilled++; }
    public static void addDeath() { deaths++; }
    public static void addRoomCleared() { roomsCleared++; }
}