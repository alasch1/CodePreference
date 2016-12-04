package com.alasch1.codepreference.guice.examples;

import com.alasch1.codepreference.guice.examples.assisited.ServiceProxy;
import com.alasch1.codepreference.guice.examples.assisited.ServiceProxyFactory;
import com.alasch1.codepreference.guice.examples.basic.ConstructorInjectionClient;
import com.alasch1.codepreference.guice.examples.basic.MemberInjectionClient;
import com.alasch1.codepreference.guice.examples.basic.ServiceProvider;
import com.alasch1.codepreference.guice.examples.basic.EagerSingletonBean;

public class App {
    public static void main( String[] args ) {
    	ServiceProvider service = DIContainer.getInjector().getInstance(ServiceProvider.class);
    	service.useMe();
    	EagerSingletonBean.just4Test("Hi !");
        ConstructorInjectionClient client1 = DIContainer.getInjector().getInstance(ConstructorInjectionClient.class);
        client1.use();
        ConstructorInjectionClient client2 = DIContainer.getInjector().getInstance(ConstructorInjectionClient.class);
        client2.use();
        MemberInjectionClient client3 = new MemberInjectionClient();
        DIContainer.getInjector().injectMembers(client3);
        client3.use();
        
        ServiceProxyFactory factory = DIContainer.getInjector().getInstance(ServiceProxyFactory.class);
        ServiceProxy proxy1 = factory.createFlexibleProxy("serviceB");
        System.out.println(proxy1.getNamedService().getName());
        ServiceProxy proxy2 = factory.createFixedProxy();
        System.out.println(proxy2.getNamedService().getName());
        
    }
}
