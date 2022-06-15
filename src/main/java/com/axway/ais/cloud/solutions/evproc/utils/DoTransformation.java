package com.axway.ais.cloud.solutions.evproc.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmValue;
import net.sf.saxon.s9api.Xslt30Transformer;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;

public class DoTransformation {

  private Optional<String> getFileExtension(String filename) {
    return Optional.ofNullable(filename)
        .filter(f -> f.contains("."))
        .map(f -> f.substring(filename.lastIndexOf(".") + 1));
  }

  public synchronized Boolean doTransform(String inputFile, String outputFile, String xslFile)
      throws Exception {
    String textFile = "";

    File xsl = new File(xslFile);

    File outputTmp = File.createTempFile("XSLTransform", "rmp");
    outputTmp.deleteOnExit();

    if (!getFileExtension(inputFile).get().equalsIgnoreCase("xml")) {
      textFile = inputFile;
      inputFile = "";
    }

    Processor processor = new Processor(false);
    XsltCompiler compiler = processor.newXsltCompiler();

    if (!textFile.isEmpty()) {
      QName qname = new QName("text-input");
      XdmValue value = new XdmAtomicValue(textFile);
      compiler.setParameter(qname, value);
    }

    XsltExecutable stylesheet = compiler.compile(new StreamSource(xsl));
    Xslt30Transformer transformer = stylesheet.load30();

    OutputStream os = new FileOutputStream(outputTmp);
    Serializer out = processor.newSerializer(os);

    if (!textFile.isEmpty()) {
      transformer.callTemplate(new QName("main"), out);
    } else if (!inputFile.isEmpty()) {
      transformer.transform(new StreamSource(new File(inputFile)), out);
    } else {
      throw new Exception("Input file or Text file must be specified");
    }

    os.flush();
    os.close();

    Files.move(Paths.get(outputTmp.getAbsolutePath()), Paths.get(outputFile), StandardCopyOption.ATOMIC_MOVE);

    return true;
  }
}
