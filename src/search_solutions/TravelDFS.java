package search_solutions;

import core_search.BaseSearch;
import core_search.LIFOQueue;
import search_problems.*;

/**
 * Solving the Romania travel problem using depth first search
 * (AIMA 4e, Ch.3.4.3, page 78)
 */
public class TravelDFS extends BaseSearch<String, String>{

    public TravelDFS (String mapFile){
        super (new Travel(mapFile), new LIFOQueue<>());
    }

    public static void main(String[] args){
        TravelDFS t = new TravelDFS("RomaniaMap.txt");
        t.search();
    }


}