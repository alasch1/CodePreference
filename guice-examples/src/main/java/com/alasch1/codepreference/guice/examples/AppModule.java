package com.alasch1.codepreference.guice.examples;

import com.alasch1.codepreference.guice.examples.assisited.FixedServiceProxy;
import com.alasch1.codepreference.guice.examples.assisited.FlexibleServiceProxy;
import com.alasch1.codepreference.guice.examples.assisited.NamedServiceProvider;
import com.alasch1.codepreference.guice.examples.assisited.ServiceProxyFactory;
import com.alasch1.codepreference.guice.examples.assisited.NamedServiceA;
import com.alasch1.codepreference.guice.examples.assisited.NamedServiceB;
import com.alasch1.codepreference.guice.examples.assisited.ServiceProxy;
import com.alasch1.codepreference.guice.examples.basic.ServiceBean;
import com.alasch1.codepreference.guice.examples.basic.ServiceProvider;
import com.alasch1.codepreference.guice.examples.basic.EagerSingletonBean;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

public class AppModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(EagerSingletonBean.class).asEagerSingleton();
		bind(ServiceProvider.class).to(ServiceBean.class);
        bind(NamedServiceProvider.class).annotatedWith(Names.named("serviceA")).to(NamedServiceA.class);
        bind(NamedServiceProvider.class).annotatedWith(Names.named("serviceB")).to(NamedServiceB.class);
		install(new FactoryModuleBuilder()
				.implement(ServiceProxy.class, Names.named("fixed"), FixedServiceProxy.class)
				.implement(ServiceProxy.class, Names.named("flex"), FlexibleServiceProxy.class)
				.build(ServiceProxyFactory.class));
	}

}
