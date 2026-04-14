package Game;

public class Main {
    public static void main(String[] args) {
    	
    	JsonHelper.WriteJSON(4, 39, 28);
    	System.out.println(JsonHelper.ReadJSON());
        new GameFrame();
    }
}