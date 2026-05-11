package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random; 

public class GameManager {

	
// Fields
	
	// Gameplay
	static ArrayList<Player> players = new ArrayList<Player>(); 
	// This creates a list of all of the players
	
	static ArrayList<Territory> allTerritories = new ArrayList<Territory>(); 
	//  all territories
	
	static int playerTurn; // This tells you which player's turn it is
	
	static HashMap<String, Territory> territoryMap = new HashMap<>();
	
	static public boolean running = true;
	
	
	
	// Settings
	static Float troopsPerTerritory = 1.4f; 
	// this is a percentage that will tell how many troops each player gets
	
	
// Methods
	
	// This sets the game up
	public static void setUp() {
		for(int i = 0; i < 5; i++) {
			if(i == 0) {
				User newUser = new User(0);  // This is creating a human user
				players.add(newUser); // Adds new human user to the list of players
			}
			else {
				Computer newComp = new Computer(i); // This is creating new CPUs 
				players.add(newComp); // adds new CPUs to list of players 
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
		setTerritoryBorders("Rivendell", "Bywater", "Gondor",
				"Mordor", "Hobbiton", "Chamber of Mazarbul");
		setTerritoryBorders("Eregion", "Mordor", "Hobbiton", "Shire");
		setTerritoryBorders("Hobbiton", "Eregion", "Mordor",
				"Rivendell", "Chamber of Mazarbul", "Eriador", "Shire" );
		setTerritoryBorders("Coombe", "Chamber of Mazarbul");
		setTerritoryBorders("Eriador", "Hobbiton", "Chamber of Mazarbul");
		setTerritoryBorders("Chamber of Mazarbul", "Coombe",
				"Eriador", "Rivendell", "Hobbiton");
		setTerritoryBorders("Shire", "Eregion", "Hobbiton" );
		
		
		
		// Give each player 6 territories
		ArrayList<Territory> availableTerritories
		= new ArrayList<Territory>(allTerritories); 
		// duplicate list of all of the territories
		
		for(Player p : players) { // this is looping through the list of players
			for(int i = 0; i< 2; i++) { // giving each player 6 territories
			Random rand = new Random();  
			int indx = rand.nextInt(availableTerritories.size()); 
			p.addTerritory(availableTerritories.get(indx)); 
			// it adds the randomly picked territory to the player
			
			availableTerritories.remove(indx); 
			// this removes the territory that was just given to the player
			// from the available territories to pick from
			}
		}
		
		
	}
	
	
	// This method creates the territory and adds it to a list and the map
	/**
	 * Creates a territory with the name you choose and adds it to the game.
	 * @param name
	 */
	private static void createTerritory(String name) {
		Territory newTerritory = new Territory(name);
		allTerritories.add(newTerritory);
		territoryMap.put(name, newTerritory);
	}
	
	
	
	
	
	
	/* 
	 * String... allows you to input different number of parameters
	 * for each time you call the method --> had to look this up
	 */  
	/**
	 * Used to set up a Territory's borderingTerritories field.
	 * Unless you want to magically move land around in the middle of the game,
	 * only use this to begin a game.
	 * @param middleTerritoryName is the territory we're looking at
	 * @param bordingTerritoryNames is an argument with a variable number of 
	 * strings; this should be all territories you want to border the middle one,
	 * listed as separate arguments
	 */
	private static void setTerritoryBorders(String middleTerritoryName, 
			String... bordingTerritoryNames) { 
		
		// create empty list for the final bordering territories
		ArrayList<Territory> finalTerritories = new ArrayList<Territory>(); 
		
		
		for(int i = 0; i < bordingTerritoryNames.length; i++) { 
			// loop through the variable argument and add the 
			// territory with that index to the final list
			if (territoryMap.containsKey(bordingTerritoryNames[i])) {
				finalTerritories.add(territoryMap.get(bordingTerritoryNames[i]));
			}
			
		}
		
		// give the final list to the middle territory
		territoryMap.get(middleTerritoryName).borderingTerritories = finalTerritories;
	}
	
	// This method allows players to roll dice
	public static ArrayList<Integer> rollDice(int dice) {
		
		// creating a new list since a player can roll several dice at once
		ArrayList<Integer> playerDice = new ArrayList<Integer>() ; 
		
		for(int i = 0; i < dice ; i++) {
			Random rand = new Random(); 
	
			// randomly choosing between 1 and 6
			// and then adding the dice to the playerDice list
			playerDice.add(rand.nextInt(1,7)); 
			
	
		}

		return playerDice; 
	}
	
	
	
	/**
	 * This method is used to determine how many dice the attacker and defender
	 * may have during an attack.
	 * @param defending a boolean where true indicates a defending player and
	 * false indicates an attacking player
	 * @param troops use this to indicate the number of troops the attacker/defender
	 * has on the relevant territory
	 * @return
	 */
	public static int getNumDice(boolean defending, int troops) {
		if(!defending) { // if you are attacking
			// this returns a smallest number between 3 and troops-1,
			// this allows it to cap the max number of dice to 3
			return Math.min(3, troops - 1); 
			
		} 
		
		// if you are defending
		// this returns a smallest number between 2 and troops,
		// this allows it to cap the max number of dice to 2
		return Math.min(2, troops); 
	}
	
	// This is the loop we will be running the game in
	public static void gameLoop() {
		// this is the current player whose turn it is
		Player currentPlayer = players.get(playerTurn); 
		
		manageTroopCards(currentPlayer); // checks troop cards
		
		
		giveTroops(currentPlayer); // gives troops to place 
		
		// a method in the specific player's function (user and computer)
		currentPlayer.doTurn(); 
		
		if (playerTurn > 0) sleep(1000);
		
		playerTurn = (playerTurn +1) % players.size();
	}
	
	
	
	// This method transfers territories from one player to another
	/**
	 * Transfers a territory from one player to another, used when
	 * an attack depletes a territory of its occupying troops.
	 * @param from player to remove territory from
	 * @param to player to be granted the territory
	 * @param t the territory to grant
	 */
	public static void transferTerritory(Player from, Player to, Territory t ) {
		from.removeTerritory(t); // Takes territory from a player
		to.addTerritory(t); // Adds the same territory to another player
		
		
	}
	
	
	
	
	/**
	 * Allows us to check how many troopcards a player has and, if they have 3,
	 * exchange those 3 troop cards for 5 bonus troops.
	 * @param player
	 */
	public static void manageTroopCards(Player player) {
		if(player.troopCards >= 3) {  
			player.removeTroopCards();
		}
	}
	
	// This method gives troops to a player at the beginning of each turn
	// (Int) turns the float into an integer --> had to look this up
	/**
	 * Gives troops to the designated player at the beginning of their turn.
	 * @param player
	 */
	public static void giveTroops(Player player) {
		int troops = (int)Math.floor(player.territories.size() * troopsPerTerritory);
		// this takes a percent of the player's territories and gives
		// them a certain amount of troops for it
		player.troops += troops; 
		
		// So the game can actually finish in a reasonable time --> just for testing
		if(player.playerNum == 0) {
			player.troops += 20; 
		}
	}
	
	
	public static void attack(Player attackingPlayer, Territory attackingTerritory,
			Player defendingPlayer, Territory defendingTerritory) {
		System.out.println(attackingPlayer.playerName + " is now attacking "
			+ defendingPlayer.playerName + "! They want "
				+ defendingTerritory.name + ".");
		sleep(2000);
		System.out.println("\nRolling dice...");
		System.out.println("Winner is determined by whoever rolls"
				+ " the largest number.\n");
		sleep(2000);
		
		// num of dice the attacker gets
		int numOfAttackingDice = getNumDice(false, attackingTerritory.numTroops);
		
		// num of dice the defender gets
		int numOfDefendingDice = getNumDice(true, defendingTerritory.numTroops);  
		
		ArrayList <Integer> attackingDiceList = rollDice(numOfAttackingDice); 
		ArrayList <Integer> defendingDiceList = rollDice(numOfDefendingDice);
		
		attackingDiceList.sort(null); // sorts the list from lowest to highest
		defendingDiceList.sort(null); // sorts the list from lowest to highest
		
		// Had to look this up, needed a way to list the dice from highest to lowest
		
		// This reverses the order of the list, now its in highest to lowest order
		Collections.reverse(attackingDiceList); 
		
		// This reverses the order of the list, now its in highest to lowest order
		Collections.reverse(defendingDiceList); 
		
		
		System.out.println(attackingPlayer.playerName + " rolled: "
		+ attackingDiceList+ "\n" + defendingPlayer.playerName
		+ " rolled: "+defendingDiceList);
		sleep(3000);
		
		// this returns the smallest number between the attacking and defending
		// to see how many dice it needs to loop through
		int lowestNumDice = Math.min(numOfAttackingDice, numOfDefendingDice);
		
		
		for(int i = 0; i < lowestNumDice; i++) { 
			// this loops through the list,
			// it is checking the smallest number of dice,
			// either from the defender or attacker
			
			System.out.println("\nComparing roll " + (i + 1));
			

			System.out.println("Attacker: " + attackingDiceList.get(i) 
			+ ", Defender: " + defendingDiceList.get(i));
			
			// if the defender rolls the higher dice
			if(defendingDiceList.get(i) >= attackingDiceList.get(i)) { 
			
				attackingTerritory.numTroops -= 1; // the attacker losses a troop
				
				System.out.println(attackingPlayer.playerName 
						+ "'s territory lost one troop.\n");
			}
			else { // if the attacker rolls the higher dice
				defendingTerritory.numTroops -= 1;  // defender losses a troop
				System.out.println(defendingPlayer.playerName 
						+"'s territory lost one troop.\n");
				}
			sleep(2000);
		}
		
		System.out.println("The battle has ended.");
		
		// This gives the defender's territory to the attacker
		// if the defending territory has 0 troops
		if(defendingTerritory.numTroops <= 0) {
			transferTerritory(defendingPlayer, attackingPlayer, defendingTerritory);
			
			// this is making sure the winner gives one of their troops
			// to the new territory
			attackingTerritory.numTroops -= 1;
			
			defendingTerritory.numTroops +=1; 
			attackingPlayer.troopCards +=1; 
			System.out.println(attackingPlayer.playerName +" won! They took "
					+ defendingTerritory.name + ".\n");
		}else {
			System.out.println(defendingPlayer.playerName + " kept "
					+ defendingTerritory.name + ".");
		}
		checkWinnersAndLosers(); 
		sleep(3000);
	}
	
	public static void sleep(int ms) {
		try {
			Thread.sleep(ms);
		}catch(Exception e) {}
	}
	
	private static void checkWinnersAndLosers() {
		for(int i = players.size() -1 ; i >=0; i--) {

			if (players.get(i).territories.size() <= 0) {
				if (i > 0) {
					System.out.println(players.get(i).playerName
							+ " lost! They're out of the game!");
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
				System.out.println(players.get(i).playerName
						+ " won the game! They have all of the territories!");
				running = false;
				return;
			}
		}
	}
}


