package com.utils;

import java.math.BigDecimal;

public class TestDecimal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String amountInbank = "30.126";
		System.out.println(Long.valueOf(new BigDecimal(amountInbank).multiply(new BigDecimal("100"))
				.longValue()));
	}

}
