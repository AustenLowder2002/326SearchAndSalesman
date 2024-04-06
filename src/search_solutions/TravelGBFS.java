package search_solutions;

import core_search.BaseSearch;
import core_search.Node;
import core_search.SortedQueue;
import search_problems.Travel;

import java.util.Comparator;

/**
 * Solving the Romania travel problem using greedy best-first search
 * (AIMA 4e, Ch. 3.5.1, page 85)
 */
public class TravelGBFS extends BaseSearch<String, String> {

    public TravelGBFS(String map, String estimates) {
        super(new Travel(map, estimates),
                new SortedQueue<>(new CompareEstimates(new Travel(map, estimates))));
    }

    public static void main(String[] args){
        TravelGBFS agent = new TravelGBFS("RomaniaMap.txt",
                "RomaniaMapEstimates.txt");

        agent.search();

    }

    public static class CompareEstimates implements Comparator<Node<String, String>> {

        public final Travel problem;
        public CompareEstimates (Travel problem){
            this.problem = problem;
        }

        @Override
        public int compare(Node<String, String> o1, Node<String, String> o2) {
            if(problem.getEstimatedDistance(o1.getState()) <
                    problem.getEstimatedDistance(o2.getState())){
                return -1;
            }else if(problem.getEstimatedDistance(o1.getState()) ==
                    problem.getEstimatedDistance(o2.getState())){
                return 0;
            }else{
                return 1;
            }
        }
    }
}



