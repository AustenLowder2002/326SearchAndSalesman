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

    public GeneticAlgorithm_TSP(
            int maxGen, double mRate, double elitism, TSP problem){
        super(maxGen, mRate, elitism);
        this.problem = problem;
    }

    public double calcFitnessScore(
            List<Integer> chromosome){
        return 1/problem.cost(chromosome);
    }

    public Individual<Integer> reproduce(
            Individual<Integer> p1, Individual<Integer> p2) {
        //TODO

        return p1;
    }

    public Individual<Integer> mutate(Individual<Integer> i){
        //TODO
        return i;
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
