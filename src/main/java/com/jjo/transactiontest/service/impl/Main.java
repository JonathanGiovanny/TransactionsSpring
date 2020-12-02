package com.jjo.transactiontest.service.impl;

public class Main {

	public static void main(String[] args) {
		try {
			System.out.println("a");
			throwE();
			System.out.println("b");
		} catch (Exception e) {
			System.out.println("c");
		} finally {
			System.out.println("d");
		}
	}

	static void throwE() {
		throw new Error();
	}
}
