package core_search;

import java.util.List;

/**
 * This interface represents a generic search problem formulation.
 * (See AIMA 4e, page 65, Ch 3.1.1 for a description.)
 *
 * S represents the date type of state
 * A represents the date type of action
 */
public interface Problem <S, A>{
    S initialState();
    S goalState();

    //executes all the applicable actions at the state and
    //returns a list of tuples of [new state, action taken, cost]
    List<Tuple<S,A>> execution(S state);

    //Prints out the state in a neat format
    void printState(S state);
}
