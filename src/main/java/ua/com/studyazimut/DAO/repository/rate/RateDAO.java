package ua.com.studyazimut.DAO.repository.rate;

import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.rate.Rate;

import java.util.List;

public interface RateDAO {

    @SuppressWarnings("unchecked")
    List<Rate> findAllRates() throws DAOException;

    List<Rate> findRateBy(String rateName) throws DAOException;

    //saveRate() for tests only
    void saveRate(Rate rate) throws DAOException;

    //cleanTable() for tests only
    void cleanTable();
}
