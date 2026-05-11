package game;

public class Computer extends Player {



	
// Methods
	public void doTurn() {
		
		System.out.println(playerName + "'s turn.");
		placeTroops(); 
		System.out.println("Territories: " + getTerritories());
		GameManager.sleep(1500);
		tryAttack(); 
		System.out.println("");
	}
	
	public Computer(int playerNum) {
		super(playerNum);
	}
	
	private void placeTroops() {
		int remainingTroops = troops; 
		int index = 0;
		
		// going through each territory and giving one troop at a time
		while(remainingTroops > 0) {
			territories.get(index).addTroops(1);
			remainingTroops -= 1; 
			index = (index + 1) % territories.size(); 
		}
		
		
	}
	
	private void tryAttack() {
		int bestTroopCount = 0;
		Territory attackFrom = null;
		
		// Looping through to see if they want to attack and which territory
		// to attack from. 
		// The condition is that they must have at least 4 troops
		for(Territory t: territories) {
			if(t.numTroops < 2) {
				continue;
			}
			if(t.numTroops > bestTroopCount) { 
				// Going through and seeing which of the territories'
				// number of troops is the most
				bestTroopCount = t.numTroops;
				attackFrom = t; 
			}
		}
		if(attackFrom == null) {
			System.out.println(playerName + " chose not to attack.");
		}
		else {
			bestTroopCount = attackFrom.numTroops;
			Territory defending = null;
			
			// Looping through to see which territory bordering territory to attack
			for(Territory t: attackFrom.borderingTerritories(this)) {
				if(t.numTroops < bestTroopCount) { 
					// going through and seeing which bordering territory
					// has less than the attacking territory troops and
					// has the least amount of troops 
					bestTroopCount = t.numTroops;
					defending = t; 
				}
			}
			
			if(defending == null) {
				System.out.println(playerName + " chose not to attack.");
			}
			else {
				GameManager.attack(this, attackFrom,defending.occupier , defending);
			}
			
			
			
			
		}
	}
}
