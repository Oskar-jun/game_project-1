public class Skill{
	private String name;
	private int level;
	
	public Skill(String name, int level){
		this.name=name;
		this.level=level;
	}
	
	public Skill(){
		this.name="test";
		this.level=0;
	}
	
	public boolean upgrade(int points){
		this.level+=points;
		return true;
	}
	
	public int toInt(){
		return this.level;
	}
	
	public String toString(){
		return "Skill "+this.name+" is at "+this.level+" now.";
	}
}