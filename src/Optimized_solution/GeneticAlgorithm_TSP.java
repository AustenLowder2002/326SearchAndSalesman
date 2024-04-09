package Optimized_solution;

import core_algorithms.GeneticAlgorithm;
import core_algorithms.Individual;
import optimization_problems.TSP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implement elements that are problem specific
 *
 */
public class GeneticAlgorithm_TSP
        extends GeneticAlgorithm<Integer> {
    private final TSP problem;
    private final double mutationRate;

    public GeneticAlgorithm_TSP(int maxGen, double mRate, double elitism, TSP problem) {
        super(maxGen, mRate, elitism);
        this.problem = problem;
        this.mutationRate = mRate;
    }

    public double calcFitnessScore(
            List<Integer> chromosome){
        return 1/problem.cost(chromosome);
    }

    public Individual<Integer> reproduce(Individual<Integer> p1, Individual<Integer> p2) {
        List<Integer> parent1 = p1.getChromosome();
        List<Integer> parent2 = p2.getChromosome();
        int size = parent1.size();

        int startPos = (int) (Math.random() * size);
        int endPos = (int) (Math.random() * size);

        List<Integer> offspringChromosome = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if (startPos < endPos && i > startPos && i < endPos) {
                offspringChromosome.add(parent1.get(i));
            } else if (startPos > endPos && !(i < startPos && i > endPos)) {
                offspringChromosome.add(parent1.get(i));
            }
        }

        // Add missing cities from parent2 to offspring
        for (int i = 0; i < size; i++) {
            if (!offspringChromosome.contains(parent2.get(i))) {
                for (int j = 0; j < size; j++) {
                    if (offspringChromosome.get(j) == null) {
                        offspringChromosome.set(j, parent2.get(i));
                        break;
                    }
                }
            }
        }

        return new Individual<>(offspringChromosome, calcFitnessScore(offspringChromosome));
    }

    public Individual<Integer> mutate(Individual<Integer> individual) {
        List<Integer> chromosome = individual.getChromosome();
        int size = chromosome.size();

        for (int pos1 = 0; pos1 < size; pos1++) {
            if (Math.random() < mutationRate) {
                int pos2 = (int) (Math.random() * size);

                // Swap cities at pos1 and pos2
                int city1 = chromosome.get(pos1);
                int city2 = chromosome.get(pos2);
                chromosome.set(pos1, city2);
                chromosome.set(pos2, city1);
            }
        }

        return new Individual<>(chromosome, calcFitnessScore(chromosome));
    }


    public List<Individual<Integer>> generateInitPopulation(
            int popSize, int numCities ){
        List<Individual<Integer>> population =
                new ArrayList<>(popSize);
        for(int i=0; i<popSize; i++){
            List<Integer> chromosome = new ArrayList<>(numCities);
            for(int j=0; j<numCities; j++){
                chromosome.add(j);
            }
            Collections.shuffle(chromosome);
            Individual<Integer> indiv = new Individual<>(
                    chromosome, calcFitnessScore(chromosome));
            population.add(indiv);
        }
        return population;
    }

    public static void main(String[] args) {
        int MAX_GEN = 200;
        double MUTATION_RATE = 0.05;
        int POPULATION_SIZE = 1000;
        int NUM_CITES = 26; //choose from 5, 6, 17, 26
        double ELITISM = 0.2;

        TSP problem = new TSP(NUM_CITES);

        GeneticAlgorithm_TSP agent = new GeneticAlgorithm_TSP(
                MAX_GEN, MUTATION_RATE, ELITISM, problem);

        Individual<Integer> best =
                agent.evolve(agent.generateInitPopulation(
                        NUM_CITES, POPULATION_SIZE));

        System.out.println(best);
        System.out.println(problem.cost(best.getChromosome()));
    }

}