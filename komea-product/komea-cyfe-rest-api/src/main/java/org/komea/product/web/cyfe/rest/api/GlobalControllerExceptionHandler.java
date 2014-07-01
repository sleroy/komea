package org.komea.product.web.cyfe.rest.api;

import javax.servlet.http.HttpServletResponse;

import org.komea.product.backend.exceptions.EntityNotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundException;
import org.komea.product.backend.exceptions.KPINotFoundRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
	
	@ExceptionHandler({ KPINotFoundException.class, EntityNotFoundException.class, KPINotFoundRuntimeException.class })
    @ResponseBody
    public void handleNotFoundException(final Throwable _ex, HttpServletResponse _response) {
		
		LOGGER.error(_ex.getMessage(), _ex);
		_response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		
	}
	
	@ExceptionHandler({ MissingServletRequestParameterException.class, TypeMismatchException.class })
    @ResponseBody
    public void handleBadRequest(final Throwable _ex, HttpServletResponse _response) {
		
		LOGGER.error(_ex.getMessage(), _ex);
		_response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		
	}
	
	@ExceptionHandler
    @ResponseBody
    public void handleException(final Throwable _ex, HttpServletResponse _response) {
		
		LOGGER.error(_ex.getMessage(), _ex);
		_response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		
	}
	
}
