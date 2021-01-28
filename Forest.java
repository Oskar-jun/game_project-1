public class Forest extends Terrain{
	public Forest(int plankAmount){
		super(true, true, new Axe(), new Plank(plankAmount));
	}
}