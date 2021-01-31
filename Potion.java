public class Potion extends Item{
	int amountRestored = 25;

	public Potion(int rarity, String name, int price){
		super(rarity, name, price,true);
	}

	public Potion(int rarity, String name, int price, int amount){
		super(rarity, name, price, amount,true);
	}
}