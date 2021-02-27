/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author kalogeros
 */
@Entity
@Table(name = "COVIDDATA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Coviddata.findAll", query = "SELECT c FROM Coviddata c")
    , @NamedQuery(name = "Coviddata.findByCoviddata", query = "SELECT c FROM Coviddata c WHERE c.coviddata = :coviddata")
    , @NamedQuery(name = "Coviddata.findByTrndate", query = "SELECT c FROM Coviddata c WHERE c.trndate = :trndate")
    , @NamedQuery(name = "Coviddata.findByDatakind", query = "SELECT c FROM Coviddata c WHERE c.datakind = :datakind")
    , @NamedQuery(name = "Coviddata.findByQty", query = "SELECT c FROM Coviddata c WHERE c.qty = :qty")
    , @NamedQuery(name = "Coviddata.findByProodqty", query = "SELECT c FROM Coviddata c WHERE c.proodqty = :proodqty")
    , @NamedQuery(name = "Coviddata.findByCountry", query = "SELECT c FROM Coviddata c WHERE c.country = :country")
    , @NamedQuery(name = "Coviddata.findByCountryAndDataKind", query = "SELECT c FROM Coviddata c WHERE c.country = :country and c.datakind = :datakind")
    , @NamedQuery(name = "Coviddata.findByCountryAndDataKindByDateRange", query = "SELECT c FROM Coviddata c WHERE c.country = :country and c.datakind = :datakind and c.trndate >= :fromDate and c.trndate <= :toDate")})
public class Coviddata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COVIDDATA")
    private Integer coviddata;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TRNDATE")
    @Temporal(TemporalType.DATE)
    private Date trndate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DATAKIND")
    private short datakind;
    @Basic(optional = false)
    @NotNull
    @Column(name = "QTY")
    private int qty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PROODQTY")
    private int proodqty;
    @JoinColumn(name = "COUNTRY", referencedColumnName = "COUNTRY")
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false)
    private Country country;

    public Coviddata() {
    }

    public Coviddata(Integer coviddata) {
        this.coviddata = coviddata;
    }

    public Coviddata(Integer coviddata, Date trndate, short datakind, int qty, int proodqty) {
        this.coviddata = coviddata;
        this.trndate = trndate;
        this.datakind = datakind;
        this.qty = qty;
        this.proodqty = proodqty;
    }

    public Integer getCoviddata() {
        return coviddata;
    }

    public void setCoviddata(Integer coviddata) {
        this.coviddata = coviddata;
    }

    public Date getTrndate() {
        return trndate;
    }

    public void setTrndate(Date trndate) {
        this.trndate = trndate;
    }

    public short getDatakind() {
        return datakind;
    }

    public void setDatakind(short datakind) {
        this.datakind = datakind;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getProodqty() {
        return proodqty;
    }

    public void setProodqty(int proodqty) {
        this.proodqty = proodqty;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coviddata != null ? coviddata.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Coviddata)) {
            return false;
        }
        Coviddata other = (Coviddata) object;
        if ((this.coviddata == null && other.coviddata != null) || (this.coviddata != null && !this.coviddata.equals(other.coviddata))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "models.Coviddata[ coviddata=" + coviddata + " ]";
    }

}
