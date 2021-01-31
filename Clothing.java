public class Clothing extends Item{
	private int placement;
	private int damageResist;
	
	public Clothing(int rarity, String name, int placement, int damageResist){
		super(rarity, name);
		this.placement=placement;
		this.damageResist = damageResist;
	}
	public Clothing(int rarity, String name, int price, int amount, int placement, int damageResist){
		super(rarity, name, price, amount);
		this.placement=placement;
		this.damageResist = damageResist;
	}
	
	public int getPlacement(){
		return this.placement;
	}
	
	public int getDamageResist(){
		return this.damageResist;
	}
}