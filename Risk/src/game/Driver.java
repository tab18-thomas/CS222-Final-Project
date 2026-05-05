package game;

public class Driver {
			
	public static void main(String[] args) {
	
	GameManager manager = new GameManager(); 
	
	manager.setUp(); 
	
	while(manager.running) {
		manager.gameLoop(); 
	}
	}
}
	
