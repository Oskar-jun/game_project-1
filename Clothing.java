public class Clothing extends Item{
	private boolean isWeared;
	private int placement;
	private int damageResist;
	
	public Clothing(int rarity, String name, int placement, int damageResist){
		super(rarity, name);
		this.isWeared=false;
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