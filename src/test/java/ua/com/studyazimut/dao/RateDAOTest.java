package ua.com.studyazimut.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.rate.Rate;
import ua.com.studyazimut.DAO.repository.rate.RateDAO;
import ua.com.studyazimut.DAO.repository.rate.RateDAOImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RateDAOTest {

    private RateDAO rateDAO = new RateDAOImpl();

    private int id;

    @Before
    public void setup() throws DAOException {
        rateDAO.cleanTable();
        Rate rate = new Rate("m3", "month 3", 400, true, true);
        rateDAO.saveRate(rate);
        id = rate.getId();
    }

    @Test
    public void getAllRates() throws DAOException {
        List<Rate> allRates = rateDAO.findAllRates();
        assertEquals(
                "[Rate{cid=" + id + ", shortName='m3', longName='month 3', dailyCost=400, oldRate=true, archive=true}]",
                allRates.toString()
        );
    }
}
