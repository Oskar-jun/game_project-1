public class Player extends Humanoid{
	//0-head, 1-top, 2-gloves, 3-bottom, 4-boots
	private Clothing[] weared = new Clothing[5];
	
	public Player(String name, Item[] inventory, Skill[] skills){
		super (name, inventory, skills);
	}
}