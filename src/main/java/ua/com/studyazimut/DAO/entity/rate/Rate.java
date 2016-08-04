package ua.com.studyazimut.DAO.entity.rate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rates", schema = "provider")
public class Rate implements Serializable {

    private static final long serialversionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rate_cid")
    private int id;

    @Column(name = "short_name")
    private String shortName;

    @Column(name = "long_name")
    private String longName;

    @Column(name = "daily_cost")
    private int dailyCost;

    @Column(name = "old_rate")
    private boolean oldRate;

    @Column(name = "archive")
    private boolean archive;

    public Rate() {
        //do nothing
    }

    public Rate(String shortName, String longName, int dailyCost, boolean oldRate, boolean archive) {
        this.shortName = shortName;
        this.longName = longName;
        this.dailyCost = dailyCost;
        this.oldRate = oldRate;
        this.archive = archive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public int getDailyCost() {
        return dailyCost / 100;
    }

    public void setDailyCost(int dailyCost) {
        this.dailyCost = dailyCost;
    }

    public boolean isOldRate() {
        return oldRate;
    }

    public void setOldRate(boolean oldRate) {
        this.oldRate = oldRate;
    }

    public boolean isArchive() {
        return archive;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }

    @Override
    public String toString() {
        return "Rate{" +
                "cid=" + id +
                ", shortName='" + shortName + '\'' +
                ", longName='" + longName + '\'' +
                ", dailyCost=" + dailyCost +
                ", oldRate=" + oldRate +
                ", archive=" + archive +
                '}';
    }
}
