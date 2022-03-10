/*
 * Copyright (C) 2022 B3Partners B.V.
 *
 * SPDX-License-Identifier: MIT
 *
 */

package nl.b3p.brmo.nhr.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("NietNatuurlijkPersoon")
public abstract class NietNatuurlijkPersoon extends Persoon {
    private String datumUitschrijving;
    public String getDatumUitschrijving() {
        return datumUitschrijving;
    }
    public void setDatumUitschrijving(String datumUitschrijving) {
        this.datumUitschrijving = datumUitschrijving;
    }

    private String rsin;
    public String getRsin() {
        return rsin;
    }
    public void setRsin(String rsin) {
        this.rsin = rsin;
    }

    private String statutenDatumAanvang;
    public String getStatutenDatumAanvang() {
        return statutenDatumAanvang;
    }
    public void setStatutenDatumAanvang(String statutenDatumAanvang) {
        this.statutenDatumAanvang = statutenDatumAanvang;
    }

    private String statutenDatumAkte;
    public String getStatutenDatumAkte() {
        return statutenDatumAkte;
    }
    public void setStatutenDatumAkte(String statutenDatumAkte) {
        this.statutenDatumAkte = statutenDatumAkte;
    }

    public String getId() {
        return "rsin-" + rsin;
    }
}
