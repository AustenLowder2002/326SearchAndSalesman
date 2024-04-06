import core_search.MyPriorityQueue;
import core_search.Node;

import java.util.Comparator;
import java.util.PriorityQueue;

public class SimplePQ<S, A> implements MyPriorityQueue<S, A> {
    private final PriorityQueue<Node<S, A>> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Node::getPathCost));

    @Override
    public Node<S, A> pop() {
        return priorityQueue.poll();
    }

    @Override
    public void add(Node<S, A> e) {
        priorityQueue.add(e);
    }

    @Override
    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }
}
