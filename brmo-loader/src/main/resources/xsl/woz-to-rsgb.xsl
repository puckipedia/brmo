<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:woz="http://www.waarderingskamer.nl/StUF/0312"
                xmlns:bg="http://www.egem.nl/StUF/sector/bg/0310"
                xmlns:s="http://www.egem.nl/StUF/StUF0301"
                version="1.0"
>
    <xsl:output method="xml" indent="yes" omit-xml-declaration="no"/>

    <!-- @see nl.b3p.brmo.loader.xml.WozXMLReader#NPS_PREFIX -->
    <xsl:variable name="PREFIX" select="'WOZ.NPS.'" />
    <xsl:param name="objectRef" select="'NL.WOZ:onbekend'"/>
    <xsl:param name="datum"/>
    <xsl:param name="volgordeNummer" select="'0'"/>
    <xsl:param name="soort" select="'woz'"/>
    <xsl:param name="rsgb-version" select="2.2"/>

    <xsl:template match="/">
        <root>
            <xsl:comment>
                <xsl:text>objectRef: </xsl:text>
                <xsl:value-of select="$objectRef"/>
                <xsl:text>, datum: </xsl:text>
                <xsl:value-of select="$datum"/>
                <xsl:text>, volgordeNummer: </xsl:text>
                <xsl:value-of select="$volgordeNummer"/>
                <xsl:text>, soort: </xsl:text>
                <xsl:value-of select="$soort"/>
            </xsl:comment>
            <data>
                <xsl:apply-templates select="//woz:object"/>
            </data>
        </root>
    </xsl:template>

<!--
    woz_deelobj (woz_deelobj_archief)

    # subject verwijzing
    woz_belang => subject
    -->

    <xsl:template name="object" match="woz:object">
        <woz_obj column-dat-beg-geldh="dat_beg_geldh" column-datum-einde-geldh="datum_einde_geldh">
            <nummer>
                <xsl:value-of select="woz:wozObjectNummer"/>
            </nummer>
            <dat_beg_geldh>
                <xsl:for-each select="s:tijdvakGeldigheid/s:beginGeldigheid">
                    <xsl:call-template name="date-numeric"/>
                </xsl:for-each>
            </dat_beg_geldh>
            <datum_einde_geldh>
                <xsl:for-each select="s:tijdvakGeldigheid/s:eindGeldigheid">
                    <xsl:call-template name="date-numeric"/>
                </xsl:for-each>
            </datum_einde_geldh>
            <gebruikscode>
                <xsl:value-of select="woz:gebruikscode"/>
            </gebruikscode>
            <grondoppervlakte>
                <xsl:value-of select="woz:grondoppervlakte"/>
            </grondoppervlakte>
            <soort_obj_code>
                <xsl:value-of select="woz:codeGebouwdOngebouwd"/>
            </soort_obj_code>
            <status>
                <xsl:value-of select="woz:statusWozObject"/>
            </status>
            <geom>
                <xsl:value-of select="woz:wozObjectGeometrie"/>
            </geom>
            <vastgestelde_waarde>
                <xsl:value-of select="woz:vastgesteldeWaarde"/>
            </vastgestelde_waarde>
            <waardepeildatum>
                <xsl:for-each select="woz:waardepeildatum">
                    <xsl:call-template name="date-numeric"/>
                </xsl:for-each>
            </waardepeildatum>
        </woz_obj>

        <!-- 1:1 relatie met woz_obj -->
        <woz_waarde column-dat-beg-geldh="waardepeildatum">
            <fk_1woz_nummer>
                <xsl:value-of select="woz:wozObjectNummer"/>
            </fk_1woz_nummer>
            <waardepeildatum>
                <xsl:for-each select="woz:waardepeildatum">
                    <xsl:call-template name="date-numeric"/>
                </xsl:for-each>
            </waardepeildatum>
            <status_beschikking>
                <xsl:value-of select="woz:isBeschiktVoor/woz:statusBeschikking"/>
            </status_beschikking>
            <toestandspeildatum>
                <xsl:value-of select="woz:toestandspeildatum"/>
            </toestandspeildatum>
            <vastgestelde_waarde>
                <xsl:value-of select="woz:vastgesteldeWaarde"/>
            </vastgestelde_waarde>
        </woz_waarde>

        <!-- conditioneel, alleen als er een brondocument identificatie is -->
        <xsl:if test="woz:isBeschiktVoor/woz:brondocument/bg:identificatie">
            <brondocument ignore-duplicates="yes">
                <!-- brondocument voor beschikking woz waarde -->
                <tabel>
                    <xsl:value-of select="'woz_waarde'"/>
                </tabel>
                <tabel_identificatie>
                    <xsl:value-of select="woz:wozObjectNummer"/>
                </tabel_identificatie>
                <identificatie>
                    <xsl:value-of select="woz:isBeschiktVoor/woz:brondocument/bg:identificatie"/>
                </identificatie>
                <datum>
                    <xsl:for-each select="woz:isBeschiktVoor/woz:brondocument/bg:datum">
                        <xsl:call-template name="date-numeric"/>
                    </xsl:for-each>
                </datum>
            </brondocument>
        </xsl:if>
    </xsl:template>

    <xsl:template name="aanduiding">
        <locaand_openb_rmte>
            <fk_sc_lh_opr_identifcode>
                <xsl:value-of select="woz:heeftAlsAanduiding/woz:gerelateerde/bg:identificatie"/>
            </fk_sc_lh_opr_identifcode>
            <fk_sc_rh_woz_nummer>
                <xsl:value-of select="woz:wozObjectNummer"/>
            </fk_sc_rh_woz_nummer>
            <locomschr>
                <xsl:value-of select="woz:heeftAlsAanduiding/woz:locatieOmschrijving"/>
            </locomschr>
        </locaand_openb_rmte>

        <locaand_adres>
            <fk_sc_lh_aoa_identif>
                <xsl:value-of select="woz:aanduidingWOZobject/bg:aoa.identificatie"/>
            </fk_sc_lh_aoa_identif>
            <fk_sc_rh_woz_nummer>
                <xsl:value-of select="woz:wozObjectNummer"/>
            </fk_sc_rh_woz_nummer>
            <locomschr>
                <xsl:value-of select="woz:aanduidingWOZobject/bg:locatieOmschrijving"/>
            </locomschr>
        </locaand_adres>
    </xsl:template>

    <xsl:template name="woz_belang">
        <!-- woz:object/woz:heeftBelanghebbende/ -->
        <woz_belang>
            <fk_sc_lh_sub_identif></fk_sc_lh_sub_identif>
            <fk_sc_rh_woz_nummer><xsl:value-of select="woz:wozObjectNummer"/></fk_sc_rh_woz_nummer>
            <aand_eigenaargebruiker><xsl:value-of select="woz:wozObjectNummer/woz:aanduidingEigenaarGebruiker"/></aand_eigenaargebruiker>
        </woz_belang>


<!--        call comfortPerson -->



    </xsl:template>

    <xsl:template name="comfortPerson">
        <xsl:param name="snapshot-date"/>
        <xsl:variable name="class">NATUURLIJK PERSOON</xsl:variable>
        <xsl:variable name="searchcol">
            <xsl:call-template name="getHash"><xsl:with-param name="bsn" select="ns2:PRS/ns2:bsn-nummer"/></xsl:call-template>
        </xsl:variable>
        <xsl:if test="searchcol != ''">
            <xsl:variable name="datum">
                <xsl:value-of select="substring($snapshot-date,0,5)"/><xsl:value-of select="'-'"/>
                <xsl:value-of select="substring($snapshot-date,5,2)"/><xsl:value-of select="'-'"/>
                <xsl:value-of select="substring($snapshot-date,7)"/>
            </xsl:variable>

            <comfort search-table="subject" search-column="identif" search-value="{$searchcol}" snapshot-date="{$datum}">
                <xsl:for-each select="ns2:PRS">
                    <xsl:call-template name="persoon" >
                        <xsl:with-param name="key" select="$searchcol"/>
                        <xsl:with-param name="class" select="$class"/>
                    </xsl:call-template>
                </xsl:for-each>
            </comfort>
        </xsl:if>
    </xsl:template>

    <!-- jjjj-mm-dd -> jjjjmmdd -->
    <xsl:template name="numeric-date">
        <xsl:value-of select="concat(substring(.,1,4),substring(.,6,2),substring(.,9,2))"/>
    </xsl:template>

    <!-- jjjjmmdd -> jjjj-mm-dd -->
    <xsl:template name="date-numeric">
        <xsl:choose>
            <xsl:when test="string-length(.) &gt; 9">
                <xsl:value-of select="concat(substring(.,1,4),'-',substring(.,5,2),'-',substring(.,7,2))"/>
            </xsl:when>
            <xsl:when test="string-length(.) &gt; 7">
                <xsl:value-of select="concat(substring(.,1,4),'-',substring(.,5,2),'-01')"/>
            </xsl:when>
            <xsl:when test="string-length(.) &gt; 5">
                <xsl:value-of select="concat(substring(.,1,4),'-01-01')"/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>

    <!-- zoek hash op in mapping tabel -->
    <xsl:template name="getHash">
        <xsl:param name="bsn" />
        <xsl:variable name="bsnwithprefix">
            <xsl:value-of select="$PREFIX" />
            <xsl:value-of select="$bsn" />
        </xsl:variable>
        <xsl:variable name="hashedbsn">
            <xsl:value-of select="$PREFIX" />
            <xsl:value-of select="/root/bsnhashes/*[name()=$bsnwithprefix]" />
        </xsl:variable>
        <xsl:value-of select="$hashedbsn" />
    </xsl:template>
</xsl:stylesheet>