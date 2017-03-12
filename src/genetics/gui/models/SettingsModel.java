package genetics.gui.models;

import com.sun.org.apache.bcel.internal.generic.POP;

import javax.swing.*;

/**
 * Created by denistimchenko on 26.02.17.
 */
public class SettingsModel {

    public int POPULATION_COUNT=0;

    public float MUTATION_LIKELIHOOD=0.0f;


    public int getpopulation() {
        return POPULATION_COUNT;
    }

    public void setpopulation( int populationCount ) {
        this.POPULATION_COUNT = populationCount;
    }

    public float getmutationLikehood() {
        return POPULATION_COUNT;
    }

    public void setMutationLikehood( float mutationLikehood ) {
        this.MUTATION_LIKELIHOOD = mutationLikehood;
    }

}
