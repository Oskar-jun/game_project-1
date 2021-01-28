public class ManaPotion extends Potion{
	public ManaPotion(int rarity, String name, int price){
		super(rarity, name, price);
	}
	
	public void addMana(Player user){
		user.spendMana(this.amountRestored*this.rarity*(-1));
	}
}