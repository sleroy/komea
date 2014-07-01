package org.komea.product.backend.csv.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;

import au.com.bytecode.opencsv.CSVWriter;

@Component
public class CsvMessageConverter extends AbstractHttpMessageConverter<CSVExport> {
	
	public static final MediaType MEDIA_TYPE = new MediaType("text", "csv", Charset.forName("utf-8"));
	
	public CsvMessageConverter() {
		super(MEDIA_TYPE);
	}
	
	public CsvMessageConverter(MediaType... supportedMediaTypes) {
		super(supportedMediaTypes);	
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return CSVExport.class.isAssignableFrom(clazz);
	}

	@Override
	protected CSVExport readInternal(Class<? extends CSVExport> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		return null;
	}

	@Override
	protected void writeInternal(CSVExport _response, HttpOutputMessage _output)
			throws IOException, HttpMessageNotWritableException {
		_output.getHeaders().setContentType(MEDIA_TYPE);
		//_output.getHeaders().set("Content-Disposition", "attachment; filename=\"" + _response.getFilename() + "\"");
		OutputStream out = _output.getBody();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(out), ',', CSVWriter.NO_QUOTE_CHARACTER);
		writer.writeAll(_response.convertToStringList());
	    writer.close();
	}

}
