package com.alasch1.codepreference.akkaexamples.utils;

import java.util.Scanner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class AppUtil {

	public static void terminate(ActorSystem system, ActorRef actor) {
		System.out.println("Enter to terminate");
		try (Scanner input = new Scanner(System.in)) {
			input.nextLine();
			System.out.println("The end");
			system.stop(actor);
			system.terminate();
			System.exit(0);
		}		
	}
}
