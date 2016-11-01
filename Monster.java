class Monster {

	String name;
	String icon;
	int health;
	int attackPower;
	int defendPower;
	int exp;

	Monster(int i) {
		if(i == 1) {
			this.name = "Flying Squid";
			this.icon = " \uD83D\uDC7E ";
			this.health = 80;
			this.attackPower = 5;
			this.defendPower = 3;
			this.exp = 15;
		}
		else if(i == 2) {
			this.name = "Ogre";
			this.icon = " \uD83D\uDC79 ";
			this.health = 100;
			this.attackPower = 8;
			this.defendPower = 5;
			this.exp = 20;
		}
		else if(i == 3) {
			this.name = "Ghost";
			this.icon = " \uD83D\uDC7B ";
			this.health = 25;
			this.attackPower = 10;
			this.defendPower = 1;
			this.exp = 10;
		}
		else if(i == 4) {
			this.name = "Skeleton";
			this.icon = " \uD83D\uDC80 ";
			this.health = 50;
			this.attackPower = 6;
			this.defendPower = 4;
			this.exp = 10;
		}
	}

}