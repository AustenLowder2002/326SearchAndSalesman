import core_search.BaseSearch;
import core_search.MyPriorityQueue;
import core_search.Problem;

import java.util.*;


import core_search.*;

import core_search.*;

public class BFS<S, A> extends BaseSearch<S, A> {
    private final Problem<S, A> problem;
    private final MyPriorityQueue<S, A> frontier;
    public BFS(Problem<S, A> problem, MyPriorityQueue<S, A> frontier) {
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
                addToFrontier(successor.getState(), successor.getAction(), currentNode.getPathCost() + successor.getCost());

                if (problem.goalState().equals(successor.getState())) {
                    printSolutionPath(new Node<>(successor.getState(), successor.getAction(), currentNode.getPathCost() + successor.getCost(), currentNode));
                    return true;
                }
            }
        }

        return false;
    }

    private void addToFrontier(S state, A action, int totalCost) {
        frontier.add(new Node<>(state, action, totalCost, null));
    }
}

