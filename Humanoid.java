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
	
	//Here are constructors
	
	public Humanoid(String name, Item[] inventory, Skill[] skills, int position){
		this.name = name;
		this.health = 100;
		this.mana = 100;
		this.money = 100;
		this.inventory = inventory;
		this.skills = skills;
		this.currentPosition=position;
	}
	
	public Humanoid(String name, int money, Item[] inventory, Skill[] skills, int position){
		this.name = name;
		this.health = 100;
		this.mana = 100;
		this.money = money;
		this.inventory = inventory;
		this.skills = skills;
		this.currentPosition=position;
	}
	
	//Here is the block of getters
	
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
	
	//Here will be setters
	
	public void setInventory(Item[] inventory){
		this.inventory=inventory;
	}
	
	public void setPosition(int index){
		this.currentPosition=index;
	}
	
	//Here will be all other functions responsible for interaction with the world
	
	//takeDamage, spendMana and spendMoney are responsible for healing and refilling as well
	public void takeDamage(int amount){
		if (amount<0) System.out.println("Gained "+amount+" health");
		if ((this.health-amount)>0){
			this.health-=amount;
		}else{
			this.die();
		}
	}
	
	public boolean spendMana(int amount){
		if (amount<0) System.out.println("Gained "+amount+" mana");
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
	
	public void upgradeSkill(int index, int points){
		if ((index>=0)&&(index<4)) skills[index].upgrade(points);
		System.out.println(skills[index].toString());
	}
	
	public void takeItem(Item loot, int amount){
		for (int i = 0; i<24; i++) if (inventory[i]==null) {
			inventory[i]=loot;
			inventory[i].setAmount(amount);
			System.out.println(this.name+" picked up "+inventory[i].getAmount()+" of "+inventory[i].getName());
			break;
		}
	}
	
	public void takeItem(Item loot){
		for (int i = 0; i<24; i++) if (inventory[i]==null) {
			inventory[i]=loot;
			System.out.println(this.name+" picked up "+inventory[i].getName());
			break;
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
		while ((!currentEnemy.checkIfDead())&&(!this.isDead)){
			switch ((int)(Math.random()*6)){
				//0-2 enemy act
				case(0):
					this.takeDamage((int)(Math.random()*(currentEnemy.getSkill(2)))+10);
					break;
				case(1):
					System.out.println(currentEnemy.getName()+" missed.");
					break;
				case(2):
					System.out.println(currentEnemy.getName()+" missed CRITICALLY.");
					currentEnemy.takeDamage((int)(Math.random()*(currentEnemy.getSkill(2)))+10);
					break;
				//3-5 player act
				case(3):
					currentEnemy.takeDamage((int)(Math.random()*(this.getSkill(2)))+15);
					break;
				case(4):
					System.out.println(this.getName()+" missed.");
					break;
				case(5):
					System.out.println(this.getName()+" missed CRITICALLY.");
					this.takeDamage((int)(Math.random()*(this.getSkill(2)))+15);
					break;
			}
		}
		if (currentEnemy.checkIfDead()){ 
			worldLayer[currentEnemy.getPosition()]=null;
		}else{
			worldLayer[this.getPosition()]=null;
		}
		return worldLayer;
	}
	
	public Entity[] interact(Item foundItem, Entity[] worldLayer){
		System.out.println("If you want to pick up "+foundItem.getName()+" press E, then Enter. Otherwise just press Enter and proceed.");
		Scanner scanner = new Scanner(System.in);
		String interact = scanner.nextLine();
		switch (interact){
			case("E"):
				this.takeItem(foundItem);
				worldLayer[this.currentPosition]=null;
				break;
			case("e"):
				this.takeItem(foundItem);
				worldLayer[this.currentPosition]=null;
				break;
		}
		return worldLayer;
	}
	
	public boolean checkIfDead(){
		return this.isDead;
	}
	
	public void die(){
		System.out.println(this.name+" died.");
		this.isDead = true;
	}
}