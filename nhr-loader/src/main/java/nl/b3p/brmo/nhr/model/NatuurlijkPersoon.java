/*
 * Copyright (C) 2022 B3Partners B.V.
 *
 * SPDX-License-Identifier: MIT
 *
 */

package nl.b3p.brmo.nhr.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NatuurlijkPersoon")
public class NatuurlijkPersoon extends Persoon {
    @Column
    public String aanduidingNaamgebruik;
    public String getAanduidingNaamgebruik() {
        return aanduidingNaamgebruik;
    }
    public void setAanduidingNaamgebruik(String aanduidingNaamgebruik) {
        this.aanduidingNaamgebruik = aanduidingNaamgebruik;
    }

    private String achternaam;
    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    private String bsn;
    public String getBsn() {
        return bsn;
    }
    public void setBsn(String bsn) {
        this.bsn = bsn;
    }

    private String geboortedatum;
    public String getGeboortedatum() {
        return geboortedatum;
    }
    public void setGeboortedatum(String geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    private String geboorteland;
    public String getGeboorteland() {
        return geboorteland;
    }
    public void setGeboorteland(String geboorteland) {
        this.geboorteland = geboorteland;
    }

    private String geboorteplaats;
    public String getGeboorteplaats() {
        return geboorteplaats;
    }
    public void setGeboorteplaats(String geboorteplaats) {
        this.geboorteplaats = geboorteplaats;
    }

    private String geslachtsaanduiding;
    public String getGeslachtsaanduiding() {
        return geslachtsaanduiding;
    }
    public void setGeslachtsaanduiding(String geslachtsaanduiding) {
        this.geslachtsaanduiding = geslachtsaanduiding;
    }

    private String geslachtsnaam;
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }
    public void setGeslachtsnaam(String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    private String geslachtsnaamPartner;
    public String getGeslachtsnaamPartner() {
        return geslachtsnaamPartner;
    }
    public void setGeslachtsnaamPartner(String geslachtsnaamPartner) {
        this.geslachtsnaamPartner = geslachtsnaamPartner;
    }

    private String nationaliteit;
    public String getNationaliteit() {
        return nationaliteit;
    }
    public void setNationaliteit(String nationaliteit) {
        this.nationaliteit = nationaliteit;
    }

    private String overlijdensdatum;
    public String getOverlijdensdatum() {
        return overlijdensdatum;
    }
    public void setOverlijdensdatum(String overlijdensdatum) {
        this.overlijdensdatum = overlijdensdatum;
    }

    private String tin;
    public String getTin() {
        return tin;
    }
    public void setTin(String tin) {
        this.tin = tin;
    }

    private String voornamen;
    public String getVoornamen() {
        return voornamen;
    }
    public void setVoornamen(String voornamen) {
        this.voornamen = voornamen;
    }

    private String voorvoegsel;
    public String getVoorvoegsel() {
        return voorvoegsel;
    }
    public void setVoorvoegsel(String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    private String voorvoegselGeslachtsnaam;
    public String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }
    public void setVoorvoegselGeslachtsnaam(String voorvoegselGeslachtsnaam) {
        this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
    }

    private String voorvoegselGeslachtsnaamPartner;
    public String getVoorvoegselGeslachtsnaamPartner() {
        return voorvoegselGeslachtsnaamPartner;
    }
    public void setVoorvoegselGeslachtsnaamPartner(String voorvoegselGeslachtsnaamPartner) {
        this.voorvoegselGeslachtsnaamPartner = voorvoegselGeslachtsnaamPartner;
    }

    public String getId() {
        if (bsn != null) {
            return "bsn-" + bsn;
        } else {
            return String.format("nobsn-%s-%s-%s", achternaam, geboortedatum, geslachtsnaam);
        }
    }
}
