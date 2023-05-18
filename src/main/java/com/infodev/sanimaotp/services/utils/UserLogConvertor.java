package com.infodev.sanimaotp.services.utils;

import com.infodev.sanimaotp.entities.UserLog;
import com.infodev.sanimaotp.pojo.UserLogPojo;
import com.infodev.sanimaotp.pojo.UserLogResponse;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserLogConvertor {

    public UserLog userLogPojoToUserLog(UserLogPojo _uLogPojo) {

        return UserLog
                .builder()
                .rDateTime(_uLogPojo.getRDateTime())
                .taskBy(_uLogPojo.getTaskBy())
                .taskType(_uLogPojo.getTaskType())
                .extraInfo1(_uLogPojo.getExtraInfo1())
                .extraInfo2(_uLogPojo.getExtraInfo2())
                .extraInfo3(_uLogPojo.getExtraInfo3())
                .build();
    }

    public UserLogPojo userLogToUserLogPojo(UserLog _uLog) {
        return UserLogPojo
                .builder()
                .rDateTime(_uLog.getRDateTime())
                .taskBy(_uLog.getTaskBy())
                .taskType(_uLog.getTaskType())
                .extraInfo1(_uLog.getExtraInfo1())
                .extraInfo2(_uLog.getExtraInfo2())
                .extraInfo3(_uLog.getExtraInfo3())
                .build();
    }

    public UserLogResponse userLogToUserLogRes(UserLog _uLog) {
        return UserLogResponse
                .builder()
                .rDateTime(_uLog.getRDateTime())
                .taskType(_uLog.getTaskType())
                .taskBy(String.valueOf(_uLog.getTaskBy()))
                .extraInfo1(_uLog.getExtraInfo1())
                .extraInfo2(_uLog.getExtraInfo2())
                .extraInfo3(_uLog.getExtraInfo3())
                .build();
    }

    public List<UserLogPojo> userLogToUserLogPojoList(final List<UserLog> _uLogList) {
        return _uLogList
                .stream()
                .map(userLog -> userLogToUserLogPojo(userLog))
                .collect(Collectors.toList());
    }

}
