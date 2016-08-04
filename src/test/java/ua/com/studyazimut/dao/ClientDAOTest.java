package ua.com.studyazimut.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.client.Client;
import ua.com.studyazimut.DAO.entity.client.clientUtils.PgInet;
import ua.com.studyazimut.DAO.repository.client.ClientDAO;
import ua.com.studyazimut.DAO.repository.client.ClientDAOImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClientDAOTest {

    private ClientDAO clientDAO = new ClientDAOImpl();

    private int id;


    @Before
    public void clearTableTest() throws DAOException {
        clientDAO.cleanTable();
        Client client = new Client(
                "testLogin", "testLastName", "testFirstName", "testPatronymicName",
                "testPhoneNumber", "testCity", "testStreet", "testHouseNumber", 0, 1, "testAgreementSignDate",
                "testComment", new PgInet("127.0.0.1"), 2, "testServiceStatus", 3, 4, 5, "testRate", "testBrasName"
        );
        clientDAO.saveClient(client);
        id = client.getId();
    }


    @Test
    public void findAllClientsTest() throws DAOException {
        List<Client> clients = clientDAO.findAllClients();

        assertEquals(
                "[Client{id=" + id + ", login='testLogin', lastName='testLastName', firstName='testFirstName', " +
                "patronymicName='testPatronymicName', phoneNumber='testPhoneNumber', city='testCity', " +
                "street='testStreet', houseNumber='testHouseNumber', apartmentNumber=0, agreementNumber=1, " +
                "agreementSignDate='testAgreementSignDate', comment='testComment', ip=127.0.0.1, vid=2, " +
                "serviceStatus='testServiceStatus', balance=3.0, warningLimit=4, shutDownLimit=5, rate='testRate', " +
                "brasName='testBrasName'}]", clients.toString()
        );
    }


    @Test
    public void updateClientTest() throws DAOException {
        List<Client> clients = clientDAO.findAllClients();
        Client client = clients.get(0);
        client.setLastName("updatedLastName");
        clientDAO.updateClient(client);

        List<Client> actual = clientDAO.findAllClients();

        assertEquals(
                "[Client{id=" + id + ", login='testLogin', lastName='updatedLastName', firstName='testFirstName', " +
                "patronymicName='testPatronymicName', phoneNumber='testPhoneNumber', city='testCity', " +
                "street='testStreet', houseNumber='testHouseNumber', apartmentNumber=0, agreementNumber=1, " +
                "agreementSignDate='testAgreementSignDate', comment='testComment', ip=127.0.0.1, vid=2, " +
                "serviceStatus='testServiceStatus', balance=3.0, warningLimit=4, shutDownLimit=5, rate='testRate', " +
                "brasName='testBrasName'}]", actual.toString()
        );
    }
}
