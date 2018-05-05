package com.utils;

import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class FutureTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FutureTest ft = new FutureTest();
		ft.testFuture();
	}

	public CompletionStage<String> testFuture() {
		// CompletableFuture<String> text =
		// CompletableFuture.completedFuture("test").thenCompose(this::test2);
		CompletableFuture.completedFuture("test").thenCompose(s -> {
			test1(s);
			return test2(s);
		});
		return null;
	}

	public CompletionStage<String> test1(String s) {

		System.out.println(Thread.currentThread().getId() + " : test1 start: " + new Date().getTime());

		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 100000; j++) {
				for (int k = 0; k < 2; k++) {
					/*
					 * for (int p = 0; p < 1000000; p++) {
					 * 
					 * }
					 */
				}
			}
		}

		System.out.println(Thread.currentThread().getName() + " : test1 end: " + new Date().getTime());
		return CompletableFuture.completedFuture("test1");
	}

	public CompletionStage<String> test2(String s) {
		System.out.println(Thread.currentThread().getId() + " : test2 start: " + new Date().getTime());
		for (int i = 0; i < 100000; i++) {
			for (int j = 0; j < 100000; j++) {
				for (int k = 0; k < 2; k++) {
					/*
					 * for (int p = 0; p < 1000000; p++) {
					 * 
					 * }
					 */
				}
			}
		}
		System.out.println(Thread.currentThread().getName() + " : test2 end: " + new Date().getTime());
		return CompletableFuture.completedFuture("test2");
	}
}
