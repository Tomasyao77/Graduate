package com.whut.tomasyao.message.mapper;

import edu.whut.pocket.message.model.LogMessage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zouy on 18-9-15.
 */
public interface LogMessageMapper {
    Integer addOne(LogMessage logMessage) throws Exception;

    List<LogMessage> getLogMessagePage(HashMap<String, Object> hashMap) throws Exception;

    Integer findCountLogMessagePage(HashMap<String, Object> hashMap) throws Exception;
}
