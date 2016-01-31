package biodiversity;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Κωνσταντίνος Σταθόπουλος
 */
public class Biodiversity2 {

    
    public static void main(String[] args) {

        Map<String, List<Animal>> animal_lists = new HashMap<>();
        Map<String, PrintWriter> writers = new HashMap<>();

        Random rn = new Random(System.currentTimeMillis());//seed με τον χρόνο του συστήματος σε msec

        //Ορισμός και αρχικοποίηση μεταβλητών:
        int i, season = 0, turn = 0, hunter_pop = 0, weight_average = 0, max_age = 0, birth_rate = 0, hit_prop = 0, maturity = 0, ini_pop = 0, litters = 0;
        double weight;
        double[] food = new double[5000];
        String name = null, escape_ability = null;
        Animal animal = null, animaltemp = null;

        //Έναρξη αρχικοποίησης:
        try (BufferedReader input = new BufferedReader(new FileReader("ini_data.txt"))) {
            String line;
            while ((line = input.readLine()) != null) {
                if (line.isEmpty()) {
                    List<Animal> list_temp = new ArrayList<>();
                    for (i = 0; i < ini_pop; i++) {
                        weight = (weight_average - weight_average / 10) + ((weight_average + weight_average / 10) - (weight_average - weight_average / 10)) * rn.nextDouble();
                        animaltemp = new Animal(weight, rn.nextInt(max_age - 1), max_age, birth_rate, rn.nextInt(food.length - 0), name, hunter_pop, litters, escape_ability, rn.nextInt(100 - 0), maturity, true, false);
                        list_temp.add(animaltemp);
                    }
                    animal_lists.put(name, list_temp);
                } else {
                    String l = line.toString();
                    String[] parts = l.split("=");
                    switch (parts[0]) {
                        case "name":
                            PrintWriter w = new PrintWriter(parts[1] + ".txt", "UTF-8");
                            writers.put(parts[1], w);
                            name = parts[1];
                            System.out.println("New population entered simulation. Population name:" + name);
                            break;
                        case "weight_average":
                            weight_average = Integer.valueOf(parts[1]);
                            break;
                        case "max_age":
                            max_age = Integer.valueOf(parts[1]);
                            break;
                        case "birth_rate":
                            birth_rate = Integer.valueOf(parts[1]);
                            break;
                        case "maturity":
                            maturity = Integer.valueOf(parts[1]);
                            break;
                        case "ini_pop":
                            ini_pop = Integer.valueOf(parts[1]);
                            break;
                        case "litters":
                            litters = Integer.valueOf(parts[1]);
                            break;
                        case "hunter_pop":
                            hunter_pop = Integer.valueOf(parts[1]);
                        case "escape_ability":
                            escape_ability = parts[1];
                            break;
                    }

                }
            }
            List<Animal> list_temp = new ArrayList<>();
            for (i = 0; i < ini_pop; i++) {
                weight = (weight_average - weight_average / 10) + ((weight_average + weight_average / 10) - (weight_average - weight_average / 10)) * rn.nextDouble();
                animaltemp = new Animal(weight, rn.nextInt(max_age - 1), max_age, birth_rate, rn.nextInt(food.length - 0), name, hunter_pop, litters, escape_ability, rn.nextInt(100 - 0), maturity, true, false);
                list_temp.add(0, animaltemp);
            }
            animal_lists.put(name, list_temp);
        } catch (IOException ex) {
            Logger.getLogger(Biodiversity2.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (i = 0; i < food.length; i++) {
            food[i] = 40 + 80 * rn.nextDouble();
        }
        System.out.println("Initialization completed successfully!!!");
        //Λήξη Αρχικοποίησης.

        //Αρχή πειράματος
        for (turn = 1; turn <= 2400; turn++) {//Ένα turn αντιστοιχεί σε ένα μήνα
            season = season + 1;
            //Έναρξη ανανέωσης τροφής:
            if (season == 13) {//Επανέναρξη του ημερολογιακού έτους
                season = 1;
            } else if (season > 2 && season < 6) {//Άνοιξη
                for (i = 0; i < food.length; i++) {
                    food[i] = food[i] + 25;
                }
            } else if (season >= 6 && season <= 8) {//Καλοκαίρι
                for (i = 0; i < food.length; i++) {
                    food[i] = food[i] + 15 - (season - 6);
                }
            } else if (season > 8 && season < 12) {//Φθινόπωρο
                for (i = 0; i < food.length; i++) {
                    food[i] = food[i] + 10 - (season - 8);
                }
            }//Χειμώνας στο 'else' + season == 13
            //Τέλος ανανέωσης τροφής.

            for (Map.Entry<String, List<Animal>> entry : animal_lists.entrySet()) {//Για κάθε είδος ζώων(λίστα ειδών):
                List<Animal> list_temp = entry.getValue();

                for (i = 0; i < list_temp.size(); i++) {//Για κάθε ζώο στην λίστα:
                    animal = list_temp.get(i);
                    if (animal.starvation > 3) {//Ελέγχος λιμοκτονίας
                        list_temp.remove(i);//Θάνατος από λιμοκτονία
                        if (list_temp.isEmpty()) {//Έλεγχος για εξαφάνιση είδους
                            System.out.println(entry.getKey() + " population extinct from starvation during turn " + turn);
                        }
                    } else {
                        animal.randomWalk(food);//Μετακίνηση ζώου
                        food[animal.get_position()] = animal.eat(food[animal.get_position()]);//Κατανάλωση τροφής
                        if (animal.sex && !animal.underage) {//Έλεγχος δυνατότητας αναπαραγωγής(φύλο και ηλικία)
                            if (animal.last_birth == 0/*Μήνες που πέρασαν από την γέννα*/ || !animal.well_fed) {//Έλεγχος δυνατότητας αναπαραγωγής(Τελευταία γέννα και ελλειπής σίτιση)
                                animal.set_last_birth(animal.rate_of_birth);
                                for (int j = 0; j < animal.litters; j++) {//Γέννηση με βάση τον μέσο αριθμό των νεογνών ανα γέννα
                                    weight = (animal.weight - animal.weight / 10) + ((animal.weight + animal.weight / 10) - (animal.weight - animal.weight / 10)) * rn.nextDouble();
                                    //Νέο ζώο του είδους γεννιέται !!!
                                    animaltemp = new Animal(animal.weight, 1, animal.max_age, animal.rate_of_birth, animal.position, animal.name, animal.hunter_pop, animal.litters, animal.escape, rn.nextInt(100 - 0), animal.maturity, true, true);
                                    list_temp.add(animaltemp);
                                }

                            } else {
                                animal.set_rate_of_birth(animal.rate_of_birth - 1);//Μείωση εναπομείναντος χρόνου για γέννα
                            }
                        }
                        animal.age = animal.age + 1;//Γήρανση ζωου
                        if (animal.age > animal.max_age) {//Ελέγχος υπέρβασης ηλικίας
                            list_temp.remove(i);//Θάνατος από υπέρβαση ηλικίας
                            //System.out.println("Animal died from natural causes " + turn);
                            if (list_temp.isEmpty()) {//Έλεγχος για εξαφάνιση είδους
                                System.out.println(entry.getKey() + " population extinct from aging during turn " + turn);
                            }
                        } else if (animal.underage && animal.age == animal.maturity) {//Έλεγχος ενηλικίωσης ζώου
                            animal.underage = false;//Ενηλικίωση ζώου
                        }
                    }
                }//Τέλος 'Για κάθε ζώο'.
                for (i = 0; i <= animal.hunter_pop; i++) {//Έναρξή κυνιγιού
                    if (rn.nextDouble() <= list_temp.size() / food.length) {//Υπολογισμός πιθανότητας να βρεθεί ζώο του είδους
                        animal = list_temp.get(rn.nextInt((list_temp.size() - 1) - 0));//Επιλογή στόχου(τυχαία)
                        if (animal.underage) {//Περίπτωση που βρέθηκε ανήλικο
                            hit_prop = 85;
                        } else {
                            switch (animal.escape) {//Υπολογισμός πιθανότητας επιτυχούς καταδίωξης
                                case "low"://
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
                        if (rn.nextInt(100 - 0) <= hit_prop) {//Πιθανότητα επιτυχούς καταδίωξης
                            list_temp.remove(rn.nextInt((list_temp.size() - 1) - 0));//Αφαίρεση του ζώου που θανατώθηκε
                            if (list_temp.isEmpty()) {//Έλεγχος για εξαφάνιση είδους
                                System.out.println(entry.getKey() + " population extinct by hunter during turn " + turn);
                            }
                        }
                    }
                }//Τελος κινυγιού
                //Έναρξη εγγραφής αποτελεσμάτων γύρου:
                for (Map.Entry<String, PrintWriter> files : writers.entrySet()) {
                    if (files.getKey() == entry.getKey()) {//Επιλογή κατάλληλου αρχείου
                        files.getValue().println(entry.getValue().size());
                        files.getValue().flush();//Γίνεται flush() για να εγγραφούν τα αποτελέσματα σε περίπτωση που σταματήσει για κάποιο λόγω η εκτέλεση
                    }

                }
                //Τέλος εγγραφής αποτελεσμάτων γύρου.
            }//Τέλος 'Για κάθε είδος'.
        }
        //Τέλος πειράματος.

        for (Map.Entry<String, PrintWriter> entry : writers.entrySet()) {
            entry.getValue().close();//Κλείσιμο των αρχείων εγγραφής αποτελεσμάτων.
        }
    }
}