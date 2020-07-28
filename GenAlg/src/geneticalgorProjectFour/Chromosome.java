package geneticalgorProjectFour;

import java.util.ArrayList;
import java.util.Random;

public class Chromosome extends ArrayList<Item> implements Comparable<Chromosome> {

    private static Random rng = new Random();;
    public static long dummy = 0; // this is a class field

    public Chromosome() {
    }

    /**
     * This class creates a  copy of each of the items passed in to this Chromosome.
     * Random numbers are used to decide whether each item’s included field is set to true
     * or false.
     *
     * @param items
     */
    public Chromosome(ArrayList<Item> items) {
        this.clear();
        for (int i = 0; i < items.size(); i++) {
            // makes a copy of each Item that is passed into the Chromosome
            Item copy = new Item(items.get(i));
            // Adds the copy to the ArrayList
            this.add(copy);
            // Random number is created
            int random = (rng.nextInt(10) + 1);
            // If number created above is greater than 5 the value is set to false, 
            // if not it's set to true
            if (random > 5) {
                copy.setIncluded(false);
            } else {
                copy.setIncluded(true);
            }
        }
    }

    public Chromosome(Chromosome other) {
        // Set to clear
        this.clear();
        // reads through other and adds it to this chromosome w/ creating
        // Item objects
        for (int i = 0; i < other.size(); i++) {
            this.add(new Item(other.get(i)));
        }
    }

    /**
     * The method below 'crossover' is the method that grabs the two
     * parents from the main class and creates the child chromosome.
     *
     * @param other
     * @return
     */
    public Chromosome crossover(Chromosome other) {
        Chromosome child = new Chromosome();
        // iterates through the chromosoe
        for (int i = 0; i < this.size(); i++) {
            // the random number is created
            int random = (int) (rng.nextInt(10) + 1);
            // If greater than or equal to 5
            if (random <= 5) {
                child.add(new Item(this.get(i)));
            } else {
                child.add(new Item(other.get(i)));
            }
        }
        // returns child
        return child;
    }

    /**
     * Below is the method that does the actual mutation operation on this 
     * Chromosome. (i.e. for each item)
     * This chromosome uses a random number to decide whether or not to 
     * change it's included field from true to false or vice versa.
     */
    public void mutate() {
        for (Item i : this) {
            // Random number generator
            int random = (int) (rng.nextInt(10) + 1);
            // If random is 1
            if (random == 1) {
                // If included is true
                if (i.isIncluded()) {
                    // sets included to false
                    i.setIncluded(false);
                } else {
                    // sets included to true
                    i.setIncluded(true);
                }
            }
        }
    }

    /**
     * The following method 'getFitness' creates variables that stores the totalWeight ,
     * totalValue, and the Fitness of the included item's weights. It then sums
     * up all of the weights of each item. 
     * 
     * There are two potential outcomes when this method is done executing 1)If the sum is > 10, the
     * fitness is set to 0. 2) Otherwise, the fitness is equal to the sum of all the
     * items included values.
     *
     * @return
     */
    public int getFitness() {
        // Creates variable to store the total weight of the items
        double weight = 0;
        // Creates variable to store the total value of the items
        int value = 0;
        // Creates variable to store the total fitness.
        int fitness = 0;
        dummy = 0;
        for (int i=0; i<this.size()*1000; i++) 
        {
            dummy +=i;
        }
        for (Item i : this) {
            if (i.isIncluded()) {
                // assinging the value and weights into their respected storing
                // variables.
                weight += i.getWeight();
                value += i.getValue();
            }
        }
        // If the total weight is greater than 10
        if (weight > 10) {
            // Sets the fitness to 0
            fitness = 0;
            
        
        }
        // If total weight is not greater than 10, the fitness is set equal to 
        //value
        else {
          
            fitness = value;
        }
        return fitness;// fitness is returned
    }

    /**
     * Returns -1 if the fitness of the chromosome is less than the the others fitness Returns +1
     * if this chromosomes fitness is < the others fitness Returns 0 if this
     * chromosomes fitness = the others fitness @param other @return @param
     * other
     * @param other
     */
    @Override
    public int compareTo(Chromosome other) {
        // Returns -1 if this chromosomes fitness is > the others fitness
        if (this.getFitness() > other.getFitness()) {
            return -1;
            //Returns +1 if this chromosomes fitness is < the others fitness
        } else if (this.getFitness() < other.getFitness()) {
            return +1;
            // Returns 0 if this chromosomes fitness = the others fitness
        } else if (this.getFitness() == other.getFitness()) {
            return 0;
        }
        return 0;
    }

    /**
     * Displays the name, weight, and value of all items in this chromosome
     * whose included value is true, followed by the fitness of this chromosome
     *
     * @return
     */
    @Override
    public String toString() {
        String word = "";
        for (Item i : this) {
            // If the Item i s included, it will 
            if (i.isIncluded()) {
                word += i.toString();
            } 
        }
        word += " " + this.getFitness();
        return word;
    }

}
