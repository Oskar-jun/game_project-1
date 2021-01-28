import java.util.Scanner; 

public class Game{
	
	//Here are all functions responcible for world generation, change, showing, etc.
	
	public static Entity[] worldPregen(int mapSize){
		double itemSpawnChance=0.0;
		double enemySpawnChance=0.0;
		double traderSpawnChance=0.0;
		Entity[] world = new Entity[mapSize];
		for (int i = 0; i<143; i++){
			if ((int)(Math.random()+itemSpawnChance)>=1){
				world[i]=new Clothing(0, "test_clothing", (int)(Math.random()*4));
				itemSpawnChance = 0.0;
			}else{
				itemSpawnChance+=0.035961;
				if ((int)(Math.random()+enemySpawnChance)>=1){
					world[i]=new Enemy("test_enemy", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0), new Skill("Agility", 0), new Skill("Intelligence", 0)}, i);
					enemySpawnChance = 0.0;
				}else{
					enemySpawnChance+=0.035961;
					if ((int)(Math.random()+traderSpawnChance)>=1){
						world[i]=new Trader("test_trader", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0), new Skill("Agility", 0), new Skill("Intelligence", 0)}, i);
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
			int terrainType = (int)(Math.random()*2);
			if (terrainType==0){
				world[i]=new Grassland();
			}else if (terrainType==1){
				world[i]=new Forest((int)(Math.random()*26));
			}
			int riverStart = (int)(Math.random()*143);
			//world=riverGenerator(world, riverStart, riverStart, 0);
		}
		return world;
	}
	
	public static Terrain[] riverGenerator(Terrain[] world, int i, int firstTry, int exceptionCount){
		world[i]=new River();
		if (exceptionCount<2){
			switch ((int)(Math.random()*6)){
				case(0):
					try{
						riverGenerator(world, i-13, firstTry, exceptionCount);
					}catch(java.lang.IndexOutOfBoundsException exception){
						riverGenerator(world, firstTry, firstTry, exceptionCount+1);
					}
					break;
				case(1):
					try{
						riverGenerator(world, i-6, firstTry, exceptionCount);
					}catch(java.lang.IndexOutOfBoundsException exception){
						riverGenerator(world, firstTry, firstTry, exceptionCount+1);
					}
					break;
				case(2):
					try{
						riverGenerator(world, i+7, firstTry, exceptionCount);
					}catch(java.lang.IndexOutOfBoundsException exception){
						riverGenerator(world, firstTry, firstTry, exceptionCount+1);
					}
					break;
				case(3):
					try{
						riverGenerator(world, i+13, firstTry, exceptionCount);
					}catch(java.lang.IndexOutOfBoundsException exception){
						riverGenerator(world, firstTry, firstTry, exceptionCount+1);
					}
					break;
				case(4):
					try{
						riverGenerator(world, i+6, firstTry, exceptionCount);
					}catch(java.lang.IndexOutOfBoundsException exception){
						riverGenerator(world, firstTry, firstTry, exceptionCount+1);
					}
					break;
				case(5):
					try{
						riverGenerator(world, i-7, firstTry, exceptionCount);
					}catch(java.lang.IndexOutOfBoundsException exception){
						riverGenerator(world, firstTry, firstTry, exceptionCount+1);
					}
					break;
			}
		}
		return world;
	}
	
	public static Entity[] worldChange(Entity[] worldLayer){
		double entityMoveChance=0.0;
		Entity[] moveEntityResult = new Entity[worldLayer.length];
		for (Entity entity : worldLayer){
			if ((entity instanceof Enemy)||(entity instanceof Trader)){
				switch ((int)(Math.random()*7)){
					case(0):
						moveEntityResult = moveEntity(-13, entity, worldLayer);
						entityMoveChance=0.0;
						break;
					case(1):
						moveEntityResult = moveEntity(-6, entity, worldLayer);
						entityMoveChance=0.0;
						break;
					case(2):
						moveEntityResult = moveEntity(7, entity, worldLayer);
						entityMoveChance=0.0;
						break;
					case(3):
						moveEntityResult = moveEntity(13, entity, worldLayer);
						entityMoveChance=0.0;
						break;
					case(4):
						moveEntityResult = moveEntity(6, entity, worldLayer);
						entityMoveChance=0.0;
						break;
					case(5):
						moveEntityResult = moveEntity(-7, entity, worldLayer);
						entityMoveChance=0.0;
						break;
					case(6):
						entityMoveChance+=0.03725;
				}
			}
		}
		return moveEntityResult;
	}
	
	public static void terrainShow(Terrain[] terrainLayer){
		int cellNum=7;
		int currStart=0;
		for (int j=0; j<22; j++){
			if (cellNum==6) System.out.print(" ");
			for (int i = currStart; i<currStart+cellNum; i++){
				if (terrainLayer[i] instanceof Forest){
					System.out.print("f");
				}else if (terrainLayer[i] instanceof Grassland){
					System.out.print("g");
				}else if (terrainLayer[i] instanceof River){
					System.out.print("r");
				}else{
					System.out.print("0");
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
	
	public static void worldShow(Entity[] worldLayer, Entity[] playerLayer){
		int cellNum=7;
		int currStart=0;
		for (int j=0; j<22; j++){
			if (cellNum==6) System.out.print(" ");
			for (int i = currStart; i<currStart+cellNum; i++){
				if (playerLayer[i]!=null){
					System.out.print("*");
				}else if (worldLayer[i] instanceof Enemy){
					System.out.print("@");
				}else if (worldLayer[i] instanceof Item){
					System.out.print("?");
				}else if (worldLayer[i] instanceof Trader){
					System.out.print("$");
				}else{
					System.out.print("0");
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
		Player testPlayer = new Player("John", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0), new Skill("Agility", 0), new Skill("Intelligence", 0)}, 68);
		int mapSize = 143;
		Entity[][] moveResult = new Entity[2][mapSize];
		Terrain[] terrainLayer = terrainPregen(mapSize);
		Entity[] worldLayer = worldPregen(mapSize);
		Entity[] playerLayer = new Entity[mapSize];
		Scanner scannerMain = new Scanner(System.in);
		playerLayer[68]=testPlayer;
		moveResult[0]=worldLayer;
		moveResult[1]=playerLayer;
		while (!testPlayer.checkIfDead()){
			worldShow(worldLayer, playerLayer);
			System.out.println();
			terrainShow(terrainLayer);
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
			worldLayer=worldChange(worldLayer);
		}
		System.out.println("Game over.");
	}
}