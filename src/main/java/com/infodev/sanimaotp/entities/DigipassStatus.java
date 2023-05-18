/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infodev.sanimaotp.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Null;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nishantshah
 */
@Entity
@Table(name = "digipass_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DigipassStatus.findAll", query = "SELECT d FROM DigipassStatus d")
    , @NamedQuery(name = "DigipassStatus.findBySerialNumber", query = "SELECT d FROM DigipassStatus d WHERE d.serialNumber = :serialNumber")
    , @NamedQuery(name = "DigipassStatus.findByStatus", query = "SELECT d FROM DigipassStatus d WHERE d.status = :status")
    , @NamedQuery(name = "DigipassStatus.findByCentralId", query = "SELECT d FROM DigipassStatus d WHERE d.centralId = :centralId")
    , @NamedQuery(name = "DigipassStatus.findByApp1Mb", query = "SELECT d FROM DigipassStatus d WHERE d.app1Mb = :app1Mb")
    , @NamedQuery(name = "DigipassStatus.findByApp2Kiosk", query = "SELECT d FROM DigipassStatus d WHERE d.app2Kiosk = :app2Kiosk")
    , @NamedQuery(name = "DigipassStatus.findByApp3Atm", query = "SELECT d FROM DigipassStatus d WHERE d.app3Atm = :app3Atm")
    , @NamedQuery(name = "DigipassStatus.findByApp4Vbv", query = "SELECT d FROM DigipassStatus d WHERE d.app4Vbv = :app4Vbv")
    , @NamedQuery(name = "DigipassStatus.findByApp5Ibank", query = "SELECT d FROM DigipassStatus d WHERE d.app5Ibank = :app5Ibank")
    , @NamedQuery(name = "DigipassStatus.findByApp6VbvCr", query = "SELECT d FROM DigipassStatus d WHERE d.app6VbvCr = :app6VbvCr")
    , @NamedQuery(name = "DigipassStatus.findByApp7Kioskcr", query = "SELECT d FROM DigipassStatus d WHERE d.app7Kioskcr = :app7Kioskcr")
    , @NamedQuery(name = "DigipassStatus.findByApp8FtSig", query = "SELECT d FROM DigipassStatus d WHERE d.app8FtSig = :app8FtSig")})
public class DigipassStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "serial_number")
    private String serialNumber;
    @Basic(optional = false)
    @Column(name = "status")
    private String status;
    @Column(name = "central_id",unique=true)
    private String centralId;
    @Column(name = "app1_mb",unique=true)
    private String app1Mb;
    @Column(name = "app2_kiosk",unique=true)
    private String app2Kiosk;
    @Column(name = "app3_atm",unique=true)
    private String app3Atm;
    @Column(name = "app4_vbv",unique=true)
    private String app4Vbv;
    @Column(name = "app5_ibank",unique=true)
    private String app5Ibank;
    @Column(name = "app6_vbv_cr",unique=true)
    private String app6VbvCr;
    @Column(name = "app7_kioskcr",unique=true)
    private String app7Kioskcr;
    @Column(name = "app8_ft_sig",unique=true)
    private String app8FtSig;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "serialNumber")
    private Collection<DigipassDetail> digipassDetailCollection;

    public DigipassStatus() {
    }

    public DigipassStatus(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public DigipassStatus(String serialNumber, String status) {
        this.serialNumber = serialNumber;
        this.status = status;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCentralId() {
        return centralId;
    }

    public void setCentralId(String centralId) {
        this.centralId = centralId;
    }

    public String getApp1Mb() {
        return app1Mb;
    }

    public void setApp1Mb(String app1Mb) {
        this.app1Mb = app1Mb;
    }

    public String getApp2Kiosk() {
        return app2Kiosk;
    }

    public void setApp2Kiosk(String app2Kiosk) {
        this.app2Kiosk = app2Kiosk;
    }

    public String getApp3Atm() {
        return app3Atm;
    }

    public void setApp3Atm(String app3Atm) {
        this.app3Atm = app3Atm;
    }

    public String getApp4Vbv() {
        return app4Vbv;
    }

    public void setApp4Vbv(String app4Vbv) {
        this.app4Vbv = app4Vbv;
    }

    public String getApp5Ibank() {
        return app5Ibank;
    }

    public void setApp5Ibank(String app5Ibank) {
        this.app5Ibank = app5Ibank;
    }

    public String getApp6VbvCr() {
        return app6VbvCr;
    }

    public void setApp6VbvCr(String app6VbvCr) {
        this.app6VbvCr = app6VbvCr;
    }

    public String getApp7Kioskcr() {
        return app7Kioskcr;
    }

    public void setApp7Kioskcr(String app7Kioskcr) {
        this.app7Kioskcr = app7Kioskcr;
    }

    public String getApp8FtSig() {
        return app8FtSig;
    }

    public void setApp8FtSig(String app8FtSig) {
        this.app8FtSig = app8FtSig;
    }

    @XmlTransient
    public Collection<DigipassDetail> getDigipassDetailCollection() {
        return digipassDetailCollection;
    }

    public void setDigipassDetailCollection(Collection<DigipassDetail> digipassDetailCollection) {
        this.digipassDetailCollection = digipassDetailCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serialNumber != null ? serialNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DigipassStatus)) {
            return false;
        }
        DigipassStatus other = (DigipassStatus) object;
        if ((this.serialNumber == null && other.serialNumber != null) || (this.serialNumber != null && !this.serialNumber.equals(other.serialNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.DigipassStatus[ serialNumber=" + serialNumber + " ]";
    }
    
}
