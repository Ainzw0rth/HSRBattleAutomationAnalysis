package Entity;

public abstract class Characters extends Beings {
    protected int toughnessDamage;
    protected int energy;
    protected boolean skillTargetEnemies = true;
    protected boolean ultiTargetEnemies = true;
    
    public Characters(int toughnessDamage, int health, int speed, int attack) {
        super(health, speed, attack);
        this.toughnessDamage = toughnessDamage;
        this.energy = 0;
    }
    
    public void setTargetSkillToCharacters() {
        this.skillTargetEnemies = false;
    }

    public void rechargeEnergy() {
        this.energy++;
    }

    public void resetEnergy() {
        this.energy = 0;
    }

    public boolean ultiAvailable() {
        return this.energy == 4;
    }

    public boolean getSkillTarget () {
        return this.skillTargetEnemies;
    }

    public boolean getUltiTarget () {
        return this.ultiTargetEnemies;
    }
    
    public String skill (Enemies e) {
        return "";
    }

    public void skill (Characters e) {
        
    }

    public String ulti (Enemies e) {
        return "";
    }

    public void ulti (Characters e) {
        
    }

    public String attack(Enemies e) {
        return "";
    }
}
