public class HealthPotion extends Potion{
	public HealthPotion(int rarity, String name, int price){
		super(rarity, name, price);
	}
	
	public void addHealth(Player user){
		user.takeDamage(this.amountRestored*this.rarity*(-1));
	}
}