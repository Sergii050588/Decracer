package ua.com.studyazimut.DAO.repository.client;

import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.client.Client;

import java.util.List;

/**
 * Created by POSITIV on 06.06.2016.
 */
public interface ClientDAO {

    @SuppressWarnings("unchecked")
    List<Client> findAllClients() throws DAOException;

    void updateClient(Client client) throws DAOException;

    Client findClientById(int id) throws DAOException;

    //saveClient() for tests only
    void saveClient(Client client) throws DAOException;

    //cleanTable() for tests only
    void cleanTable();

}
