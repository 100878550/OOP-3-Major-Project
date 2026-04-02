package Game;

// Holds the map (2D array of tile IDs)
public class Room {

    private int[][] map;

    public Room(int[][] map) {
        this.map = map;
    }

    // Get tile ID at position
    public int getTileId(int row, int col) {
        return map[row][col];
    }

    public int getRows() {
        return map.length;
    }

    public int getCols() {
        return map[0].length;
    }
}