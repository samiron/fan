package of.cgi.assignment.lib.statemachine;

import of.cgi.assignment.lib.statemachine.fsm.FiniteStateMachineBuilder;
import org.javatuples.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static of.cgi.assignment.lib.statemachine.FiniteStateMachineTest.ACTION.BACKWARD;
import static of.cgi.assignment.lib.statemachine.FiniteStateMachineTest.ACTION.FORWARD;
import static of.cgi.assignment.lib.statemachine.FiniteStateMachineTest.STATE.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

class FiniteStateMachineTest {

    public enum STATE {
        STATE_0,
        STATE_1,
        STATE_2
    }

    public enum ACTION {
        FORWARD,
        BACKWARD
    }

    private StateMachine<ACTION, STATE> stateMachine;

    @BeforeEach
    public void initStateMachine() {

        this.stateMachine = new FiniteStateMachineBuilder<ACTION, STATE>()
                .initialState(STATE_0)
                .addTransitions(FORWARD, Arrays.asList(
                        Pair.with(STATE_0, STATE_1),
                        Pair.with(STATE_1, STATE_2),
                        Pair.with(STATE_2, STATE_0)
                ))
                .addTransitions(ACTION.BACKWARD, Collections.singletonList(
                        Pair.with(STATE_0, STATE_2)
                ))
                .build();
    }

    @Test
    void goWithValidActionShouldTransitionSuccessfully() {
        STATE nextState = stateMachine.go(FORWARD);
        assertThat(nextState, is(equalTo(STATE_1)));
    }

    @Test
    void getCurrentState() {
        assertThat(stateMachine.getCurrentState(), is(equalTo(STATE_0)));
        assertThat(stateMachine.go(BACKWARD), is(equalTo(STATE_2)));
    }

    @Test
    void getAllStates() {
        assertThat(stateMachine.getAllStates(), containsInAnyOrder(STATE_0, STATE_1, STATE_2));
    }
}