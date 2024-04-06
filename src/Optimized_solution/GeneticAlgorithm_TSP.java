package Optimized_solution;

import core_algorithms.Chromosome;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithm_TSP {
    private int populationSize;
    private double mutationRate;
    private int elitismCount;
    private Random random;

    public GeneticAlgorithm_TSP(int populationSize, double mutationRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.elitismCount = elitismCount;
        this.random = new Random();
    }

    public void run(int chromosomeLength, int numGenerations) {
        // Initialize population
        List<Chromosome<Integer>> population = initPopulation(chromosomeLength);

        // Evolve the population for a certain number of generations
        for (int i = 0; i < numGenerations; i++) {
            population = evolvePopulation(population);
            System.out.println("Generation " + (i+1) +);
            Chromosome<Object> getBestSolution = null;
            System.out.println("Best Solution: " + getBestSolution.getGenes());
        }

    }

    private List<Chromosome<Integer>> initPopulation(int chromosomeLength) {
        List<Chromosome<Integer>> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            Chromosome<Integer> newChromosome = new Chromosome<>(chromosomeLength);
            newChromosome.generateChromosome();
            population.add(newChromosome);
        }
        return population;
    }

    private Chromosome<Integer> crossover(Chromosome<Integer> parent1, Chromosome<Integer> parent2) {
        Chromosome<Integer> child = new Chromosome<>(parent1.getGenes().size());
        int startPos = parent1.getGenes().isEmpty() ? 0 : random.nextInt(parent1.getGenes().size());
        int endPos = parent1.getGenes().isEmpty() ? 0 : random.nextInt(parent1.getGenes().size());

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

    private void mutate(Chromosome<Integer> chromosome) {
        for (int i = 0; i < chromosome.getGenes().size(); i++) {
            if (random.nextDouble() <= mutationRate) {
                int index1 = random.nextInt(chromosome.getGenes().size());
                int index2 = random.nextInt(chromosome.getGenes().size());
                Collections.swap(chromosome.getGenes(), index1, index2);
            }
        }
    }

    private List<Chromosome<Integer>> evolvePopulation(List<Chromosome<Integer>> population) {
        List<Chromosome<Integer>> newPopulation = new ArrayList<>();

        // Keep the elite individuals
        for (int i = 0; i < elitismCount; i++) {
            newPopulation.add(population.get(i));
        }

        // Crossover and mutate the rest of the population
        for (int i = elitismCount; i < population.size(); i++) {
            Chromosome<Integer> parent1 = population.get(random.nextInt(population.size()));
            Chromosome<Integer> parent2 = population.get(random.nextInt(population.size()));
            Chromosome<Integer> child = crossover(parent1, parent2);
            mutate(child);
            newPopulation.add(child);
        }

        return newPopulation;
    }

    private double calculateFitness(Chromosome<Integer> chromosome) {
        List<Integer> genes = chromosome.getGenes();
        double totalDistance = 0.0;
        // Assuming genes represent the order of visiting cities in the TSP
        for (int i = 0; i < genes.size() - 1; i++) {
            totalDistance += Math.abs(genes.get(i) - genes.get(i+1));
        }
        totalDistance += Math.abs(genes.get(genes.size() - 1) - genes.get(0));
        return 1.0 / totalDistance;
    }

    private Chromosome<Integer> getBestSolution(List<Chromosome<Integer>> population) {
        Chromosome<Integer> bestSolution = population.get(0);
        double bestFitness = calculateFitness(bestSolution);
        for (Chromosome<Integer> chromosome : population) {
            double fitness = calculateFitness(chromosome);
            if (fitness > bestFitness) {
                bestSolution = chromosome;
                bestFitness = fitness;
            }
        }
        return bestSolution;
    }

}

