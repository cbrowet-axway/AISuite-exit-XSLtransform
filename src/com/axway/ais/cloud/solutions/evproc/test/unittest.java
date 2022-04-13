package com.axway.ais.cloud.solutions.evproc.test;

import com.axway.ais.cloud.solutions.evproc.utils.DoTransformation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class unittest {
  
  public static void main(String[] args) {

    Logger LOGGER = LogManager.getLogger(unittest.class.getName());

    /* Text to XML */
		String inputFile = "flat2xml.txt";
		String outputFile = "src/com/axway/ais/cloud/solutions/evproc/test/flat2xml.xml";
		String xslFile = "src/com/axway/ais/cloud/solutions/evproc/test/flat2xml.xsl";

		try {
			DoTransformation.doTransform(inputFile, outputFile, xslFile);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		} catch (NoClassDefFoundError e) {
			LOGGER.error(e.getMessage());
		}

    /* XML to Text */
    inputFile = "src/com/axway/ais/cloud/solutions/evproc/test/xml2flat.xml";
		outputFile = "src/com/axway/ais/cloud/solutions/evproc/test/xml2flat.txt";
		xslFile = "src/com/axway/ais/cloud/solutions/evproc/test/xml2flat.xsl";

    try {
			DoTransformation.doTransform(inputFile, outputFile, xslFile);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		} catch (NoClassDefFoundError e) {
			LOGGER.error(e.getMessage());
		}

	}

}
