package ua.com.studyazimut.DAO.repository.rate;

import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.rate.Rate;
import ua.com.studyazimut.DAO.repository.AbstractDAO;

import java.util.List;

public class RateDAOImpl extends AbstractDAO implements RateDAO {

    public RateDAOImpl() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rate> findAllRates() throws DAOException {
        try {
            return super.findAllRecords(Rate.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Rate> findRateBy(String rateName) throws DAOException {
        try {
            String [] neededRate = rateName.split(" ");
            String shortRateName = neededRate[0];
            return super.findBy(Rate.class, "shortName", shortRateName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DAOException(e);
        }
    }

    @Override
    public void saveRate(Rate rate) throws DAOException {
        try {
            super.saveRecord(rate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanTable() {
        super.cleanTable("Rate");
    }
}
