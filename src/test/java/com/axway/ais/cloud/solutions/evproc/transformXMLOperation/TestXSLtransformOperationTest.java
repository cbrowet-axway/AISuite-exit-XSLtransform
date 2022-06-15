package com.axway.ais.cloud.solutions.evproc.transformXMLOperation;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import com.axway.ais.cloud.solutions.evproc.utils.DoTransformation;

public class TestXSLtransformOperationTest {

	@Test
	@DisplayName("Flat file to XML")
	public void flat2xml() {

		/* Text to XML */

		Path resourceDirectory = Paths.get("src","test","resources");
		URI absoluteURI = resourceDirectory.toFile().toURI();
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		System.out.println(absolutePath);

		File outputPath = null;
		try {
			outputPath = File.createTempFile("test-flat2xml", ".xml");
			outputPath.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		String outputFile = outputPath.getAbsolutePath();

		String inputFile = absoluteURI + "/flat2xml.txt";
		String xslFile = absolutePath + "/flat2xml.xsl";
		String expectedFile = absolutePath + "/flat2xml_expected.xml";


		try {
			new DoTransformation().doTransform(inputFile.toString(), outputFile, xslFile);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());

		} catch (NoClassDefFoundError e) {
			fail(e.getMessage());
		}
		
		final File expected = new File(expectedFile);		
    final File output = new File(outputFile);

		try {
			assertEquals(
					FileUtils.readFileToString(expected, "utf-8"),
					FileUtils.readFileToString(output, "utf-8"),
					"The files differ!");
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	@DisplayName("XML to Flat file")
	public void xml2flat(@TempDir Path tempDir) {

		/* Text to XML */

		Path resourceDirectory = Paths.get("src","test","resources");
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		System.out.println(absolutePath);

		File outputPath = null;
		try {
			outputPath = File.createTempFile("test-xml2flat", ".txt");
			outputPath.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		String outputFile = outputPath.getAbsolutePath();

		String inputFile = absolutePath + "/xml2flat.xml";
		String xslFile = absolutePath + "/xml2flat.xsl";
		String expectedFile = absolutePath + "/xml2flat_expected.txt";


		try {
			new DoTransformation().doTransform(inputFile.toString(), outputFile, xslFile);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());

		} catch (NoClassDefFoundError e) {
			fail(e.getMessage());
		}
		
		final File expected = new File(expectedFile);		
    final File output = new File(outputFile);

		try {
			assertEquals(
					FileUtils.readFileToString(expected, "utf-8"),
					FileUtils.readFileToString(output, "utf-8"),
					"The files differ!");
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

	@Test
	@DisplayName("XML to Flat file 2")
	public void xml2flat2(@TempDir Path tempDir) {

		/* Text to XML */

		Path resourceDirectory = Paths.get("src","test","resources");
		String absolutePath = resourceDirectory.toFile().getAbsolutePath();
		System.out.println(absolutePath);

		File outputPath = null;
		try {
			outputPath = File.createTempFile("test-xml2flat2", ".txt");
			outputPath.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		String outputFile = outputPath.getAbsolutePath();

		String inputFile = absolutePath + "/xml2flat2.xml";
		String xslFile = absolutePath + "/xml2flat2.xsl";
		String expectedFile = absolutePath + "/xml2flat2_expected.txt";


		try {
			new DoTransformation().doTransform(inputFile.toString(), outputFile, xslFile);

		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());

		} catch (NoClassDefFoundError e) {
			fail(e.getMessage());
		}
		
		final File expected = new File(expectedFile);		
    final File output = new File(outputFile);

		try {
			assertEquals(
					FileUtils.readFileToString(expected, "utf-8"),
					FileUtils.readFileToString(output, "utf-8"),
					"The files differ!");
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}

}
