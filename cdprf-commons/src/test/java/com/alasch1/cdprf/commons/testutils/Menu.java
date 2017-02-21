package com.alasch1.cdprf.commons.testutils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Menu<T> {
	private Method[] methods;
	private final T  instance;
	
	
	public Menu(T instance) {
		this.instance = instance;
		initAccessibleMethods();
	}
	
	private void initAccessibleMethods() {		
		List<Method> result = new ArrayList<Method>();
	    for (Method method : instance.getClass().getDeclaredMethods()) {
	    	if(Modifier.isPublic(method.getModifiers()) && method.getGenericReturnType() == Void.TYPE 
					&& method.getGenericParameterTypes().length == 0) {
	                result.add(method);
	            }
	     }
	    methods = result.toArray(new Method[result.size()]);
	}
	
	private void print() {
		int counter = 1;
		System.out.println("\nPlease select an action by specifying its number and click on enter");
		for(Method method : methods){
			System.out.println(counter++ + ". " + method.getName());
		}
		System.out.println(counter + ". " + "Quit");
	}
	
	private int getActionNumber(){
		@SuppressWarnings("resource")
		Scanner scanInput = new Scanner(System.in);
		System.out.println("Action number : ");
		int input = -1;		
		do {
			while (!scanInput.hasNextInt()) { 
				System.out.println("Not-numeric invalid value was enetred! Enter action number from 1 to " + (methods.length +1) + ": "); 
				scanInput.next();
			}
			
			input = scanInput.nextInt();
			if (input < 1 || input > methods.length+1) {
				System.out.println("Invalid value " + input + " was entered! Enter action number from 1 to " + (methods.length +1) + ": "); 
				input = -1;
				//scanInput.next();
			}
		} while(input <= 0);
		
		System.out.println("Selected action:" + input);
		return input;
	}
	
	public void show() {
		boolean runTest = methods.length > 0;
		while(runTest) {
			print();
			int testNum = getActionNumber();
		    runTest = (testNum >= 1 && testNum <= methods.length);
		    if(runTest) {
		    	try {
					methods[testNum-1].invoke(instance);
				} 
		    	catch (Exception e) {
				}
		    }
		}
	}
}