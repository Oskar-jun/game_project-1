public class Terrain{
	boolean isWalkable;
	boolean isLootable;
	Item requiredToLoot;
	Item possibleLoot;
	boolean isHabitable;
	Entity[] habitants;
	
	public Terrain(boolean isWalkable, boolean isLootable, Item requiredToLoot, Item possibleLoot, boolean isHabitable, Entity[] habitants){
		this.isWalkable=isWalkable;
		this.isLootable=isLootable;
		this.requiredToLoot=requiredToLoot;
		this.possibleLoot = possibleLoot;
		this.isHabitable=isHabitable;
		this.habitants=habitants;
	}
	
	public Terrain(boolean isWalkable, boolean isLootable, Item requiredToLoot, Item possibleLoot){
		this.isWalkable=isWalkable;
		this.isLootable=isLootable;
		this.requiredToLoot=requiredToLoot;
		this.possibleLoot = possibleLoot;
		this.isHabitable=false;
		this.habitants=null;
	}
	
	public Terrain(boolean isWalkable, boolean isHabitable, Entity[] habitants){
		this.isWalkable=isWalkable;
		this.isLootable=false;
		this.requiredToLoot=null;
		this.possibleLoot = null;
		this.isHabitable=isHabitable;
		this.habitants=habitants;
	}
	
	public Terrain(boolean isWalkable){
		this.isWalkable=isWalkable;
		this.isLootable=false;
		this.requiredToLoot=null;
		this.possibleLoot = null;
		this.isHabitable=false;
		this.habitants=null;
	}
}