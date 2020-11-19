package of.cgi.assignment.fan.ceilingfan;


import of.cgi.assignment.fan.ActionResult;
import of.cgi.assignment.fan.Fan;
import of.cgi.assignment.lib.statemachine.StateMachine;
import of.cgi.assignment.lib.statemachine.fsm.FiniteStateMachineBuilder;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import static of.cgi.assignment.fan.ActionResult.result;
import static of.cgi.assignment.fan.ceilingfan.Actions.CHANGE_DIRECTION;
import static of.cgi.assignment.fan.ceilingfan.Actions.INCREASE_SPEED;
import static of.cgi.assignment.fan.ceilingfan.FanSpeed.*;
import static of.cgi.assignment.fan.ceilingfan.Rotations.FORWARD;
import static of.cgi.assignment.fan.ceilingfan.Rotations.REVERSE;

public class CeilingFan implements Fan {

    Logger logger = LoggerFactory.getLogger(CeilingFan.class);

    private static final String NAME = "Ceiling Fan";
    final StateMachine<Actions, FanSpeed> stateMachine;
    final StateMachine<Actions, Rotations> rotations;

    /**
     * Default Constructor of {@link CeilingFan}
     * It initializes the the speed state machine
     */
    public CeilingFan(){
        stateMachine = new FiniteStateMachineBuilder<Actions, FanSpeed>()
                .initialState(OFF)
                .addTransitions(INCREASE_SPEED, Arrays.asList(
                        Pair.with(OFF, SPEED_1),
                        Pair.with(SPEED_1, SPEED_2),
                        Pair.with(SPEED_2, SPEED_3),
                        Pair.with(SPEED_3, OFF)
                ))
                .build();

        rotations = new FiniteStateMachineBuilder<Actions, Rotations>()
                .initialState(FORWARD)
                .addTransitions(CHANGE_DIRECTION, Arrays.asList(
                        Pair.with(FORWARD, REVERSE),
                        Pair.with(REVERSE, FORWARD)
                ))
                .build();
    }

    @Override
    public ActionResult turnOn() {
        String msg = String.format("%s is always turned on", NAME);
        logger.info(msg);
        return result(true, msg);
    }

    @Override
    public ActionResult turnOff() {
        String msg = String.format("Sorry %s can not be explicitly turned off. Set speed to \"off\" to save power.", NAME);
        logger.warn(msg);
        return result(false, msg);
    }

    @Override
    public ActionResult pullSpeedCord() {
        FanSpeed oldSpeed = stateMachine.getCurrentState();
        FanSpeed newSpeed = stateMachine.go(INCREASE_SPEED);
        String msg = String.format("Fan speed changed from %s to %s", oldSpeed, newSpeed);

        logger.info(msg);
        return result(true, msg);
    }

    @Override
    public String getSpeed() {
        return stateMachine.getCurrentState().name();
    }

    @Override
    public ActionResult pullDirectionCord() {
        Rotations oldDirection = rotations.getCurrentState();
        Rotations newDirection = rotations.go(CHANGE_DIRECTION);
        String msg = String.format("Rotate direction changed from %s to %s", oldDirection, newDirection);

        logger.info(msg);
        return result(true, msg);
    }

    @Override
    public String getDirection() {
        return rotations.getCurrentState().name();
    }

    @Override
    public ActionResult pullLightChord() {
        String msg = String.format("Sorry %s have no light", NAME);
        throw new UnsupportedOperationException(msg);
    }

    @Override
    public String getLightStatus() {
        return "-";
    }

    @Override
    public Collection<String> getStatus() {
        return Arrays.asList(
                "Name: " + NAME,
                "Speed: " + this.getSpeed(),
                "Direction: " + this.getDirection()

        );
    }
}
