package example.israt.com.testbioscopeserver.model;

/**
 * Created by HP on 11/26/2017.
 */

public class EventReceived {
    private Boolean yes;
    private String time;
    private String state;

    public EventReceived(Boolean yes,String mtime,String up) {
        this.yes = yes;
        this.time=mtime;
        this.state=up;
    }

    public Boolean getYes() {
        return yes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setYes(Boolean yes) {
        this.yes = yes;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
