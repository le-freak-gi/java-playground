package example.stringExample;

import java.text.DecimalFormat;

public class StringNumberFormatExample {

	public static void main(String[] args) {
		
		// String.format 이용한 숫자포맷 천단위 , ###,###
		int num1 = 35000000;
		System.out.println(String.format("%,d", num1));
		
		// DecimalFormat 이용한 숫자포맷 천단위 , ###,###
		DecimalFormat formatter = new DecimalFormat("###,###");
		System.out.println(formatter.format(num1));
		
		// String.format 이용한 lpad
		int num2 = 35;
		System.out.println(String.format("%05d", num2));
		
	}

}
