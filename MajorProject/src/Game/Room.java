package Game;

// Holds the map (2D array of tile IDs)
public class Room {

    private int[][] map;

    public Room(int[][] map) {
        this.map = map;
    }

    // Get tile ID at position
    public int getTileId(int row, int col) {
        
        if (row >= 0 && row < map.length) {
            
        	
            if (col >= 0 && col < map[row].length) {
                return map[row][col];
            }
        }
        
        // Return a default "Black" tile if out of bounds to avoid a crash
        return 5; 
    }

    public int getRows() {
        return map.length;
    }

    public int getCols() {
        return map[0].length;
    }
}