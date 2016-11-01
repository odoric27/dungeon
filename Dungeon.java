class Dungeon {

	static String doorIcon = " \uD83D\uDEAA  ";
	static String keyIcon = " \uD83D\uDD11  ";
	static String flagIcon = " \uD83D\uDEA9  ";
	static String mountainIcon = "\u303D\u303D";
	String[][] dungeonMap = new String[20][20];
	
	//can have user input for difficulty as parameter
	Dungeon() {
		initDungeonEasy(dungeonMap);
	}
	
		
	public static void initDungeonEasy(String[][] map) {

		for(int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[i].length; j++) {
				//prints X X X around dungeon border
				if(j == 0 || i == 0)
					map[i][j] = mountainIcon;
				else if(j == 19 || i == 19)
					map[i][j] = mountainIcon;
				else if(j == 4 && i < 10)
					map[i][j] = " x  ";
				else if(j == 10 && i >= 10)
					map[i][j] = " x  ";
				else if(j == 11 && i >= 11)
					map[i][j] = " x  ";
				else if(j == 12 && i >= 13)
					map[i][j] = " x  ";
				else if(j == 13 && i >= 15)
					map[i][j] = " x  ";
				else if(j >= 14 && i >= 4 && i <= 8) {
					map[i][j] = " x  ";
					if(j == 16 && i == 6)
						map[i][j] = keyIcon;
				}
				else if(j <= 5 && i >= 16) {
					map[i][j] = " x  ";
					if(j == 3 && i == 17)
						map[i][j] = doorIcon;
				}
				else
					map[i][j] = " -  ";
			}
		}
	}
	
}