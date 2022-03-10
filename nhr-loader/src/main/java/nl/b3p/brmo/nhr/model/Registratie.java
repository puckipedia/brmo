/*
 * Copyright (C) 2022 B3Partners B.V.
 *
 * SPDX-License-Identifier: MIT
 *
 */

package nl.b3p.brmo.nhr.model;

import javax.persistence.Embeddable;

/**
 * Represents any object with a FormeleRegistratie and a MateriëleRegistratie.
 **/

@Embeddable
public class Registratie {
    private String datumAanvang;
    public String getDatumAanvang() {
        return datumAanvang;
    }
    public void setDatumAanvang(String datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    private String datumEinde;
    public String getDatumEinde() {
        return datumEinde;
    }
    public void setDatumEinde(String datumEinde) {
        this.datumEinde= datumEinde;
    }

    private String registratieTijdstip;
    public String getRegistratieTijdstip() {
        return registratieTijdstip;
    }
    public void setRegistratieTijdstip(String registratieTijdstip) {
        this.registratieTijdstip = registratieTijdstip;
    }
}
