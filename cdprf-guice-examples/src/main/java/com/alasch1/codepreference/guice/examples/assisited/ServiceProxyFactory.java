package com.alasch1.codepreference.guice.examples.assisited;

import com.google.inject.name.Named;

public interface ServiceProxyFactory {
	@Named("flex") ServiceProxy createFlexibleProxy(String serviceName);
	@Named("fixed") ServiceProxy createFixedProxy();
}
