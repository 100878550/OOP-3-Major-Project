package Game;

import java.util.Random;

public class PowerupData {
    public enum Type {
        HEALTH,    
        SPEED,     
        FIRE_RATE  
    }
    private static final Random random = new Random();

    public static Type getRandomPowerupType() {
        Type[] types = Type.values();
        return types[random.nextInt(types.length)];
    }
}