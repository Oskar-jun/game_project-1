public class Weapon extends Item{
        private int damage;

        public Weapon(int rarity, String name, int damage){
            super(rarity, name);
            this.damage = damage;
        }

        public Weapon(int rarity, String name, int price, int amount, int damage){
            super(rarity, name, price, amount);
            this.damage = damage;
        }

        public int getDamage(){
            return this.damage;
        }
}