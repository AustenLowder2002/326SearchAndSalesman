package search_solutions;

import core_search.BaseSearch;
import core_search.FIFOQueue;
import search_problems.*;

/**
 * Solving the Romania travel problem using breadth first search
 * (AIMA 4e, Ch.3.4.1, page 76)
 */
public class TravelBFS extends BaseSearch<String, String> {
    public TravelBFS(String mapFile) {
        super(new Travel(mapFile), new FIFOQueue<>());
    }


    public static void main(String[] args) {
        TravelBFS t = new TravelBFS("RomaniaMap.txt");
        t.search();
    }
}