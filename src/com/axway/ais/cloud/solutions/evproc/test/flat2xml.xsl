<xsl:stylesheet version="2.0" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://exslt.org/strings" extension-element-prefixes="str" exclude-result-prefixes="xs" xmlns:axway="http=//axway.com">
  <xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes" />
  <xsl:decimal-format decimal-separator="." />

  <xsl:param name="text-input" as="xs:string" />
  <xsl:param name="min-length" select="600"/>

  <xsl:function name="axway:DT8toISO" as="xs:dateTime" >
    <xsl:param name="iDate"/>

    <xsl:variable name="date" select="xs:date(concat(substring($iDate, 1, 4), '-', substring($iDate, 5, 2), '-', substring($iDate, 7, 2)))"/>
    <xsl:variable name="time" select="xs:time('00:00:00')"/>
    <xsl:value-of select="dateTime($date, $time)" />
  </xsl:function>

  <xsl:function name="axway:amount">
    <xsl:param name="iAmt"/>

    <xsl:sequence select="translate($iAmt, ',', '.')" />
  </xsl:function>

  <xsl:template match="/" name="main">
    <rows>
      <xsl:variable name="lines" as="xs:string*" select="tokenize(unparsed-text($text-input), '\r?\n')" />
      <xsl:for-each select="subsequence($lines, 1)">
        <xsl:if test="string-length(.) &gt; $min-length">
          <row>
            <xsl:attribute name="RECORD_STATUS">
              <xsl:value-of select="normalize-space(substring(., 601, 1))" />
            </xsl:attribute>
            <xsl:attribute name="INTERNAL_SCHEDULE_LINE_ID">
              <xsl:value-of select="normalize-space(substring(., 602, 10))" />
            </xsl:attribute>
            <xsl:attribute name="INTERNAL_SCHEDULE_DATE">
              <xsl:value-of select="normalize-space(format-dateTime(axway:DT8toISO(substring(., 612, 8)), '[Y0001]-[M01]-[D01]T[H01]:[m01]:[s01]Z'))" />
            </xsl:attribute>
            <xsl:attribute name="CURRENCY_CODE">
              <xsl:value-of select="normalize-space(substring(., 620, 3))" />
            </xsl:attribute>
            <xsl:attribute name="INTERNAL_SCHEDULE_TYPE">
              <xsl:value-of select="normalize-space(substring(., 623, 25))" />
            </xsl:attribute>
            <xsl:attribute name="AMOUNT_TYPE">
              <xsl:value-of select="normalize-space(substring(., 648, 50))" />
            </xsl:attribute>
            <xsl:attribute name="ACCOUNTING_SYSTEM">
              <xsl:value-of select="normalize-space(substring(., 698, 5))" />
            </xsl:attribute>
            <xsl:attribute name="CONTRACT_ID">
              <xsl:value-of select="normalize-space(substring(., 703, 20))" />
            </xsl:attribute>
            <xsl:attribute name="ASSET_ID">
              <xsl:value-of select="normalize-space(substring(., 723, 15))" />
            </xsl:attribute>
            <xsl:attribute name="VALUE">
              <xsl:value-of select="normalize-space(format-number(number(substring(., 738, 20)) div 100, '0.00'))" />
              <!-- <xsl:value-of select="normalize-space(string(number(substring(., 738, 20)) div 100))" /> -->
            </xsl:attribute>
          </row>
        </xsl:if>
      </xsl:for-each>
    </rows>

  </xsl:template>

</xsl:stylesheet>