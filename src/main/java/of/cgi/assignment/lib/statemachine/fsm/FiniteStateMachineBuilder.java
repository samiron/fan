package of.cgi.assignment.lib.statemachine.fsm;

import of.cgi.assignment.lib.statemachine.StateMachineBuilder;
import of.cgi.assignment.lib.statemachine.exception.InvalidStateException;
import org.javatuples.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * A {@link FiniteStateMachine} builder.
 * @param <ACTION>
 * @param <STATE>
 */
public class FiniteStateMachineBuilder<ACTION, STATE> implements StateMachineBuilder<ACTION, STATE> {
    private final HashSet<STATE> allStates = new HashSet<>();
    private final Map<ACTION, Map<STATE, STATE>> allTransitions = new HashMap<>();
    private STATE initialState;

    @Override
    public FiniteStateMachineBuilder<ACTION, STATE> initialState(STATE initialState) {
        this.initialState = initialState;
        registerState(initialState);
        return this;
    }

    @Override
    public FiniteStateMachineBuilder<ACTION, STATE> addTransition(ACTION action, Pair<STATE, STATE> route) {
        STATE from = route.getValue0();
        STATE to = route.getValue1();
        if (from == null || to == null) {
            throw new InvalidStateException("State can not be null");
        }

        if (!allTransitions.containsKey(action)) {
            allTransitions.put(action, new HashMap<>());
        }
        allTransitions.get(action).put(from, to);
        registerState(from);
        registerState(to);

        return this;
    }

    @Override
    public FiniteStateMachineBuilder<ACTION, STATE> addTransitions(final ACTION action, final Collection<Pair<STATE, STATE>> routes) {
        routes.forEach(aRoute -> addTransition(action, aRoute));

        return this;
    }

    @Override
    public FiniteStateMachine<ACTION, STATE> build() {
        validate();
        return new FiniteStateMachine<>(this.initialState, this.allTransitions, this.allStates);
    }

    protected void validate() {
        if (this.initialState == null) {
            throw new InvalidStateException("State can not be null");
        }
    }

    protected void registerState(STATE initialState) {
        this.allStates.add(initialState);
    }
}
