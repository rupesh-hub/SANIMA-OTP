package com.infodev.sanimaotp.entities;

import javax.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "activityLog")
public class DataLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String centralId;
    private String taskBy;
    private String channelId;
    private String applicationId;
    private String activityDescription;
    private int activityStatus;
    private String logType;
    private String extraInfo1;
    private String extraInfo2;
    private String extraInfo3;
    private Date rDate;
    private String rTime;

    public DataLogger(String logType, String centralId, String applicationId, String channelId) {

        this.logType = logType;
        this.centralId = centralId;
        this.applicationId = applicationId;
        this.channelId = channelId;
        this.setDateTime();
    }


    public DataLogger(String logType) {
        this.setDateTime();
        this.logType = logType;
    }

    public DataLogger() {
        this.setDateTime();
    }

    public DataLogger(String centralId, String channelId, String applicationId, String activityDescription, int activityStatus, String logType, String extraInfo1, String extraInfo2, String extraInfo3, Date rDate, String dTime) {
        this.centralId = centralId;
        this.channelId = channelId;
        this.applicationId = applicationId;
        this.activityDescription = activityDescription;
        this.activityStatus = activityStatus;
        this.logType = logType;
        this.extraInfo1 = extraInfo1;
        this.extraInfo2 = extraInfo2;
        this.extraInfo3 = extraInfo3;
        this.rDate = rDate;
        this.rTime = dTime;
        this.setDateTime();
    }

    public String getTaskBy() {
        return taskBy;
    }

    public void setTaskBy(String taskBy) {
        this.taskBy = taskBy;
    }

    public String getrTime() {
        return rTime;
    }

    public void setrTime(String rTime) {
        this.rTime = rTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCentralId() {
        return centralId;
    }

    public void setCentralId(String centralId) {
        this.centralId = centralId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public int getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(int activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getExtraInfo1() {
        return extraInfo1;
    }

    public void setExtraInfo1(String extraInfo1) {
        this.extraInfo1 = extraInfo1;
    }

    public String getExtraInfo2() {
        return extraInfo2;
    }

    public void setExtraInfo2(String extraInfo2) {
        this.extraInfo2 = extraInfo2;
    }

    public String getExtraInfo3() {
        return extraInfo3;
    }

    public void setExtraInfo3(String extraInfo3) {
        this.extraInfo3 = extraInfo3;
    }

    public Date getrDate() {
        return rDate;
    }

    public void setrDate(Date rDate) {
        this.rDate = rDate;
    }

    public String getdTime() {
        return rTime;
    }

    public void setdTime(String dTime) {
        this.rTime = dTime;
    }

    public void setDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        try {
            this.rDate = formatter.parse(formatter.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalTime time = LocalTime.now();

        this.rTime = String.valueOf(time);
    }

}
