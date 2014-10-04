package controllers;

import org.springframework.beans.factory.annotation.Autowired;

import play.mvc.Controller;
import play.mvc.Result;
import services.SomeService;
import views.html.index;

@org.springframework.stereotype.Controller
public class Application extends Controller {

	private SomeService service;

	@Autowired
	public Application(SomeService service) {
		this.service = service;
	}

	public Result index() {
		
		int i = (int) (Math.random() * 500);
		
		for (int j = 0; j < i; j++) {
			service.doSomething();	
		}
		
		return ok(index.render("Your new application is ready."));
	}
}
