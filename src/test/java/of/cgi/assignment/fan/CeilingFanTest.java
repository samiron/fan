package of.cgi.assignment.fan;

import of.cgi.assignment.fan.ceilingfan.CeilingFan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CeilingFanTest {

    CeilingFan ceilingFan;

    @BeforeEach
    public void initialize() {
        ceilingFan = new CeilingFan();
    }

    @Test
    void turnOn() {
        ActionResult result = ceilingFan.turnOn();
        assertThat(result.isSuccess(), is(true));
        assertThat(result.getMessage(), is(equalTo("Ceiling Fan is always turned on")));
    }

    @Test
    void turnOff() {
        ActionResult result = ceilingFan.turnOff();
        assertThat(result.isSuccess(), is(false));
        assertThat(result.getMessage(), is(equalTo("Sorry Ceiling Fan can not be explicitly turned off. Set speed to \"off\" to save power.")));
    }

    @Test
    void pullSpeedCord() {
        {
            ActionResult result = ceilingFan.pullSpeedCord();
            assertThat(result.isSuccess(), is(true));
            assertThat(result.getMessage(), is(equalTo("Fan speed changed from OFF to SPEED_1")));
        }

        {
            ActionResult result = ceilingFan.pullSpeedCord();
            assertThat(result.isSuccess(), is(true));
            assertThat(result.getMessage(), is(equalTo("Fan speed changed from SPEED_1 to SPEED_2")));
        }

        {
            ActionResult result = ceilingFan.pullSpeedCord();
            assertThat(result.isSuccess(), is(true));
            assertThat(result.getMessage(), is(equalTo("Fan speed changed from SPEED_2 to SPEED_3")));
        }

        {
            ActionResult result = ceilingFan.pullSpeedCord();
            assertThat(result.isSuccess(), is(true));
            assertThat(result.getMessage(), is(equalTo("Fan speed changed from SPEED_3 to OFF")));
        }
    }

    @Test
    void pullDirectionCord() {
        {
            ActionResult result = ceilingFan.pullDirectionCord();
            assertThat(result.isSuccess(), is(true));
            assertThat(result.getMessage(), is(equalTo("Rotate direction changed from FORWARD to REVERSE")));
        }
        {
            ActionResult result = ceilingFan.pullDirectionCord();
            assertThat(result.isSuccess(), is(true));
            assertThat(result.getMessage(), is(equalTo("Rotate direction changed from REVERSE to FORWARD")));
        }
        {
            ActionResult result = ceilingFan.pullDirectionCord();
            assertThat(result.isSuccess(), is(true));
            assertThat(result.getMessage(), is(equalTo("Rotate direction changed from FORWARD to REVERSE")));
        }

    }

    @Test
    void pullLightChord() {
        UnsupportedOperationException exc = assertThrows(UnsupportedOperationException.class,
                () -> ceilingFan.pullLightChord()
        );
        assertThat(exc.getMessage(), is(equalTo("Sorry Ceiling Fan have no light")));
    }
}