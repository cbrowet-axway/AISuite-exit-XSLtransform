package com.axway.ais.cloud.solutions.evproc.transformXMLOperation;

import com.axway.ais.evproc.operation.AbstractServiceRequest;

public class XSLtransformRequest extends AbstractServiceRequest {
	private String objectTypeName;
	private String period;
	private String componentName;
	private String outputFlow;
	private String inputfilename;
	private String outputfilename;
	private String outputDirectory;
	private String inputDirectory;
	private String xslFile;

	
	public String getObjectTypeName() {
		return objectTypeName;
	}
	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getOutputFlow() {
		return outputFlow;
	}
	public void setOutputFlow(String outputFlow) {
		this.outputFlow = outputFlow;
	}
	@Override
	public String toString() {
		return "XSLtransform [objectTypeName=" + objectTypeName
				+ ", period=" + period + ", componentName=" + componentName
				+ ", outputFlow=" + outputFlow + ", inputfilename="
				+ inputfilename + ", outputfilename=" + outputfilename + ", xslfilename=" + xslFile+ "]";
	}
	public String getInputfilename() {
		return inputfilename;
	}
	public void setInputfilename(String inputfilename) {
		this.inputfilename = inputfilename;
	}
	public String getOutputfilename() {
		return outputfilename;
	}
	public void setOutputfilename(String outputfilename) {
		this.outputfilename = outputfilename;
	}
	public String getOutputDirectory() {
		return outputDirectory;
	}
	public void setOutputDirectory(String outputDirectory) {
		this.outputDirectory = outputDirectory;
	}
	public String getInputDirectory() {
		return inputDirectory;
	}
	public void setInputDirectory(String inputDirectory) {
		this.inputDirectory = inputDirectory;
	}
	public String getXslFile() {
		return xslFile;
	}
	public void setXslFile(String xslFile) {
		this.xslFile = xslFile;
	}

}
