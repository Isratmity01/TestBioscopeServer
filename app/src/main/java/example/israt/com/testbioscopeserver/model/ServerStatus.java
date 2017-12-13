package example.israt.com.testbioscopeserver.model;

/**
 * Created by HP on 12/5/2017.
 */

public class ServerStatus {
    private int Id;
    private String UpTime;
    private String DownTime;
    private String Date;
    private String ServerStatus;

    public int getId() {
        return Id;
    }

    public String getServerStatus() {
        return ServerStatus;
    }

    public void setServerStatus(String serverStatus) {
        ServerStatus = serverStatus;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUpTime() {
        return UpTime;
    }

    public void setUpTime(String upTime) {
        UpTime = upTime;
    }

    public String getDownTime() {
        return DownTime;
    }

    public void setDownTime(String downTime) {
        DownTime = downTime;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public ServerStatus(int id, String upTime, String downTime, String date) {
        Id = id;
        UpTime = upTime;
        DownTime = downTime;
        Date = date;
    }

    public ServerStatus() {
    }
}
