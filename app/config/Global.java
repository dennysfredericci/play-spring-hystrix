package config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import play.Application;
import play.GlobalSettings;

public class Global extends GlobalSettings {

	private ApplicationContext ctx;

	@Override
	public void onStart(Application app) {

	}

	@Override
	public <A> A getControllerInstance(Class<A> clazz) {
		if (ctx == null) {
			ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		}
		return ctx.getBean(clazz);
	}

}