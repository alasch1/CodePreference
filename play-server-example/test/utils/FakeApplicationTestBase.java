package utils;

import static play.inject.Bindings.bind;
import guiceExamples.cache.CacheProvider;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.test.WithApplication;

/**
 * Overcomes cacxhe problem in unit tests
 * @author aschneider
 *
 */
public abstract class FakeApplicationTestBase extends WithApplication {

	@Override
	protected Application provideApplication() {
		return new GuiceApplicationBuilder()
			.overrides(bind(CacheProvider.class).to(FakeCacheProvider.class))
			.overrides(bind(HpaSessionProvider.class).to(HpaSessionInBrowser.class))
			.in(Mode.TEST)
			.build();
	}
	
}
