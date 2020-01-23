package ssy066.part2;

import ssy066.part1.State;

import java.util.HashSet;

/**
 * This is the Graph class that you need to implement. It is used when creating a graph
 * and will be used when navigating the graph. Your task is to create new methods for adding new states
 * and transitions as well as decide how the graph should be represented internally in this class. You
 * can store all states and transitions explicitly or use a more compact representation.
 * <p>
 * Read more about graph representation in this great article by Vaidehi Joshi:
 * https://medium.com/basecs/from-theory-to-practice-representing-graphs-cfd782c5be38
 * <p>
 * Observe that this class should not know anything about operations or store operations internally
 */

public class Graph {

    private State initial;

    /**
     * This is the constructor that creates an empty Graph with only one initial state.
     * The method is already defined and you do not need to change it.
     */
    public Graph(State initial) {
        this.initial = initial;
    }

    /**
     * Returns the initial state. You do not need to change this.
     *
     * @return the initial state of this graph
     */
    public State getInitialState() {
        return initial;
    }


    // *************************
    // Below, you should add methods to fill the graph with states and transitions.
    // You can add one thing at the time (or all at ones).
    // Do not forget to add unit tests to test all your methods
    // *************************


    private HashSet<State> states = new HashSet<>();
    private HashSet<Transition> transitions = new HashSet<>();


    // *************************
    // Below, you should implement the methods.
    // Do not forget to add unit tests to test all your methods
    // *************************


    /**
     * This functions should return all states that this graph represent. Depending on your
     * internal representation, this may need to compute all possible states
     * <p>
     * It should not be possible to add states to the graph by changing the returned set.
     *
     * @return a set of all states in this graph.
     */
    public HashSet<State> getStates() {
        return states;
    }

    /**
     * This functions should return all transitions that this graph represent. Depending on your
     * internal representation, this may need to compute all possible transitions and create
     * Transition objects and return.
     * <p>
     * It should not be possible to add transitions to the graph by changing the returned set.
     *
     * @return a set of all transitions of this graph
     */
    public HashSet<Transition> getTransitions() {
        return transitions;

    }


    /**
     * Given a state, returns all outgoing transitions from this state, i.e. all transition where
     * the state is a tail.
     *
     * @param s The state
     * @return All outgoing transitions
     */
    public HashSet<Transition> getOutGoingTransitions(State s) {

        HashSet<Transition> trans = new HashSet<>();

        for (Transition T : transitions) {

            if (T.tail.equals(s)) {

                trans.add(T);
            }
        }
        return trans;
    }

    /**
     * Given a state, return all transitions coming into that state, i.e. all transitions where
     * the state is head
     *
     * @param s the state
     * @return a set including the incoming transitions
     */
    public HashSet<Transition> getIncomingTransitions(State s) {

        HashSet<Transition> trans = new HashSet<>();

        for (Transition T : transitions) {
            if (T.head.equals(s)) {
                trans.add(T);
            }
        }

        return trans;
    }

    /**
     * Returns all states that does not have any incoming transitions
     *
     * @return A set of states
     */
    public HashSet<State> getSourceStates() {

        HashSet<State> sourceStates = new HashSet<>();

        for (State s : states) {

            if (getIncomingTransitions(s).isEmpty()) {

                sourceStates.add(s);
            }
        }
        return sourceStates;
    }

    /**
     * Returns all states that does not have any outgoing transitions
     *
     * @return A set of states
     */
    public HashSet<State> getSinkStates() {

        HashSet<State> sinkStates = new HashSet<>();

        for (State s : states) {

            if (getOutGoingTransitions(s).isEmpty()) {

                sinkStates.add(s);
            }
        }
        return sinkStates;
    }


}
