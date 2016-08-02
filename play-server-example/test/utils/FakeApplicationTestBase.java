package utils;

import static play.inject.Bindings.bind;
import guiceExamples.cache.CacheProvider;
import play.Application;
import play.Mode;
import play.inject.Injector;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

/**
 * Overcomes cache problem in unit tests
 * @author aschneider
 *
 */
public abstract class FakeApplicationTestBase extends WithApplication {

	@Override
	protected Application provideApplication() {
		System.out.println("FakeApplicationTestBase.provideApplication ...");
		return new GuiceApplicationBuilder()
			.overrides(bind(CacheProvider.class).to(FakeCacheProvider.class))
			.in(Mode.TEST)
			.build();
	}
	
	protected Injector getInjector() {
		return app.injector();
	}
	
}
