package com.axway.ais.cloud.solutions.evproc.transformXMLOperation;

import static com.axway.ais.evproc.api.EventHeader.DATA_FILE;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.axway.ais.evproc.api.EventHeader;
import com.axway.ais.cloud.solutions.evproc.utils.DoTransformation;
import com.axway.ais.evproc.api.*;
import com.axway.ais.evproc.operation.AbstractAsyncOperation;
import com.axway.ais.rs.api.AsyncTaskResult;
import com.axway.ais.rs.api.AsyncRequest;
import com.axway.ais.rs.api.ExecutionError;

import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component(XSLtransformOperation.FACTORY_NAME)
@Scope("prototype")
public class XSLtransformOperation extends AbstractAsyncOperation {
	static Logger LOGGER = LogManager.getLogger(XSLtransformOperation.class.getName());

	public static final String FACTORY_NAME = "operation.XSLtransformOperation";
	public static final String FUNCTION = "XSLtransformOperation";

	public static final String RESULT_TYPE = "FileResult";
	public static final String SGORTLONGTERM_RESULT_TYPE = "XSLtransformResult";
	// -- Arguments
	private static final String ARGUMENT_EVENT = "event";
	private static final String ARGUMENT_OUTPUT_FILENAME = "outputfilename";
	private static final String ARGUMENT_OUTPUT_FLOW = "outputFlow";
	public static final String ARG_OUTPUT_DIRECTORY = "outputDirectory";
	private static final String ARGUMENT_XSLFILE = "xslFile";
	private static final String ARGUMENT_TEXTFILE = "textFile";

	@Override
	public Event execute(Event event, Map<String, Object> arguments) {

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Entering XSLtransform operation: " + getFunctionName());
		}

		// -- Event is mandatory
		Event triggerEvent = (Event) arguments.get(XSLtransformOperation.ARGUMENT_EVENT);
		if (triggerEvent == null) {
			LOGGER.error("Cannot process operation, the event for process XSLtransform is missing");
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("Input event is: " + triggerEvent.toString());
		}

		XSLtransformRequest request = new XSLtransformRequest();
		String dataFile = event.getHeader(DATA_FILE.getName());

		// -- Constructing the request
		if (dataFile == null) {
			return handleEarlyError(event, "err.UpdateRates.noInputFiles", null);
		} else {
			request.setInputfilename(dataFile);

		}

		request.setEvent(triggerEvent);

		if (arguments.get(ARGUMENT_XSLFILE) != null) {
			request.setXslFile(arguments.get(ARGUMENT_XSLFILE).toString());
		} else {
			int dotIndex = dataFile.lastIndexOf('.');
			String xslFile = dataFile.substring(0, dotIndex) + ".xsl";

			File file = new File(xslFile);

			if (file.exists()) {
				request.setXslFile(xslFile);
			} else
				request.setXslFile(null);

		}
		// -- Getting the output directory
		if (arguments.get(ARG_OUTPUT_DIRECTORY) != null) {
			request.setOutputDirectory(arguments.get(ARG_OUTPUT_DIRECTORY).toString());
		}

		if (arguments.get(XSLtransformOperation.ARGUMENT_OUTPUT_FLOW) != null) {
			request.setOutputFlow(arguments.get(XSLtransformOperation.ARGUMENT_OUTPUT_FLOW).toString());
		}
		int dotIndex = (FilenameUtils.getName(dataFile)).lastIndexOf('.');

		request.setOutputfilename(
				request.getOutputDirectory() + (FilenameUtils.getName(dataFile)).substring(0, dotIndex) + ".term");
		setRequest(request);

		return null;
	}

	@Override
	public AsyncTaskResult call() throws Exception {

		XSLtransformResult result = new XSLtransformResult();
		XSLtransformRequest request = getRequest();

		String inputFile = request.getInputfilename();
		String outputFile = request.getOutputfilename();
		String xslFile = request.getXslFile();

		try {
			new DoTransformation().doTransform(inputFile, outputFile, xslFile);
			result.setOutputFilename(outputFile);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			ExecutionError ee = new ExecutionError();
			ee.setMessage(e.getMessage());
			result.setError(ee);
			result.setExitCode(-1);

		} catch (NoClassDefFoundError e) {
			LOGGER.error(e.getMessage());
			ExecutionError ee = new ExecutionError();
			ee.setMessage(e.getMessage());
			result.setError(ee);
			result.setExitCode(-1);

		}

		if (request.getOutputFlow() != null)
			result.setNextFlow(request.getOutputFlow());

		return result;
	}

	@Override
	public void serviceFinished(AsyncRequest theRequest, AsyncTaskResult theResult) {

		XSLtransformRequest request = (XSLtransformRequest) theRequest;
		XSLtransformResult result = (XSLtransformResult) theResult;

		List<Event> resultEvents = new ArrayList<Event>();
		resultEvents.add(getXSLtransformEvent(request, result));

		LOGGER.debug("XSLtransformRequest Control finished: {} ", resultEvents);

		eventProcessor.dispatch(resultEvents.toArray(new Event[resultEvents.size()]));
	}

	private Event getXSLtransformEvent(XSLtransformRequest request, XSLtransformResult result) {
		EventBuilder evtBuilder = EventBuilder.createResult(getEvent(request)).setType(RESULT_TYPE);

		ExecutionError error = result.getError();
		evtBuilder.setHeader(EventHeader.SUCCESS, (error == null));

		if (request.getXslFile() != null) {
			evtBuilder.setHeader(EventHeader.PROPERTY_FILE, request.getXslFile());
			evtBuilder.setHeader(EventHeader.PROPERTY_FILE_NAME, FilenameUtils.getName(request.getXslFile()));
		}

		if (error != null) {
			evtBuilder.setHeader(EventHeader.ERROR_CODE, error.getCode());
			evtBuilder.setHeader(EventHeader.ERROR_MESSAGE, error.getMessage());

		}
		evtBuilder.setHeader(EventHeader.CREATION_DATE, new Date());

		evtBuilder.setHeader(EventHeader.DATA_FILE, result.getOutputFilename());
		evtBuilder.setHeader(EventHeader.FUNCTION, this.getFunctionName());

		if (result.getNextFlow() != null && !result.getNextFlow().isEmpty()) {
			evtBuilder.setHeader(EventHeader.FLOW, result.getNextFlow());
		}
		return evtBuilder.build();
	}

	@Override
	protected String getFunctionName() {
		// TODO Auto-generated method stub
		return FUNCTION;
	}

	@Override
	public String getImplementationId() {
		// TODO Auto-generated method stub
		return FACTORY_NAME;
	}

	@Override
	protected String getResultType() {
		// TODO Auto-generated method stub
		return RESULT_TYPE;
	}
}
