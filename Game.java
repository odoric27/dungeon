/* Dungeon_v1
 *
 * to add:
 * increased function for atk/def numbers in battle
 * functions for each items
 * make Class for dungeon map; make second map
 * new monsters
 * settings menu
 * error checking battle for incorrect player input
 * error checking for main manu input
 * item checking in battle, loops if non-usable item selected
 * flashlight in field - area within grid around player becomes illuminated, hidden items can be picked up
 * flashlight in battle - temporarily blinds enemy, accuracy lower or no hit next turn
 * rope - use to swing over gaps?
 * need way to store multiple med packs and note amount in inventory
 * should there be weapons? if yes, need separate equip menu
 */

import java.util.Scanner;

public class Game {
	
	static String doorIcon = " \uD83D\uDEAA  ";
	static String keyIcon = " \uD83D\uDD11  ";
	static String flagIcon = " \uD83D\uDEA9  ";
	static String mountainIcon = "\u303D\u303D";
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String mainInput = "";
		char playerButton = ' ';
		int battleCounter = 0;
		int randomNum = 0;
		
		System.out.println("\nAh, my head... where am I... who am I...?");
		System.out.print("Enter your name: ");
		
		mainInput = scanner.nextLine();
		
		Player player = new Player(mainInput);
		Dungeon d = new Dungeon();
		
		System.out.println("\nMysterious Voice: You must've hit your head pretty hard, " +
						   player.name + ". You're in the dungeon. Good luck getting out.\n");
		
		System.out.println(player.name + ": Well, shit... Guess I'll look around.");
		 		 
		 while(true) {
		 	printDungeon(d, playerButton, player);		 
			playerControls();
		 	mainInput = scanner.next();
		 	playerButton = mainInput.toLowerCase().charAt(0);
			 
		 	 	switch(playerButton) {
					case 'w': player.xPos--; break;
					case 'd': player.yPos++; break;
					case 's': player.xPos++; break;
					case 'a': player.yPos--; break;
					case 'i': checkInventory(player); break;
					case 'j': player.getStatus(); break;
					//case 'm': checkSettings(player); break;
				}
			
				if(isWalking(mainInput)) {
					player.steps++;
					randomNum = randomNum(player.steps); //used for random encounters
				}
				
				//random encounter generator
		 	 	if(player.steps >= 10 && player.steps % randomNum == 0){
		 	 		battle(player);
		 	 		player.battleCounter++;
		 	 	}
		}
	} // close main method
	
	//design classes for each equippable item... need better way to make changes to dungeon map
	public static void printDungeon(Dungeon d, char button, Player player) {
		String output = "";
		
			for(int i = 0; i < d.dungeonMap.length; i++) {
				for(int j = 0; j < d.dungeonMap[i].length; j++) {
					if(j == player.yPos && i == player.xPos && d.dungeonMap[i][j].equals(Dungeon.mountainIcon)) {
						output = "The path is blocked...\n";
							switch(button) {
								case 'w': player.xPos++; break;
								case 'd': player.yPos--; break;
								case 's': player.xPos--; break;
								case 'a': player.yPos++; break;
							}
						d.dungeonMap[player.xPos][player.yPos] = player.icon;
					}
					else if(j == player.yPos && i == player.xPos && d.dungeonMap[i][j].equals(" x  ") && !player.equipState[0]) {
						output = player.name + ": I could probably break through here...\n";
							switch(button) {
								case 'w': player.xPos++; break;
								case 'd': player.yPos--; break;
								case 's': player.xPos--; break;
								case 'a': player.yPos++; break;
							}
						d.dungeonMap[player.xPos][player.yPos] = player.icon;
					}
					else if(j == player.yPos && i == player.xPos && d.dungeonMap[i][j].equals(" x  ") && player.equipState[0]) {
						d.dungeonMap[i][j] = player.icon;
						output = "Smashed with rock hammer!\n";
					}
					else if(j == player.yPos && i == player.xPos && d.dungeonMap[i][j].equals(Dungeon.keyIcon)) {
						d.dungeonMap[i][j] = player.icon;
						output = player.name + ": Hm... an old key. Wonder what it goes to...\n";
						player.inventory[9] = "Key";
					}
					else if(j == player.yPos && i == player.xPos && d.dungeonMap[i][j].equals(Dungeon.doorIcon) && !player.equipState[9]) {
						output = "The door is locked.\n" + player.name + ": If only I had the key...\n";
							switch(button) {
								case 'w': player.xPos++; break;
								case 'd': player.yPos--; break;
								case 's': player.xPos--; break;
								case 'a': player.yPos++; break;
							}
						d.dungeonMap[player.xPos][player.yPos] = player.icon;
					}
					else if(j == player.yPos && i == player.xPos && d.dungeonMap[i][j].equals(Dungeon.doorIcon) && player.equipState[9]) {
						d.dungeonMap[i][j] = Dungeon.flagIcon;
						output = "Used key... it worked!\n";
						player.inventory[9] = "[empty]";
						player.equipState[9] = false;
					}
					else if(j == player.yPos && i == player.xPos) {
						d.dungeonMap[i][j] = player.icon;
					}
					else if(d.dungeonMap[i][j].equals(player.icon) && !(j == player.yPos && i == player.xPos)) {
						d.dungeonMap[i][j] = " -  ";
					}
				}
			}
		
		//prints dungeon map
		System.out.println();
			for(int i = 0; i < d.dungeonMap.length; i++) {
				for(int j = 0; j < d.dungeonMap[i].length; j++) {
					System.out.print(d.dungeonMap[i][j]);
				}
				System.out.println();
			}
		System.out.println("\n" + output);
	}
	
	public static void playerControls() {
		String[][] controls = new String[5][30];
		
			//initialize controls array
			for(int i = 0; i < controls.length; i++) {
				for(int j = 0; j < controls[i].length; j++) {
					controls[i][j] = " ";
				}
			}
		
		controls[0][10] = "(move up)";
		controls[1][14] = "w     ";
		controls[2][5] = " (move right)";
		controls[2][4] = " d";
		controls[4][9] = "(move down)";
		controls[3][14] = "s     ";
		controls[2][0] = "(move left) ";
		controls[2][1] = "a";
		
		controls[0][25] = "i: inventory";
		controls[1][28] = "j: status";
		controls[2][9] = "m: settings";
		controls[3][28] = "x: [empty]";
		controls[4][23] = "x: [empty]";
		
		//prints control map
			for(int i = 0; i < controls.length; i++) {
				for(int j = 0; j < controls[i].length; j++) {
					System.out.print(controls[i][j]);
				}
				System.out.println();
			}
		System.out.print("\nEnter your selection: ");
	}
	
	public static void checkInventory(Player player) {
		Scanner scanner = new Scanner(System.in);
		String input = "";
		int intInput = -1;
		
		System.out.println("\n" + player.name + "'s Pack");
		
		//check for equipped items
		for(int i = 0; i < player.inventory.length; i++) {
			if(player.equipState[i])
				System.out.println("\n" + player.inventory[i] + " is equipped.\n");
		}
		
		
		//print inventory
		for(int i = 0; i < player.inventory.length; i++) {
			if(i == 5 && player.equipState[i])//break up inventory into 2 rows of 5 items
				System.out.format("\n[E]%-15s", player.inventory[i]);
			else if(i == 5)
				System.out.format("\n[%d]%-15s", (i + 1), player.inventory[i]);
			else if(player.equipState[i]) 
				System.out.format("[E]%-15s", player.inventory[i]);
			else
				System.out.format("[%d]%-15s", (i + 1), player.inventory[i]);
		}
		System.out.println();
		
			while(intInput < 1 || intInput > 10){
				System.out.print("\nEnter item number [#] to equip or [i] to close pack: ");
					if(scanner.hasNextInt())
						intInput = scanner.nextInt();
					else
						input = scanner.next();
				
				if(input.toLowerCase().equals("i")) {
					System.out.println("Closing pack...");
					break;
				}
				else if(intInput >= 1 && intInput <= 10) {
					//checks if selection is empty
					if(player.inventory[intInput - 1].equals("[empty]")) {
						System.out.println(player.name + ": I can only use what I have here...");
						intInput = -1;
						continue;
					}
					//equips item
					else {
						player.equipState[intInput - 1] = true;
						System.out.println("\n" + player.inventory[intInput - 1] + " is now equipped.");		
					}
					
					//checks inventory and unequips previous item, if any
					if(player.equipState[intInput - 1]) {
						for(int j = 0; j < player.inventory.length; j++) {
							if(j == (intInput - 1))
								continue;
							else if(player.equipState[j])
								player.equipState[j] = false;
						}
					}
					//checks selection for consumable item
					if(player.inventory[intInput - 1].equals("Medpack")) {
						System.out.println("Health restored!");
						player.health += 50;
							if(player.health > player.healthMax)
								player.health = player.healthMax;
						player.equipState[intInput - 1] = false;
						player.inventory[intInput - 1] = "[empty]";
					}
				} else
					System.out.println(player.name + ": I can only use what I have here...");
			}		
		intInput = -1; //re-set sentinel value
	}
	
	public static void battle(Player player) {
		Scanner scanner = new Scanner(System.in);
		Monster monster = new Monster(randomNum());
		
		String temp = "";
		int input = 0;
		int playerAP = player.attackPower;
		int playerDP = player.defendPower;
		int monsterAP = monster.attackPower;
		int monsterDP = monster.defendPower;
		
		System.out.println("\n" + monster.name + monster.icon + " appeared!");
		
		while(monster.health > 0) {
			do {
				battleControls(player);
					if(scanner.hasNextInt())
						input = scanner.nextInt();
					else
						temp = scanner.next();
				
					switch(input) {
						case 1: //attack
							System.out.println("\n" + player.name + " attacks!");
								try {
									Thread.sleep(player.battleSpeed);
								} catch(InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
							System.out.println(monster.icon + " takes " + playerAP + " damage!");
							monster.health -= playerAP;	
								try {
										Thread.sleep(player.battleSpeed);
									} catch(InterruptedException ex) {
										Thread.currentThread().interrupt();
									}
							
							break;
						case 2: //defend
							System.out.println("\n" + player.name + " defends!");
								try {
									Thread.sleep(player.battleSpeed);
								} catch(InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
							playerDP += 3;
							break;
						case 3: //item
							checkInventory(player);
							break;
						case 4: //run
							System.out.println("\n" + player.name + " runs away!");
								try {
									Thread.sleep(player.battleSpeed);
								} catch(InterruptedException ex) {
									Thread.currentThread().interrupt();
								}
							break;
						default:
							System.out.println("Invalid input. Try again.");
							break;
					}
					if(input > 0 && input < 5)
						break;

			} while(input < 1 && input > 4 || !temp.equals("")); //close player battle action loop
			
				if(input == 4)
					break;
				else if(monster.health <= 0)
					break;
				else {
					System.out.println("\n" + monster.icon + " attacks!");
						try {
							Thread.sleep(player.battleSpeed);
						} catch(InterruptedException ex) {
							Thread.currentThread().interrupt();
						}
					System.out.println(player.name + " takes " + monsterAP + " damage!\n");
						player.health -= monsterAP;
							if(player.health <= 0) {
								System.out.println(monster.icon + " killed you!");
								System.out.println("\nGame over!");
								System.exit(0);
							}
						try {
							Thread.sleep(player.battleSpeed);
						} catch(InterruptedException ex) {
							Thread.currentThread().interrupt();
						}
				}
		} // close battle loop
		
			if(input != 4) {
				System.out.println("\n" + player.name + " defeated " + monster.name + monster.icon + "!");
					try {
						Thread.sleep(player.battleSpeed);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				System.out.println(player.name + " got " + monster.exp + " EXP!");
					try {
						Thread.sleep(player.battleSpeed);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				player.exp += monster.exp;
			}
		
			if(player.exp > 50) {
				player.level++;
				System.out.println(player.name + " gained a level!");
				//there should be a method that automate stat increases upon level increase
			}
	}
	
	public static void battleControls(Player player) {
		String[] controls = new String[4];
		
		controls[0] = "1: attack";
		controls[1] = "2: defend";
		controls[2] = "3: inventory";
		controls[3] = "4: run away";
		
			//prints current health graphic
			System.out.print("\n" + player.name + " " + player.level + " [");
				for(int i = 0; i < player.health; i = i + 3) {
					System.out.print("*");
				}
				for(int i = player.health; i < player.healthMax; i = i + 3) {
					System.out.print("_");
				}
			System.out.print("]");
			//prints battle controls
			System.out.println();
				for(int i = 0; i < controls.length; i++) {
					System.out.println(controls[i]);
				}
		System.out.print("\nEnter your selection: ");
	}
	
	public static void checkSettings(Player player) {
		//change battlespeed
		//change name
		// other??
	}
	
	public static boolean isWalking(String mainInput) {
		if(mainInput.equals("w") || mainInput.equals("d") ||
		   mainInput.equals("s") || mainInput.equals("a"))
			return true;
		else
			return false;
	}
	
	//generates random number between 1 & counter
	public static int randomNum(int counter) {
		int n = (int)(1 + Math.random() * counter);
		return n;
	}
	
	//generates random number between 1 & 4 (for monster generator)
	public static int randomNum() {
		int n = (int)(1 + Math.random() * 4);
		return n;
	}
}