package ssy066.part2;

import junit.framework.TestCase;
import ssy066.part1.*;

import java.util.*;

/**
 * Finally, we have all the pieces ready! In this test you will create code that
 * creates a graph based on a set of operations. It requires some more of you since you have to
 * implement your own Graph class as well as the tests. Also the graph making
 * algorithm is a little bit harder then in part 1.
 *
 * Read about graphs in:
 *
 * http://en.wikipedia.org/wiki/Directed_graph
 * http://en.wikipedia.org/wiki/Graph_(mathematics)
 *
 * Here you will implement the algorithm that creates a Graph. The Graph is implemented in
 * the Graph class, and you have to create methods for adding states and transitions and complete the
 * methods that haven't been implemented. How you represent the graph internally in the class
 * is up to you.
 *
 * Read more about graph representation in this great article by Vaidehi Joshi:
 * https://medium.com/basecs/from-theory-to-practice-representing-graphs-cfd782c5be38
 *
 * A graph should be created given a set of operations by the GraphFactory.makeMeAGraph method that you also
 * have to implement. The Graph itself should not know anything about operations!
 *
 * In part 2 (Test3 and Test4), you have to create your own tests for each method that you make. Try to divide everything
 * into small parts that you can test from here.
 *
 * You can use the searchGraph_no_loops() - testGraph3() defined later in the code for testing your algorithms,
 * or you can create your own operations. When submitting you final code, we will test with other graphs.
 *
 * Also remember to use System.out.println() in your code, when troubleshooting.
 *
 * Also try debugging. Add a line break and run the test in debug-mode and step through
 * your code, line by line.
 *
 *
 * @author Kristofer
 */
public class Test3_Graphs extends TestCase {

    // Add your tests here and use the operations defined in searchGraph_no_loops(), searchGraph_with_loops() and searchGraph_no_loops().
    // First add test so you know that makeMeAGraph works and that your graph can be created.
    // For example, maybe you need to have a method that can say what operations that are enabled
    // in a given state?


    // When you have some sort of graph-making code, add tests for all the methods that has not been
    // implemented in the Graph class.

    /**
     * This is an example test to evaluate that the graph contains the state
     */
    public void test_graph1_states() {
        TestGraph t = testGraph1();
        Graph g = GraphFactory.makeMeAGraph(t.operations,t.initialState);

        Set<State> result = g.getStates();

        // below is created to test the result.
        Set<State> compare = new HashSet<State>();
        compare.add(t.initialState);
        compare.add(t.initialState.newState("v1", 1));
        compare.add(t.initialState.newState("v1", 2));

        System.out.println("Result: " + result);
        System.out.println("Should be: " + compare);

        assertTrue(result.equals(compare));

    }


    /**
     * This is another example that test some of the methods in the graph class
     */
    public void test_graph3_some() {
        // This should not be the same graph as the other tests! So create it here
        TestGraph t = testGraph3();
        Graph result = GraphFactory.makeMeAGraph(t.operations,t.initialState);

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";

        Predicate v1Eq0 = new EQ(v1, 0);
        Predicate v1Eq1 = new EQ(v1, 1);
        Predicate v1Eq2 = new EQ(v1, 2);
        Predicate v1Eq2AndFalse = new AND(new NOT(new EQ(v2, true)), v1Eq2);
        Predicate v1Eq2AndTrue = new AND(new EQ(v2, true), v1Eq2);

        Action v1Inc = new NextInc(v1, 1);
        Action v1Dec = new NextDec(v1, 1);
        Action v1To0 = new Next(v1, 0);

        Operation o1 = new Operation("o1", v1Eq0, v1Inc);
        Operation o2 = new Operation("o2", v1Eq1, v1Inc);
        Operation o3 = new Operation("o3", new OR(v1Eq1, v1Eq2AndTrue), Arrays.asList(v1To0, new Next(v2, false)));
        Operation o4 = new Operation("o4", v1Eq2, v1Inc);
        Operation o5 = new Operation("o5", v1Eq2AndFalse, new Next(v2, true));

        Set<State> sourceStates = result.getSourceStates();
        Set<State> sinkstates = result.getSinkStates();
        State temp = (t.initialState.newState(v2, true).newState(v1, 2));
        Set<Transition> intrans = result.getIncomingTransitions(temp);
        Set<Transition> outtrans = result.getOutGoingTransitions(temp);
        int noOfStates = result.getStates().size();
        int noOfTrans = result.getTransitions().size();


        // comparators
        Set<State> cSourceN = new HashSet<State>();
        Set<State> cSinkN = new HashSet<State>();
        cSinkN.add((t.initialState.newState(v1, 3)));
        cSinkN.add((temp.newState(v1, 3)));
        Set<Transition> cInT = new HashSet<Transition>();
        cInT.add(new Transition("o5", (t.initialState.newState(v1, 2)), temp, 1));
        Set<Transition> cOutT = new HashSet<Transition>();
        cOutT.add(new Transition("o3", temp, result.getInitialState(), 1));
        cOutT.add(new Transition("o4", temp, (temp.newState(v1, 3)), 1));
        State someState = o2.execute(o1.execute(t.initialState));

        System.out.println(outtrans);
        System.out.println(cOutT);

        assertTrue(sourceStates.equals(cSourceN));
        assertTrue(sinkstates.equals(cSinkN));
        assertTrue(intrans.equals(cInT));
        assertTrue(outtrans.equals(cOutT));
        assertTrue(noOfStates == 6);
        assertTrue(noOfTrans == 7);

    }


    /**
     * The TestGraph is just a wrapper around a set of operations and an initial state, which you
     * can use in your tests
     */
    class TestGraph {
        public State initialState;
        public Set<Operation> operations;

        public TestGraph(State initialState, Set<Operation> operations) {
            this.initialState = initialState;
            this.operations = operations;
        }
    }

    /**
     * A simple test-model including two operations and a no loops
     * @return the operations and the initial state
     */
    public TestGraph testGraph1() {
        String v1 = "v1";
        HashSet<Operation> ops = new HashSet<>();
        ops.add(new Operation(
                "o1",
                new EQ(v1, 0),
                new NextInc(v1, 1)
        ));
        ops.add(new Operation(
                "o2",
                new EQ(v1, 1),
                new NextInc(v1, 1)
        ));

        HashMap<String, Object> state = new HashMap<>();
        state.put(v1, 0);
        State initial = new State(state);

        return new TestGraph(initial, ops);
    }

    /**
     * A simple test-model including two operations, but with a loop
     * @return the operations and the initial state
     */
    public TestGraph testGraph2() {
        String v1 = "v1";
        HashSet<Operation> ops = new HashSet<>();
        ops.add(new Operation(
                "o1",
                new EQ(v1, 0),
                new NextInc(v1, 1)
        ));
        ops.add(new Operation(
                "o2",
                new EQ(v1, 1),
                new Next(v1, 0)
        ));

        HashMap<String, Object> state = new HashMap<>();
        state.put(v1, 0);
        State initial = new State(state);

        return new TestGraph(initial, ops);
    }

    /**
     * Another example with more operations, that you can change and update for testing more types of
     * situations.
     *
     * @return the operations and the initial state
     */
    public TestGraph testGraph3() {

        String v1 = "v1";
        String v2 = "v2";
        String v3 = "v3";

        Predicate v1Eq0 = new EQ(v1, 0);
        Predicate v1Eq1 = new EQ(v1, 1);
        Predicate v1Eq2 = new EQ(v1, 2);
        Predicate v1Eq2AndFalse = new AND(new NOT(new EQ(v2, true)), v1Eq2);
        Predicate v1Eq2AndTrue = new AND(new EQ(v2, true), v1Eq2);

        Action v1Inc = new NextInc(v1, 1);
        Action v1Dec = new NextDec(v1, 1);
        Action v1To0 = new Next(v1, 0);

        Operation o1 = new Operation("o1", v1Eq0, v1Inc);
        Operation o2 = new Operation("o2", v1Eq1, v1Inc);
        Operation o3 = new Operation("o3", new OR(v1Eq1, v1Eq2AndTrue), Arrays.asList(v1To0, new Next(v2, false)));
        Operation o4 = new Operation("o4", v1Eq2, v1Inc);
        Operation o5 = new Operation("o5", v1Eq2AndFalse, new Next(v2, true));

        HashMap<String, Object> state = new HashMap<>();
        state.put(v1, 0);
        state.put(v2, false);
        state.put(v3, "Initial");
        State initial = new State(state);

        HashSet<Operation> ops = new HashSet<>();
        ops.add(o1);
        ops.add(o2);
        ops.add(o3);
        ops.add(o4);
        ops.add(o5);

        return new TestGraph(initial, ops);
    }

    public void test1getStates(){

        TestGraph t = testGraph2();
        Graph g = GraphFactory.makeMeAGraph(t.operations, t.initialState);
        Set<State> result = g.getStates();

        Set<State> compare = new HashSet<State>();
        compare.add(t.initialState);
        compare.add(t.initialState.newState("v1", 1));

        System.out.println(result);
        System.out.println(compare);

        assertTrue(result.equals(compare));

    }

    public void test2getTransition(){

        TestGraph t = testGraph2();
        Graph g = GraphFactory.makeMeAGraph(t.operations, t.initialState);
        HashSet<Transition> result = g.getTransitions();

        HashMap<String, Object> o1 = new HashMap<>();
        HashMap<String, Object> o2 = new HashMap<>();

        o1.put("v1", 0);
        o2.put("v1", 1);

        State s1 = new State(o1);
        State s2 = new State(o2);

        Transition T1 = new Transition("o1", s1, s2, 1);
        Transition T2 = new Transition("o2", s2, s1, 1);

        Set<Transition> compare = new HashSet<>();
        compare.add(T1);
        compare.add(T2);

        System.out.println(compare);
        System.out.println(result);

        assertTrue(result.equals(compare));

    }

    public void test3getOutgoingTransitions(){

        TestGraph t = testGraph2();
        Graph g = GraphFactory.makeMeAGraph(t.operations, t.initialState);

        HashMap<String, Object> o1 = new HashMap<>();
        HashMap<String, Object> o2 = new HashMap<>();

        o1.put("v1", 0);
        o2.put("v1", 1);

        State s1 = new State(o1);
        State s2 = new State(o2);

        Transition T1 = new Transition("o1", s1, s2, 1);
        Transition T2 = new Transition("o2", s2, s1, 1);

        HashSet<Transition> result1 = g.getOutGoingTransitions(s1);
        HashSet<Transition> result2 = g.getOutGoingTransitions(s2);

        Set<Transition> compareT1 = new HashSet<>();
        Set<Transition> compareT2 = new HashSet<>();

        compareT1.add(T1);
        compareT2.add(T2);

        System.out.println(result1 + " should be " + compareT1);
        System.out.println(result2 + " should be " + compareT2);

        assertTrue(result1.equals(compareT1));
        assertTrue(result2.equals(compareT2));
    }

    public void test4getIncomingTransitions(){

        TestGraph t = testGraph2();
        Graph g = GraphFactory.makeMeAGraph(t.operations, t.initialState);

        HashMap<String, Object> o1 = new HashMap<>();
        HashMap<String, Object> o2 = new HashMap<>();

        o1.put("v1", 0);
        o2.put("v1", 1);

        State s1 = new State(o1);
        State s2 = new State(o2);

        Transition T1 = new Transition("o1", s1, s2, 1);
        Transition T2 = new Transition("o2", s2, s1, 1);

        HashSet<Transition> result1 = g.getIncomingTransitions(s2);
        HashSet<Transition> result2 = g.getIncomingTransitions(s1);

        Set<Transition> compareT1 = new HashSet<>();
        Set<Transition> compareT2 = new HashSet<>();

        compareT1.add(T1);
        compareT2.add(T2);

        System.out.println(result1 + " should be " + compareT1);
        System.out.println(result2 + " should be " + compareT2);

        assertTrue(result1.equals(compareT1));
        assertTrue(result2.equals(compareT2));
    }

    public void test5getSourceStates(){

        TestGraph t = testGraph1();
        Graph g = GraphFactory.makeMeAGraph(t.operations, t.initialState);

        HashMap<String, Object> o1 = new HashMap<>();
        HashMap<String, Object> o2 = new HashMap<>();
        HashMap<String, Object> o3 = new HashMap<>();

        o1.put("v1", 0);
        o2.put("v1", 1);
        o3.put("v1", 2);

        State s1 = new State(o1);
        State s2 = new State(o2);
        State s3 = new State(o3);

        Transition T1 = new Transition("o1", s1, s2, 1);
        Transition T2 = new Transition("o2", s2, s3, 1);

        HashSet<State> result = g.getSourceStates();

        Set<State> compare1 = new HashSet<>();
        Set<State> compare2 = new HashSet<>();
        Set<State> compare3 = new HashSet<>();

        compare1.add(s1);
        compare2.add(s2);
        compare3.add(s3);

        System.out.println(result + " should be " + compare3);


        assertTrue(result.equals(compare1));
    }

    public void test6getSinkStates(){

        TestGraph t = testGraph1();
        Graph g = GraphFactory.makeMeAGraph(t.operations, t.initialState);

        HashMap<String, Object> o1 = new HashMap<>();
        HashMap<String, Object> o2 = new HashMap<>();
        HashMap<String, Object> o3 = new HashMap<>();

        o1.put("v1", 0);
        o2.put("v1", 1);
        o3.put("v1", 2);

        State s1 = new State(o1);
        State s2 = new State(o2);
        State s3 = new State(o3);

        Transition T1 = new Transition("o1", s1, s2, 1);
        Transition T2 = new Transition("o2", s2, s3, 1);

        HashSet<State> result = g.getSinkStates();

        Set<State> compare1 = new HashSet<>();
        Set<State> compare2 = new HashSet<>();
        Set<State> compare3 = new HashSet<>();

        compare1.add(s1);
        compare2.add(s2);
        compare3.add(s3);

        System.out.println(result + " should be " + compare3);


        assertTrue(result.equals(compare3));
    }

}
