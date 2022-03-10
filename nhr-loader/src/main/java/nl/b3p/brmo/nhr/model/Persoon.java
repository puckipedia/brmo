/*
 * Copyright (C) 2022 B3Partners B.V.
 *
 * SPDX-License-Identifier: MIT
 *
 */

package nl.b3p.brmo.nhr.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Embedded;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
public abstract class Persoon {
    @Embedded
    private Registratie registratie = new Registratie();
    public Registratie getRegistratie() {
        return registratie;
    }
    public void setRegistratie(Registratie registratie) {
        this.registratie = registratie;
    }

    private String persoonRechtsvorm;
    public String getPersoonRechtsvorm() {
        return persoonRechtsvorm;
    }
    public void setPersoonRechtsvorm(String persoonRechtsvorm) {
        this.persoonRechtsvorm = persoonRechtsvorm;
    }

    public String uitgebreideRechtsvorm;
    public String getUitgebreideRechtsvorm() {
        return uitgebreideRechtsvorm;
    }
    public void setUitgebreideRechtsvorm(String uitgebreideRechtsvorm) {
        this.uitgebreideRechtsvorm = uitgebreideRechtsvorm;
    }

    public String volledigeNaam;
    public String getVolledigeNaam() {
        return volledigeNaam;
    }
    public void setVolledigeNaam(String volledigeNaam) {
        this.volledigeNaam = volledigeNaam;
    }

    @Id
    @Column
    public abstract String getId();
}
