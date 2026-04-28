package game;

import java.util.Scanner;

public class User extends Player {

	
	
	
// Methods
	
	// This method takes the abstract Player method, and says what happens when its the user's turn
	public void doTurn() {
		System.out.println("It is now your turn! \n");
		Scanner input = new Scanner(System.in); // Input Scanner
		
		System.out.println(toString()); // prints out the toString() from Player class
		while(troops > 0) {
			System.out.println("Troops Left: " + troops + "\n");
			int territoryIndex = 0;
			System.out.println(getTerritoryIndices()); // prints out your territories with their index
			System.out.println("Enter territory index to place troops on: ");
			territoryIndex = input.nextInt(); 
			while(territoryIndex < 0 || territoryIndex > territories.size()) { // this loop will run until the user puts a valid index in, if the index is less than what they have or more than what they have
				System.out.println("Invalid Index. Try Again: ");
				territoryIndex = input.nextInt(); 	// re asking for an index
			}
			int troopsToPlace = 0;
			System.out.println("How many troops do you want to place?");
			troopsToPlace = input.nextInt(); // setting troops to place as the user's input 
			troopsToPlace = takeTroops(troopsToPlace); // this is calling the function that checks if the amount of troops you want to place is valid and it removes the amount of troops from the total available troops a player can place
			while(troopsToPlace== -1) { // this while loop keeps asking for a valid number 
				troopsToPlace = input.nextInt(); 
				troopsToPlace = takeTroops(troopsToPlace); 
			}
			territories.get(territoryIndex).addTroops(troopsToPlace); // this adds the user's valid number and it adds that many troops to the chosen territory
		}

		String command = "";
		while(!command.equals("end")) {
		System.out.println("Your Territories: " + getTerritories() + "\n");
		System.out.println(toString());
		System.out.println("Commands: \nattack: Attack a territory from a specified territory. \nend: Ends your turn.\n");
		
		System.out.print("Enter a Command: ");
        
		command = input.next().toLowerCase();  
		if(command.equals("attack")) {
			
			// this is essentially how the user can go back if they accidently entered attack
			System.out.print("Are you sure you want to attack? (y/n): ");
			command = input.next().toLowerCase();  
			
			
			while(!command.equals("y") && !command.equals("n")) {
				System.out.print("Are you sure you want to attack? (y/n): ");
				command = input.next().toLowerCase(); 
			}
			if(command.equals("n")) {
				continue;
			}
			int territoryIndex = 0;
			System.out.println(getTerritoryIndices());
			System.out.println("Enter territory index to attack from: ");
			
			Territory attackFrom = null;
			
			// This checks that the if the territory is null, or if the territory has 1 troop
			while(attackFrom == null || attackFrom.numTroops < 2) {
				territoryIndex = input.nextInt(); 
				
			// This makes sure that you pick the right index for the territory you want to attack
			// This loop will run until the user puts a valid index in
			while(territoryIndex < 0 || territoryIndex > territories.size()) {
				System.out.println("Invalid Index. Try Again: ");
				territoryIndex = input.nextInt(); 	// re asking for an index
			}
			
			attackFrom = territories.get(territoryIndex); // this is the territory they are attacking from
			if (attackFrom.numTroops < 2) {
				System.out.println("This territory doesn't have enough troops to attack. You need 2 or more troops. Enter a different index: ");
			}
			
			// This is checking to make sure you don't already occupy all bordering territories of the territory you want to attack from
			if (attackFrom.borderingTerritories(this).size() == 0) {
				System.out.println("You occupy all of the bordering territories of " + attackFrom.name + ". Pick another one: ");
			}
			}
			
			System.out.println(attackFrom.getBorderingTerritoryIndices(this)); // prints the bordering territories and their indices for attackFrom
			System.out.println("Enter territory index to attack: ");
			territoryIndex = input.nextInt(); 
			while(territoryIndex < 0 || territoryIndex > attackFrom.borderingTerritories(this).size() - 1) { // this loop will run until the user puts a valid index in
				System.out.println("Invalid Index. Try Again: ");
				territoryIndex = input.nextInt(); 	// re asking for an index
			}
			
			Territory defending = attackFrom.borderingTerritories(this).get(territoryIndex);
			
			manager.attack(this, attackFrom, defending.occupier, defending);
		}
		}
	}

	// Constructor
	public User(int playerNum, GameManager manager) {
		super(playerNum, manager);
	}
}
