package of.cgi.assignment.fan;

public class ActionResult {
    private final boolean success;
    private final String message;

    private ActionResult(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public static ActionResult result(boolean success, String message){
        return new ActionResult(success, message);
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
