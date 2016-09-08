package com.alasch1.mocking.examples.argumentCapture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.alasch1.mocking.examples.argumentCapture.FooService;
import com.alasch1.mocking.examples.argumentCapture.FooService.BarService;
import com.alasch1.mocking.examples.argumentCapture.FooService.Foo;
import com.alasch1.mocking.examples.argumentCapture.FooService.RedisService;

import junit.framework.Assert;

public class TestFooService {
	
	@Mock
	private BarService barService;
	@Mock
	private RedisService rediceService;
	@InjectMocks
	private FooService fooService = new FooService();
	@Captor
	ArgumentCaptor<List<Foo>> fooListCaptor;
	
	@Before
    public void init(){
       MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testFooServiceEmptyList() {
		fooService.doSomething();
		Mockito.verify(barService).batchAddFoos(fooListCaptor.capture());
		List<Foo> fooList = fooListCaptor.getValue();
		//Assert.assertNotNull(fooList.get(0).getCode());
		Assert.assertFalse(fooList.isEmpty());
		List<String> codeList = new ArrayList<>();
		for (Foo foo : fooList) {
			//foo.setCode("aaa");
		    codeList.add(foo.getCode());
			//System.out.println("foo.code=" + foo.getCode());
		}
		System.out.println(Arrays.toString(codeList.toArray()));
		Mockito.verify(rediceService).sadd("foo_codes", codeList.toArray(new String[]{}));
	}



}
