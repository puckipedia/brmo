/*
 * Copyright (C) 2022 B3Partners B.V.
 */
package nl.b3p.brmo.persistence.staging;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "nhr_laadproces")
public class NHRLaadProces implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datum;

    @Temporal(TemporalType.TIMESTAMP)
    private Date laatstGeprobeerd;

    @Temporal(TemporalType.TIMESTAMP)
    private Date volgendProberen;

    private Integer probeerAantal;

    private String kvkNummer;

    public static enum STATUS {
        OK, NOK, WAITING,
    }

    // <editor-fold defaultstate="collapsed" desc="getters and setters">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Date getLaatstGeprobeerd() {
        return laatstGeprobeerd;
    }

    public void setLaatstGeprobeerd(Date laatstGeprobeerd) {
        this.laatstGeprobeerd = laatstGeprobeerd;
    }

    public Date getVolgendProberen() {
        return volgendProberen;
    }

    public void setVolgendProberen(Date volgendProberen) {
        this.volgendProberen = volgendProberen;
    }

    public Integer getProbeerAantal() {
        return probeerAantal;
    }

    public void setProbeerAantal(Integer probeerAantal) {
        this.probeerAantal = probeerAantal;
    }

    public String getKvkNummer() {
        return kvkNummer;
    }

    public void setKvkNummer(String kvkNummer) {
        this.kvkNummer = kvkNummer;
    }
//</editor-fold>
}
