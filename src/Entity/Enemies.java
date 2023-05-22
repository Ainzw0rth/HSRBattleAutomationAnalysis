package Entity;

public class Enemies extends Beings {
    private int toughness;
    private int baseToughness;

    public Enemies(int toughness, int health, int speed, int attack) {
        super(health, speed, attack);
        this.toughness = toughness;
        this.baseToughness = toughness;
    }

    public void dealDamageToToughness(int x) {
        if (this.toughness < x) {
            toughness = 0;
        } else {
            toughness -= x;
        }
    }

    public int getToughness() {
        return this.toughness;
    }

    public void refreshToughness() {
        this.toughness = this.baseToughness;
    }
}
