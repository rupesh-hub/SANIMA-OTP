/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.infodev.sanimaotp.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nishantshah
 */
@Entity
@Table(name = "digipass_batch")
@XmlRootElement

public class DigipassBatch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "batch_id")
    private String batchId;
    @Column(name = "import_date")
    private String importDate;
    @Column(name = "imported_by")
    private String importedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "batchId")
    private Collection<DigipassDetail> digipassDetailCollection;

    public DigipassBatch() {
    }

    public DigipassBatch(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getImportedBy() {
        return importedBy;
    }

    public void setImportedBy(String importedBy) {
        this.importedBy = importedBy;
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
        hash += (batchId != null ? batchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DigipassBatch)) {
            return false;
        }
        DigipassBatch other = (DigipassBatch) object;
        if ((this.batchId == null && other.batchId != null) || (this.batchId != null && !this.batchId.equals(other.batchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.DigipassBatch[ batchId=" + batchId + " ]";
    }
    
}
