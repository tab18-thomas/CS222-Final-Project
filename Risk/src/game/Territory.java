package game;

import java.util.ArrayList;

public class Territory {

// Fields
	
	public String name; // Name of territory
	public int numTroops = 1;  // Number of troops on a territory, always has one troop on it
	public ArrayList<Territory> borderingTerritories ; // A list of the bordering territories
	public Player occupier; // who occupies the territory
	
	
// Methods
	
	// Constructor
	public Territory(String name) {
		this.name = name;
	}

	//String 
	public String toString() {
		return "Territory: " + name + ", Number of Troops: " + numTroops + ", Bordering Territories: " + getBorderingTerritories();
	}
	
	// This method allows a territory to add troops to it
	public void addTroops(int troops) {
		numTroops += troops; 
	}
	
	// this method looks through the player's territories and removes the territories the player has from the list of bordering territories
	// This is to check that when you are checking for territories to attack from your territory, it doesn't include territories you occupy
	public ArrayList<Territory> borderingTerritories(Player excludePlayer){
		ArrayList<Territory> territories = new ArrayList<Territory>(borderingTerritories); // makes a copy of the list territories
		if(excludePlayer != null) { 
			for(Territory t : excludePlayer.territories)
			if (territories.contains(t)) { // if the territory is in the list of player's territories
				territories.remove(t); // then the territory is removed from the list
			}
		}
		return territories;
	}
	
	// This method writes out the list of bordering territories 
	public String getBorderingTerritories() {
		String finalString = "";
		for(Territory t : borderingTerritories) {
			finalString += t.name + ", ";
		}
		return finalString;
	}
	
	// This method writes out the list of bordering territories with the territory's index
	public String getBorderingTerritoryIndices(Player excludePlayer) {
		String finalString = "";
		for(int i = 0; i < borderingTerritories(excludePlayer).size(); i++) {
			finalString += "Index: " + i + " " + borderingTerritories(excludePlayer).get(i).name + "\n";
		}
		return finalString;
	}
}
