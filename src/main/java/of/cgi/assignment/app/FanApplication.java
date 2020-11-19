package of.cgi.assignment.app;

import of.cgi.assignment.fan.ActionResult;
import of.cgi.assignment.fan.Fan;
import of.cgi.assignment.fan.ceilingfan.CeilingFan;

import java.util.Collection;
import java.util.Scanner;

import static of.cgi.assignment.fan.ActionResult.result;

public class FanApplication {

    public static void main(String[] args) {
        new FanApplication().run();
    }

    void run() {
        System.out.println("Welcome to Fun with Fan");

        Fan fan = new CeilingFan();

        while (true) {

            String s = takeInput();
            if (s.equals("e")) {
                break;
            }
            ActionResult result;
            try {

                switch (s) {
                    case "i":
                        result = fan.pullSpeedCord();
                        break;
                    case "d":
                        result = fan.pullDirectionCord();
                        break;
                    case "p":
                        Collection<String> statusLines = fan.getStatus();
                        result = result(true, String.join(", ", statusLines));
                        break;
                    case "l":
                        result = fan.pullLightChord();
                        break;
                    default:
                        result = result(false, "Invalid choice, please try again");
                }
            } catch (RuntimeException exc) {
                result = result(false, exc.getMessage());
            }
            System.out.println(result.getMessage());
        }
    }


    private String takeInput() {
        System.out.println("\n------------------------------------------------------------------");
        System.out.print("i(speed cord), d(direction cord), p(print), e(exit). Your choice: ");
        Scanner input = new Scanner(System.in);
        return input.nextLine().toLowerCase();
    }
}
