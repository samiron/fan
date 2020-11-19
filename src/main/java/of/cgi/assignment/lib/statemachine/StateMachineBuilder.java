package of.cgi.assignment.lib.statemachine;

import org.javatuples.Pair;

import java.util.Collection;

/**
 * Represents a {@link StateMachine} builder
 * @param <ACTION> Action type
 * @param <STATE> State type
 */
public interface StateMachineBuilder<ACTION, STATE> {

    /**
     * Sets the initial state.
     * This is the current state before taking any action.
     * @param state Desired initial state
     * @return The builder itself
     */
    StateMachineBuilder<ACTION, STATE> initialState(STATE state);

    /**
     * Add one transition. A transition is caused by action and results in the next state.
     * @param action Desired action.
     * @param route Desired next state
     * @return The builder itself
     */
    StateMachineBuilder<ACTION, STATE> addTransition(ACTION action, Pair<STATE, STATE> route);

    /**
     * Add multiple transitions for same action
     * @param action Desired action.
     * @param routes Multiple transition map for the action
     * @return The builder itself
     */
    StateMachineBuilder<ACTION, STATE> addTransitions(ACTION action, Collection<Pair<STATE, STATE>> routes);

    /**
     * Build the {@link StateMachine} object.
     * @return The state machine
     */
    StateMachine<ACTION, STATE> build();

}
