package of.cgi.assignment.lib.statemachine;


import of.cgi.assignment.lib.statemachine.exception.InvalidStateException;
import of.cgi.assignment.lib.statemachine.exception.InvalidTransitionException;
import of.cgi.assignment.lib.statemachine.fsm.FiniteStateMachineBuilder;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FiniteStateMachineBuilderTest {

    @Test
    void canBeInitiatedWithInitialState() {
        final StateMachine<String, String> stateMachine = new FiniteStateMachineBuilder<String, String>()
                .initialState("State 0")
                .build();

        assertThat(stateMachine.getCurrentState(), is(equalTo("State 0")));
        assertThat(stateMachine.getAllStates(), contains("State 0"));
    }

    @Test
    void singleStateMachineFailsOnTransition() {
        final StateMachine<String, String> stateMachine = new FiniteStateMachineBuilder<String, String>()
                .initialState("State 0")
                .build();

        InvalidTransitionException exc = assertThrows(
                InvalidTransitionException.class,
                () -> stateMachine.go("an_action"));
        assertThat(exc.getMessage(), is(equalTo("Invalid transition action")));
    }

    @Test
    void canNotBeInitializedWithNullState() {
        StateMachineBuilder<String, String> FiniteStateMachineBuilder = new FiniteStateMachineBuilder<String, String>()
                .initialState(null);

        InvalidStateException exc = assertThrows(
                InvalidStateException.class,
                FiniteStateMachineBuilder::build
        );

        assertThat(exc.getMessage(), is(equalTo("State can not be null")));
    }

    @Test
    void stateMachineWithOneActionMap_shouldTransitionSuccessfully() {
        StateMachine<String, String> stateMachine = new FiniteStateMachineBuilder<String, String>()
                .initialState("State 0")
                .addTransition("action", Pair.with("State 0", "State 1"))
                .addTransition("action", Pair.with("State 1", "State 0"))
                .build();

        assertThat(stateMachine.getAllStates(), contains("State 0", "State 1"));

        {
            String nextState = stateMachine.go("action");
            assertThat(nextState, is(equalTo("State 1")));
        }

        {
            String nextState = stateMachine.go("action");
            assertThat(nextState, is(equalTo("State 0")));
        }

        {
            String nextState = stateMachine.go("action");
            assertThat(nextState, is(equalTo("State 1")));
        }
    }

    @Test
    void stateMachineWithOneActionMap_invalidTransitionShouldThrowException() {
        StateMachine<String, String> stateMachine = new FiniteStateMachineBuilder<String, String>()
                .initialState("State 0")
                .addTransition("action", Pair.with("State 1", "State 0"))
                .build();

        InvalidTransitionException exc = assertThrows(
                InvalidTransitionException.class,
                () -> stateMachine.go("action"));

        assertThat(exc.getMessage(), is(equalTo("No transition defined for this action")));
    }

    @Test
    void stateMachineWithMultipleActions_shouldTransitionSuccessfully() {
        StateMachine<String, String> stateMachine = new FiniteStateMachineBuilder<String, String>()
                .initialState("State 2")
                .addTransitions("forward", Arrays.asList(
                        Pair.with("State 2", "State 0"),
                        Pair.with("State 1", "State 2"),
                        Pair.with("State 0", "State 1")
                ))
                .addTransitions("backward", Arrays.asList(
                        Pair.with("State 2", "State 0"),
                        Pair.with("State 1", "State 2"),
                        Pair.with("State 0", "State 1")
                ))
                .build();

        assertThat(stateMachine.getAllStates(), containsInAnyOrder("State 2", "State 1", "State 0"));
        List<Pair<String, String>> testDrive = Arrays.asList(
                Pair.with("forward", "State 0"),
                Pair.with("backward", "State 1"),
                Pair.with("backward", "State 2"),
                Pair.with("backward", "State 0"),
                Pair.with("backward", "State 1"),
                Pair.with("forward", "State 2"),
                Pair.with("forward", "State 0")
        );
        for (Pair<String, String> testTransition : testDrive) {
            String nextState = stateMachine.go(testTransition.getValue0());
            assertThat(nextState, is(equalTo(testTransition.getValue1())));
        }
        assertThat(stateMachine.getCurrentState(), is(equalTo("State 0")));
    }

    @Test
    void transitionEndpointsCanNotBeNull() {
        InvalidStateException exc = assertThrows(InvalidStateException.class,
                () -> new FiniteStateMachineBuilder<String, String>()
                        .addTransition("action", Pair.with("State 0", null)));

        assertThat(exc.getMessage(), is(equalTo("State can not be null")));
    }
}
