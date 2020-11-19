package of.cgi.assignment.fan;

import java.util.Collection;

/**
 * A generic Fan interface.
 */
public interface Fan {

    /**
     * Turns on the fan
     * @return result represents if the operation was performed successfully
     */
    ActionResult turnOn();

    /**
     * Turns off the fan
     * @return result represents if the operation was performed successfully
     */
    ActionResult turnOff();

    /**
     * Pull the fan speed cord.
     * This should only affect the fan speed.
     * @return result represents if the operation was performed successfully
     * @throws UnsupportedOperationException When the operation is not supported
     */
    ActionResult pullSpeedCord() throws UnsupportedOperationException;

    /**
     * Returns the current speed.
     * @return String, the current speed.
     */
    String getSpeed();

    /**
     * Pull the direction cord. This should only alter the fan rotation direction.
     * @return result represents if the operation was performed successfully
     * @throws UnsupportedOperationException When the operation is not supported
     */
    ActionResult pullDirectionCord() throws UnsupportedOperationException;

    /**
     * Returns the current direction
     * @return String, the current direction.
     */
    String getDirection();

    /**
     * Pull the light cord. This should only affect the state of the light.
     * @return result represents if the operation was performed successfully
     * @throws UnsupportedOperationException When the operation is not supported
     */
    ActionResult pullLightChord() throws UnsupportedOperationException;

    /**
     * Get the current light status
     * @return String, light status
     */
    String getLightStatus();

    /**
     * Return description of all fan statuses
     * @return List of strings represents multiple information about fan.
     */
    Collection<String> getStatus();

}
