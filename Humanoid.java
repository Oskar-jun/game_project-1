import java.util.Scanner; 

public class Humanoid extends Entity{
	private String name;
	private int health;
	private int mana;
	private int money;
	private int xp;
	private int level;
	private int currentPosition=0;
	private Item[] inventory = new Item[24];
	//Health, mana, strength, agility, intelligence
	private Skill[] skills = new Skill[5];
	private boolean isDead=false;
	private Item weapon;

	
	//Here are constructors
	
	public Humanoid(String name, Item[] inventory, Skill[] skills, int position){
		this.name = name;
		this.health = 100;
		this.mana = 100;
		this.money = 100;
		this.xp = 200;
		this.inventory = inventory;
		this.skills = skills;
		this.currentPosition=position;
		this.weapon=null;
	}

	public Humanoid(String name, Item[] inventory, Skill[] skills, int position, Item weapon){
		this.name = name;
		this.health = 100;
		this.mana = 100;
		this.money = 100;
		this.xp = 200;
		this.inventory = inventory;
		this.skills = skills;
		this.currentPosition=position;
		this.weapon=weapon;
	}
	
	public Humanoid(String name, int money, Item[] inventory, Skill[] skills, int position){
		this.name = name;
		this.health = 100;
		this.mana = 100;
		this.money = money;
		this.xp = 200;
		this.inventory = inventory;
		this.skills = skills;
		this.currentPosition=position;
	}
	
	//Here is the block of getters

	public int getHealth() {return this.health;}
	
	public int getPosition(){
		return this.currentPosition;
	}
	
	public int getMoney(){
		return this.money;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getSkill(int index){
		return skills[index].toInt();
	}
	
	public Item[] getInventory(){
		return this.inventory;
	}

	public int getXp() {return this.xp;}

	public int getWeaponDamage(){
		if (this.weapon!=null) return ((Weapon)this.weapon).getDamage();
		return 0;
	}
	
	//Here will be setters
	
	public void setInventory(Item[] inventory){
		this.inventory=inventory;
	}
	
	public void setPosition(int index){
		this.currentPosition=index;
	}

	public void setHealth(int amount){this.health=amount;}
	
	//Here will be all other functions responsible for interaction with the world
	
	//takeDamage, spendMana and spendMoney are responsible for healing and refilling as well
	public void takeDamage(int amount){
		if (amount<0) System.out.println("Gained "+(-1)*amount+" health");
		if ((this.health-amount)>0){
			this.health-=amount;
		}else{
			this.die();
		}
	}
	
	public boolean spendMana(int amount){
		if (amount<0) System.out.println("Gained "+(-1)*amount+" mana");
		if ((this.mana-amount)>0){
			this.mana-=amount;	
			return true;
		}else{
			System.out.println("Not enough mana");
			return false;
		}
	}
	
	public boolean spendMoney(int amount){
		if (this.money-amount>=0){
			this.money-=amount;
			System.out.println(this.name+"'s wallet contains "+this.money+" now.");
			return true;
		}else{
			System.out.println(this.name+"'s funds are unsufficient");
			return false;
		}
	}

	public void earnXp(int amount){
		this.xp+=amount;
	}
	
	public void takeItem(Item loot){
		for (int i = 0; i<24; i++) if (inventory[i]==null) {
			inventory[i]=loot;
			System.out.println(this.name+" picked up "+loot.getAmount()+" of "+inventory[i].getName());
			i=25;
		}else if (inventory[i].getName().equals(loot.getName())){
			inventory[i].addAmount(loot.getAmount());
			System.out.println(this.name+" picked up "+loot.getAmount()+" of "+inventory[i].getName());
			i=25;
		}
	}



	public boolean equipWeapon(Weapon weapon){
		Scanner scanner = new Scanner(System.in);
		if (this.weapon==null){
			this.weapon=weapon;
			return true;
		}else{
			System.out.println("Do you want to replace "+this.weapon.getName()+" with "+weapon.getName()+"? If so, press E.");
			String replaceConfirm = scanner.next();
			switch (replaceConfirm){
				case("E"):
				case("e"):
					this.takeItem(weapon);
					this.weapon=weapon;
					return true;
			}
		}
		this.takeItem(weapon);
		return false;
	}

	public void checkLevel(){
		if (this.xp/600==1){
			this.xp-=600;
			this.level+=1;
			System.out.println("Type in the number of skill you want to upgrade:\n" +
					"1. Health\n" +
					"2. Mana\n" +
					"3. Strength (influences the damage you cah deal in combat)\n" +
					"4. Agility (increases your chance to land a hit)\n" +
					"5. Intelligence (lets you barter with traders)\n");
			boolean upgradeDone=false;
			while(!upgradeDone) {
				Scanner scanner = new Scanner(System.in);
				int skillChoice = scanner.nextInt();
				try {
					upgradeDone=this.skills[skillChoice - 1].upgrade(1);
				} catch (ArrayIndexOutOfBoundsException e){
					System.out.println("Looks like you mistyped. Try again.");
				}
			}
			this.health=100+this.getSkill(1)*10;
			this.mana=100+this.getSkill(2)*10;
		}
	}

	public int move(int moveIndex){
		if ((moveIndex<0)&&((this.currentPosition+moveIndex)<0)){
			moveIndex=130;
		}else if ((moveIndex>0)&&((this.currentPosition+moveIndex)>=143)){
			moveIndex=-130;
		}
		this.currentPosition+=moveIndex;
		return this.currentPosition;
	}
	
	public Entity[] fight(Enemy currentEnemy, Entity[] worldLayer){
		int enemyWeaponDamage = currentEnemy.getWeaponDamage();
		int thisWeaponDamage = this.getWeaponDamage();
		while ((!currentEnemy.checkIfDead())&&(!this.isDead)){
			int hitChance = (int)(Math.random()*6);
			if (hitChance>2) {
				hitChance -= (int) (this.getSkill(3) * 0.34);
				if (hitChance < 3) hitChance = 3;
			}
			switch (hitChance){
				//0-2 enemy act
				case(0):
					this.takeDamage((int)(Math.random()*(currentEnemy.getSkill(2)))+enemyWeaponDamage*currentEnemy.getSkill(3));
					break;
				case(2):
					currentEnemy.takeDamage((int)(Math.random()*(currentEnemy.getSkill(2)))+10);
					break;
				//3-5 player act
				case(3):
					currentEnemy.takeDamage((int)(Math.random()*(this.getSkill(2)))+thisWeaponDamage*this.getSkill(3));
					break;
				case(5):
					this.takeDamage((int)(Math.random()*(this.getSkill(2)))+15);
					break;
			}
		}
		if (currentEnemy.checkIfDead()){ 
			worldLayer[currentEnemy.getPosition()]=null;
			this.spendMoney(-1*(currentEnemy.getMoney()));
			this.earnXp(currentEnemy.getXp());
		}else{
			worldLayer[this.getPosition()]=null;
		}
		return worldLayer;
	}



	public void cleanInventory(){
		boolean wasCleaned = false;
		int indexCleaned = -1;
		for (int i = 0; i<this.inventory.length;i++){
			if ((this.inventory[i]!=null)&&(this.inventory[i].getAmount()==0)) {
				this.inventory[i]=null;
				wasCleaned=true;
				indexCleaned=i;
			}else if (wasCleaned&&(i>indexCleaned)&&(this.inventory[i]!=null)){
				this.inventory[indexCleaned]=this.inventory[i];
				this.inventory[i]=null;
				indexCleaned=i;
			}
		}
	}
	
	public boolean checkIfDead(){
		return this.isDead;
	}
	
	public void die(){
		System.out.println(this.name+" died.");
		this.isDead = true;
	}
}