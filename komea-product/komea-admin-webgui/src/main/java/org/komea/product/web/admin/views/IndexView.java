package org.komea.product.web.admin.views;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.komea.product.service.esper.IEsperAlertSnapshotService;
import org.komea.product.service.esper.IEsperQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexView {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IndexView.class);

	public static Throwable getRootCause(final Throwable _throwable) {

		Throwable throwable = _throwable;

		Throwable cause;
		while ((cause = throwable.getCause()) != null) {
			throwable = cause;
		}
		return throwable;
	}

	@Autowired
	private IEsperQueryService queryService;

	// http://stackoverflow.com/questions/804581/spring-mvc-controller-redirect-to-previous-page
	@Autowired
	private IEsperAlertSnapshotService service;

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = { "/error.html" })
	public String customError(final HttpServletRequest request,
			final HttpServletResponse response, final Model model) {

		// retrieve some useful information from the request
		final Integer statusCode = (Integer) request
				.getAttribute("javax.servlet.error.status_code");
		final Throwable throwable = (Throwable) request
				.getAttribute("javax.servlet.error.exception");
		// String servletName = (String)
		// request.getAttribute("javax.servlet.error.servlet_name");
		final String exceptionMessage = getExceptionMessage(throwable,
				statusCode);
		if (throwable != null) {
			LOGGER.error(throwable.getMessage(), throwable);
		}
		String requestUri = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}

		final StringWriter message = new StringWriter();
		message.write(MessageFormat.format(
				"{0} returned for {1} with message {2}", statusCode,
				requestUri, exceptionMessage));
		if (throwable != null) {
			throwable.printStackTrace(new PrintWriter(message));
		}

		model.addAttribute("errorMessage", message.toString());
		model.addAttribute("errorCode", statusCode);
		model.addAttribute("requestURI", requestUri);
		return "error";
	}

	@PostConstruct
	public void init() {
		queryService.registerQuery("select * from Alert.win:time(60 sec)",
				"ACTIVITY_VIEW_PAGE");
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/metrics", "" })
	public ModelAndView requestMetricsPage() {

		final ModelAndView modelAndView = new ModelAndView("metrics");
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = { "/", "" })
	public ModelAndView requestPage() {

		final ModelAndView modelAndView = new ModelAndView("index");
		modelAndView.addObject("alerts", service.getDefaultView());
		return modelAndView;
	}

	private String getExceptionMessage(final Throwable throwable,
			final Integer statusCode) {

		if (throwable != null) {
			return getRootCause(throwable).getMessage();
		}
		final HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		return httpStatus.name();
	}
}
