package genetics.classes;

public class Chromosome {

    private int genes[] = new int[DiofantManager.GENES_COUNT];

    private float fitness;

    private float fitnessValue;

    private float likelihood;


    public float getFitness() {
        return fitness;
    }

    /*public float getFitnessVal() {
        return fitnessValue ;
    }

    public void setFitnessVal(float val) {
       fitnessValue = val;
    }*/

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    public float getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(float likelihood) {
        this.likelihood = likelihood;
    }

    public float calculateFitness(boolean getRealFitness) {
        int a = genes[0];
        int b = genes[1];
        int c = genes[2];
        int d = genes[3];

        int closeness = Math.abs(DiofantManager.TARGET_VALUE - DiofantManager.function(a, b, c, d)) * -1;

        if(getRealFitness){
            return closeness;
        }

        float modulFitness = ((float) closeness * -1);
        DiofantManager.addItem("Fitness value: " + closeness);

        return 0 != closeness ? 1 / modulFitness : DiofantManager.TARGET_IS_REACHED_FLAG;
    }

    public Chromosome mutateWithGivenLikelihood() {

        Chromosome result = (Chromosome) this.clone();

        for (int i = 0; i < DiofantManager.GENES_COUNT; ++i) {
            float randomPercent = DiofantManager.getRandomFloat(0, 100);
            if (randomPercent < DiofantManager.MUTATION_LIKELIHOOD) {
                int oldValue = result.getGenes()[i];
                int newValue = DiofantManager.getRandomGene();
                result.getGenes()[i] = newValue;


            }
        }
        return result;
    }

    public Chromosome[] doubleCrossover(Chromosome chromosome) {

        DiofantManager.log("Starting DOUBLE crossover operation...");
        DiofantManager.log("THIS chromo:" + this);
        DiofantManager.log("ARG chromo:" + chromosome);


        int crossoverline = getRandomCrossoverLine();
        Chromosome[] result = new Chromosome[2];
        result[0] = new Chromosome();
        result[1] = new Chromosome();

        for (int i = 0; i < DiofantManager.GENES_COUNT; ++i) {
            if (i <= crossoverline) {
                result[0].getGenes()[i] = this.getGenes()[i];
                result[1].getGenes()[i] = chromosome.getGenes()[i];

            } else {
                result[0].getGenes()[i] = chromosome.getGenes()[i];
                result[1].getGenes()[i] = this.getGenes()[i];
            }

        }

        DiofantManager.log("RESULTING chromo #0:\n" + result[0]);
        DiofantManager.log("RESULTING chromo #1:\n" + result[1]);
        DiofantManager.log("DOUBLE crossover operation is finished...");

        return result;

    }

    public Chromosome singleCrossover(Chromosome chromosome) {
        DiofantManager.log("Starting SINGLE crossover operation...Calling DOUBLE crossover first....");
        Chromosome[] children = doubleCrossover(chromosome);
        DiofantManager.log("Selecting ONE of the 2 children returned by DOUBLE crossover ....");
        int childNumber = DiofantManager.getRandomInt(0, 1);
        DiofantManager.log("Selected child #" + childNumber + ", here it is:\n" + children[childNumber]);
        DiofantManager.log("SINGLE crossover operation is finished");
        return children[childNumber];
    }

    public String toString() {

        StringBuffer result = new StringBuffer();

        result.append("Genes: (");

        for (int i = 0; i < DiofantManager.GENES_COUNT; ++i) {
            result.append("" + genes[i]);
            result.append(i < DiofantManager.GENES_COUNT - 1 ? ", " : "");

        }

        result.append(")\n");

        //result.append("Fitness:" + fitness + "\n");
        //result.append("Likelihood:" + likelihood + "\n");


        return result.toString();


    }

    private static int getRandomCrossoverLine() {
        int line = DiofantManager.getRandomInt(0, DiofantManager.GENES_COUNT - 2);  //-2 because we dn't need the position after the last gene
        DiofantManager.log("Generated random CrossoverLine at position " + line);
        return line;
    }

    protected Object clone() {
        Chromosome resultChromosome = new Chromosome();
        resultChromosome.setFitness(this.getFitness());
        resultChromosome.setLikelihood(this.getLikelihood());

        int resultGenes[] = this.genes.clone();

        resultChromosome.setGenes(resultGenes);

        return resultChromosome;
    }

}
