package ua.com.studyazimut.DAO.entity.client;


import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import ua.com.studyazimut.DAO.entity.client.clientUtils.PgInet;
import ua.com.studyazimut.DAO.entity.client.clientUtils.PgInetType;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "clients", schema = "provider")
@TypeDef(name = "convertInet", typeClass = PgInetType.class)
public class Client implements Serializable {

    private static final long serialversionUID = 270L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cid")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "patronymic_name")
    private String patronymicName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "apartment_number")
    private int apartmentNumber;

    @Column(name = "agreement_number")
    private int agreementNumber;

    @Column(name = "agreement_sign_date")
    private String agreementSignDate;

    @Column(name = "comment")
    private String comment;

    @Column(name = "ip", nullable = false)
    @Type(type = "convertInet")
    private PgInet ip;

    @Column(name = "vid")
    private int vid;

    @Column(name = "service_status")
    private String serviceStatus;

    @Column(name = "balance")
    private float balance;

    @Column(name = "warning_limit")
    private int warningLimit;

    @Column(name = "shut_down_limit")
    private int shutDownLimit;

    @Column(name = "rate")
    private String rate;

    @Column(name = "bras_name")
    private String brasName;

    public Client() {
        //do nothing
    }

    public Client(String login, String lastName, String firstName, String patronymicName, String phoneNumber, String city,
                  String street, String houseNumber, int apartmentNumber, int agreementNumber, String agreementSignDate,
                  String comment, PgInet ip, int vid, String serviceStatus, float balance, int warningLimit,
                  int shutDownLimit, String rate, String brasName) {
        this.login = login;
        this.lastName = lastName;
        this.firstName = firstName;
        this.patronymicName = patronymicName;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
        this.agreementNumber = agreementNumber;
        this.agreementSignDate = agreementSignDate;
        this.comment = comment;
        this.ip = ip;
        this.vid = vid;
        this.serviceStatus = serviceStatus;
        this.balance = balance;
        this.warningLimit = warningLimit;
        this.shutDownLimit = shutDownLimit;
        this.rate = rate;
        this.brasName = brasName;
    }

    public int getShutDownLimit() {
        return shutDownLimit / 100;
    }

    public void setShutDownLimit(int shutDownLimit) {
        this.shutDownLimit = shutDownLimit * 100;
    }

    public int getWarningLimit() {
        return warningLimit / 100;
    }

    public void setWarningLimit(int warningLimit) {
        this.warningLimit = warningLimit * 100;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBrasName() {
        return brasName;
    }

    public void setBrasName(String brasName) {
        this.brasName = brasName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymicName() {
        return patronymicName;
    }

    public void setPatronymicName(String patronymicName) {
        this.patronymicName = patronymicName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public int getAgreementNumber() {
        return agreementNumber;
    }

    public void setAgreementNumber(int agreementNumber) {
        this.agreementNumber = agreementNumber;
    }

    public String getAgreementSignDate() {
        return agreementSignDate;
    }

    public void setAgreementSignDate(String agreementSignDate) {
        this.agreementSignDate = agreementSignDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public PgInet getIp() {
        return ip;
    }

    public void setIp(PgInet ip) {
        this.ip = ip;
    }

    public int getVid() {
        return vid;
    }

    public void setVid(int vid) {
        this.vid = vid;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public float getBalance() {
        return balance / 100;
    }

    public void setBalance(float balance) {
        this.balance = balance * 100;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patronymicName='" + patronymicName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                ", agreementNumber=" + agreementNumber +
                ", agreementSignDate='" + agreementSignDate + '\'' +
                ", comment='" + comment + '\'' +
                ", ip=" + ip +
                ", vid=" + vid +
                ", serviceStatus='" + serviceStatus + '\'' +
                ", balance=" + balance +
                ", warningLimit=" + warningLimit +
                ", shutDownLimit=" + shutDownLimit +
                ", rate='" + rate + '\'' +
                ", brasName='" + brasName + '\'' +
                '}';
    }
}
