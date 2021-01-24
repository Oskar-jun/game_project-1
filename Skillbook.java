public class Skillbook extends Item{
	private int level;
	
	public Skillbook(int rarity, String name, int level){
		super(rarity, name);
		this.level = level;
	}
}