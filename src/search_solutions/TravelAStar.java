package search_solutions;

import core_search.BaseSearch;
import core_search.Node;
import core_search.SortedQueue;
import search_problems.Travel;

import java.util.Comparator;

/**
 * Solving the Romania travel problem using A* search
 * (AIMA 4e Ch. 3.5.2, page 85)
 */
public class TravelAStar extends BaseSearch<String, String> {
    public TravelAStar(String map, String estimates) {
        super(new Travel(map,estimates),
                new SortedQueue<>(new CompareSum(new Travel(map, estimates))));
    }

    public static void main(String[] args){
        TravelAStar agent = new TravelAStar("RomaniaMap.txt",
                "RomaniaMapEstimates.txt");

        agent.search();

    }

    public static class CompareSum implements Comparator<Node<String, String>> {
        private final Travel problem;
        public CompareSum (Travel problem){
            this.problem = problem;
        }
        @Override
        public int compare(Node<String, String> o1, Node<String, String> o2) {
            if(problem.getEstimatedDistance(o1.getState()) + o1.getPathCost() <
                    problem.getEstimatedDistance(o2.getState()) + o2.getPathCost()){
                return -1;
            }else if(problem.getEstimatedDistance(o1.getState()) + o1.getPathCost() ==
                    problem.getEstimatedDistance(o2.getState()) + o2.getPathCost()){
                return 0;
            }else{
                return 1;
            }
        }
    }
}




