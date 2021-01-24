import java.util.Scanner; 

public class Game{
	
	public static void showField(Entity[] worldLayer, Entity[] playerLayer){
		int cellNum=7;
		int currStart=0;
		for (int j=0; j<22; j++){
			for (int i = currStart; i<currStart+cellNum; i++){
				//System.out.println("Curr i "+i);
				//System.out.println("Curr j "+j);
				//System.out.println("Curr cellNum "+cellNum);
				//System.out.println("Curr lastStart+cellNum "+(currStart+cellNum));
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
			//System.out.println("There is something here...");
			if (worldLayer[indexOfMove] instanceof Enemy){
				//System.out.println("It is an enemy!");
				worldLayer=currentEntity.fight((Enemy)worldLayer[indexOfMove], worldLayer);
				worldLayer[indexOfMove]=currentEntity;
			}
			if (worldLayer[indexOfMove] instanceof Item){
				//System.out.println("It is an item!");
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
				//System.out.println("It is an trader!");
				//Trader currentTrader = (Trader)worldLayer[currentEntity.getPosition()];
				//currentTrader.trade(currentEntity);
				//worldLayer[currentEntity.getPosition()]=currentTrader;
				//worldLayer[currentEntity.move(0)]=currentEntity;
				//results[0]=worldLayer;
			}
		}
		return worldLayer;
	}
	
	public static Entity[] worldPregen(int mapSize){
		double itemSpawnChance=0.0;
		//int itemCount = 0;
		//int enemyCount = 0;
		double enemySpawnChance=0.0;
		double traderSpawnChance=0.0;
		Entity[] world = new Entity[mapSize];
		for (int i = 0; i<143; i++){
			if ((int)(Math.random()+itemSpawnChance)>=1){
				world[i]=new Clothing(0, "test_clothing", (int)(Math.random()*4));
				itemSpawnChance = 0.0;
				//System.out.println (i+" test_clothing"); //itemCount+=1;
			}else{
				itemSpawnChance+=0.035961;
				if ((int)(Math.random()+enemySpawnChance)>=1){
					world[i]=new Enemy("test_enemy", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0), new Skill("Agility", 0), new Skill("Intelligence", 0)});
					enemySpawnChance = 0.0;
					//System.out.println (i+" test_enemy"); //enemyCount+=1;
				}else{
					enemySpawnChance+=0.035961;
					if ((int)(Math.random()+traderSpawnChance)>=1){
						world[i]=new Trader("test_trader", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0), new Skill("Agility", 0), new Skill("Intelligence", 0)});
						traderSpawnChance = 0.0;
						//System.out.println (i+" test_trader"); //enemyCount+=1;
						traderSpawnChance = 0.0;
					}else{
						traderSpawnChance+=0.035961;
					}
				}
			}
		}
		//System.out.println ("Generated "+itemCount+" test items and "+enemyCount+" test enemies.");
		return world;
	}
	
	public static Entity[] worldChange(Entity[] worldLayer){
		double entityMoveChance=0.0;
		Entity[] moveEntityResult = new Entity[worldLayer.length];
		for (Entity entity : worldLayer){
			if (((entity instanceof Enemy)||(entity instanceof Trader))&&((int)(Math.random()*2)>=1)){
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
	
	
	
	public static void main (String[] args){
		Player testPlayer = new Player("John", new Item[24], new Skill[]{new Skill("Health", 0), new Skill("Mana", 0), new Skill("Strength", 0), new Skill("Agility", 0), new Skill("Intelligence", 0)});
		int mapSize = 143;
		Entity[][] moveResult = new Entity[2][mapSize];
		Entity[] worldLayer = worldPregen(mapSize);
		Entity[] playerLayer = new Entity[mapSize];
		Entity[] terrainLayer = new Entity[mapSize];
		Scanner scannerMain = new Scanner(System.in);
		playerLayer[68]=testPlayer;
		testPlayer.setPosition(68);
		while (!testPlayer.checkIfDead()){
			showField(worldLayer, playerLayer);
			System.out.println("Make your move: W for North, D for North-East Up, X for North-East Down, S for South, Z for North-East Down, A for North-East Up, then press enter");
			String move = scannerMain.nextLine();
			switch (move){
				case("W"):
					moveResult = moveMain(-13, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("w"):
					moveResult = moveMain(-13, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("D"):
					moveResult = moveMain(-6, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("d"):
					moveResult = moveMain(-6, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("X"):
					moveResult = moveMain(7, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("x"):
					moveResult = moveMain(7, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("S"):
					moveResult = moveMain(13, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("s"):
					moveResult = moveMain(13, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("Z"):
					moveResult = moveMain(6, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("z"):
					moveResult = moveMain(6, testPlayer, worldLayer, playerLayer, mapSize);
					break;
				case("A"):
					moveResult = moveMain(-7, testPlayer, worldLayer, playerLayer, mapSize);
					break;
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