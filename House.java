public class House extends Terrain{
    private Item[] chest = new Item[72];

    public House(Item[] chest){
        super(true);
        this.chest = chest;
    }
}
