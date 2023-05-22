import java.util.ArrayList;

import Entity.Characters;
import Entity.Enemies;
import Entity.Healer;
import Entity.Karakter.Natasha;
import Entity.Karakter.Sushang;
import Entity.Musuh.SilvermaneGunner;

public class Main {
    private int skillPoints;
    private ArrayList<Characters> characters;
    private ArrayList<Enemies> enemies;

    public Main() {
        // banyak skill yang bisa digunakan
        this.skillPoints = 4;

        // inisialisasi karakter-karakternya
        characters = new ArrayList<>();
        enemies = new ArrayList<>();

        characters.add(new Sushang());
        characters.add(new Natasha());

        enemies.add(new SilvermaneGunner(1));
    }

    public String greedy() {
        int actionstaken = 0;
        String actions = "";

        // greedy 1: jika ada satu atau lebih karakter yang bisa mengeluarkan ulti, maka keluarkan
        // greedy 2: selalu target musuh yang memiliki persentase HP terkecil
        // greedy 3: jika buffer, maka buff teammate yang memiliki attack terbesar
        // greedy 4: jika memiliki skill, maka selalu pakai
        // greedy 5: jika memiliki skill heal, hanya lakukan heal ketika ada teammate yang persentase HP-nya <= 80%

        while (enemies.size() > 0) {
            actions += "status tim:\n";
            for (Characters c: characters) {
                actions += c.getName() + " (HP: " + c.getHealth() + ", Attack: " + c.getAtt() + ")\n";
            }
            actions += "\n";
            
            actions += "status musuh:\n";
            for (Enemies c: enemies) {
                actions += c.getName() + " (HP: " + c.getHealth() + ")\n";
            }
            actions += "\n";
            // menerapkan greedy 1
            for (Characters c: characters) {
                if (c instanceof Healer) {
                    if (c.ultiAvailable()) {
                        // menerapkan greedy 5
                        int i = 0;
                        int idxMin = 0;
                        Double minRatio = (double) 100;
                        for (Characters e: characters) {
                            if (e.getHealthPercentage() < minRatio) {
                                minRatio = e.getHealthPercentage();
                                idxMin = i;
                            }
                            
                            i++;
                        }
    
                        if (minRatio <= 80) {
                            actionstaken++;
                            actions += c.getName() + " menyembuhkan " + characters.get(idxMin).getName() + " dengan ultimate\n";
                            (c).ulti(characters.get(idxMin));
                            c.resetEnergy();
                        }
                    }
                } else {
                    if (c.ultiAvailable()) {
                        // jika ulti menargetkan musuh 
                        if (c.getUltiTarget()) {
                            // menerapkan greedy 2
                            int i = 0;
                            int idxMin = 0;
                            Double minRatio = (double) 100;
                            for (Enemies e: enemies) {
                                if (e.getHealthPercentage() < minRatio) {
                                    minRatio = e.getHealthPercentage();
                                    idxMin = i;
                                }
                                
                                i++;
                            }

                            // jika ratio sama semua
                            i = 0;
                            int minHP = 999999999;
                            if (minRatio == 100) {
                                for (Enemies e: enemies) {
                                    if (e.getHealth() < minHP) {
                                        minHP = e.getHealth();
                                        idxMin = i;
                                    }
                                    
                                    i++;
                                }
                            }
    
                            actionstaken++;
                            actions += c.getName() + " menyerang " + enemies.get(idxMin).getName() + " dengan menggunakan ultimate\n";
                            actions += c.ulti(enemies.get(idxMin));
                            c.resetEnergy();
    
                            if (enemies.get(idxMin).getHealthPercentage() <= 0) {
                                enemies.remove(idxMin);
                            }
                        } else { // jika tidak
                            // menerapkan greedy 3
                            int i = 0;
                            int idxMax = 0;
                            int maxAtt = 0;
                            for (Characters e: characters) {
                                if (e.getAtt() > maxAtt) {
                                    maxAtt = e.getAtt();
                                    idxMax = i;
                                }
                                
                                i++;
                            }
    
                            actionstaken++;
                            actions += c.getName() + " memperkuat " + characters.get(idxMax).getName() + " dengan menggunakan ultimate\n";
                            c.ulti(characters.get(idxMax));
                            c.resetEnergy();
                        }
                    }
                }
            }

            Characters current = characters.remove(0);

            //  menerapkan greedy 4
            if (skillPoints > 0) {
                if (!(current instanceof Healer)) {
                    if (current.getSkillTarget()) { // jika skill menargetkan musuh
                        // menerapkan greedy 2
                        int i = 0;
                        int idxMin = 0;
                        Double minRatio = (double) 100;
                        for (Enemies e: enemies) {
                            if (e.getHealthPercentage() < minRatio) {
                                minRatio = e.getHealthPercentage();
                                idxMin = i;
                            }
                            
                            i++;
                        }
                        
                        // jika ratio sama semua
                        i = 0;
                        int minHP = 999999999;
                        if (minRatio == 100) {
                            for (Enemies e: enemies) {
                                if (e.getHealth() < minHP) {
                                    minHP = e.getHealth();
                                    idxMin = i;
                                }
                                
                                i++;
                            }
                        }
                        actionstaken++;
                        actions += current.getName() + " menyerang " + enemies.get(idxMin).getName() + " dengan menggunakan skill\n";
                        actions += current.skill(enemies.get(idxMin));
                        skillPoints--;
    
                        if (enemies.get(idxMin).getHealthPercentage() <= 0) {
                            enemies.remove(idxMin);
                        }
                    }
                } else { // jika skill menargetkan teammate
                    if (current instanceof Healer) {
                        // untuk heal, menerapkan greedy 6
                        int i = 0;
                        int idxMin = 0;
                        Double minRatio = (double) 100;
                        for (Characters e: characters) {
                            if (e.getHealthPercentage() < minRatio) {
                                minRatio = e.getHealthPercentage();
                                idxMin = i;
                            }
                            
                            i++;
                        }
    
                        if (minRatio <= 80) {
                            actionstaken++;
                            actions += current.getName() + " menyembuhkan " + characters.get(idxMin).getName() + " dengan menggunakan skill\n";
                            ((Healer)current).heal(characters.get(idxMin));
                            skillPoints--;
                        } else {
                            // menerapkan greedy 2
                            i = 0;
                            idxMin = 0;
                            minRatio = (double) 100;
                            for (Enemies e: enemies) {
                                if (e.getHealthPercentage() < minRatio) {
                                    minRatio = e.getHealthPercentage();
                                    idxMin = i;
                                }
                                
                                i++;
                            }
                            
                            // jika ratio sama semua
                            i = 0;
                            int minHP = 999999999;
                            if (minRatio == 100) {
                                for (Enemies e: enemies) {
                                    if (e.getHealth() < minHP) {
                                        minHP = e.getHealth();
                                        idxMin = i;
                                    }
                                    
                                    i++;
                                }
                            }

                            actionstaken++;
                            actions += current.getName() + " menyerang " + enemies.get(idxMin).getName() + "\n";
                            actions += current.attack(enemies.get(idxMin));
                            skillPoints++;
                        }
                    } else {
                        // untuk skill lainnya
                        int i = 0;
                        int idxMax = 0;
                        int maxAtt = 0;
                        for (Characters e: characters) {
                            if (e.getAtt() > maxAtt) {
                                maxAtt = e.getAtt();
                                idxMax = i;
                            }
                            
                            i++;
                        }

                        actionstaken++;
                        actions += current.getName() + " memperkuat " + characters.get(idxMax).getName() + " dengan skill\n";
                        current.skill(characters.get(idxMax));
                        skillPoints--;
                    }
                }
            } else { // menyerang musuh
                // menerapkan greedy 2
                int i = 0;
                int idxMin = 0;
                Double minRatio = (double) 100;
                for (Enemies e: enemies) {
                    if (e.getHealthPercentage() < minRatio) {
                        minRatio = e.getHealthPercentage();
                        idxMin = i;
                    }
                    
                    i++;
                }
                
                // jika ratio sama semua
                i = 0;
                int minHP = 999999999;
                if (minRatio == 100) {
                    for (Enemies e: enemies) {
                        if (e.getHealth() < minHP) {
                            minHP = e.getHealth();
                            idxMin = i;
                        }
                        
                        i++;
                    }
                }

                actionstaken++;
                actions += current.getName() + " menyerang " + enemies.get(idxMin).getName() + "\n";
                actions += current.attack(enemies.get(idxMin));
                skillPoints++;
            }

            characters.add(current);
        }

        actionstaken++;
        actions += "jumlah langkah yang diambil: " + actionstaken;
        return actions;
    }

    public static void main(String[] args) {
        Main actions = new Main();
        System.out.println(actions.greedy());
    }
}