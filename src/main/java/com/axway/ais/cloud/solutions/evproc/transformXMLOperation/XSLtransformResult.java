package com.axway.ais.cloud.solutions.evproc.transformXMLOperation;

import com.axway.ais.core.async.DefaultAsyncTaskResult;

public class XSLtransformResult extends DefaultAsyncTaskResult {
	private String nextFlow;
	private int exitCode;
	private String outputFilename;
	
	public String getNextFlow() {
		return nextFlow;
	}

	public void setNextFlow(String nextFlow) {
		this.nextFlow = nextFlow;
	}

	public int getExitCode() {
		return exitCode;
	}

	public void setExitCode(int exitCode) {
		this.exitCode = exitCode;
	}

	public String getOutputFilename() {
		return outputFilename;
	}

	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}


}
