package ua.com.studyazimut.service;

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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ServiceTest {

    private LoggingDAO loggingDAO = new LoggingDAOImpl();

    private ClientDAO clientDAO = new ClientDAOImpl();

    private RateDAO rateDAO = new RateDAOImpl();

    private Service service = new ServiceImpl(rateDAO, clientDAO, loggingDAO);

    private int clientId;

    private float clientOldBalance;


    @Before
    public void setup() throws DAOException {
        loggingDAO.cleanTable();
        clientDAO.cleanTable();
        rateDAO.cleanTable();
        Rate rate = new Rate("m3", "month 3", 400, true, true);
        rateDAO.saveRate(rate);
    }


    @Test
    public void updateClientServiceEnoughMoneyTest() throws DAOException, ServiceException {
        Client client = new Client(
                "testLogin", "testLastName", "testFirstName", "testPatronymicName",
                "testPhoneNumber", "testCity", "testStreet", "testHouseNumber", 0, 1, "testAgreementSignDate",
                "testComment", new PgInet("127.0.0.1"), 2, "testServiceStatus", 1000, 4, 5, "m3", "testBrasName"
        );
        clientDAO.saveClient(client);
        clientId = client.getId();
        clientOldBalance = client.getBalance();

        service.updateClientEnoughMoney(client);

        List<Log> logs = loggingDAO.findAllLogs();
        int logId = logs.get(0).getId();

        Client actual = clientDAO.findClientById(clientId);

        float newBalance = actual.getBalance();

        assertEquals(
                "[Log{id=" + logId + ", adminName='Billing Scheduler', action='Баланс клиента обновлен." +
                        " Денег достаточно', " +
                "clientID=" + clientId + ", actionInfo='Снятие денег. Старый баланс: " + clientOldBalance +
                        "грн. Актуальный баланс: "+ newBalance + "грн.', dateTime='" + getCurrentTime() + "', " +
                "value=4.0}]", logs.toString()
        );

        assertEquals(
                "Client{id=" + clientId + ", login='testLogin', lastName='testLastName', firstName='testFirstName', " +
                "patronymicName='testPatronymicName', phoneNumber='testPhoneNumber', city='testCity', " +
                        "street='testStreet', houseNumber='testHouseNumber', apartmentNumber=0, " +
                        "agreementNumber=1, agreementSignDate='testAgreementSignDate', comment='testComment', " +
                        "ip=127.0.0.1, vid=2, serviceStatus='testServiceStatus', balance=600.0, warningLimit=4, " +
                        "shutDownLimit=5, rate='m3', brasName='testBrasName'}", actual.toString()
        );
    }


    @Test
    public void updateClientWarningTest() throws DAOException, ServiceException {
        Client client = new Client(
                "testLogin", "testLastName", "testFirstName", "testPatronymicName",
                "testPhoneNumber", "testCity", "testStreet", "testHouseNumber", 0, 1, "testAgreementSignDate",
                "testComment", new PgInet("127.0.0.1"), 2, "testServiceStatus", 600, 4, 5, "m3", "testBrasName"
        );
        clientDAO.saveClient(client);
        clientId = client.getId();
        clientOldBalance = client.getBalance();

        service.updateClientWarning(client);

        List<Log> logs = loggingDAO.findAllLogs();
        int logId = logs.get(0).getId();

        Client actual = clientDAO.findClientById(clientId);
        float newBalance = actual.getBalance();

        assertEquals(
                "[Log{id=" + logId + ", adminName='Billing Scheduler', action='Баланс клиента обновлен. Денег мало', " +
                        "clientID=" + clientId + ", actionInfo='Снятие денег. Старый баланс: " + clientOldBalance + "грн." +
                        " Актуальный баланс: "+ newBalance +"грн. Статус услуги изменени на 'Warning'', " +
                        "dateTime='" + getCurrentTime() + "', value=4.0}]", logs.toString()
        );

        assertEquals(
                "Client{id=" + clientId + ", login='testLogin', lastName='testLastName', firstName='testFirstName', " +
                        "patronymicName='testPatronymicName', phoneNumber='testPhoneNumber', city='testCity', " +
                        "street='testStreet', houseNumber='testHouseNumber', apartmentNumber=0, " +
                        "agreementNumber=1, agreementSignDate='testAgreementSignDate', comment='testComment', " +
                        "ip=127.0.0.1, vid=2, serviceStatus='Warning', balance=200.0, warningLimit=4, " +
                        "shutDownLimit=5, rate='m3', brasName='testBrasName'}", actual.toString()
        );
    }

    @Test
    public void updateClientNotEnoughMoneyTest() throws DAOException, ServiceException {
        Client client = new Client(
                "testLogin", "testLastName", "testFirstName", "testPatronymicName",
                "testPhoneNumber", "testCity", "testStreet", "testHouseNumber", 0, 1, "testAgreementSignDate",
                "testComment", new PgInet("127.0.0.1"), 2, "testServiceStatus", 200, 4, 5, "m3", "testBrasName"
        );
        clientDAO.saveClient(client);
        clientId = client.getId();
        clientOldBalance = client.getBalance();


        service.updateClientNotEnoughMoney(client);

        List<Log> logs = loggingDAO.findAllLogs();
        int logId = logs.get(0).getId();

        Client actual = clientDAO.findClientById(clientId);
        float newBalance = actual.getBalance();

        assertEquals(
                "[Log{id=" + logId + ", adminName='Billing Scheduler', action='Денег недостаточно', " +
                        "clientID=" + clientId + ", actionInfo='Денег недостаточно. Статус услуги изменени на " +
                        "'Not active'', " +
                        "dateTime='" + getCurrentTime() + "', value=2.0}]", logs.toString()
        );

        assertEquals(
                "Client{id=" + clientId + ", login='testLogin', lastName='testLastName', firstName='testFirstName', " +
                        "patronymicName='testPatronymicName', phoneNumber='testPhoneNumber', city='testCity', " +
                        "street='testStreet', houseNumber='testHouseNumber', apartmentNumber=0, " +
                        "agreementNumber=1, agreementSignDate='testAgreementSignDate', comment='testComment', " +
                        "ip=127.0.0.1, vid=2, serviceStatus='NOT ACTIVE', balance=200.0, warningLimit=4, " +
                        "shutDownLimit=5, rate='m3', brasName='testBrasName'}", actual.toString()
        );
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
