package controllers;

import hystrix.stream.HystrixEventSource;
import hystrix.stream.HystrixStreamTask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PreDestroy;

import play.mvc.Controller;
import play.mvc.Result;

@org.springframework.stereotype.Controller
public class HystrixController extends Controller {

	private ExecutorService executorService = Executors.newCachedThreadPool();
	
	@PreDestroy
	public void preDestroy() {
		executorService.shutdown();
	}
	
	public Result index() {

		final int delay = getDelayParameter();
		final HystrixEventSource eventSource = new HystrixEventSource();
		
		HystrixStreamTask hystrixStreamTask = new HystrixStreamTask(eventSource, delay);
		executorService.execute(hystrixStreamTask);

		return ok(eventSource);
	}

	
	private int getDelayParameter() {
		int delay = 1000;
           try {
        	   String delayParameter = request().getQueryString("delay");
               if (delayParameter != null) {
                   delay = Integer.parseInt(delayParameter);
               }
           } catch (Exception e) {
               // ignore if it's not a number, using default value
           }
		return delay;
	}

}
