public class HealthPotion extends Potion{
	public HealthPotion(int rarity, String name, int price){
		super(rarity, name, price);
	}

	public HealthPotion(int rarity, String name, int price, int amount){
		super(rarity, name, price, amount);
	}
	
	public void addHealth(Player user){
		user.takeDamage(this.amountRestored*this.getRarity()*(-1));
	}
}