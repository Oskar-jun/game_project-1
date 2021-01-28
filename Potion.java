public class Potion extends Item{
	int amountRestored = 25;
	
	public Potion(int rarity, String name, int price){
		super(rarity, name, price, true);
	}
}