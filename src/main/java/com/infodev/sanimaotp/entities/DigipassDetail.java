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
@Table(name = "digipass_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DigipassDetail.findAll", query = "SELECT d FROM DigipassDetail d")
    , @NamedQuery(name = "DigipassDetail.findBySerialAppKey", query = "SELECT d FROM DigipassDetail d WHERE d.serialAppKey = :serialAppKey")
    , @NamedQuery(name = "DigipassDetail.findByAppMode", query = "SELECT d FROM DigipassDetail d WHERE d.appMode = :appMode")
    , @NamedQuery(name = "DigipassDetail.findByAppType", query = "SELECT d FROM DigipassDetail d WHERE d.appType = :appType")
    , @NamedQuery(name = "DigipassDetail.findByAppBlob", query = "SELECT d FROM DigipassDetail d WHERE d.appBlob = :appBlob")})
public class DigipassDetail implements Serializable {

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

    public DigipassDetail() {
    }

    public DigipassDetail(String serialAppKey) {
        this.serialAppKey = serialAppKey;
    }

    public DigipassDetail(String serialAppKey, String appMode, String appType, String appBlob) {
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
        if (!(object instanceof DigipassDetail)) {
            return false;
        }
        DigipassDetail other = (DigipassDetail) object;
        if ((this.serialAppKey == null && other.serialAppKey != null) || (this.serialAppKey != null && !this.serialAppKey.equals(other.serialAppKey))) {
            return false;
        }
        return true;
    }


    
}
