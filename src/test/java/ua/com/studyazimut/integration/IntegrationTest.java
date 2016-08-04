package ua.com.studyazimut.integration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.client.Client;
import ua.com.studyazimut.DAO.entity.client.clientUtils.PgInet;
import ua.com.studyazimut.DAO.entity.log.Log;
import ua.com.studyazimut.DAO.entity.rate.Rate;
import ua.com.studyazimut.DAO.repository.client.ClientDAO;
import ua.com.studyazimut.DAO.repository.client.ClientDAOImpl;
import ua.com.studyazimut.DAO.repository.log.LoggingDAO;
import ua.com.studyazimut.DAO.repository.log.LoggingDAOImpl;
import ua.com.studyazimut.DAO.repository.rate.RateDAO;
import ua.com.studyazimut.DAO.repository.rate.RateDAOImpl;
import ua.com.studyazimut.Main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private ClientDAO clientDAO = new ClientDAOImpl();

    private RateDAO rateDAO = new RateDAOImpl();

    private LoggingDAO loggingDAO = new LoggingDAOImpl();

    private int id1;
    private int id2;
    private int id3;

    @Before
    public void setup() throws DAOException {

        clientDAO.cleanTable();
        rateDAO.cleanTable();
        loggingDAO.cleanTable();
        Client notEnoughMoneyClient = new Client(
                "testLogin1", "testLastName1", "testFirstName1", "testPatronymicName1",
                "testPhoneNumber1", "testCity1", "testStreet1", "testHouseNumber1", 0, 1,
                "testAgreementSignDate1", "testComment1", new PgInet("127.0.0.1"), 2,
                "testServiceStatus1", 300, 4000, 5, "m3", "testBrasName1"
        );
        clientDAO.saveClient(notEnoughMoneyClient);
        id1 = notEnoughMoneyClient.getId();

        Client warningLimitClient = new Client(
                "testLogin2", "testLastName2", "testFirstName2", "testPatronymicName2",
                "testPhoneNumber2", "testCity2", "testStreet2", "testHouseNumber2", 0, 11,
                "testAgreementSignDate2", "testComment2", new PgInet("127.0.0.2"), 22,
                "testServiceStatus2", 500, 600, 55, "m3", "testBrasName2"
        );
        clientDAO.saveClient(warningLimitClient);
        id2 = warningLimitClient.getId();

        Client enoughMoneyClient = new Client(
                "testLogin3", "testLastName3", "testFirstName3", "testPatronymicName3",
                "testPhoneNumber3", "testCity3", "testStreet3", "testHouseNumber3", 0, 111,
                "testAgreementSignDate3", "testComment3", new PgInet("127.0.0.3"), 222,
                "testServiceStatus3", 3000, 333, 555, "m3", "testBrasName3"
        );
        clientDAO.saveClient(enoughMoneyClient);
        id3 = enoughMoneyClient.getId();

        rateDAO.saveRate(new Rate("m3", "month 3", 400, true, true));
    }

    @Test
    public void integrationTest() throws DAOException {
        Main.main(new String[0]);

        List<Client> clients = clientDAO.findAllClients();
        List<Log> logs = loggingDAO.findAllLogs();
        int logId1 = logs.get(0).getId();
        int logId2 = logs.get(1).getId();
        int logId3 = logs.get(2).getId();
        assertEquals(
                "[Client{id=" + id1 + ", login='testLogin1', lastName='testLastName1', firstName='testFirstName1', " +
                        "patronymicName='testPatronymicName1', phoneNumber='testPhoneNumber1', city='testCity1', " +
                        "street='testStreet1', houseNumber='testHouseNumber1', apartmentNumber=0, agreementNumber=1, " +
                        "agreementSignDate='testAgreementSignDate1', comment='testComment1', ip=127.0.0.1, vid=2, " +
                        "serviceStatus='NOT ACTIVE', balance=300.0, warningLimit=4000, shutDownLimit=5, " +
                        "rate='m3', brasName='testBrasName1'}, " +
                        "Client{id=" + id2 + ", login='testLogin2', lastName='testLastName2', " +
                        "firstName='testFirstName2', patronymicName='testPatronymicName2', " +
                        "phoneNumber='testPhoneNumber2', city='testCity2', " +
                        "street='testStreet2', houseNumber='testHouseNumber2', apartmentNumber=0, " +
                        "agreementNumber=11, agreementSignDate='testAgreementSignDate2', comment='testComment2', " +
                        "ip=127.0.0.2, vid=22, serviceStatus='WARNING', balance=100.0, warningLimit=600, " +
                        "shutDownLimit=55, rate='m3', brasName='testBrasName2'}, " +
                        "Client{id=" + id3 + ", login='testLogin3', lastName='testLastName3', " +
                        "firstName='testFirstName3', patronymicName='testPatronymicName3', " +
                        "phoneNumber='testPhoneNumber3', city='testCity3', " +
                        "street='testStreet3', houseNumber='testHouseNumber3', apartmentNumber=0, " +
                        "agreementNumber=111, agreementSignDate='testAgreementSignDate3', comment='testComment3', " +
                        "ip=127.0.0.3, vid=222, serviceStatus='testServiceStatus3', balance=2600.0, " +
                        "warningLimit=333, shutDownLimit=555, rate='m3', brasName='testBrasName3'}]", clients.toString()
        );
        assertEquals(
                "[Log{id=" + logId1 + ", adminName='Billing Scheduler', action='updated client', " +
                        "clientID=" + id1 + ", actionInfo='Not enough money. Transfer to NOT ACTIVE', " +
                        "dateTime='" + getCurrentTime() + "', value=3.0}, " +
                        "Log{id=" +logId2 + ", adminName='Billing Scheduler', action='updated client', " +
                        "clientID=" + id2 + ", actionInfo='Little money. Transfer to WARNING', " +
                        "dateTime='" + getCurrentTime() + "', value=4.0}, " +
                        "Log{id=" + logId3 + ", adminName='Billing Scheduler', action='updated client', " +
                        "clientID=" + id3 + ", actionInfo='Withdraw money', " +
                        "dateTime='" + getCurrentTime() + "', value=4.0}]", logs.toString()
        );
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
