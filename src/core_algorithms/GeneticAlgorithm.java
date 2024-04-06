package core_algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm<T> {
    private int populationSize;
    private double mutationRate;
    private int elitismCount;
    private Random random;

    public GeneticAlgorithm(int populationSize, double mutationRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.elitismCount = elitismCount;
        this.random = new Random();
    }

    public List<Chromosome<T>> initPopulation(int chromosomeLength) {
        List<Chromosome<T>> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome<T> newChromosome = new Chromosome<>(chromosomeLength);
            newChromosome.generateChromosome();
            population.add(newChromosome);
        }
        return population;
    }

    public Chromosome<T> crossover(Chromosome<T> parent1, Chromosome<T> parent2) {
        Chromosome<T> child = new Chromosome<>(parent1.getGenes().size());
        int startPos = random.nextInt(parent1.getGenes().size());
        int endPos = random.nextInt(parent1.getGenes().size());

        for (int i = 0; i < child.getGenes().size(); i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                child.getGenes().set(i, parent1.getGenes().get(i));
            } else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.getGenes().set(i, parent1.getGenes().get(i));
                }
            }
        }

        for (int i = 0; i < parent2.getGenes().size(); i++) {
            if (!child.getGenes().contains(parent2.getGenes().get(i))) {
                for (int ii = 0; ii < child.getGenes().size(); ii++) {
                    if (child.getGenes().get(ii) == null) {
                        child.getGenes().set(ii, parent2.getGenes().get(i));
                        break;
                    }
                }
            }
        }

        return child;
    }

    public void mutate(Chromosome<T> chromosome) {
        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            if (random.nextDouble() <= mutationRate) {
                T newGene = chromosome.generateGene();
                chromosome.getGenes().set(i, newGene);
            }
        }
    }

    public List<Object> evolvePopulation(List<Chromosome<T>> population) {
        List<Object> newPopulation = new ArrayList<>();

        // Keep the elite individuals
        for (int i = 0; i < elitismCount; i++) {
            newPopulation.add(population.get(i));
        }

        // Crossover and mutate the rest of the population
        for (int i = elitismCount; i < population.size(); i++) {
            Chromosome<T> parent1 = population.get(random.nextInt(population.size()));
            Chromosome<T> parent2 = population.get(random.nextInt(population.size()));
            Chromosome<T> child = crossover(parent1, parent2);
            mutate(child);
            newPopulation.add(child);
        }

        return newPopulation;
    }
}
