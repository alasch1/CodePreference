package guiceExamples;

import static org.junit.Assert.assertNotNull;
import static play.inject.Bindings.bind;

import org.junit.Test;

import guiceExamples.cache.CacheProvider;
import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import utils.FakeApplicationTestBase;
import utils.FakeCacheProvider;

public class TestTask extends FakeApplicationTestBase {

	@Override
	protected Application configureGuice() {
		return new GuiceApplicationBuilder()
				.overrides(bind(CacheProvider.class).to(FakeCacheProvider.class))
				// The next line demonstrated, how to do named binding with GuiceApplicationBuilder
				// It used qualifiedWith instead of annotatedWith, that is used in the module
				//.bindings(bind(ServiceProvider.class).qualifiedWith(Names.named("serviceA")).to(ServiceA.class))
				.in(Mode.TEST)
				.build();		
	}

//	@Test
//	public void testStaticNameInject() {
//		StaticInjectTask task = getInjector().instanceOf(StaticInjectTask.class);
//		assertNotNull(task);
//	}
//	
//	@Test
//	public void testDynamicNameInject() {
//		DynamicTaskFactory factory = getInjector().instanceOf(DynamicTaskFactory.class);
//		DynamicInjectTask task = factory.create("serviceA");
//		assertNotNull(task);
//	}
}
