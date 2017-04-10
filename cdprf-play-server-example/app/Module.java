import play.Configuration;
import play.Environment;

import com.google.inject.AbstractModule;

import customization.StartupHandler;
import examples.guiceExamples.cache.CacheProvider;
import examples.guiceExamples.cache.RuntimeCacheProvider;

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
	private final Configuration configuration;
   
	public Module(Environment env, Configuration configuration) {
		this.env = env;
		this.configuration = configuration;
	}
	
    @Override
    public void configure() {
        // The call is commented, since SessionProvider is injected with @ImplementedBy annotation
        //bind(SessionProvider.class).to(SessionInCashProvider.class); 
		bind(StartupHandler.class).asEagerSingleton();
 
		// Injection for test is done with GuiceApplicationBuilder
		if (!env.isTest() ) {
			bind(CacheProvider.class).to(RuntimeCacheProvider.class);			
		}
    }

}
