import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import customization.JsonStartupHandler;
import guiceExamples.cacheInspector.CacheInspector;
import guiceExamples.cacheInspector.FakeCacheInspector;
import guiceExamples.cacheInspector.RuntimeCacheInspector;
import guiceExamples.sessionProvider.SessionInCacheProvider;
import guiceExamples.sessionProvider.SessionProvider;

import java.time.Clock;

import play.Configuration;
import play.Environment;
import services.ApplicationTimer;
import services.AtomicCounter;
import services.Counter;

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
    //private final Configuration configuration;
   
	public Module(Environment env, Configuration configuration) {
		this.env = env;
		//this.configuration = configuration;
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
        //bind(SessionProvider.class).to(SessionInCashProvider.class);
		bind(JsonStartupHandler.class).asEagerSingleton();
 
		if (env.isTest() ) {
			bind(CacheInspector.class).to(FakeCacheInspector.class);
		}
		else {
			bind(CacheInspector.class).to(RuntimeCacheInspector.class);			
		}
    }
    
//    @Provides
//    public SessionProvider sessionProvider() {
//    	return new SessionInCacheProvider();
//    }

}
