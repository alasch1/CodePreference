import guiceExamples.cache.CacheProvider;
import guiceExamples.cache.RuntimeCacheProvider;

import java.time.Clock;

import play.Configuration;
import play.Environment;
import services.ApplicationTimer;
import services.AtomicCounter;
import services.Counter;

import com.google.inject.AbstractModule;
import com.google.inject.ImplementedBy;

import customization.JsonStartupHandler;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 *
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

	private final Environment env;
   
	public Module(Environment env, Configuration configuration) {
		this.env = env;
	}
	
    @Override
    public void configure() {
        // Use the system clock as the default implementation of Clock
        bind(Clock.class).toInstance(Clock.systemDefaultZone());
        // Ask Guice to create an instance of ApplicationTimer when the
        // application starts.
        bind(ApplicationTimer.class).asEagerSingleton();
        // Set AtomicCounter as the implementation for Counter.
        bind(Counter.class).to(AtomicCounter.class);
        // My code
        // The call is commented, since SessionProvider is injected with @ImplementedBy annotation
        //bind(SessionProvider.class).to(SessionInCashProvider.class); 
		bind(JsonStartupHandler.class).asEagerSingleton();
 
		// Injection for test is done with GuiceApplicationBuilder
		if (!env.isTest() ) {
			bind(CacheProvider.class).to(RuntimeCacheProvider.class);			
		}
    }

}
