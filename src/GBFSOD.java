import core_search.*;

public class GBFSOD<S, A> extends BaseSearch<S, A> {
    private final Problem<S, A> problem;
    private final MyPriorityQueue<S, A> frontier;

    public GBFSOD(Problem<S, A> problem, MyPriorityQueue<S, A> frontier) {
        super(problem, frontier);
        this.problem = problem;
        this.frontier = frontier;
    }

    public boolean search() {

        addToFrontier(problem.initialState(), null, 0);

        while (!frontier.isEmpty()) {
            Node<S, A> currentNode = frontier.pop();
            S currentState = currentNode.getState();

            if (problem.goalState().equals(currentState)) {
                printSolutionPath(currentNode);
                return true;
            }

            for (Tuple<S, A> successor : problem.execution(currentState)) {

                int heuristicCost = calculateHeuristic(successor.getState(), problem.goalState());

                addToFrontier(successor.getState(), successor.getAction(), heuristicCost);

                if (problem.goalState().equals(successor.getState())) {
                    printSolutionPath(new Node<>(successor.getState(), successor.getAction(), heuristicCost, currentNode));
                    return true;
                }
            }
        }

        return false;
    }



    // Method to calculate the heuristic cost (sum of distances)
    private int calculateHeuristic(S currentState, S goalState) {
        int[][] currentStateArray = (int[][]) currentState;
        int[][] goalStateArray = (int[][]) goalState;
        int heuristic = 0;

        for (int i = 0; i < currentStateArray.length; i++) {
            for (int j = 0; j < currentStateArray[i].length; j++) {
                int value = currentStateArray[i][j];
                if (value != 0) {
                    outerloop:
                    for (int x = 0; x < goalStateArray.length; x++) {
                        for (int y = 0; y < goalStateArray[x].length; y++) {
                            if (goalStateArray[x][y] == value) {
                                heuristic += Math.abs(x - i) + Math.abs(y - j);
                                break outerloop;
                            }
                        }
                    }
                }
            }
        }

        return heuristic;
    }

    private void addToFrontier(S state, A action, int cost) {
        frontier.add(new Node<>(state, action, cost, null));
    }
}
