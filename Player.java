import java.util.Scanner;

class Player {
	
	String name;
	String icon;
	int level, health, healthMax, attackPower, defendPower, exp;
	int xPos, yPos, steps, battleSpeed, battleCounter;
	
	boolean[] equipState;
	String[] inventory;
	
	Player(String name) {
		this.name = name;
		this.icon = " \uD83D\uDE36  ";
		this.level = 1;
		this.health = 30;
		this.healthMax = 30; //initial health
		this.attackPower = 4;
		this.defendPower = 2;
		this.exp = 0;
		this.xPos = 5; //initial starting position
		this.yPos = 5; //initial starting position
		this.steps = 0;
		this.battleSpeed = 500; //milliseconds
		this.inventory = initInventory();
		this.equipState = new boolean[10];
	}	
	
	String[] initInventory() {
		String[] inventory = new String[10];
		
		for(int i = 0; i < inventory.length; i++) {
			inventory[i] = "[empty]";
		}
		
		// initial equipment
		inventory[0] = "Rock Hammer";
		inventory[1] = "Flashlight";
		inventory[2] = "Rope";
		inventory[3] = "Medpack";
	
		return inventory;
	}
	
	void getStatus() {
		Scanner scanner = new Scanner(System.in);
		String input = "";
		
		System.out.println("\nStatus:\n");
		System.out.println("Name: " + name);
		System.out.println("Level: " + level);
		System.out.println("Health: " + health + "/" + healthMax);
		System.out.println("Attack Power: " + attackPower);
		System.out.println("Defend Power: " + defendPower);
		System.out.println("Current Position: (" + yPos + ", " + xPos + ")");
		System.out.println("Number of Steps: " + steps);
		System.out.println("Number of Battles: " + battleCounter);
		//total damage dealt

			while(!input.toLowerCase().equals("j")) {
				System.out.print("\nEnter [j] to close status: ");
				input = scanner.next();
					if(!input.toLowerCase().equals("j"))
						System.out.println("Invalid input. Try again.");
			}
	}

}