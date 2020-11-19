package of.cgi.assignment.lib.statemachine.fsm;

import of.cgi.assignment.lib.statemachine.StateMachine;
import of.cgi.assignment.lib.statemachine.exception.InvalidTransitionException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * An Immutable State Machine. The state machine can only be created using {@link FiniteStateMachineBuilder}
 * Once created, the states, actions can not be mutated. The only action can be done is to transition from
 * one state to another.
 */
public class FiniteStateMachine<ACTION, STATE> implements StateMachine<ACTION, STATE> {
    private STATE currentState;
    private final STATE initialState;
    private final HashSet<STATE> allStates;
    private final Map<ACTION, Map<STATE, STATE>> transitionMap;

    /**
     * Constructor
     * @param initialState Desired initial state
     * @param transitionMap Map containing all transitions for all actions.
     * @param allStates Collection of all states
     */
    FiniteStateMachine(STATE initialState, Map<ACTION, Map<STATE, STATE>> transitionMap, HashSet<STATE> allStates) {
        this.initialState = this.currentState = initialState;
        this.transitionMap = transitionMap;
        this.allStates = allStates;
    }

    @Override
    public STATE go(ACTION action) {
        Map<STATE, STATE> actionMap = getActionMap(action);
        this.currentState = getNextState(actionMap);
        return currentState;
    }

    @Override
    public STATE getCurrentState() {
        return currentState;
    }

    @Override
    public STATE getInitialState() {
        return this.initialState;
    }

    @Override
    public HashSet<STATE> getAllStates() {
        return allStates;
    }

    @Override
    public Collection<ACTION> getAvailableActions() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private STATE getNextState(Map<STATE, STATE> actionMap) {
        if (!actionMap.containsKey(currentState)) {
            throw new InvalidTransitionException("No transition defined for this action");
        }
        return actionMap.get(currentState);
    }

    private Map<STATE, STATE> getActionMap(ACTION action) throws InvalidTransitionException {
        if (!transitionMap.containsKey(action)) {
            throw new InvalidTransitionException("Invalid transition action");
        }
        return transitionMap.get(action);
    }
}
