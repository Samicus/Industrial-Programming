package ssy066.part1;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * The State represent a map between the variables and their current value.
 * Variables are represented as Strings and the values can be of any type. Usually
 * the values is of type String, Integer or Boolean.
 *
 * The variables are simplified since they do not have any
 * domain, which mean that they can be updated to anything.
 *
 * A State object should be immutable, i.e. when created it should never change.
 *
 */
public class State {

	private final Map<String, Object> state = new HashMap<String, Object>();

    /**
     * The constructor takes a map defining the values of the variables. The
     * variables are defined as a String and the values can be of any type (mostly
     * String, Integer or Boolean).
     *
     * @param init Each variable
     */
	public State(Map<String, Object> init) {
        // You have to remove this throw when you see it and write code that is doing the correct things
        // In this case, you have to complete the constructor. Try to just delete the line below and
        // see what errors you get. This is a hint of what you need to do.
        //throw new NotImplementedException();

        this.state.putAll(init);
	}

    /**
     * If the state include the variable var, the method should return its value.
     * If the variable is not part of the state, a java.util.NoSuchElementException
     * should be fired (throw new java.util.NoSuchElementException("The variable "+var+" is not part of the state");)
     *
     * @param var The variable name
     * @return the value of the variable named var
     */
	public Object get(String var) {
	    Map<String, Object> copy = new HashMap<String, Object>();;
	    copy.putAll(state);
	    if(copy.containsKey(var)){
	        return copy.get(var);
        }
	    else {
            throw new java.util.NoSuchElementException("The variable "+var+" is not part of the state");
        }


	}

    /**
     * Returns a new State where value of variable var is updated to value. If var is
     * not part of the state, a NoSuchElementException should be fired.
     * (throw new java.util.NoSuchElementException("The variable "+var+" is not part of the state");)
     *
     * @param var the name of the variable
     * @param value the newState value
     * @return a new State
     */
	public State newState(String var, Object value) {
	    Map<String, Object> copy = new HashMap<String, Object>();;
	    copy.putAll(state);
	    if(!copy.containsKey(var)){
            throw new java.util.NoSuchElementException("The variable "+var+" is not part of the state");
        }
	    copy.put(var, value);
	    State newState = new State(copy);
	    return newState;
    }

    /**
     * Returns a new State where values of variables in updVars is updated. If any of the
     * variables is not part of the state, a NoSuchElementException should be fired.
     * (throw new java.util.NoSuchElementException("The variable "+var+" is not part of the state");)
     *
     * @param updVars The map including only the variable and values to be updated
     * @return a new State
     */
	public State newState(Map<String, Object> updVars) {
	    Map<String, Object> copy = new HashMap<String, Object>();;
	    copy.putAll(state);
	    for(Map.Entry<String, Object> entry: updVars.entrySet()){
	        String key = entry.getKey();
	        Object value = entry.getValue();
	        if(!copy.containsKey(key)){
                throw new java.util.NoSuchElementException("The variable "+key+" is not part of the state");
            }
	        copy.put(key, value);
        }
        State newState = new State(copy);
	    return newState;
	}
	// Do not change below

    @Override
    public String toString() {
        StringBuffer name = new StringBuffer();
        for (Entry<String, Object> kv : state.entrySet()){
            String line = kv.getKey() +":"+ kv.getValue().toString();
            name.append(line + ", ");
        }
        return name.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof State))
            return false;
        State other = (State) obj;
        if (state == null) {
          return false;
        } else if (!state.equals(other.state))
            return false;
        return true;
    }

}
