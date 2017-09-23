package ua.com.studyazimut.DAO.repository.log;

import ua.com.studyazimut.DAO.DAOException;
import ua.com.studyazimut.DAO.entity.log.Log;
import ua.com.studyazimut.DAO.repository.AbstractDAO;

import java.util.Collections;
import java.util.List;

public class LoggingDAOImpl extends AbstractDAO implements LoggingDAO {

    public LoggingDAOImpl() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")

    public List<Log> findAllLogs() {
        List<Log> logs = Collections.emptyList();
        try {
            logs = super.findAllRecords(Log.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

    @Override
    public void saveLog(Log log) {
        try {
            super.saveRecord(log);
        } catch (Exception e) {
            try {
                super.saveRecord(log);
            } catch (DAOException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void cleanTable() {
        super.cleanTable("Log");
    }
}
