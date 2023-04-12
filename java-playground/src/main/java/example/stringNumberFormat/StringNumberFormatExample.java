package example.stringNumberFormat;

import java.text.DecimalFormat;

public class StringNumberFormatExample {

	public static void main(String[] args) {
		
		DecimalFormat decimalFormet = new DecimalFormat("###,###");
		System.out.println("format ###,### : " + decimalFormet.format(35000000));
		
		System.out.println("format ###,### : " +String.format("%,d", 35000000));
		
		System.out.println("format lpad(35,5,'0') : " +String.format("%05d", 35));

	}

}
