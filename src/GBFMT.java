import core_search.*;

import java.util.Stack;

public class GBFMT<S, A> extends BaseSearch<S, A> {
    private final Problem<S, A> problem;
    private final MyPriorityQueue<S, A> frontier;

    public GBFMT(Problem<S, A> problem, MyPriorityQueue<S, A> frontier) {
        super(problem, frontier);
        this.problem = problem;
        this.frontier = frontier;
    }

    @Override
    public boolean search() {

        addToFrontier(problem.initialState(), null, 0);

        while (!frontier.isEmpty()) {
            Node<S, A> currentNode = frontier.pop();
            S currentState = currentNode.getState();

            if (problem.goalState().equals(currentState)) {
                printSolutionPath(currentNode);
                System.exit(0);
                return true;
            }


            for (Tuple<S, A> successor : problem.execution(currentState)) {

                int heuristicCost = calculateHeuristic(successor.getState(), problem.goalState());

                addToFrontier(successor.getState(), successor.getAction(), heuristicCost);

                if (problem.goalState().equals(successor.getState())) {
                    printSolutionPath(new Node<>(successor.getState(), successor.getAction(), heuristicCost, currentNode));
                    System.exit(0);
                    return true;
                }
            }
        }

        return false;
    }

    // Method to calculate the heuristic cost (misplaced tiles)
    private int calculateHeuristic(S currentState, S goalState) {
        int[][] currentStateArray = (int[][]) currentState;
        int[][] goalStateArray = (int[][]) goalState;
        int misplacedTiles = 0;

        for (int i = 0; i < currentStateArray.length; i++) {
            for (int j = 0; j < currentStateArray[i].length; j++) {
                if (currentStateArray[i][j] != goalStateArray[i][j]) {
                    misplacedTiles++;
                }
            }
        }

        return misplacedTiles;
    }

    private void addToFrontier(S state, A action, int cost) {
        frontier.add(new Node<>(state, action, cost, null));
    }

}


