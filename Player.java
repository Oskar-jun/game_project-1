import java.util.InputMismatchException;
import java.util.Scanner;

public class Player extends Humanoid{
	//0-head, 1-top, 3-bottom, 4-boots
	private Clothing[] weared = new Clothing[4];
	
	public Player(String name, Item[] inventory, Skill[] skills, int position){
		super (name, inventory, skills, position);
	}

	public void takeDamage(int amount){
		int damageResist=0;
		for (Clothing item : weared) if (item!=null) damageResist+=item.getDamageResist();
			if (amount<0) System.out.println("Gained "+(-1)*amount+" health");
		if ((this.getHealth()-amount)>0){
			this.setHealth(this.getHealth()-amount);
		}else{
			this.die();
		}
	}
	
	public Entity[] interact(Item foundItem, Entity[] worldLayer){
		boolean isClothingOrWeapon = (foundItem instanceof Clothing)||(foundItem instanceof Weapon);
		System.out.println("If you want to pick up "+foundItem.getAmount()+" of "+foundItem.getName()+" press E, then Enter. Otherwise just press Enter and proceed.");
		if (isClothingOrWeapon) System.out.println("If you want to equip "+foundItem.getName()+" instead, press Q, then Enter. Otherwise just press Enter and proceed.");
		Scanner scanner = new Scanner(System.in);
		String interact = scanner.nextLine();
		switch (interact){
			case("E"):
			case("e"):
				this.takeItem(foundItem);
				worldLayer[this.getPosition()]=null;
		}
		if (isClothingOrWeapon){
			switch (interact) {
				case ("Q"):
				case ("q"):
					if (foundItem instanceof Clothing) {
						this.equipClothing((Clothing)foundItem);
						worldLayer[this.getPosition()] = null;
					} else {
						this.equipWeapon((Weapon)foundItem);
						worldLayer[this.getPosition()] = null;
					}
			}
		}
		return worldLayer;
	}
	
	public boolean equipClothing(Clothing clothing){
		Scanner scanner = new Scanner(System.in);
		if (this.weared[clothing.getPlacement()]==null){
			this.weared[clothing.getPlacement()]=clothing;
			System.out.println(this.getName()+" now wears "+this.weared[clothing.getPlacement()].getName()+".");
			scanner.nextLine();;
			return true;
		}else{
			System.out.println("Do you want to replace "+this.weared[clothing.getPlacement()].getName()+" with "+clothing.getName()+"? If so, press E.");
			String replaceConfirm = scanner.nextLine();
			switch (replaceConfirm){
				case("E"):
				case("e"):
					this.takeItem(clothing);
					this.weared[clothing.getPlacement()]=clothing;
					return true;
			}
		}
		this.takeItem(clothing);
		return false;
	}
	
	public Item[] showInventory(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type in I if you want to see the inventory.");
		String open = scanner.nextLine();
		boolean isEmpty = true;
		Item[] thisInventory = this.getInventory();
		if (open.equals("I")||open.equals("i")) {
			for (int i = 0; i < 24; i++) {
				if (thisInventory[i] != null) {
					isEmpty=false;
					System.out.println((i + 1) + ". " + thisInventory[i].getAmount() + " of " + thisInventory[i].getName() + " for " + thisInventory[i].getPrice() + " each.");
				}
			}
			if(!isEmpty) {
				System.out.println("Type in the number and amount of item you want to use/equip (For clothing and weapons amount is 1).");
				try {
					int choice = scanner.nextInt();
					int amount = scanner.nextInt();
					if (((choice - 1 < 24) && (choice - 1 >= 0)) && (thisInventory[choice - 1] != null)) {
						if (thisInventory[choice - 1] instanceof Clothing) {
							if (equipClothing((Clothing) thisInventory[choice - 1]))
								thisInventory[choice - 1].addAmount(-1);
						} else if (thisInventory[choice - 1] instanceof Weapon) {
							if (equipWeapon((Weapon) thisInventory[choice - 1]))
								thisInventory[choice - 1].addAmount(-1);
						} else if ((thisInventory[choice - 1].getAmount() >= amount) && (thisInventory[choice - 1].getConsumability())) {
							if (thisInventory[choice - 1] instanceof HealthPotion) {
								for (int i = 0; i < amount; i++)
									((HealthPotion) thisInventory[choice - 1]).addHealth(this);
							} else {
								for (int i = 0; i < amount; i++) ((ManaPotion) thisInventory[choice - 1]).addMana(this);
							}
							thisInventory[choice - 1].addAmount(-amount);
						} else {
							System.out.println("I have only " + thisInventory[choice - 1].getAmount() + " of those.");
							scanner.nextLine();
						}
					} else {
						System.out.println("Sorry, I do not have it.");
						scanner.nextLine();
					}
				}catch (InputMismatchException e){
					System.out.println("That's not a number");
					scanner.nextLine();
				}
			}else{
				System.out.println("I have nothing right now.");
				scanner.nextLine();
			}
		}
		return thisInventory;
	}

	public boolean canBuildHouse(){
		Item[] thisInventory = this.getInventory();
		for (int i = 0; i < 24; i++) {
			if ((thisInventory[i] != null)&&(thisInventory[i] instanceof Plank)&&(thisInventory[i].getAmount()>500)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAxe(){
		Item[] thisInventory = this.getInventory();
		for (int i = 0; i < 24; i++) {
			if ((thisInventory[i] != null)&&(thisInventory[i] instanceof Axe)) {
				return true;
			}
		}
		return false;
	}
}