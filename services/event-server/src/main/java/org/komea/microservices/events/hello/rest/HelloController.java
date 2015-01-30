package org.komea.microservices.events.hello.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	/**
	 * Returns hello
	 *
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public String helloServer() {
		return "Komea Eventory Server";

	}

}
