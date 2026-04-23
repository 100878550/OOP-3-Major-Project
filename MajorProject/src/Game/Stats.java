package Game;

public class Stats {

    private int enemiesKilled;
    private int deaths;
    private int roomsCleared;

    public Stats() {
        enemiesKilled = 0;
        deaths = 0;
        roomsCleared = 0;
    }

    // Getters
    public int getEnemiesKilled() {
        return enemiesKilled;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getRoomsCleared() {
        return roomsCleared;
    }

    // Setters
    public void setEnemiesKilled(int enemiesKilled) {
        this.enemiesKilled = enemiesKilled;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void setRoomsCleared(int roomsCleared) {
        this.roomsCleared = roomsCleared;
    }

    // Increment methods 
    public void addEnemyKilled() {
        enemiesKilled++;
    }

    public void addDeath() {
        deaths++;
    }

    public void addRoomCleared() {
        roomsCleared++;
    }

    // Optional reset
    public void reset() {
        enemiesKilled = 0;
        deaths = 0;
        roomsCleared = 0;
    }
}