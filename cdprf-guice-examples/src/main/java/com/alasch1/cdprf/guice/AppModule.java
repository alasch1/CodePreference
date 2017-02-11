package com.alasch1.cdprf.guice;

import com.alasch1.cdprf.guice.assisited.AppNames;
import com.alasch1.cdprf.guice.assisited.FixedServiceProxy;
import com.alasch1.cdprf.guice.assisited.FlexibleServiceProxy;
import com.alasch1.cdprf.guice.assisited.NamedServiceA;
import com.alasch1.cdprf.guice.assisited.NamedServiceB;
import com.alasch1.cdprf.guice.assisited.NamedServiceProvider;
import com.alasch1.cdprf.guice.assisited.ServiceProxy;
import com.alasch1.cdprf.guice.assisited.ServiceProxyFactory;
import com.alasch1.cdprf.guice.basic.EagerSingletonBean;
import com.alasch1.cdprf.guice.basic.ServiceBean;
import com.alasch1.cdprf.guice.basic.ServiceProvider;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

public class AppModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EagerSingletonBean.class).asEagerSingleton();
		bind(ServiceProvider.class).to(ServiceBean.class);
        bind(NamedServiceProvider.class).annotatedWith(Names.named(AppNames.SERVICE_A)).to(NamedServiceA.class);
        bind(NamedServiceProvider.class).annotatedWith(Names.named(AppNames.SERVICE_B)).to(NamedServiceB.class);
		install(new FactoryModuleBuilder()
				.implement(ServiceProxy.class, Names.named(AppNames.FIXED), FixedServiceProxy.class)
				.implement(ServiceProxy.class, Names.named(AppNames.FLEX), FlexibleServiceProxy.class)
				.build(ServiceProxyFactory.class));
	}

}
