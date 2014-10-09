package services;

import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class ServiceSample {

	
	@HystrixCommand(fallbackMethod = "sleepServiceFallback")
	public void sleepService(int duration) {

		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// Do nothing
		}

	}
	
	public void sleepServiceFallback(int duration) {
		// your fallback logic here
	}

	
	
	
	
	@HystrixCommand(fallbackMethod = "randomExceptionFallback")
	public void randomException() {

		int rnd = (int) (Math.random() * 100);

		if (rnd < 70) {
			throw new RuntimeException("Random exception");
		}

	}

	public void randomExceptionFallback() {
		// your fallback logic here
	}

}
