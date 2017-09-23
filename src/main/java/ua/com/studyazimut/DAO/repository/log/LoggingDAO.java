package ua.com.studyazimut.DAO.repository.log;


import ua.com.studyazimut.DAO.entity.log.Log;

import java.util.List;

public interface LoggingDAO {

    @SuppressWarnings("unchecked")
    List<Log> findAllLogs();

    void saveLog(Log log);

    //cleanTable() for tests only
    void cleanTable();
}
