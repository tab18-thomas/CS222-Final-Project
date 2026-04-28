package game;

public class Computer extends Player {



	
// Methods
	public void doTurn() {
		System.out.println(toString());
		
	}
	
	public Computer(int playerNum, GameManager manager) {
		super(playerNum, manager);
	}
}
