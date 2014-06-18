package org.komea.product.web.cyfe.rest.api;

import java.util.Date;
import java.util.List;

import org.komea.product.database.enums.Severity;
import org.komea.product.model.timeserie.TimeScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/cyfe")
public class CyfeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CyfeController.class);
	
	@RequestMapping(method = RequestMethod.GET, value = "/value/{kpiKey}/{entityKey}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public String getValue(@PathVariable(value="kpiKey") final String _kpiKey, 
			@PathVariable(value="entityKey") final String _entityKey,
			@RequestParam(value="timescale", required=false) final TimeScale _timeScale,
			@RequestParam(value="date", required=false) @DateTimeFormat(pattern="ddmmyyyy") Date _date,
			@RequestParam(value="goal", required=false) final Double _goal) {
		
		LOGGER.debug("Cyfe API getValue with params : {}, {}", _kpiKey, _entityKey);
		return "";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/values", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public String getValues(@RequestParam(value="kpiKeys") List<String> kpiKeys, 
			@RequestParam(value="entityKeys", required=false) List<String> entityKeys) {
		
		return "";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/serie/{kpiKey}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public String getSerie(@PathVariable(value="kpiKey") final String kpiKey,
			@RequestParam(value="entityKeys", required=false) List<String> entityKeys,
			@RequestParam(value="timescale", required=false) final TimeScale timeScale,
			@RequestParam(value="since", required=false) @DateTimeFormat(pattern="ddmmyyyy") Date since,
			@RequestParam(value="colors", required=false) List<String> colors,
			@RequestParam(value="types", required=false) List<String> types) {
		
		return "";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/pie/{kpiKey}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public String getPie(@RequestParam(value="kpiKey") final String kpiKey,
			@RequestParam(value="entityKeys", required=false) List<String> entityKeys,
			@RequestParam(value="colors", required=false) List<String> colors) {
		
		return "";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/events/{entityType}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public String getEvents(@PathVariable(value="entityType") final String entityType,
			@RequestParam(value="severityMin") final Severity severityMin,
			@RequestParam(value="sizeMax") final Integer sizeMax,
			@RequestParam(value="eventKeys") List<String> eventKeys,
			@RequestParam(value="entityKeys", required=false) List<String> entityKeys) {
		
		return "";
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cohort/{kpiKey}", produces = "text/csv; charset=utf-8")
	@ResponseBody
	public String getCohort(@PathVariable(value="kpiKey") final String kpiKey,
			@RequestParam(value="entityKeys", required=false) List<String> entityKeys,
			@RequestParam(value="timescale") final TimeScale timeScale,
			@RequestParam(value="since") @DateTimeFormat(pattern="ddmmyyyy") Date since,
			@RequestParam(value="goal", required=false) final String goal) {
		
		return "";
		
	}
	
}
