package com.alasch1.cdprf.mocking.argumentCapture;

import java.util.ArrayList;
import java.util.List;

public class FooService {
	
	private BarService barService;
	private RedisService redisService;
	
	public void setBarService(BarService barService) {
		this.barService = barService;
	}
	
	public void setRedisService(RedisService redisService) {
		this.redisService = redisService;
	}
	
	public void doSomething(){
	    ArrayList<Foo> fooList = new ArrayList<Foo>(){
	    	{add(new Foo("dd"));}
	    	{add(new Foo("ff"));}
	    	{add(new Foo("cc"));}
	    };
	    barService.batchAddFoos(fooList); 

	    List<String> codeList = new ArrayList<>();
	    for (Foo foo : fooList) {
	        codeList.add(foo.getCode());
	    }
	    String key = "foo_codes";
	    redisService.sadd(key, codeList.toArray(new String[]{}));
	    // other code also need use code
	}
	
	public static class Foo {
		private String code;// = "";
		
		public Foo() {}
		public Foo(String code)	{	
			setCode(code);
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
	}
	
	public static class BarService {
		public void batchAddFoos(List<Foo> foos) {
			for (Foo foo : foos) {
				foo.setCode("TS:" + Long.toString(System.currentTimeMillis()));
			}
		}
		
	}
	
	public static class RedisService {
		public void sadd(String key, String[] codes) {			
		}
	}
}
