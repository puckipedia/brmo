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
import javax.persistence.Id;

@Entity
@DiscriminatorValue("Samenwerkingsverband")
public class Samenwerkingsverband extends BinnenlandseNietNatuurlijkPersoon {
    private Integer aantalCommanditaireVennoten;
    public Integer getAantalCommanditaireVennoten() {
        return aantalCommanditaireVennoten;
    }

    private String rechtsvorm;
    public String getRechtsvorm() {
        return rechtsvorm;
    }
}
