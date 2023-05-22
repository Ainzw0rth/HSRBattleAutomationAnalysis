package Entity.Karakter;

import Entity.Healer;
import Entity.Characters;
import Entity.Enemies;
import Entity.Elemen.Physical;

public class Natasha extends Characters implements Physical, Healer {
    public Natasha() {
        super(30, 1200, 120, 1300);
        this.name = "Natasha";
        this.setTargetSkillToCharacters();
    }

    public void heal (Characters e) {
        e.addHP((int) (basehealth*0.1225 + 375));
    }

    @Override
    public void ulti(Characters e) {
        e.addHP((int) (basehealth*0.1225 + 375));
    }

    @Override
    public String attack(Enemies e) {
        boolean breakeffect = false;
        if (e instanceof Physical) {
            e.dealDamage((int)(attack * 0.8));
            e.dealDamageToToughness(this.toughnessDamage);
            if (e.getToughness() <= 0) {
                e.dealDamage((int)(attack * 0.8 * 2)); // break effect
                breakeffect = true;
                e.refreshToughness();
            }
        } else {
            e.dealDamage((int)(attack * 0.8));
        }

        this.rechargeEnergy();

        if (breakeffect) {
            return "(break)\n";
        } else {
            return "";
        }
    }
}
