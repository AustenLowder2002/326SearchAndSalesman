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
            // Initialize the frontier with the initial state
            addToFrontier(problem.initialState(), null, 0);

            while (!frontier.isEmpty()) {
                Node<S, A> currentNode = frontier.pop();
                S currentState = currentNode.getState();

                // Check if the current state is the goal state
                if (problem.goalState().equals(currentState)) {
                    printSolutionPath(currentNode);
                    return true; // Found a solution
                }

                // Expand the current node and add its successors to the frontier
                for (Tuple<S, A> successor : problem.execution(currentState)) {
                    Node<S, A> childNode = new Node<>(successor.getState(), successor.getAction(), successor.getCost(), currentNode);
                    addToFrontier(successor.getState(), successor.getAction(), successor.getCost() + currentNode.getPathCost());
                }
            }

            return false; // No solution found
        }

        private void addToFrontier(S state, A action, int cost) {
            frontier.add(new Node<>(state, action, cost, null));
        }
    }
