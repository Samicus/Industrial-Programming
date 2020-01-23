package ssy066.part2;

import ssy066.part1.Predicate;
import ssy066.part1.State;

import java.util.*;


public class SearchAGraph {

    /**
     * This method searches a graph for a shortest path to a goal state. The goal
     * state is the state that evaluates the goal predicate to true.
     * <p>
     * Remember that the graph can contain loops, however, if you think about it, maybe this is
     * not a problem when trying to find the shortest path!
     * <p>
     * I have also created a PimpedState class. Use that if you like, to
     * store the state you have visited together with the path to it and its
     * cost.
     *
     * @param g    the graph. Start searching in its initial state. Can contain loops.
     * @param goal the goal that you are searching for
     * @return the SearchResult that includes the shortest path. If no goal is found, return
     * an empty SearchResult
     * REMEMBER: The path always start at the initial state.
     * Maybe you have to do Collections.reverse(list_with_transition_labels)
     */
    public SearchResult findShortestPath(Graph g, Predicate goal) {

        // A tip: Use pimped state when searching instead of only state.
        Deque<PimpedState> que = new ArrayDeque<PimpedState>();
        HashSet<PimpedState> visited = new HashSet<>();
        List<Transition> init = new LinkedList<>();


        PimpedState initP = new PimpedState(g.getInitialState(), init, 0);
        que.push(initP);
        PimpedState temp = initP;

        int cost = 0;
        int runs;
        boolean res = false;

        while (!que.isEmpty()) {

            runs = 0;
            PimpedState pimpTemp = que.pop();

            if (!visited.contains(pimpTemp.pathToState())) {

                visited.add(pimpTemp);

                if (goal.eval(pimpTemp.state)) {

                    if (!res) {

                        temp = pimpTemp;
                        res = true;
                    }

                    if (pimpTemp.pathToState().size() < temp.pathToState().size()) {

                        temp = pimpTemp;
                    }
                } else {

                    for (Transition T : pimpTemp.pathToState()) {

                        if (T.head.equals(pimpTemp.state)) {

                            runs++;
                        }
                    }
                    if (runs < 2) {

                        for (Transition T : g.getOutGoingTransitions(pimpTemp.state)) {

                            State s = T.head;
                            List<Transition> path = pimpTemp.pathToState();
                            path.add(T);
                            cost = pimpTemp.cost() + T.cost;
                            PimpedState qPimped = new PimpedState(s, path, cost);

                            que.push(qPimped);
                        }
                    }
                }
            }


        }

        List<String> path = new LinkedList<>();

        for (Transition T : temp.pathToState()) {

            path.add(T.label);
        }

        SearchResult searchRes = new SearchResult(path, temp.cost());

        return searchRes;
    }


    /**
     * This method searches a graph for the lowest cost path to a goal state. The goal
     * state is the state that evaluates the goal predicate to true.
     * <p>
     * This is trickier compared to the shortest path since we need to search the complete graph. But since
     * there can be loops in the graph, it is important to have a strategy for stopping the search
     * so it does not search forever. Think about when you can stop the search
     *
     * @param g    the graph. Start searching in its initial state. It can contain loops!
     * @param goal the goal that you are searching for
     * @return the SearchResult that includes the lowest cost path
     * and the cost to the lowest cost path. If no goal is found, return
     * an empty SearchResult.
     * REMEMBER: The path you return should always start in the initial state, so
     * maybe you have to do Collections.reverse(list_with_transition_labels) before returning?
     */
    public SearchResult findLowestCostPath(Graph g, Predicate goal) {

        // A tip: Use pimped state when searching instead of only state.
        Deque<PimpedState> que = new ArrayDeque<PimpedState>();
        HashSet<PimpedState> visited = new HashSet<>();
        List<Transition> init = new LinkedList<>();


        PimpedState initP = new PimpedState(g.getInitialState(), init, 0);
        que.push(initP);
        PimpedState temp = initP;

        int cost = 0;
        int runs;
        boolean res = false;

        while (!que.isEmpty()) {

            runs = 0;
            PimpedState pimpTemp = que.pop();

            if (!visited.contains(pimpTemp.pathToState())) {

                visited.add(pimpTemp);

                if (goal.eval(pimpTemp.state)) {

                    if (!res) {

                        temp = pimpTemp;
                        res = true;
                    }

                    if (pimpTemp.cost() < temp.cost()) {

                        temp = pimpTemp;
                    }
                } else {

                    for (Transition T : pimpTemp.pathToState()) {

                        if (T.head.equals(pimpTemp.state)) {

                            runs++;
                        }
                    }
                    if (runs < 2) {

                        for (Transition T : g.getOutGoingTransitions(pimpTemp.state)) {

                            State s = T.head;
                            List<Transition> path = pimpTemp.pathToState();
                            path.add(T);
                            cost = pimpTemp.cost() + T.cost;
                            PimpedState qPimped = new PimpedState(s, path, cost);

                            que.push(qPimped);
                        }
                    }
                }
            }


        }

        List<String> path = new LinkedList<>();

        for (Transition T : temp.pathToState()) {

            path.add(T.label);
        }

        SearchResult searchRes = new SearchResult(path, temp.cost());

        return searchRes;
    }
}
