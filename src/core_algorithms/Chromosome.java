package core_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chromosome<T> {
    private List<T> genes;

    public Chromosome(int chromosomeLength) {
        this.genes = new ArrayList<>(chromosomeLength);
    }

    public List<T> getGenes() {
        return genes;
    }

    public void generateChromosome() {
        Random random = new Random();
        for (int i = 0; i < genes.size(); i++) {
            genes.add((T) Integer.valueOf(random.nextInt(10))); // Change Integer to your desired gene type
        }
    }

    public T generateGene() {
        Random random = new Random();
        return (T) Integer.valueOf(random.nextInt(10)); // Change Integer to your desired gene type
    }
}
