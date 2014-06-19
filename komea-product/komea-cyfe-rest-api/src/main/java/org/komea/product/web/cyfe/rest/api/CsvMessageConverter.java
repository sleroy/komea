package org.komea.product.web.cyfe.rest.api;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.komea.product.web.cyfe.rest.utils.CsvResponse;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import au.com.bytecode.opencsv.CSVWriter;

public class CsvMessageConverter extends AbstractHttpMessageConverter<CsvResponse> {
	
	public static final MediaType MEDIA_TYPE = new MediaType("text", "csv", Charset.forName("utf-8"));
	
	public CsvMessageConverter() {
		super(MEDIA_TYPE);
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return CsvResponse.class.equals(clazz);
	}

	@Override
	protected CsvResponse readInternal(Class<? extends CsvResponse> clazz,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void writeInternal(CsvResponse _response, HttpOutputMessage _output)
			throws IOException, HttpMessageNotWritableException {
		_output.getHeaders().set("Accept", "text/csv");
		_output.getHeaders().setContentType(MEDIA_TYPE);
		_output.getHeaders().set("Content-Disposition", "attachment; filename=\"" + _response.getFilename() + "\"");
		OutputStream out = _output.getBody();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(out), '\u0009');
		writer.writeAll(_response.getRecords());
	    writer.close();
	}

}
