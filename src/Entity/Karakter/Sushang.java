package Entity.Karakter;

import Entity.Characters;
import Entity.Enemies;
import Entity.Elemen.Physical;

public class Sushang extends Characters implements Physical {
    public Sushang() {
        super(30, 1200, 120, 1300);
        this.name = "Sushang";
    }

    @Override
    public String skill (Enemies e) {
        boolean breakeffect = false;
        if (e instanceof Physical) {
            e.dealDamage((int)(attack * 1.57));
            e.dealDamageToToughness(this.toughnessDamage*2);
            if (e.getToughness() <= 0) {
                e.dealDamage((int)(attack * 1.57 * 2)); // break effect
                breakeffect = true;
                e.refreshToughness();
            }
        } else {
            e.dealDamage((int)(attack * 1.57));
        }

        this.rechargeEnergy();

        if (breakeffect) {
            return "(break)\n";
        } else {
            return "";
        }
    }

    @Override
    public String ulti(Enemies e) {
        boolean breakeffect = false;
        if (e instanceof Physical) {
            e.dealDamage((int)(attack * 0.256));
            e.dealDamageToToughness(this.toughnessDamage);
            if (e.getToughness() <= 0) {
                e.dealDamage((int)(attack * 0.256 * 2)); // break effect
                breakeffect = true;
                e.refreshToughness();
            }
        } else {
            e.dealDamage((int)(attack * 0.256));
        }

        this.rechargeEnergy();

        if (breakeffect) {
            return "(break)\n";
        } else {
            return "";
        }
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
