package ua.com.studyazimut.DAO.entity.log;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "logs", schema = "provider")
public class Log implements Serializable {

    private static final long serialversionUID = 261L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cid")
    private int id;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "action")
    private String action;

    @Column(name = "client_id")
    private int clientID;

    @Column(name = "action_info")
    private String actionInfo;

    @Column(name = "date")
    private String dateTime;

    @Column(name = "value")
    private float value;

    public Log() {
        // do nothing
    }

    public Log(String adminName, String action, int clientID, String actionInfo, String dateTime, float value) {
        this.adminName = adminName;
        this.action = action;
        this.clientID = clientID;
        this.actionInfo = actionInfo;
        this.dateTime = dateTime;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public String getActionInfo() {
        return actionInfo;
    }

    public void setActionInfo(String actionInfo) {
        this.actionInfo = actionInfo;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", adminName='" + adminName + '\'' +
                ", action='" + action + '\'' +
                ", clientID=" + clientID +
                ", actionInfo='" + actionInfo + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", value=" + value +
                '}';
    }
}
