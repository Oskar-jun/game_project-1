import java.util.Scanner;

public class Item extends Entity{
	private int rarity;
	private String name;
	private int price;
	private int amount;
	
	public Item(int rarity, String name){
		this.rarity = rarity;
		this.name = name;
		this.price = 100;
		this.amount = 1;
	}
	
	public Item(int rarity, String name, int price, int amount){
		this.rarity = rarity;
		this.name = name;
		this.price = price;
		this.amount = amount;
	}
	
	public int getRarity(){
		return this.rarity;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getPrice(){
		return this.price;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public void addAmount(int amount){
		this.amount += amount;
	}
	
	public int getAmount(){
		return this.amount;
	}
	
	
}