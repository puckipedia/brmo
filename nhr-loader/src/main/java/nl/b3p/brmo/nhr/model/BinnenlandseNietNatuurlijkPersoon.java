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
@DiscriminatorValue("BinnenlandseNietNatuurlijkPersoon")
public abstract class BinnenlandseNietNatuurlijkPersoon extends NietNatuurlijkPersoon {
}
