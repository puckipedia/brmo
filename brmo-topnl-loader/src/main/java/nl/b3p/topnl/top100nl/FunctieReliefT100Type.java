//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.12.07 at 02:40:39 PM CET 
//


package nl.b3p.topnl.top100nl;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FunctieReliefT100Type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="FunctieReliefT100Type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="geluid weren"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "FunctieReliefT100Type", namespace = "http://register.geostandaarden.nl/gmlapplicatieschema/top100nl/1.1.0")
@XmlEnum
public enum FunctieReliefT100Type {

    @XmlEnumValue("geluid weren")
    GELUID_WEREN("geluid weren");
    private final String value;

    FunctieReliefT100Type(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FunctieReliefT100Type fromValue(String v) {
        for (FunctieReliefT100Type c: FunctieReliefT100Type.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
