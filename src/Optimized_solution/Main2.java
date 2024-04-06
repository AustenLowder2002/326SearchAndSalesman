package Optimized_solution;

public class Main2 {
    public static void main(String[] args) {
        int populationSize = 50;
        double mutationRate = 0.01;
        int elitismCount = 2;
        int chromosomeLength = 10;
        int numGenerations = 100;

        GeneticAlgorithm_TSP ga = new GeneticAlgorithm_TSP(populationSize, mutationRate, elitismCount);
        ga.run(chromosomeLength, numGenerations);
    }
}


