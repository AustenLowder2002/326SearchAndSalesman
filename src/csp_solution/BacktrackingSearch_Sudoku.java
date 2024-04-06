package csp_solution;

import core_algorithms.BacktrackingSearch;
import csp_problems.Sudoku;

public class BacktrackingSearch_Sudoku extends BacktrackingSearch<String,Integer> {

    public BacktrackingSearch_Sudoku(Sudoku problem){
        super(problem);
    }

    /**
     * To revise an arc: for each value in tail's domain, there must be a value in head's domain that's different
     *                   if not, delete the value from the tail's domain
     * @param head head of the arc to be revised
     * @param tail tail of the arc to be revised
     * @return true if the tail has been revised (lost some values), false otherwise
     */
    @Override
    public boolean revise(String head, String tail) {
        boolean revised = false;
        // Get the domain of the head variable
        var headDomain = allVariables.get(head).domain();

        // For each value in the head's domain, check if there's a value in the tail's domain that's different
        for (Integer value : headDomain) {
            boolean hasDifferentValue = false;
            for (Integer tailValue : allVariables.get(tail).domain()) {
                if (!value.equals(tailValue)) {
                    hasDifferentValue = true;
                    break;
                }
            }
            // If there's no different value in the tail's domain, remove the value from the head's domain
            if (!hasDifferentValue) {
                headDomain.remove(value);
                revised = true;
            }
        }
        return revised;
    }

    /**
     * Implementing the minimum-remaining-values(MRV) ordering heuristic.
     * @return the variable with the smallest domain among all the unassigned variables;
     *         null if all variables have been assigned
     */
    @Override
    public String selectUnassigned() {
        String minVariable = null;
        int minDomainSize = Integer.MAX_VALUE;

        // Iterate through all unassigned variables
        for (String variable : allVariables.keySet()) {
            if (!assigned(variable)) {
                int domainSize = allVariables.get(variable).domain().size();
                // Update minVariable if the current variable has a smaller domain size
                if (domainSize < minDomainSize) {
                    minVariable = variable;
                    minDomainSize = domainSize;
                }
            }
        }
        return minVariable;
    }

    /**
     * @param args (no command-line argument is needed to run this program)
     */
    public static void main(String[] args) {
        String filename = "src/local_search/TestCase9.txt";
        Sudoku problem = new Sudoku(filename);
        BacktrackingSearch_Sudoku agent = new BacktrackingSearch_Sudoku(problem);
        System.out.println("loading puzzle from " + filename + "...");
        problem.printPuzzle(problem.getAllVariables());
        if (agent.initAC3() && agent.search()) {
            System.out.println("Solution found:");
            problem.printPuzzle(agent.getAllVariables());
        } else {
            System.out.println("Unable to find a solution.");
        }
    }
}
