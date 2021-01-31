import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;

public class Game{
	
	//Here are all functions responsible for world generation, change, showing, etc.

	public static Item[] itemTable(){
		String[] data;
		Item[] itemTable = new Item[19];
		int i = 0;
		try (
				BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Pigeon\\Desktop\\PPJ_project\\game_project\\itemTable.txt"));
		){
			while (((data = reader.readLine().split(" "))!=null)) {
				if (i < 3) {
					itemTable[i] = new Weapon(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]));
				} else if (i < 11) {
					itemTable[i] = new Clothing(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), Integer.parseInt(data[3]));
				}
				i += 1;
			}
		}catch (Exception exception){
			for (int j = 11; j<14; j++) itemTable[j] = new Axe();
			for (int j = 14; j<19; j++) itemTable[j] = new HealthPotion(1, "Healing Potion", 240);
		}
		return itemTable;
	}

	public static Enemy[] enemyTable(){
		String data;
		Enemy[] enemyTable = new Enemy[10];
		int i = 0;
		try (
				BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Pigeon\\Desktop\\PPJ_project\\game_project\\enemyTable.txt"));
		){
			while (((data = reader.readLine())!=null)&&(i<10)) {
				enemyTable[i]=new Enemy(data, new Item[24], new Skill[]{new Skill("Health", (int)(Math.random()*2)), new Skill("Mana", (int)(Math.random()*2)), new Skill("Strength", (int)(Math.random()*2)), new Skill("Agility", (int)(Math.random()*2)), new Skill("Intelligence", (int)(Math.random()*2))}, 0);
				i+=1;
			}
		}catch (Exception exception){
			for (int j=0; j<10; j++) enemyTable[j]=new Enemy("test_enemy",  new Item[24], new Skill[]{new Skill("Health", (int)(Math.random()*2)), new Skill("Mana", (int)(Math.random()*2)), new Skill("Strength", (int)(Math.random()*2)), new Skill("Agility", (int)(Math.random()*2)), new Skill("Intelligence", (int)(Math.random()*2))}, 0);

		}
		return enemyTable;
	}
	
	public static Entity[] worldPregen(int mapSize){
		double itemSpawnChance=0.0;
		double enemySpawnChance=0.0;
		double traderSpawnChance=0.0;
		Entity[] world = new Entity[mapSize];
		Item[] items = itemTable();
		Enemy[] enemies = enemyTable();
		for (int i = 0; i<143; i++){
			if ((int)(Math.random()+itemSpawnChance)>=1){
				int itemChance = (int)(Math.random()*19);
				if ((int)(Math.random()*2)!=1) itemChance-=items[itemChance].getRarity();
				world[i]=items[itemChance];
				itemSpawnChance = 0.0;
			}else{
				itemSpawnChance+=0.035961;
				if ((int)(Math.random()+enemySpawnChance)>=1){
					int enemyChance = (int)(Math.random()*10);
					world[i]=enemies[enemyChance];
					((Humanoid)world[i]).setPosition(i);
					enemySpawnChance = 0.0;
				}else{
					enemySpawnChance+=0.035961;
					if ((int)(Math.random()+traderSpawnChance)>=1){
						world[i]=new Trader("Travelling merchant", new Item[24], new Skill[]{new Skill("Health", (int)(Math.random()*2)), new Skill("Mana", (int)(Math.random()*2)), new Skill("Strength", (int)(Math.random()*2)), new Skill("Agility", (int)(Math.random()*2)), new Skill("Intelligence", (int)(Math.random()*2))}, i);
						for (int j = 0; j<(int)(Math.random()*6); j++) ((Trader)world[i]).takeItem(items[(int)(Math.random()*19)]);
						traderSpawnChance = 0.0;
					}else{
						traderSpawnChance+=0.035961;
					}
				}
			}
		}
		return world;
	}
	
	public static Terrain[] terrainPregen(int mapSize){
		Terrain[] world = new Terrain[mapSize];
		for (int i = 0; i<143; i++){
			int terrainType = (int)(Math.random()*3);
			if ((terrainType==0)||(terrainType==1)){
				world[i]=new Grassland();
			}else if (terrainType==2){
				world[i]=new Forest((int)(Math.random()*26));
			}
			//int riverStart = (int)(Math.random()*143);
			//world=riverGenerator(world, riverStart, riverStart, 0);
		}
		return world;
	}
	
	 /*public static Terrain[] riverGenerator(Terrain[] world, int i, int firstTry, int exceptionCount){
		world[i]=new River();
		if (exceptionCount<2){
			try{
				switch ((int)(Math.random()*6)) {
					case (0):
						riverGenerator(world, i - 13, firstTry, exceptionCount);
						break;
					case (1):
						riverGenerator(world, i - 6, firstTry, exceptionCount);
						break;
					case (2):
						riverGenerator(world, i + 7, firstTry, exceptionCount);
						break;
					case (3):
						riverGenerator(world, i + 13, firstTry, exceptionCount);
						break;
					case (4):
						riverGenerator(world, i + 6, firstTry, exceptionCount);
						break;
					case (5):
						riverGenerator(world, i - 7, firstTry, exceptionCount);
						break;
					}
				}catch(java.lang.IndexOutOfBoundsException exception){
					riverGenerator(world, firstTry, firstTry, exceptionCount+1);
				}
			}
		 return world;
		}*/

	
	public static Entity[] worldChange(Entity[] worldLayer){
		Entity[] moveEntityResult = new Entity[worldLayer.length];
		for (Entity entity : worldLayer){
			if ((entity instanceof Enemy)||(entity instanceof Trader)){
				switch ((int)(Math.random()*7)){
					case(0):
						moveEntityResult = moveEntity(-13, entity, worldLayer);
						break;
					case(1):
						moveEntityResult = moveEntity(-6, entity, worldLayer);
						break;
					case(2):
						moveEntityResult = moveEntity(7, entity, worldLayer);
						break;
					case(3):
						moveEntityResult = moveEntity(13, entity, worldLayer);
						break;
					case(4):
						moveEntityResult = moveEntity(6, entity, worldLayer);
						break;
					case(5):
						moveEntityResult = moveEntity(-7, entity, worldLayer);
						break;
					case(6):
				}
			}
		}
		return moveEntityResult;
	}
	
	public static void worldShow(Entity[] worldLayer, Entity[] playerLayer, Terrain[] terrainLayer){
		int cellNum=7;
		int currStart=0;
		for (int j=0; j<22; j++){
			if (cellNum==6) System.out.print(" ");
			for (int i = currStart; i<currStart+cellNum; i++){
				if (playerLayer[i]!=null){
					System.out.print("*");
				}else if (terrainLayer[i] instanceof Forest){
					System.out.print("f");
				}else if(worldLayer[i] instanceof Enemy) {
					System.out.print("@");
				}else if (worldLayer[i] instanceof Item){
					System.out.print("?");
				}else if (worldLayer[i] instanceof Trader){
					System.out.print("$");
				}else if (terrainLayer[i] instanceof Grassland){
					System.out.print("g");
				}
				System.out.print(" ");
			}
			currStart+=cellNum;
			if (cellNum==7){
				cellNum=6;
			}else{
				cellNum=7;
			}
			System.out.println();
		}
	}

	public static Terrain[] terrainChange(Player currentPlayer, Terrain[] terrainLayer){
		Scanner chop = new Scanner(System.in);
		if ((terrainLayer[currentPlayer.getPosition()]instanceof Forest)&&currentPlayer.hasAxe()) {
			System.out.println("Press E if you want to chop this forest down. Otherwise press enter and proceed.");
			String chopDecision = chop.nextLine();
			if (chopDecision.equals("e")||chopDecision.equals("E")) {
				currentPlayer.takeItem(terrainLayer[currentPlayer.getPosition()].loot());
				terrainLayer[currentPlayer.getPosition()] = new Grassland();
			}
		}
		if ((terrainLayer[currentPlayer.getPosition()]instanceof Grassland)&&currentPlayer.canBuildHouse()) {
			System.out.println("Press E if you want to build a house here(warning: this will be the end of the game). Otherwise press enter and proceed.");
			String buildDecision = chop.nextLine();
			if (buildDecision.equals("e")||buildDecision.equals("E")) terrainLayer[currentPlayer.getPosition()]=new House(new Item[72]);
			System.out.println("Congrats! You've reached your aim and can live in peace from now on. Watch out for updates, in which you will be able to continue your story \n" +
					"by slaying the village sheriff who accused you of thievery you did not commit.");
			System.exit(0);
		}
		return terrainLayer;
	}
	
	public static Entity[][] moveMain(int index, Player currentPlayer, Entity[] worldLayer, Entity[] playerLayer, int mapSize){
		playerLayer[currentPlayer.getPosition()]=null;
		playerLayer[currentPlayer.move(index)]=currentPlayer;
		Entity[][] results = new Entity[2][mapSize];
		results[0]=worldLayer;
		results[1]=playerLayer;
		if (worldLayer[currentPlayer.getPosition()]!=null){
			System.out.println("There is something here...");
			if (worldLayer[currentPlayer.getPosition()] instanceof Enemy){
				System.out.println("It is an enemy!");
				results[0]=currentPlayer.fight((Enemy)worldLayer[currentPlayer.getPosition()], worldLayer);
			}
			if (worldLayer[currentPlayer.getPosition()] instanceof Item){
				System.out.println("It is an item!");
				results[0]=currentPlayer.interact((Item)worldLayer[currentPlayer.getPosition()], worldLayer);
			}
			if (worldLayer[currentPlayer.getPosition()] instanceof Trader){
				System.out.println("It is an trader!");
				Trader currentTrader = (Trader)worldLayer[currentPlayer.getPosition()];
				currentTrader.trade(currentPlayer);
				worldLayer[currentPlayer.getPosition()]=currentTrader;
				results[0]=worldLayer;
			}
		}
		return results;
	}
	
	public static Entity[] moveEntity(int index, Entity currentEntityRaw, Entity[] worldLayer){
		Humanoid currentEntity = (Humanoid) currentEntityRaw;
		worldLayer[currentEntity.getPosition()]=null;
		int indexOfMove = currentEntity.move(index);
		if (worldLayer[indexOfMove]!=null){
			if (worldLayer[indexOfMove] instanceof Enemy){
				worldLayer=currentEntity.fight((Enemy)worldLayer[indexOfMove], worldLayer);
				worldLayer[indexOfMove]=currentEntity;
			}
			if (worldLayer[indexOfMove] instanceof Item){
				if (currentEntity instanceof Trader){
					currentEntity.takeItem((Item)worldLayer[indexOfMove]);
					worldLayer[indexOfMove]=currentEntity;
				}else{
					if ((int)(Math.random()*2)>=1){
						currentEntity.takeItem((Item)worldLayer[indexOfMove]);
					}
					worldLayer[indexOfMove]=currentEntity;
				}
			}
			if (worldLayer[indexOfMove] instanceof Trader){
				worldLayer[currentEntity.getPosition()]=currentEntity;
			}
		}else{
			worldLayer[indexOfMove]=currentEntity;
		}
		return worldLayer;
	}
	
	public static void main (String[] args){
		Player testPlayer = new Player("John", new Item[24], new Skill[]{new Skill("Health", (int)(Math.random()*2)), new Skill("Mana", (int)(Math.random()*2)), new Skill("Strength", (int)(Math.random()*2)), new Skill("Agility", (int)(Math.random()*2)), new Skill("Intelligence", (int)(Math.random()*2))}, 68);
		int mapSize = 143;
		Entity[][] moveResult = new Entity[2][mapSize];
		Terrain[] terrainLayer = terrainPregen(mapSize);
		Entity[] worldLayer = worldPregen(mapSize);
		Entity[] playerLayer = new Entity[mapSize];
		Scanner scannerMain = new Scanner(System.in);
		playerLayer[68]=testPlayer;
		moveResult[0]=worldLayer;
		moveResult[1]=playerLayer;
		System.out.println("Your adventure begins here. Some tutorial for you: @ is for enemy, ? is for item, $ is for trader. Terrain types: f - forest, g - grassland. \n" +
				"There are things hiding in the woods you can not see unless you enter it, so keep an eye out. \n" +
				"You can earn money and experience by slaying your enemies. They might be dangerous, so you'd probably want to loot a bit before stumbling into foes. \n" +
				"You would really like to build yourself a house in grasslands to live in peace, as you were banished from your village for thieving.\n" +
				"Perhaps you could chop down some wood to do it, if only you could find an axe.\n");
		int moveCount = 0;
		while (!testPlayer.checkIfDead()){
			worldShow(worldLayer, playerLayer, terrainLayer);
			System.out.println();
			System.out.println("Make your move: W for North, D for North-East Up, X for North-East Down, S for South, Z for North-East Down, A for North-East Up, then press enter");
			String move = scannerMain.nextLine();
			switch (move){
				case("W"):
				case("w"):
					moveResult = moveMain(-13, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("D"):
				case("d"):
					moveResult = moveMain(-6, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("X"):
				case("x"):
					moveResult = moveMain(7, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("S"):
				case("s"):
					moveResult = moveMain(13, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("Z"):
				case("z"):
					moveResult = moveMain(6, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("A"):
				case("a"):
					moveResult = moveMain(-7, testPlayer, worldLayer, playerLayer, mapSize);
					break;
			}
			worldLayer=moveResult[0];
			playerLayer=moveResult[1];
			testPlayer.earnXp(20);
			testPlayer.checkLevel();
			if (!testPlayer.checkIfDead())testPlayer.setInventory(testPlayer.showInventory());
			testPlayer.cleanInventory();
			terrainLayer=terrainChange(testPlayer, terrainLayer);
			moveCount+=1;
			if (moveCount==10){
				moveCount=0;
				worldLayer=worldPregen(mapSize);
			}else {
				worldLayer = worldChange(worldLayer);
			}
		}
		System.out.println("Game over.");
	}
}