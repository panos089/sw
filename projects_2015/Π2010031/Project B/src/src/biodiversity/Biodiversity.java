/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biodiversity;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kwstas
 */
public class Biodiversity {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PrintWriter writer = null;

        List<Animal> list_temp = new ArrayList<Animal>();// TO DO πολλές λίστες για κάθε ζώο που βάζει ο χρήστης average num of babies mwra den trwne
        //Map<String, List<Animal>> animal_lists = new HashMap<>();

        int i, turn, hunter_pop = 100;
        double weight;
        double[] food = new double[1000];
        Random rn = new Random(System.currentTimeMillis());

        /*Scanner input = new Scanner(System.in);
         System.out.println("Enter the animal's name");
         String name = input.next();
         System.out.println("Enter the animal's average weight");
         int weight_average = input.nextInt();
         System.out.println("Enter the average rate of birth");
         int birth_rate = input.nextInt();
         System.out.println("Enter the escape ability variable");
         int escape = input.nextInt();
         System.out.println("Enter the propability of the animal to give birth");
         int birth_prop = input.nextInt();
         System.out.println("Enter the maximum age of the animal");
         int max_age = input.nextInt(); */
        //Scanner fileIn = new Scanner(new File(ini_data.txt));
        String name = "new_test", escape_ability = "low";
        int weight_average = 10, max_age = 150, birth_rate = 12, hit_prop = 0, maturity = 11, ini_pop = 10, litters = 1;// kathe posa turn gennaei ?
        try {
            writer = new PrintWriter(name + ".txt", "UTF-8");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Biodiversity.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Biodiversity.class.getName()).log(Level.SEVERE, null, ex);
        }

        Animal animal = null, animaltemp;
        for (i = 0; i < ini_pop; i++) {
            weight = (weight_average - weight_average / 10) + ((weight_average + weight_average / 10) - (weight_average - weight_average / 10)) * rn.nextDouble();
            animaltemp = new Animal(weight, rn.nextInt(max_age - 1), max_age, birth_rate, rn.nextInt(1000 - 0), name, litters, hunter_pop, escape_ability, rn.nextInt(100 - 0), maturity, true, false);
            list_temp.add(animaltemp);
        }
        //animal_lists.put(name, list_temp);

        for (i = 0; i < food.length; i++) {
            food[i] = 20 + 80 * rn.nextDouble();
        }
        int season = 0;
        for (turn = 1; turn <= 2400; turn++) {// ti monada metrisis tha einai ??
            season = season + 1;
            if (season == 13) {
                season = 1;
            } else if (season > 2 && season < 6) {//spring
                for (i = 0; i < food.length; i++) {
                    food[i] = food[i] + 20;
                }
            } else if (season >= 6 && season <= 8) {//summer
                for (i = 0; i < food.length; i++) {
                    food[i] = food[i] + 15 - (season - 6);
                }
            } else if (season > 8 && season < 12) {//fall
                for (i = 0; i < food.length; i++) {
                    food[i] = food[i] + 10 - (season - 8);
                }
            }//winter in 'else' + season == 13
            /*for (Map.Entry<Object, Object> entry : m.entrySet()) {
             name = entry.getKey();
             list_temp = entry.getValue();
            
            
             animal_lists.put(name, list_temp);//alla3e to !!!!
             }*/
            for (i = 0; i < list_temp.size(); i++) {
                animal = list_temp.get(i);
                if (animal.starvation > 3) {
                    list_temp.remove(i);
                    //System.out.println("Animal starved to death !");
                } else if (animal.age > max_age) {
                    list_temp.remove(i);
                    System.out.println("Animal died from natural causes");
                } else {
                    //System.out.println("OldPos:"+animal.get_position());
                    animal.randomWalk(food);
                    //System.out.println("NewPos:"+animal.get_position());
                    food[animal.get_position()] = animal.eat(food[animal.get_position()]);
                    if (animal.sex && !animal.underage) {
                        if (animal.last_birth == 0/*mines pou perasan apo tin proigoumeni genna*/ || !animal.well_fed) {
                            animal.set_last_birth(animal.rate_of_birth);
                            weight = (weight_average - weight_average / 10) + ((weight_average + weight_average / 10) - (weight_average - weight_average / 10)) * rn.nextDouble();
                            animaltemp = new Animal(weight, 1, animal.max_age, animal.rate_of_birth, animal.position, animal.name, animal.litters, animal.hunter_pop, escape_ability, rn.nextInt(100 - 0), animal.maturity, true, true);
                            list_temp.add(animaltemp);

                            //System.out.println("Animal birth");
                        } else {
                            animal.set_rate_of_birth(animal.rate_of_birth - 1);
                        }

                    }

                    animal.age = animal.age + 1;
                    if (animal.underage && animal.age == animal.maturity) {
                        animal.underage = false;
                    }
                }

            }
            for (i = 0; i <= animal.hunter_pop; i++) {
                if (rn.nextDouble() <= list_temp.size() / 1000) {
                    animal = list_temp.get(rn.nextInt((list_temp.size() - 1) - 0));
                    if (animal.underage) {
                        //System.out.println("Newborn found !!!");
                        hit_prop = 85;
                    } else {
                        switch (animal.escape) {
                            case "low":
                                hit_prop = 80;
                                break;
                            case "mid":
                                hit_prop = 50;
                                break;
                            case "high":
                                hit_prop = 10;
                                break;
                        }
                    }
                    if (rn.nextInt(100 - 0) <= hit_prop) {
                        list_temp.remove(rn.nextInt((list_temp.size() - 1) - 0));
                        hit_prop = list_temp.size() / 1000;
                        //System.out.println("Animal got eaten !");
                    }
                }
            }
            writer.println(turn + "\t" + list_temp.size());//print turn --- animalList.size() ---- ta turns na antistoixoun stous mines -- psa3e real dedomena
            /*for (Map.Entry<String, PrintWriter> entry : writers.entrySet()) {
             if (entry.getKey().equals(name)) {
             entry.getValue().println(turn + "\t" + list_temp.size());
             }

             }*/

        }
        writer.close();
    }
}
