import java.util.Scanner; 

public class Trader extends Humanoid{
	//0-head, 1-top, 2-gloves, 3-bottom, 4-boots
	private Clothing[] weared = new Clothing[5];
	
	public Trader(String name, Item[] inventory, Skill[] skills, int position){
		super (name, inventory, skills, position);
	}
	
	public Item[] trade(Player currentPlayer){
		boolean tradeDone = true;
		boolean traderInventoryEmpty = true;
		Scanner scanner = new Scanner(System.in);
		System.out.println("Here is what I have, adventurer");
						System.out.println("Type in E and press Enter if you want to trade: otherwise type in anything else and press Enter");
						//Can be shorter if brought down to chars
						String tradeBeginning = scanner.next();
						switch (tradeBeginning){
							case("E"):
							case("e"):
								tradeDone = false;
								break;
						}
		Item[] traderInventory = this.getInventory();
		while (!tradeDone){
			for (int i = 0; i<24; i++){
				if (traderInventory[i]!=null){
					System.out.println((i+1)+". "+traderInventory[i].getAmount()+" of "+traderInventory[i].getName()+" for "+traderInventory[i].getPrice()+" each.");
					traderInventoryEmpty = false;
				}
			}
			if (!traderInventoryEmpty){
				System.out.println("Type in the number of desired item and it's amount (press Enter after each of them)");
				int choice = scanner.nextInt();
				int amount = scanner.nextInt();
				if (((choice-1<24)&&(choice-1>=0))&&(traderInventory[choice-1]!=null)){
					if (traderInventory[choice-1].getAmount()>=amount){
						System.out.println("This will cost you "+amount*traderInventory[choice-1].getPrice()+".");
						if (currentPlayer.spendMoney(amount*traderInventory[choice-1].getPrice()*(-1))){
							currentPlayer.takeItem(new Item (traderInventory[choice-1].getRarity(), traderInventory[choice-1].getName(), traderInventory[choice-1].getPrice(), amount));
							if (traderInventory[choice-1].getAmount()==amount){
								traderInventory[choice-1]=null;
							}else{
								traderInventory[choice-1].addAmount(amount*(-1));
							}
							System.out.println("Type in E and press Enter if you are done: otherwise trading will start again");
							//Can be shorter if brought down to chars
							String tradeContinue = scanner.next();
							switch (tradeContinue){
								case("E"):
								case("e"):
									tradeDone = true;
									break;
							}
						}
					}else{
						System.out.println("Sorry, I have only "+traderInventory[choice-1].getAmount()+" of those.");
					}
				}else{
					System.out.println("Sorry, I do not have it. Try again.");
				}
			}else{
				System.out.println("Sorry, nothing in stock for now. Try again later.");
				tradeDone = true;
			}
		}
		return traderInventory;
	}
}