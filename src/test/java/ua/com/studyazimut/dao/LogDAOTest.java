package ua.com.studyazimut.dao;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.log.Log;
import ua.com.studyazimut.DAO.repository.log.LoggingDAO;
import ua.com.studyazimut.DAO.repository.log.LoggingDAOImpl;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class LogDAOTest {

    private LoggingDAO loggingDAO = new LoggingDAOImpl();

    private int id;


    @Before
    public void clearTableTest() {
        loggingDAO.cleanTable();
        Log log = new Log("testAdminName", "testAction", 1, "testActionInfo", "testDateTime", 1);
        loggingDAO.saveLog(log);
        id = log.getId();
    }


    @Test
    public void findAllLogsTest() throws DAOException {
        List<Log> log = loggingDAO.findAllLogs();
        assertEquals(
                "[Log{id=" + id + ", adminName='testAdminName', action='testAction', clientID=1, " +
                "actionInfo='testActionInfo', dateTime='testDateTime', value=1.0}]", log.toString()
        );
    }
}
