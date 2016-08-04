package ua.com.studyazimut;

import ua.com.studyazimut.DAO.repository.client.ClientDAO;
import ua.com.studyazimut.DAO.repository.client.ClientDAOImpl;
import ua.com.studyazimut.DAO.repository.log.LoggingDAO;
import ua.com.studyazimut.DAO.repository.log.LoggingDAOImpl;
import ua.com.studyazimut.DAO.repository.rate.RateDAO;
import ua.com.studyazimut.DAO.repository.rate.RateDAOImpl;
import ua.com.studyazimut.controller.Controller;
import ua.com.studyazimut.controller.ControllerImpl;
import ua.com.studyazimut.service.Service;
import ua.com.studyazimut.service.ServiceImpl;

public class Main {

    public static void main(String[] args) {
        ClientDAO clientDAO = new ClientDAOImpl();
        RateDAO rateDAO = new RateDAOImpl();
        LoggingDAO loggingDAO = new LoggingDAOImpl();

        Service service = new ServiceImpl(rateDAO, clientDAO, loggingDAO);

        Controller controller = new ControllerImpl(rateDAO, clientDAO, service);
        controller.run();
    }
}
