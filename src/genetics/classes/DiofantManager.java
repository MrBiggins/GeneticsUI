package genetics.classes;

import genetics.gui.Interfaces.LogEvent;
import genetics.gui.models.SettingsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiofantManager {

    //the result of the function that we're looking for
    public static final int TARGET_VALUE = 30;

    //value returned when total success is reached(i.e.  target solution is found)
    public static final int TARGET_IS_REACHED_FLAG = -1;

    //value returned to indicate that target solution not found
    private static final int TARGET_NOT_REACHED_FLAG = -2;

    //number of individuals in population
    public static int POPULATION_COUNT = 10;

    //number of genes in a chromosome
    public static final int GENES_COUNT = 4;

    //min. value a gene has
    public static final int GENE_MIN = 1;

    //		max. value a gene has
    public static final int GENE_MAX = 30;

    //likelihood (in percent) of the mutation
    public static float MUTATION_LIKELIHOOD = 2.0F;

    //maximum number of "generations". If solution not found after
    //this number of iterations, the program will return.
    //But for sure it will not happen for MAX_ITERATIONS>=1000
    public static final int MAX_ITERATIONS = 10000;


    //array of individuals (Chromosomes)
    private Chromosome population[] = new Chromosome[POPULATION_COUNT];


    public DiofantManager(LogEvent listener, SettingsModel settings) {
        POPULATION_COUNT = settings.getpopulation();
        MUTATION_LIKELIHOOD = settings.getmutationLikehood();
        addListener(listener);

    }

    /*
     * Iterate through all chromosomes and fill their "fitness" property
     * */
    private int fillChromosomesWithFitnesses() {
        for (int i = 0; i < POPULATION_COUNT; ++i) {
            float currentFitness = population[i].calculateFitness(false);

            //float realFitness=population[i].calculateFitness(true);
            //population[i].setFitnessVal(realFitness);
            population[i].setFitness(currentFitness);

              //  addItem("Fitness: " + currentFitness * -1);

            //target solution is found
            if (currentFitness == TARGET_IS_REACHED_FLAG) {

                return i;
            }

        }

        return TARGET_NOT_REACHED_FLAG;
    }


    /*
     * Function of the equalization we're solving
     * */
    public static int function(int a, int b, int c, int d) {
        return a + 2 * b + 3 * c + 4 * d;
    }

    /*
     * Returns sum of fitnesses of all chromosomes.
     * This value is used when calculating likelihood
     * */
    private float getAllFitnessesSum() {
        float allFitnessesSum = .0F;
        for (int i = 0; i < POPULATION_COUNT; ++i) {
            allFitnessesSum += population[i].getFitness();
        }
        return allFitnessesSum;
    }

    /*
     * Iterate through all chromosomes and fill their "likelihood" property
     * */
    private void fillChromosomeWithLikelihoods() {
        float allFitnessesSum = getAllFitnessesSum();
        float last = .0F;

        int i;
        for (i = 0; i < POPULATION_COUNT; ++i) {

            float likelihood = last + (100 * population[i].getFitness() / allFitnessesSum);
            last = likelihood;
            population[i].setLikelihood(likelihood);
        }

        //setting last chromosome's likeliness to 100 by hand.
        //because sometimes it's 99.9999 and that's not good
        population[i - 1].setLikelihood(100);

    }

    /*
     * Prints all chromosomes to the log using toString() of Chromosome objects
     * */
    private void printAllChromosomes() {
        log("Here is the current state of all chromosomes:");
        for (int i = 0; i < POPULATION_COUNT; ++i) {
            log("**********  Chromosome " + i + "  ********");
            log(population[i].toString());
        }
    }

    /*
     * Writes a string to the log
     * */
    public static void log(String message) {
        //System.out.println( message );
    }

    public static void logEvent(String message) {
        addItem(message);
    }

    /*
     * Returns random integer number between min and max ( all included :)  )
     * */
    public static int getRandomInt(int min, int max) {
        Random randomGenerator;
        randomGenerator = new Random();
        return randomGenerator.nextInt(max + 1) + min;
    }

    /*
     * Returns random float number between min (included) and max ( NOT included :)  )
     * */
    public static float getRandomFloat(float min, float max) {
        return (float) (Math.random() * max + min);
    }


    /*
     * Returns a correct random value for a gene
     * */
    public static int getRandomGene() {
        return getRandomInt(GENE_MIN, GENE_MAX);
    }


    /*
     * Fills a chromosome with random genes.
     * */
    private void fillChromosomeWithRandomGenes(Chromosome chromosome) {
        for (int i = 0; i < GENES_COUNT; ++i) {
            chromosome.getGenes()[i] = getRandomGene();
        }

    }

    /*
     * Creates an initial population
     * */
    private void createInitialPopulation() {
        for (int i = 0; i < POPULATION_COUNT; ++i) {
            population[i] = new Chromosome();
            fillChromosomeWithRandomGenes(population[i]);
        }
    }


    /*
     * Returns pairs for the crossover operations.
     * [0][0] with [0][1]
     * [1][0] with [1][1]
     * etc. etc.
     * */
    private int[][] getPairsForCrossover() {

        int[][] pairs = new int[POPULATION_COUNT][2];

        for (int i = 0; i < POPULATION_COUNT; ++i) {
            float rand = getRandomFloat(0, 100);
            int firstChromosome = getChromosomeNumberForThisRand(rand);

            int secondChromosome;
            do {
                rand = getRandomFloat(0, 100);
                secondChromosome = getChromosomeNumberForThisRand(rand);

            } while (firstChromosome == secondChromosome);  //prevent individual's crossover with itself :)


            pairs[i][0] = firstChromosome;
            pairs[i][1] = secondChromosome;

        }

        return pairs;
    }

    private void analizePairs(int[][] pairs) {

        int[] totals = new int[POPULATION_COUNT];

        for (int i = 0; i < POPULATION_COUNT; ++i) {
            totals[i] = 0;
        }

        for (int i = 0; i < POPULATION_COUNT; ++i) {
            for (int j = 0; j < 2; ++j) {
                totals[pairs[i][j]]++;
            }
        }
    }

    private int getChromosomeNumberForThisRand(float rand) {

        int i;
        for (i = 0; i < POPULATION_COUNT; ++i) {

            if (rand <= population[i].getLikelihood()) {
                return i;
            }
        }
        return i - 1; //unreachable code imho :) But without this it doesn't compile

    }

    private Chromosome[] performCrossoverAndMutationForThePopulationAndGetNextGeneration(int[][] pairs) {

        Chromosome nextGeneration[] = new Chromosome[POPULATION_COUNT];

        for (int i = 0; i < POPULATION_COUNT; ++i) {
            Chromosome firstParent = population[pairs[i][0]];
            Chromosome secondParent = population[pairs[i][1]];

            Chromosome result = firstParent.singleCrossover(secondParent);
            nextGeneration[i] = result;

            nextGeneration[i] = nextGeneration[i].mutateWithGivenLikelihood();

        }

        return nextGeneration;
    }


    public Chromosome[] getPopulation() {
        return population;
    }


    public void setPopulation(Chromosome[] population) {
        this.population = population;
    }


    public void startGenetecs() {
        addItem("main() is started");
        addItem("POPULATION_COUNT=" + POPULATION_COUNT);
        addItem("GENES_COUNT=" + GENES_COUNT);
        createInitialPopulation();

        long iterationsNumber = 0;
        do {

            int fillingWithFitnessesResult = fillChromosomesWithFitnesses();
            Chromosome[] population = getPopulation();

            if (fillingWithFitnessesResult != TARGET_NOT_REACHED_FLAG) {
                addItem("Solution is found: " + getPopulation()[fillingWithFitnessesResult]);
                return;
            }

            fillChromosomeWithLikelihoods();
            printAllChromosomes();
            int[][] pairs = getPairsForCrossover();
            analizePairs(pairs);


            Chromosome nextGeneration[] = performCrossoverAndMutationForThePopulationAndGetNextGeneration(pairs);
            setPopulation(nextGeneration);

            addItem("-=-=========== Finished iteration #" + iterationsNumber);


        } while (iterationsNumber++ < MAX_ITERATIONS);

        addItem("SOLUTION NOT FOUND. Sad but true...");

    }

    private static List<LogEvent> listeners = new ArrayList<LogEvent>();

    public void addListener(LogEvent toAdd) {
        listeners.add(toAdd);
    }

    public static void addItem(String message) {

        for (LogEvent hl : listeners)
            hl.addLogItem(message);
    }

}
