package ua.com.studyazimut.service;

import ua.com.studyazimut.DAO.entity.client.Client;

/**
 * Created by POSITIV on 06.06.2016.
 */
public interface Service {

    void updateClientEnoughMoney(Client client) throws ServiceException;

    void updateClientNotEnoughMoney(Client client) throws ServiceException;

    void updateClientWarning(Client client) throws ServiceException;

    void updateClientWithWarning(Client client) throws ServiceException;
}
