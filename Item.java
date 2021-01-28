import java.util.Scanner;

public class Item extends Entity{
	private int rarity;
	private String name;
	private int price;
	private int amount;
	private boolean isConsumable;
	
	public Item(int rarity, String name){
		this.rarity = rarity;
		this.name = name;
		this.price = 100;
		this.amount = 1;
		this.isConsumable=false;
	}
	
	public Item(int rarity, String name, int price){
		this.rarity = rarity;
		this.name = name;
		this.price = price;
		this.amount = 1;
		this.isConsumable=false;
	}
	
	public Item(int rarity, String name, int price, int amount){
		this.rarity = rarity;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.isConsumable=false;
	}
	
	public Item(int rarity, String name, int price, boolean consumability){
		this.rarity = rarity;
		this.name = name;
		this.price = price;
		this.amount = 1;
		this.isConsumable=consumability;
	}
	
	public Item(int rarity, String name, int price, int amount, boolean consumability){
		this.rarity = rarity;
		this.name = name;
		this.price = price;
		this.amount = amount;
		this.isConsumable=consumability;
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
	
	public int getAmount(){
		return this.amount;
	}
	
	public boolean getConsumability(){
		return this.isConsumable;
	}
	
	public void setAmount(int amount){
		this.amount = amount;
	}
	
	public void addAmount(int amount){
		this.amount += amount;
	}
}