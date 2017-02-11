package com.alasch1.cdprf.guice;

import com.alasch1.cdprf.guice.assisited.ServiceProxy;
import com.alasch1.cdprf.guice.assisited.ServiceProxyFactory;
import com.alasch1.cdprf.guice.basic.ConstructorInjectionClient;
import com.alasch1.cdprf.guice.basic.EagerSingletonBean;
import com.alasch1.cdprf.guice.basic.MemberInjectionClient;
import com.alasch1.cdprf.guice.basic.ServiceProvider;

public class App {
    public static void main( String[] args ) {
    	DIContainer.getInjector();
    	EagerSingletonBean.just4Test("Hi !");
    	ServiceProvider service = DIContainer.getInjector().getInstance(ServiceProvider.class);
    	service.useMe();
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
