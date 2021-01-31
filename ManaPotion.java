public class ManaPotion extends Potion{
	public ManaPotion(int rarity, String name, int price){
		super(rarity, name, price);
	}

	public ManaPotion(int rarity, String name, int price, int amount){
		super(rarity, name, price, amount);
	}
	
	public void addMana(Player user){
		user.spendMana(this.amountRestored*this.getRarity()*(-1));
	}
}