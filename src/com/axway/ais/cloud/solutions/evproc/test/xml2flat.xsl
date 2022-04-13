<?xml version='1.0' encoding='utf-8'?>
<xsl:stylesheet version="3.0"
  xmlns:xs="http://www.w3.org/2001/XMLSchema"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns:str="http://exslt.org/strings"
  xmlns:math="http://www.w3.org/2005/xpath-functions/math"
  xmlns:axway="http://axway.com/" extension-element-prefixes="str" exclude-result-prefixes="xs">

  <xsl:output method="text" indent="no" omit-xml-declaration="yes" />
  <xsl:strip-space elements="*" />

  <xsl:variable name="newline">
    <xsl:text>&#10;</xsl:text>
  </xsl:variable>

  <xsl:variable name="some_spaces" select="'                                                                                                                                                                                                      '" />
  <xsl:variable name="some_zeroes" select="'000000000000000000000000000000'" />

  <xsl:function name="axway:fixed_size">
    <xsl:param name="sValue"/>
    <xsl:param name="iWidth"/>

    <xsl:sequence select="substring(concat($sValue,$some_spaces ), 1, $iWidth)"/>
  </xsl:function>

  <xsl:function name="axway:fixed_size_amount">
    <xsl:param name="sValue"/>
    <xsl:param name="nDecimal"/>
    <xsl:param name="iWidth"/>

    <xsl:variable name="amount" select="number($sValue) * math:exp10($nDecimal)"/>
    <xsl:choose>
      <xsl:when test="string($amount) != 'NaN'">
        <xsl:sequence select="format-number($amount, concat(substring($some_zeroes, 1, $iWidth), ';-', substring($some_zeroes, 1, $iWidth -1)))"/>
      </xsl:when>
      <xsl:otherwise>
        <xsl:sequence select="substring($some_zeroes, 1, $iWidth)"/>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:function>

  <xsl:function name="axway:filler">
    <xsl:param name="iWidth"/>

    <xsl:sequence select="substring($some_spaces, 1, $iWidth)"/>
  </xsl:function>

  <xsl:template match="pivot/transaction">
    <xsl:value-of select="axway:fixed_size(./entete/axway_type_cre, 25)" />
    <xsl:value-of select="axway:fixed_size(./entete/axway_code_enregistrement, 25)" />
    <xsl:value-of select="axway:fixed_size(./entete/axway_code_instance, 25)" />
    <xsl:value-of select="axway:fixed_size(./entete/axway_code_annulation, 1)" />
    <xsl:value-of select="axway:fixed_size(./entete/axway_code_lot, 34)" />
    <xsl:value-of select="axway:fixed_size(./entete/axway_code_compostage, 25)" />
    <xsl:value-of select="axway:fixed_size(./entete/axway_dar, 8)" />
    <xsl:value-of select="axway:filler(157)" />
    <xsl:value-of select="axway:fixed_size(./ref_doc_no, 25)" />
    <xsl:value-of select="axway:fixed_size(./type_doc, 25)" />
    <xsl:value-of select="axway:fixed_size(./ref_appli_source, 25)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/origine_flux, 25)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/item_num, 4)" />
    <xsl:value-of select="axway:fixed_size(./company, 4)" />
    <xsl:value-of select="axway:fixed_size(./journal/@id, 3)" />
    <xsl:value-of select="axway:fixed_size(./mov_dpt, 4)" />
    <xsl:value-of select="axway:fixed_size(./mov_sysdate, 8)" />
    <xsl:value-of select="axway:fixed_size(./mov_date_mov, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_invoice, 13)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_acct, 8)" />
    <xsl:value-of select="axway:fixed_size_amount(./journal/ecriture/mov_db, 2, 14)" />
    <xsl:value-of select="axway:fixed_size_amount(./journal/ecriture/mov_cr, 2, 14)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_sense, 1)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_cur, 3)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_name, 32)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_initial, 4)" />
    <!-- <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_transac, 4)" /> -->
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_ledger, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_a1, 12)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_a2, 12)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_mode, 2)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_code, 2)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_date_ech, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_date_init, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_type, 1)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_tax_code, 5)" />
    <xsl:value-of select="axway:fixed_size_amount(./journal/ecriture/mov_tax_basis, 2, 14)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_a3, 12)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_proj, 12)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_agent, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_order, 17)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_seq, 6)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_led_type, 4)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_cheque, 10)" />
    <xsl:value-of select="axway:fixed_size_amount(./journal/ecriture/mov_qty, 3, 14)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_date_1, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_date_2, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_extrait, 8)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_day_late, 4)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_ident_doc, 25)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_mar_zic7, 32)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_mar_zic8, 32)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_mar_zic9, 32)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_mar_zic10, 32)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/mov_lot_type, 12)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/motif_extourne, 32)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/sous_traitance_directe, 5)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/objet_matiere, 20)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/ref_facture_avoir, 13)" />
    <xsl:value-of select="axway:fixed_size(./journal/ecriture/flag_avoir_multi_facture, 1)" />
    <xsl:value-of select="$newline"/>
  </xsl:template>


</xsl:stylesheet>