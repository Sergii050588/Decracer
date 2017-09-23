package ua.com.studyazimut.controller;

import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.client.Client;
import ua.com.studyazimut.DAO.entity.rate.Rate;
import ua.com.studyazimut.DAO.repository.HibernateUtil;
import ua.com.studyazimut.DAO.repository.client.ClientDAO;
import ua.com.studyazimut.DAO.repository.rate.RateDAO;
import ua.com.studyazimut.service.Service;

import java.util.List;

public class ControllerImpl implements Controller {

    private RateDAO rateDAO;

    private ClientDAO clientDAO;

    private Service service;

    public ControllerImpl(RateDAO rateDAO, ClientDAO clientDAO, Service service) {
        this.rateDAO = rateDAO;
        this.clientDAO = clientDAO;
        this.service = service;
    }

    @Override
    public void run() {
        try {
            List<Client> clients = clientDAO.findAllClients();

            for (Client client : clients) {
                float balance = client.getBalance();
                String status = client.getServiceStatus();

                if ("Active".equals(status)) {
                    if (balance <= client.getShutDownLimit()) {
                        service.updateClientNotEnoughMoney(client);
                    } else if (balance <= client.getWarningLimit()) {
                        service.updateClientWarning(client);
                    } else {
                        service.updateClientEnoughMoney(client);
                    }
                }

                if ("Warning".equals(status)) {
                    if (balance <= client.getShutDownLimit()) {
                        service.updateClientNotEnoughMoney(client);
                    } else if (balance < client.getWarningLimit()) {
                        service.updateClientWithWarning(client);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }

    @SuppressWarnings("unchecked")
    private float getRateDailyCost(Client client) throws DAOException {
        String rateName = client.getRate();
        List<Rate> rates = rateDAO.findRateBy(rateName);
        if (rates.size() == 0) {
            return -1;
        }
        return rates.get(0).getDailyCost();
    }
}
