package controllers;

import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public class HystrixStream extends Controller {

	public Result index() {
		
		Request request = request();
		Response response = response();
		
		return null;
	}
	
	
}
