package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random; 

public class GameManager {

	
// Fields
	
	// Gameplay
	ArrayList<Player> players = new ArrayList<Player>(); // This creates a list of all of the players
	ArrayList<Territory> allTerritories = new ArrayList<Territory>(); //  all territories
	int playerTurn; // This tells you which player's turn it is
	HashMap<String, Territory> territoryMap = new HashMap<>();
	public boolean running = true;
	
	// Settings
	Float troopsPerTerritory = 1.4f; // this is a percentage that will tell how many troops each player gets
	
// Methods
	
	// This sets the game up
	public void setUp() {
		for(int i = 0; i < 5; i++) {
			if(i == 0) {
				User newUser = new User(0, this);  // This is creating a human user
				players.add(newUser); // Adds new human user to the list of players
			}
			else {
				Computer newComp = new Computer(i, this); // This is creating new Cpus 
				players.add(newComp); // adds new Cpus to list of players 
			} 
			
		}
		// All of the territories name
		createTerritory("Gondor"); 
		createTerritory("Mordor"); 
		createTerritory("Bywater");
		createTerritory("Rivendell"); 
		createTerritory("Chamber of Mazarbul"); 
		createTerritory("Eregion"); 
		createTerritory("Coombe"); 
		createTerritory("Hobbiton");  
		createTerritory("Eriador"); 
		createTerritory("Shire"); 

	
		
		// Sets all the borders for each territory
		
		setTerritoryBorders("Gondor", "Mordor", "Bywater", "Rivendell");
		setTerritoryBorders("Mordor", "Gondor", "Rivendell", "Eregion", "Hobbiton");
		setTerritoryBorders("Bywater", "Gondor", "Rivendell");
		setTerritoryBorders("Rivendell", "Bywater", "Gondor", "Mordor", "Hobbiton", "Chamber of Mazarbul");
		setTerritoryBorders("Eregion", "Mordor", "Hobbiton", "Shire");
		setTerritoryBorders("Hobbiton", "Eregion", "Mordor", "Rivendell", "Chamber of Mazarbul", "Eriador", "Shire" );
		setTerritoryBorders("Coombe", "Chamber of Mazarbul");
		setTerritoryBorders("Eriador", "Hobbiton", "Chamber of Mazarbul");
		setTerritoryBorders("Chamber of Mazarbul", "Coombe", "Eriador", "Rivendell", "Hobbiton");
		setTerritoryBorders("Shire", "Eregion", "Hobbiton" );
		
		
		
		// Give each player 6 territories
		ArrayList<Territory> availableTerritories = new ArrayList<Territory>(allTerritories); // duplicate list of all of the territories
		for(Player p : players) { // this is looping through the list of players
			for(int i = 0; i< 2; i++) { // giving each player 6 territories
			Random rand = new Random();  
			int indx = rand.nextInt(availableTerritories.size()); 
			p.addTerritory(availableTerritories.get(indx)); // it adds the randomly picked territory to the player
			availableTerritories.remove(indx); // this removes the territory that was just given to the player from the available territories to pick from
			}
		}
		
		
	}
	
	// This method creates the territory and adds it to a list and the map
	private void createTerritory(String name) {
		Territory newTerritory = new Territory(name);
		allTerritories.add(newTerritory);
		territoryMap.put(name, newTerritory);
	}
	// This method sets the borders for the specific territory 
	// String... allows you to input different number of parameters for each time you call the method --> had to look this up 
	private void setTerritoryBorders(String middleTerritoryName, String... bordingTerritoryNames) { // parameters are the territory you are looking at and then its a variable argument that allows you to have a different amount of bordering territories
		ArrayList<Territory> finalTerritories = new ArrayList<Territory>(); // create empty list for the final bordering territories
		
		for(int i = 0; i < bordingTerritoryNames.length; i++) { // loop through the variable argument and add the territory with that index to the final list
			if (territoryMap.containsKey(bordingTerritoryNames[i])) {
				finalTerritories.add(territoryMap.get(bordingTerritoryNames[i]));
			}
			
		}
		
		territoryMap.get(middleTerritoryName).borderingTerritories = finalTerritories; // give the final list to the middle territory
	}
	
	// This method allows players to roll dice
	public ArrayList<Integer> rollDice(int dice) {
		ArrayList<Integer> playerDice = new ArrayList<Integer>() ; // creating a new list since a player can roll several dice at once
		for(int i = 0; i < dice ; i++) {
		Random rand = new Random(); 
	
		playerDice.add(rand.nextInt(1,7)); // randomly choosing between 1 and 6 and then adding the dice to the playerDice list
	
		}

		return playerDice; 
	}
	// This method tells you how many dice the attacker and the defender can have
	public int getNumDice(boolean defending, int troops) {
		if(!defending) { // if you are attacking
			return Math.min(3, troops - 1); // this returns a smallest number between 3 and troops-1 , this allows it to cap the max number of dice to 3
		} // if you are defending
		return Math.min(2, troops); // this returns a smallest number between 2 and troops , this allows it to cap the max number of dice to 2
	}
	
	// This is the loop we will be running the game in
	public void gameLoop() {
		Player currentPlayer = players.get(playerTurn); // this is the current player whose turn it is
		manageTroopCards(currentPlayer); // checks troop cards
		giveTroops(currentPlayer); // gives troops to place 
		currentPlayer.doTurn(); // a method in the specific player's function (user and computer)
		if (playerTurn > 0) sleep(1000);
		playerTurn = (playerTurn +1) % players.size();
	}
	
	// This method transfers territories from one player to another
	public void transferTerritory(Player from, Player to, Territory t ) {
		from.removeTerritory(t); // Takes territory from a player
		to.addTerritory(t); // Adds the same territory to another player
		
		
	}
	
	// This method keeps track of the number of troop cards a player has
	// This checks if the player has 3 troop cards, then it gives bonus troops, 5, else they get no bonus troops
	public void manageTroopCards(Player player) {
		if(player.troopCards >= 3) {  
			player.removeTroopCards();
		}
	}
	
	// This method gives troops to a player at the beginning of each turn
	// (Int) turns the float into an integer --> had to look this up
	public void giveTroops(Player player) {
		int troops = (int)Math.floor(player.territories.size() * troopsPerTerritory); // this takes a percent of the player's territories and gives them a certain amount of troops for it
		player.troops += troops; 
		
		// So the game can actually finish in a reasonable time --> just for testing
		if(player.playerNum == 0) {
			player.troops += 20; 
		}
	}
	
	
	public void attack(Player attackingPlayer, Territory attackingTerritory,  Player defendingPlayer, Territory defendingTerritory) {
		System.out.println(attackingPlayer.playerName + " is now attacking " + defendingPlayer.playerName + "! They want "+ defendingTerritory.name + ".");
		sleep(2000);
		System.out.println("\nRolling dice...");
		System.out.println("Winner is determined by whoever rolls the largest number.\n");
		sleep(2000);
		int numOfAttackingDice = getNumDice(false, attackingTerritory.numTroops); // num of dice the attacker needs
		int numOfDefendingDice = getNumDice(true, defendingTerritory.numTroops);  // num fo dice the defender needs
		
		ArrayList <Integer> attackingDiceList = rollDice(numOfAttackingDice); 
		ArrayList <Integer> defendingDiceList = rollDice(numOfDefendingDice);
		
		attackingDiceList.sort(null); // sorts the list from lowest to highest
		defendingDiceList.sort(null); // sorts the list from lowest to highest
		
		// Had to look this up, needed a way to list the dice from highest to lowest
		Collections.reverse(attackingDiceList); // This reverses the order of the list, now its in highest to lowest order
		Collections.reverse(defendingDiceList); // This reverses the order of the list, now its in highest to lowest order
		
		System.out.println(attackingPlayer.playerName + " rolled: " + attackingDiceList+ "\n" + defendingPlayer.playerName + " rolled: "+defendingDiceList);
		sleep(3000);
		int lowestNumDice = Math.min(numOfAttackingDice, numOfDefendingDice); // this returns the smallest number between the attacking an defending to see how many dice it needs to loop through
		
		for(int i = 0; i < lowestNumDice; i++) { // this loops through the list, it is checking the smallest number of dice, either from the defender or attacker
			
			System.out.println("\nComparing roll " + (i + 1));
			

			System.out.println("Attacker: " + attackingDiceList.get(i) + ", Defender: " + defendingDiceList.get(i));
			if(defendingDiceList.get(i) >= attackingDiceList.get(i)) { // if the defender rolls the higher dice
				attackingTerritory.numTroops -= 1; // the attacker losses a troop
				
				System.out.println(attackingPlayer.playerName + "'s territory lost one troop.\n");
			}
			else { // if the attacker rolls the higher dice
				defendingTerritory.numTroops -= 1;  // defender losses a troop
				System.out.println(defendingPlayer.playerName +"'s territory lost one troop.\n");
				}
			sleep(2000);
		}
		
		System.out.println("The battle has ended.");
		
		// This gives the defender's territory to the attacker if the defending territory has 0 troops
		if(defendingTerritory.numTroops <= 0) {
			transferTerritory(defendingPlayer, attackingPlayer, defendingTerritory); // transfers territory 
			attackingTerritory.numTroops -= 1; // this is making sure the winner gives one of their troops to the new territory
			defendingTerritory.numTroops +=1; 
			attackingPlayer.troopCards +=1; 
			System.out.println(attackingPlayer.playerName +" won! They took " + defendingTerritory.name + ".\n");
		}else {
			System.out.println(defendingPlayer.playerName + " kept " + defendingTerritory.name + ".");
		}
		checkWinnersAndLosers(); 
		sleep(3000);
	}
	
	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		}catch(Exception e) {}
	}
	
	private void checkWinnersAndLosers() {
		for(int i = players.size() -1 ; i >=0; i--) {

			if (players.get(i).territories.size() <= 0) {
				if (i > 0) {
					System.out.println(players.get(i).playerName + " lost! They're out of the game!");
					players.remove(players.get(i));
					continue;
				}
				else {
					System.out.println("You lost! Game over :(");
					running = false;
					return;
				}
			}
			if (players.get(i).territories.size() >= allTerritories.size()) {
				System.out.println(players.get(i).playerName + " won the game! They have all of the territories!");
				running = false;
				return;
			}
		}
	}
}


