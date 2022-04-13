package com.axway.ais.cloud.solutions.evproc.utils;

import java.io.File;
import java.util.Optional;

import javax.xml.transform.stream.StreamSource;

import com.axway.ais.cloud.solutions.evproc.transformXMLOperation.XSLtransformOperation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.Xslt30Transformer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;

public class DoTransformation {

  static Logger LOGGER = LogManager.getLogger(XSLtransformOperation.class.getName());

  private static Optional<String> getFileExtension(String filename) {
    return Optional.ofNullable(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1));
  }

  public static Boolean doTransform(String inputFile, String outputFile, String xslFile) throws Exception {
    String textFile = "";

    File output = new File(outputFile);
    File xsl = new File(xslFile);

    if (!getFileExtension(inputFile).get().equalsIgnoreCase("xml")) {
      textFile = inputFile;
      inputFile = "";
    }

    LOGGER.info("[*] Start XSL processing---------");

    Processor processor = new Processor(false);
    XsltCompiler compiler = processor.newXsltCompiler();

    if (!textFile.isEmpty()) {
      QName qname = new QName("text-input");
      XdmValue value = new XdmAtomicValue(textFile);
      compiler.setParameter(qname, value);
    }

    XsltExecutable stylesheet = compiler.compile(new StreamSource(xsl));
    Serializer out = processor.newSerializer(output);
    Xslt30Transformer transformer = stylesheet.load30();

    if (!textFile.isEmpty()) {
        transformer.callTemplate(new QName("main"), out);
    } else if (!inputFile.isEmpty()) {
        transformer.transform(new StreamSource(new File(inputFile)), out);
    } else {
      throw new Exception("Input file or Text file must be specified");
    }

    LOGGER.info("[*] Done XSL processing---------");

    return true;

  }
}
