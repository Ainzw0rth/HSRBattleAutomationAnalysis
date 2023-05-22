package Entity.Musuh;

import Entity.Enemies;
import Entity.Elemen.Ice;
import Entity.Elemen.Physical;

public class SilvermaneGunner extends Enemies implements Physical, Ice {
    public SilvermaneGunner(int x) {
        super(90, 15000, 90, 800);
        this.name = "Silvermane Gunner " + Integer.toString(x);
    }
}
