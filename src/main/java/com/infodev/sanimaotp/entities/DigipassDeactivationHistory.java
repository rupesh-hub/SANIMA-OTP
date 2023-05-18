package com.infodev.sanimaotp.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "DigipassDeactivationHistory")
public class DigipassDeactivationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Basic(optional = false)
    @Column(name = "serial_number")
    private String serialNumber;
    @Basic(optional = false)
    @Column(name = "central_id")
    private String centralId;
    @Column(name = "app1_mb")
    private String app1Mb;
    @Column(name = "app2_kiosk")
    private String app2Kiosk;
    @Column(name = "app3_atm")
    private String app3Atm;
    @Column(name = "app4_vbv")
    private String app4Vbv;
    @Column(name = "app5_ibank")
    private String app5Ibank;
    @Column(name = "app6_vbv_cr")
    private String app6VbvCr;
    @Column(name = "app7_kioskcr")
    private String app7Kioskcr;
    @Column(name = "app8_ft_sig")
    private String app8FtSig;
    @Column(name = "deactivation_date")
    private Date deactivationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public Date getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(Date deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public DigipassDeactivationHistory(DigipassStatus digipassStatus){
        this.serialNumber=digipassStatus.getSerialNumber();
        this.centralId=digipassStatus.getCentralId();
        this.app1Mb=digipassStatus.getApp1Mb();
        this.app2Kiosk=digipassStatus.getApp2Kiosk();
        this.app3Atm=digipassStatus.getApp3Atm();
        this.app4Vbv=digipassStatus.getApp4Vbv();
        this.app5Ibank=digipassStatus.getApp5Ibank();
        this.app6VbvCr=digipassStatus.getApp6VbvCr();
        this.app7Kioskcr=digipassStatus.getApp7Kioskcr();
        this.app8FtSig=digipassStatus.getApp8FtSig();
        this.deactivationDate=new Date();
    }
}
