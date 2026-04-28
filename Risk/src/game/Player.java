package game;

import java.util.ArrayList;

public abstract class Player {

// Fields
	public int playerNum; // This is the number and order of players, 1-5
	ArrayList<Territory> territories ; // This is the list of the specific player's territories
	int troopCards; // Number of troop cards each player has
	int troops; 
	public GameManager manager;
	
	
// Methods
	// Constructor
	public Player(int playerNum, GameManager manager) {
		this.playerNum = playerNum;
		territories = new ArrayList<>();
		troopCards = 0;
		this.manager = manager;
	}
	
	// String
	public String toString() {
		String outputString = ""; 
		if (playerNum > 0) { // this makes it so it only prints player number when its a CPU's turn
			outputString = "Player " + playerNum + ": ";
		}
		outputString += "Troop Cards: " + troopCards + "\n";
		return outputString;
	}
	
	// This method lets you add territories to the player's list of territories
	public void addTerritory(Territory t) {
		territories.add(t); 
		t.occupier = this;  // setting the newly added territory to the occupier
	}
	
	// This method removes territories from a player's list of territories
	public void removeTerritory(Territory t) {
		territories.remove(t);
	}
	
	// This method adds troop cards to the player's inventory
	public void addTroopCards(int amount) {
		troopCards += amount; 
	}
	
	// This method removes troop cards to the player and then gives players a specific number of troops
	public void removeTroopCards() {
		troopCards = 0; // Removes the troop cards
		troops += 5; // I want to add a specific amount of troops
		System.out.println("Traded 3 troop cards for 5 troops.");
	}
	
	public abstract void doTurn(); // Abstract method that tells us what happens when its a player's turn
	
	// This method writes out each territory and its troops
	public String getTerritories() {
		String finalString = "";
		for(Territory t : territories) {
			finalString += t.name + ": "+ t.numTroops + ", ";
		}
		return finalString;
	}
	
	// This method writes out the index of the territory, the territory name, and the number of troops it has
	public String getTerritoryIndices() {
		String finalString = "";
		for(int i = 0; i< territories.size(); i++) {
			finalString += "Index: "+i +" " +territories.get(i).name + ": "+ territories.get(i).numTroops + "\n";
		}
		return finalString;
	}
	
	// This method is taking the amount of troops you want to place and making sure you have enough troops to place that certain number
	public int takeTroops(int troops) {
		if(troops <= this.troops) { // if the number of troops you want to place is less than the amount of troops you can place
			this.troops -= troops;
			return troops; 
		}
		System.out.println("You only have: " + this.troops+ ". Pick a lower number.");
		return -1; // This number is returned to show it was an invalid choice
	}
}
