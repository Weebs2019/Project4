/*
 * This is Project 4 for CS 1181 Summer Class. This method represents a file of 
 * This project is using Project 1, where we were concerned with chromosome parings.
 * This time we are more concerned about threads being used efficiently
 * We are wanting to find the best possible set for the items/chromosomes using 
 * multi-threading.
 * The avg time taken for different numbers of threads: 
 * 15 threads: 12 seconds
 * 30 threads: 2 seconds
 * 60 threads: 1 second
 * 10 threads: 51 seconds
 * This is also for github
 * The average fitness was all the same at 3400
 */
package geneticalgorProjectFour;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

/**
 * Developer: Stephen Wibowo 
 * TA: John G.
 * Instructor: Prof.  Michelle Cheatham
 */
public class GeneticAlgorithm {

    private static Random rng = new Random();

    /**
     * The method, called readData', reads the more_items.txt file, Scans through the
     * file creating an Item object for each item in file and adding it to the
     * ArrayList
     *
     * @param fileName
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Item> readData(String fileName) throws FileNotFoundException {
        ArrayList<Item> fileItems = new ArrayList<>();
        // Reads through the file
        Scanner scan = new Scanner(new File("items.txt"));
        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            Scanner newScan = new Scanner(line).useDelimiter(", ");
            // Stores the values to their respected values
            String label = newScan.next();
            double weight = newScan.nextDouble();
            int value = newScan.nextInt();
            // adds the items to the ArrayList w/ creating Items for each one
            fileItems.add(new Item(label, weight, value));
        }
        return fileItems;
    }

    /**
     * The following method iterates through the Items, creates Chromosome objects and
     * adds to the initialPopulation. Returns the initial population
     *
     * @param items
     * @param populationSize
     * @return
     */
    public static ArrayList<Chromosome> initializePopulation(ArrayList<Item> items, int populationSize) {
        ArrayList<Chromosome> initializePopulation = new ArrayList<>();
        // Iterate through Items
        // Creating Chromosomes objects
        // Add to initializePopulation
        for (int i = 0; i < populationSize; i++) {
            Chromosome c = new Chromosome(items);
            initializePopulation.add(c);
        }
        return initializePopulation;
    }

    public static void main(String[] args) throws FileNotFoundException {
        final int POP_SIZE = 100;
        final int NUM_EPOCHS = 1000;
        final int NUM_THREADS = 10;
        // read in the fileItems from the datafile
        ArrayList<Item> items = readData("more_items.txt");
        // create the fileItems based on the data in the file
        ArrayList<Chromosome> nextGen = new ArrayList<>();
        ArrayList<Chromosome> genCopy = new ArrayList<>();

        // sets the population ArrayList to be the inilitalizepop method
        ArrayList<Chromosome> pop = initializePopulation(items, POP_SIZE);

        // the evolution is done here
        for (int i = 0; i < NUM_EPOCHS/NUM_THREADS; i++) { // each generation

            // a crossover is executed
            // randomly pair off the existing Chromosomes in the population
            Collections.shuffle(pop);

            // in a loop, take two at a time from the population arraylist and make them the parents
            try {
                //parent 1, parent 2, and the child are created in the below for-loop 
                for (int j = 0; j < pop.size(); j++) {
                    
                    Chromosome par1 = pop.get(j);
                    
                    Chromosome par2 = pop.get(j + 1);
                 
                    Chromosome child = par1.crossover(par2);
                    // Parent and Child are added to the array list
                    genCopy.add(par1);
                    genCopy.add(par2);
                    nextGen.add(child);
                }
            } catch (IndexOutOfBoundsException e) {

            }

            //  the mutation is performed by calling population.get(j).mutate();
            for (int j = 0; j < pop.size(); j++) {
                int random = (int) (rng.nextInt(10) + 1);
                if (random == 1) {
                    genCopy.get(j).mutate();
                }
            }
            // Clearing the Population
            pop.clear();
            // for loop to iterate through nextGen to grab best Items
            // and adds them to the population
            nextGen.addAll(genCopy);
            Collections.sort(nextGen);
            
                for (int j = 0; j < POP_SIZE/NUM_THREADS; j++) {
                    pop.add(nextGen.get(j));

                }
            
        }
        // this organizes the population
        Collections.sort(pop);

        // this prints out the first item
        System.out.println(pop.get(0));
    }
}
