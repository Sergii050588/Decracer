package ua.com.studyazimut.service;

import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.client.Client;
import ua.com.studyazimut.DAO.entity.log.Log;
import ua.com.studyazimut.DAO.entity.rate.Rate;
import ua.com.studyazimut.DAO.repository.client.ClientDAO;
import ua.com.studyazimut.DAO.repository.log.LoggingDAO;
import ua.com.studyazimut.DAO.repository.rate.RateDAO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ServiceImpl implements Service {

    private ClientDAO clientDAO;

    private LoggingDAO loggingDAO;

    private RateDAO rateDAO;

    public ServiceImpl(RateDAO rateDAO, ClientDAO clientDAO, LoggingDAO loggingDAO) {
        this.rateDAO = rateDAO;
        this.clientDAO = clientDAO;
        this.loggingDAO = loggingDAO;
    }

    @Override
    public void updateClientEnoughMoney(Client client) throws ServiceException {
        try {
            float oldBalance = client.getBalance();
            client.setBalance(client.getBalance() - getRateDailyCost(client));

            clientDAO.updateClient(client);
            float newBalance = client.getBalance();
            loggingDAO.saveLog(new Log(
                    "Billing Scheduler", "Баланс клиента обновлен. Денег достаточно", client.getId(),
                    "Снятие денег. Старый баланс: " + oldBalance + "грн. Актуальный баланс: " + newBalance + "грн.",
                    getCurrentTime(), getRateDailyCost(client))
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateClientWarning(Client client) throws ServiceException {
        try {
            float balance = client.getBalance();
            client.setBalance(balance - getRateDailyCost(client));
            client.setServiceStatus("Warning");

            clientDAO.updateClient(client);
            float newBalance = client.getBalance();
            loggingDAO.saveLog(new Log(
                    "Billing Scheduler", "Баланс клиента обновлен. Денег мало", client.getId(),
                    "Снятие денег. Старый баланс: " + balance + "грн. Актуальный баланс: " + newBalance + "грн. " +
                            "Статус услуги изменени на 'Warning'",
                    getCurrentTime(), getRateDailyCost(client))
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateClientWithWarning(Client client) throws ServiceException {
        try {
            float balance = client.getBalance();
            client.setBalance(balance - getRateDailyCost(client));

            clientDAO.updateClient(client);
            float newBalance = client.getBalance();
            loggingDAO.saveLog(new Log(
                    "Billing Scheduler", "Warning! Баланс клиента обновлен. Денег мало", client.getId(),
                    "Снятие денег. Старый баланс: " + balance + "грн. Актуальный баланс: " + newBalance + "грн.",
                    getCurrentTime(), getRateDailyCost(client))
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateClientNotEnoughMoney(Client client) throws ServiceException {
        try {
            client.setServiceStatus("Not active");

            clientDAO.updateClient(client);
            loggingDAO.saveLog(new Log(
                    "Billing Scheduler", "Денег недостаточно", client.getId(),
                    "Денег недостаточно. Статус услуги изменени на 'Not active'",
                    getCurrentTime(), client.getBalance())
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private float getRateDailyCost(Client client) throws DAOException {
        String rateName = client.getRate();
        List<Rate> rates = rateDAO.findRateBy(rateName);
        if (rates.size() == 0) {
            loggingDAO.saveLog(new Log(
                    "Billing Scheduler", "Ошибка при обновлении клиента", client.getId(),
                    "Тариф не найден",
                    getCurrentTime(), getRateDailyCost(client))
            );
            return 0;
        }
        return rates.get(0).getDailyCost();
    }

    private String getCurrentTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return dateFormat.format(new Date());
    }
}
