package com.utils;

import java.math.BigDecimal;

public class DivideLong {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		long l = -1L;
		BigDecimal d=new BigDecimal(l);
		d=d.divide(new BigDecimal(100));
		System.out.println(new BigDecimal(l).divide(new BigDecimal(100)).toString());

	}

}
