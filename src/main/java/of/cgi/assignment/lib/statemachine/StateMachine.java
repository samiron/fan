package of.cgi.assignment.lib.statemachine;

import of.cgi.assignment.lib.statemachine.exception.InvalidTransitionException;

import java.util.Collection;

/**
 * Represents a state machine that can consists of several states
 * and one or more actions. Based on the actions and provided transition map
 * it can move from one state to another.
 *
 * @param <ACTION> Type of Action
 * @param <STATE> Type of State
 */
public interface StateMachine<ACTION, STATE> {

    /**
     * Make the transition from current state to the next state
     * as defined in the transition map.
     * @param action Taken action
     * @return Next state
     * @throws InvalidTransitionException Throws when a transition is not found for a given action
     */
    STATE go(ACTION action) throws InvalidTransitionException;

    /**
     * Returns the current state.
     * @return Current state
     */
    STATE getCurrentState();

    /**
     * Returns the initial state.
     * This should always return the same value.
     * @return Initial state.
     */
    STATE getInitialState();

    /**
     * It returns all states this state machine has.
     * @return List of states
     */
    Collection<STATE> getAllStates();

    /**
     * Returns all possible actions from the current state
     * @return List of actions.
     */
    Collection<ACTION> getAvailableActions();

}
