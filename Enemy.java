public class Enemy extends Humanoid{
	public Enemy(String name, Item[] inventory, Skill[] skills, int position){
		super (name, inventory, skills, position, new Weapon(1, "Knife", 5));
	}
}