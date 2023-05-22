package Entity;

public class Beings {
    protected int health;
    protected int basehealth;
    protected int speed;
    protected int attack;
    protected String name;
    
    public Beings(int health, int speed, int attack) {
        this.health = health;
        this.basehealth = health;
        this.speed = speed;
        this.attack = attack;
    }

    public void dealDamage(int x) {
        this.health -= x;
    }

    public String getName() {
        return this.name;
    }

    public double getHealthPercentage() {
        return ((double)this.health/(double)this.basehealth)*100;
    }

    public int getHealth() {
        return this.health;
    }

    public int getSpd() {
        return this.speed;
    }

    public int getAtt() {
        return this.attack;
    }

    public void addHP(int x) {
        this.health += x;
    }
}
