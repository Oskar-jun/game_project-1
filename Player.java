import java.util.Scanner; 

public class Player extends Humanoid{
	//0-head, 1-top, 2-gloves, 3-bottom, 4-boots
	private Clothing[] weared = new Clothing[5];
	private Item weapon;
	
	public Player(String name, Item[] inventory, Skill[] skills, int position){
		super (name, inventory, skills, position);
	}
	
	public boolean equipClothing(Clothing clothing){
		if (this.weared[clothing.getPlacement()]==null){
			this.weared[clothing.getPlacement()]=clothing;
			return true;
		}else{
			System.out.println("Do you want to replace "+this.weared[clothing.getPlacement()].getName()+" with "+clothing.getName()+"? If so, press E.");
			String replaceConfirm = scanner.next();
			switch (replaceConfirm){
				case("E"):
				case("e"):
					this.weared[clothing.getPlacement()]=clothing;
					return true;
					break;
			}
		}
		return false;
	}
	
	public boolean equipWeapon(Weapon weapon){
		if (this.weapon==null){
			this.weapon=weapon;
			return true;
		}else{
			System.out.println("Do you want to replace "+this.weapon.getName()+" with "+weapon.getName()+"? If so, press E.");
			String replaceConfirm = scanner.next();
			switch (replaceConfirm){
				case("E"):
				case("e"):
					this.weapon=weapon;
					return true;
					break;
			}
		}
		return false;
	}
	
	public showInventory(){
		for (int i = 0; i<24; i++){
			if (this.inventory[i]!=null){
				System.out.println((i+1)+". "+this.inventory[i].getAmount()+" of "+this.inventory[i].getName()+" for "+this.inventory[i].getPrice()+" each.");
			}
		}
		
		System.out.println("Type in the number of item you want to use/equip.");
		int choice = scanner.nextInt();
		//int amount = scanner.nextInt();
		if (((choice-1<24)&&(choice-1>=0))&&(this.inventory[choice-1]!=null)){
			if (this.inventory[choice-1] instanceof Clothing){
				if (equipClothing((Clothing)this.inventory[choice-1])) this.inventory[choice-1].addAmount(-1);
			}else if (this.inventory[choice-1] instanceof Weapon){
				if (equipWeapon((Weapon)this.inventory[choice-1])) this.inventory[choice-1].addAmount(-1);
			if ((this.inventory[choice-1].getAmount()>=amount)&&(this.inventory[choice-1].getConsumability())){
				
			}else{
				System.out.println("I have only "+traderInventory[choice-1].getAmount()+" of those.");
			}
		}else{
			System.out.println("Sorry, I do not have it. Try again.");
		}
	}
}