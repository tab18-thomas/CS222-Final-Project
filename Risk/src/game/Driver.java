package game;

public class Driver {
			
	public static void main(String[] args) {
	
	GameManager manager = new GameManager(); 
	
	manager.setUp();
	boolean running = true; 
	
	while(running) {
		manager.gameLoop(); 
	}
	}

}
	
