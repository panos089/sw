package biodiversity;

import java.util.Random;

/**
 *
 * @author Κωνσταντίνος Σταθόπουλος
 */
public class Animal {

    double weight;
    int age, max_age, rate_of_birth, last_birth, position, starvation, maturity,hunter_pop,litters;
    String name, escape;
    boolean sex, well_fed, underage;

    public Animal(double weight, int age, int max_age, int rate_of_birth, int position, String name,int hunter_pop,int litters, String escape, int sex, int maturity, boolean well_fed, boolean underage) {//float high_temp, float low_temp
        this.weight = weight;
        this.age = age;
        this.max_age = max_age;
        this.position = position;
        this.name = name;
        this.hunter_pop=hunter_pop;
        this.litters = litters;
        this.escape = escape;
        this.starvation = 0;
        this.well_fed = well_fed;
        this.underage = underage;
        this.maturity = maturity;
        this.sex = sex <= 55;
        if (this.sex) {
            this.rate_of_birth = rate_of_birth;
            this.last_birth = 0;
        }
    }
    
    public void set_rate_of_birth(int rob) {
        this.rate_of_birth = rob;
    }

    public void set_last_birth(int rob) {
        this.rate_of_birth = rob;
    }

    public int get_position() {
        return this.position;
    }

    public void randomWalk(double[] f) {
        Random rn = new Random(System.currentTimeMillis());
        int pos = get_position();
        if (pos == 0) {
            if (f[pos] < f[pos + 1]) {
                if (rn.nextInt(100 - 0) > 20) {
                    move(pos + 1);
                }
            }
        } else if (pos == f.length - 1) {
            if (f[pos] < f[pos - 1]) {
                if (rn.nextInt(100 - 0) > 20) {
                    move(pos - 1);
                }
            }

        } else if ((f[pos] < f[pos + 1]) || (f[pos] < f[pos - 1])) {
            if (f[pos + 1] > f[pos - 1]) {
                if (rn.nextInt(100 - 0) > 20) {
                    move(pos + 1);
                }
            } else {
                if (rn.nextInt(100 - 0) > 20) {
                    move(pos - 1);
                }
            }
        }

    }

    public void move(int x) {
        this.position = x;
    }

    public double eat(double food) {
        double amount = 0.4 * weight;
        
        if (underage) {
            amount = amount * 20/100;
        }
        if (food >= amount) {
            //set_weight(amount);
            return food - amount;
        } else if (food == 0) {
            if (well_fed) {
                starvation++;
                well_fed = false;
            } else {
                starvation += 2;
            }

            return 0;
        } else {
            if (well_fed) {
                well_fed = false;
            } else {
                starvation++;
            }
            return 0;
        }
    }
}