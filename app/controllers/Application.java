package controllers;

import org.springframework.beans.factory.annotation.Autowired;

import play.mvc.Controller;
import play.mvc.Result;
import services.ServiceSample;
import views.html.index;

@org.springframework.stereotype.Controller
public class Application extends Controller {

	private ServiceSample service;

	@Autowired
	public Application(ServiceSample service) {
		this.service = service;
	}

	public Result index() {
		return ok(index.render());
	}

	public Result sleep() {
		Integer duration = Integer.valueOf(request().getQueryString("duration"));
		this.service.sleepService(duration);
		return ok();
	}

	public Result random() {

		try {
			this.service.randomException();
		} catch (Exception e) {
			// Do nothing...
		}

		return ok();
	}

}
