package game;

import java.util.Scanner;

public class User extends Player {

	
	
	
// Methods
	
	// This method takes the abstract Player method, and says what happens when its the user's turn
	public void doTurn() {
		System.out.println("It is now your turn!");
		Scanner input = new Scanner(System.in); // Input Scanner
		
		System.out.println("You have " + troopCards + " troop cards.\n"); // prints out the toString() from Player class
		placeTroops(); 
		
		String command = ""; 
		while(!command.equals("n")) {
			System.out.print("Do you want to attack a territory? (y/n): ");
			command = input.next().toLowerCase(); 
			while(!command.equals("y") && !command.equals("n")) {
				System.out.print("Do you want to attack a territory? (y/n): ");
				command = input.next().toLowerCase(); 
			}
			if (command.equals("y")) {
				attack();
			}
		}
	}

	
	private void attack() {
		// this is essentially how the user can go back if they accidently entered attack
		
 
					

		int territoryIndex = 0;
		System.out.println("\nYour Territories: \n" + getTerritoryIndices());
		System.out.println("Enter territory index to attack from (type -1 to cancel): ");
		territoryIndex = getIntInput();
		if (territoryIndex < 0) {
			return;
		}
		Territory attackFrom = null;
		
		// This makes sure that you pick the right index for the territory you want to attack
		// This loop will run until the user puts a valid index in
		while(territoryIndex < 0 || territoryIndex >= territories.size()) {
			System.out.println("Invalid Index. Try Again: ");
			territoryIndex = getIntInput(); 	// re asking for an index
		}
		
		attackFrom = territories.get(territoryIndex);
		// This checks that the if the territory is null, or if the territory has 1 troop
		while(attackFrom == null || attackFrom.numTroops < 2 || attackFrom.borderingTerritories(this).size() == 0) {		
			
			
			attackFrom = territories.get(territoryIndex); // this is the territory they are attacking from
			if (attackFrom.numTroops < 2) {
				System.out.println("This territory doesn't have enough troops to attack. You need 2 or more troops. Enter a different index: ");
				territoryIndex = getIntInput(); 
				attackFrom = territories.get(territoryIndex);
			}
			
			// This is checking to make sure you don't already occupy all bordering territories of the territory you want to attack from
			if (attackFrom.borderingTerritories(this).size() == 0) {
				System.out.println("You occupy all of the bordering territories of " + attackFrom.name + ". Pick another one: ");
				territoryIndex = getIntInput(); 
				attackFrom = territories.get(territoryIndex);
			}
		}
		
		System.out.println("Territories you can attack:\n" + attackFrom.getBorderingTerritoryIndices(this) ); // prints the bordering territories and their indices for attackFrom
		System.out.println("Enter territory index to attack: ");
		territoryIndex = getIntInput(); 
		while(territoryIndex < 0 || territoryIndex > attackFrom.borderingTerritories(this).size() - 1) { // this loop will run until the user puts a valid index in
			System.out.println("Invalid Index. Try Again: ");
			territoryIndex = getIntInput(); 	// re asking for an index
		}
		
		Territory defending = attackFrom.borderingTerritories(this).get(territoryIndex);
		
		GameManager.attack(this, attackFrom, defending.occupier, defending);
	}
	
	private void placeTroops() {
		Scanner input = new Scanner(System.in); // Input Scanner
		System.out.println("Place troops on your territories.\n");
		while(troops > 0) {
			System.out.println("Troops Left: " + troops + "\n");
			System.out.println("Your territories:");
			int territoryIndex = 0;
			System.out.println(getTerritoryIndices()); // prints out your territories with their index
			System.out.println("Enter territory index to place troops on: ");
		
			territoryIndex = getIntInput();
			while(territoryIndex < 0 || territoryIndex > territories.size()) { // this loop will run until the user puts a valid index in, if the index is less than what they have or more than what they have
				System.out.println("Invalid Index. Try Again: ");
				territoryIndex = getIntInput(); 	// re asking for an index
				}
			int troopsToPlace = 0;
			System.out.println("How many troops do you want to place?");
			troopsToPlace = getIntInput(); // setting troops to place as the user's input 
			troopsToPlace = takeTroops(troopsToPlace); // this is calling the function that checks if the amount of troops you want to place is valid and it removes the amount of troops from the total available troops a player can place
			while(troopsToPlace== -1) { // this while loop keeps asking for a valid number 
				troopsToPlace = getIntInput(); 
				troopsToPlace = takeTroops(troopsToPlace); 
			}
			territories.get(territoryIndex).addTroops(troopsToPlace); // this adds the user's valid number and it adds that many troops to the chosen territory
		}
	}

	private int getIntInput() {
		Scanner input = new Scanner(System.in); // Input Scanner
		while(!input.hasNextInt()) {
			System.out.println("Please enter a number.");
			input.next();
		}
		return input.nextInt();
	}
	
	// Constructor
	public User(int playerNum) {
		super(playerNum);
	}
}
