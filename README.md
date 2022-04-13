# Generic XSLT transformation exit

## Parameters

- InputFilename: source file for the transformation
  - If the file has anything else than a ".xml" extension, a flat file to XML transformation is used.
- OutputFilename: output file, text or xml
- XslFile: XSLT to be applied

## Specifics for a flat file transformation

- The XSLT must have a template named "main", which will be the entry point

```xml
<xsl:template match="/" name="main">
```

- The XSLT must have a "text-input" parameter that will receive the flat file to be read

```xml
  <xsl:param name="text-input" as="xs:string" />
```