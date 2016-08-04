package ua.com.studyazimut.DAO.repository.client;

import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.client.Client;
import ua.com.studyazimut.DAO.repository.AbstractDAO;

import java.util.List;

/**
 * Created by POSITIV on 06.06.2016.
 */
public class ClientDAOImpl extends AbstractDAO implements ClientDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<Client> findAllClients() throws DAOException {
        try {
            return super.findAllRecords(Client.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public void saveClient(Client client) throws DAOException {
        try {
            super.saveRecord(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public Client findClientById(int id) throws DAOException {
        try {
            return (Client) super.findById(Client.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public void updateClient(Client client) throws DAOException {
        try {
            super.updateRecord(client);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public void cleanTable() {
        super.cleanTable("Client");
    }
}
