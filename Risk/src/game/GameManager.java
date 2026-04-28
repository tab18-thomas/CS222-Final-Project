package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random; 

public class GameManager {

	
// Fields
	
	// Gameplay
	ArrayList<Player> players = new ArrayList<Player>(); // This creates a list of all of the players
	ArrayList<Territory> allTerritories = new ArrayList<Territory>(); //  all territories
	int playerTurn; // This tells you which player's turn it is
	
	// Settings
	Float troopsPerTerritory = 1.7f; // this is a percentage that will tell how many troops each player gets
	
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
		allTerritories.add(new Territory("Arnor")); //0
		allTerritories.add(new Territory("Amroth")); //1
		allTerritories.add(new Territory("Archet")); //2
		allTerritories.add(new Territory("Bywater")); //3
		allTerritories.add(new Territory("Blackroot Vale"));  //4
		allTerritories.add(new Territory("Caras")); //5
		allTerritories.add(new Territory("Carnen")); //6
		allTerritories.add(new Territory("Coombe")); //7
		allTerritories.add(new Territory("Chamber of Mazarbul")); //8
		allTerritories.add(new Territory("Dale")); //9
		allTerritories.add(new Territory("Dagorlad")); //10
		allTerritories.add(new Territory("Dorwinion")); //11
		allTerritories.add(new Territory("Dunland")); //12
		allTerritories.add(new Territory("Edoras")); //13
		allTerritories.add(new Territory("Eregion")); //14
		allTerritories.add(new Territory("Ethring")); //15
		allTerritories.add(new Territory("Forlond")); //16
		allTerritories.add(new Territory("Gondor")); //17
		allTerritories.add(new Territory("Mordor")); //18
		allTerritories.add(new Territory("Harad")); //19
		allTerritories.add(new Territory("Eriador")); //20
		allTerritories.add(new Territory("Hornburg")); //21
		allTerritories.add(new Territory("Kelos")); //22
		allTerritories.add(new Territory("Shire")); //23
		allTerritories.add(new Territory("Hobbiton")); //24
		allTerritories.add(new Territory("Rivendell")); //25
		allTerritories.add(new Territory("Isengard")); //26
		allTerritories.add(new Territory("Pelennor")); //27
		allTerritories.add(new Territory("Mirkwood")); //28
		allTerritories.add(new Territory("Ithilien")); //29
		allTerritories.add(new Territory("Moria")); //30
		
		// Sets all the borders for each territory
		setTerritoryBorders(0, 4, 27, 26, 8, 20);
		setTerritoryBorders(1, 25, 15, 20, 13);
		setTerritoryBorders(2, 30, 4, 7);
		setTerritoryBorders(3, 29, 28, 30,6);
		setTerritoryBorders(4, 20, 0, 27, 2, 30,6);
		setTerritoryBorders(5, 20, 13, 23, 8);
		setTerritoryBorders(6, 28, 20, 11, 18, 30, 3, 4);
		setTerritoryBorders(7, 16, 2, 30);
		setTerritoryBorders(8, 20, 0, 17, 23, 5, 21);
		setTerritoryBorders(9, 23, 24, 17, 21);
		setTerritoryBorders(10, 13, 25, 19, 14);
		setTerritoryBorders(11, 18, 6);
		setTerritoryBorders(12, 23, 13, 19);
		setTerritoryBorders(13, 23, 12, 19, 10, 25, 5,1);
		setTerritoryBorders(14, 10, 19, 22);
		setTerritoryBorders(15, 20, 1, 18);
		setTerritoryBorders(16, 7);
		setTerritoryBorders(17, 9, 8, 24,21);
		setTerritoryBorders(18, 20, 11, 6, 15);
		setTerritoryBorders(19, 13, 12, 10, 14);
		setTerritoryBorders(20, 0, 4, 6, 18, 15, 1, 5, 8);
		setTerritoryBorders(21, 9, 17, 23, 8);
		setTerritoryBorders(22, 14);
		setTerritoryBorders(23,9, 21, 5, 13, 12, 8 );
		setTerritoryBorders(24, 9, 17);
		setTerritoryBorders(25, 13, 1, 10);
		setTerritoryBorders(26, 27, 0);
		setTerritoryBorders(27, 0, 4, 26);
		setTerritoryBorders(28, 3, 6);
		setTerritoryBorders(29, 3);
		setTerritoryBorders(30, 4, 2, 7, 6, 3);
		
		// Give each player 6 territories
		ArrayList<Territory> availableTerritories = new ArrayList<Territory>(allTerritories); // duplicate list of all of the territories
		for(Player p : players) { // this is looping through the list of players
			for(int i = 0; i< 6; i++) { // giving each player 6 territories
			Random rand = new Random();  
			int indx = rand.nextInt(availableTerritories.size()); 
			p.addTerritory(availableTerritories.get(indx)); // it adds the randomly picked territory to the player
			availableTerritories.remove(indx); // this removes the territory that was just given to the player from the available territories to pick from
		}
		}
		
	}
	// This method sets the borders for the specific territory 
	// int... allows you to input different number of parameters for each time you call the method --> had to look this up 
	private void setTerritoryBorders(int middleTerritory, int... bordingTerritories) { // parameters are the territory you are looking at and then its a variable argument that allows you to have a different amount of bordering territories
		ArrayList<Territory> finalTerritories = new ArrayList<Territory>(); // create empty list for the final bordering territories
		
		for(int i = 0; i < bordingTerritories.length; i++) { // loop through the variable argument and add the territory with that index to the final list
			finalTerritories.add(allTerritories.get(bordingTerritories[i]));
		}
		
		allTerritories.get(middleTerritory).borderingTerritories = finalTerritories; // give the final list to the middle territory
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
		playerTurn = (playerTurn +1) % 4;
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
	}
	
	
	public void attack(Player attackingPlayer, Territory attackingTerritory,  Player defendingPlayer, Territory defendingTerritory) {
		System.out.println("Player " + attackingPlayer.playerNum + " is now attacking " + "Player: "+defendingPlayer.playerNum);
		System.out.println("\nRolling dice...");
		int numOfAttackingDice = getNumDice(false, attackingTerritory.numTroops); // num of dice the attacker needs
		int numOfDefendingDice = getNumDice(true, defendingTerritory.numTroops);  // num fo dice the defender needs
		
		ArrayList <Integer> attackingDiceList = rollDice(numOfAttackingDice); 
		ArrayList <Integer> defendingDiceList = rollDice(numOfDefendingDice);
		
		attackingDiceList.sort(null); // sorts the list from lowest to highest
		defendingDiceList.sort(null); // sorts the list from lowest to highest
		
		// Had to look this up, needed a way to list the dice from highest to lowest
		Collections.reverse(attackingDiceList); // This reverses the order of the list, now its in highest to lowest order
		Collections.reverse(defendingDiceList); // This reverses the order of the list, now its in highest to lowest order
		
		System.out.println("\nPlayer " + attackingPlayer.playerNum + " rolled: " + attackingDiceList+ "\nPlayer " + defendingPlayer.playerNum + " rolled: "+defendingDiceList);
		
		int lowestNumDice = Math.min(numOfAttackingDice, numOfDefendingDice); // this returns the smallest number between the attacking an defending to see how many dice it needs to loop through
		
		for(int i = 0; i < lowestNumDice; i++) { // this loops through the list, it is checking the smallest number of dice, either from the defender or attacker
			System.out.println("\nRoll number: " + i + 1);
			if(defendingDiceList.get(i) >= attackingDiceList.get(i)) { // if the defender rolls the higher dice
				attackingTerritory.numTroops -= 1; // the attacker losses a troop
				System.out.println("Attacker: " + attackingDiceList.get(i) + ", Defender: " + defendingDiceList.get(i));
				System.out.println("Player "+ attackingPlayer.playerNum +"'s territory lost one troop.\n");
			}
			else { // if the attacker rolls the higher dice
				defendingTerritory.numTroops -= 1;  // defender losses a troop
				System.out.println("Player "+ defendingPlayer.playerNum +"'s territory lost one troop.\n");
				}
			
		}
		// This gives the defender's territory to the attacker if the defending territory has 0 troops
		if(defendingTerritory.numTroops <= 0) {
			transferTerritory(defendingPlayer, attackingPlayer, defendingTerritory); // transfers territory 
			attackingTerritory.numTroops -= 1; // this is making sure the winner gives one of their troops to the new territory
			defendingTerritory.numTroops +=1; 
			attackingPlayer.troopCards +=1; 
			System.out.println("Player "+ attackingPlayer.playerNum +" won! They took " + defendingTerritory.name + "\n");
		}else {
			System.out.println("Player " + defendingPlayer.playerNum + " won!");
		}
		
	}
		

	
}


