package game;

import java.util.ArrayList;
import java.util.Scanner;

public abstract class Player {

// Fields
	public int playerNum; // This is the number and order of players, 1-5
	
	// This is the list of the specific player's territories
	ArrayList<Territory> territories; 
	
	int troopCards; // Number of troop cards each player has
	int troops; // number of troops the player has available to place on a territory
	public String playerName; // name of the player
	
	
// Methods
	// Constructor
	public Player(int playerNum) {
		this.playerNum = playerNum;
		territories = new ArrayList<>();
		troopCards = 0;
		if(playerNum == 0) {
			Scanner input = new Scanner(System.in); // Input Scanner
			System.out.println("Enter your name.");
			playerName = input.next();
		}
		else {
			playerName = "Player " + (playerNum + 1); 
		}
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
	
	
	
	/**
	 * Method that trades 3 troop cards for 5 troops.
	 */
	public void removeTroopCards() {
		troopCards -= 3; // Removes the troop cards
		troops += 5; // I want to add a specific amount of troops
		System.out.println(playerName + " traded 3 troop cards for 5 troops.");
	}
	
	
	// Abstract method that tells us what happens when its a player's turn
	public abstract void doTurn(); 
	
	
	// This method writes out each territory and its troops
	/**
	 * 
	 * @return a comma-separated string of all the territories the player occupies
	 */
	public String getTerritories() {
		String finalString = "";
		for(Territory t : territories) {
			finalString += t.name + ": "+ t.numTroops + ", ";
		}
		return finalString;
	}
	
	// This method writes out the index of the territory,
	// the territory name, and the number of troops it has
	/**
	 * 
	 * @return A string that has been formatted to indicate for each territory
	 * a player owns an index that they may refer it with and how many troops
	 * they have on it.
	 */
	public String getTerritoryIndices() {
		String finalString = "";
		for(int i = 0; i< territories.size(); i++) {
			finalString += "Index: "+i +" " +territories.get(i).name
					+ " - Troops: "+ territories.get(i).numTroops + "\n";
		}
		return finalString;
	}
	
	// This method is taking the amount of troops you want to place
	// and making sure you have enough troops to place that certain number
	public int takeTroops(int troops) {
		// if the number of troops you want to place is less than
		// the amount of troops you can place
		if(troops <= this.troops) { 
			this.troops -= troops;
			return troops; 
		}
		System.out.println("You only have: " + this.troops+ ". Pick a lower number.");
		return -1; // This number is returned to show it was an invalid choice
	}
	
}
