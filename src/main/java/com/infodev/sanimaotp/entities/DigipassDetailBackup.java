/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infodev.sanimaotp.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nishantshah
 */
@Entity
@Table(name = "digipass_detail_backup")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "DigipassDetailBackup.findAll", query = "SELECT d FROM DigipassDetailBackup d")
        , @NamedQuery(name = "DigipassDetailBackup.findBySerialAppKey", query = "SELECT d FROM DigipassDetailBackup d WHERE d.serialAppKey = :serialAppKey")
        , @NamedQuery(name = "DigipassDetailBackup.findByAppMode", query = "SELECT d FROM DigipassDetailBackup d WHERE d.appMode = :appMode")
        , @NamedQuery(name = "DigipassDetailBackup.findByAppType", query = "SELECT d FROM DigipassDetailBackup d WHERE d.appType = :appType")
        , @NamedQuery(name = "DigipassDetailBackup.findByAppBlob", query = "SELECT d FROM DigipassDetailBackup d WHERE d.appBlob = :appBlob")})
public class DigipassDetailBackup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "serial_app_key")
    private String serialAppKey;
    @Basic(optional = false)
    @Column(name = "app_mode")
    private String appMode;
    @Basic(optional = false)
    @Column(name = "app_type")
    private String appType;
    @Basic(optional = false)
    @Column(name = "app_blob")
    private String appBlob;
    @JoinColumn(name = "batch_id", referencedColumnName = "batch_id")
    @ManyToOne(optional = false)
    private DigipassBatch batchId;
    @JoinColumn(name = "serial_number", referencedColumnName = "serial_number")
    @ManyToOne(optional = false)
    private DigipassStatus serialNumber;

    public DigipassDetailBackup() {
    }

    public DigipassDetailBackup(String serialAppKey) {
        this.serialAppKey = serialAppKey;
    }

    public DigipassDetailBackup(String serialAppKey, String appMode, String appType, String appBlob) {
        this.serialAppKey = serialAppKey;
        this.appMode = appMode;
        this.appType = appType;
        this.appBlob = appBlob;
    }

    public String getSerialAppKey() {
        return serialAppKey;
    }

    public void setSerialAppKey(String serialAppKey) {
        this.serialAppKey = serialAppKey;
    }

    public String getAppMode() {
        return appMode;
    }

    public void setAppMode(String appMode) {
        this.appMode = appMode;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppBlob() {
        return appBlob;
    }

    public void setAppBlob(String appBlob) {
        this.appBlob = appBlob;
    }

    public DigipassBatch getBatchId() {
        return batchId;
    }

    public void setBatchId(DigipassBatch batchId) {
        this.batchId = batchId;
    }

    public DigipassStatus getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(DigipassStatus serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serialAppKey != null ? serialAppKey.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DigipassDetailBackup)) {
            return false;
        }
        DigipassDetailBackup other = (DigipassDetailBackup) object;
        if ((this.serialAppKey == null && other.serialAppKey != null) || (this.serialAppKey != null && !this.serialAppKey.equals(other.serialAppKey))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.DigipassDetailBackup[ serialAppKey=" + serialAppKey + " ]";
    }

}
