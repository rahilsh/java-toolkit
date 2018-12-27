package in.number;

import java.math.BigDecimal;

public class TestDecimal {

	public static void main(String[] args) {

		String amountInbank = "30.126";
		System.out.println(Long.valueOf(new BigDecimal(amountInbank).multiply(new BigDecimal("100"))
				.longValue()));
	}

}
