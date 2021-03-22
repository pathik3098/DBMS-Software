package com.group11;

import java.util.regex.*;

public class InsertRegex {

	public static void main(String []args){
		String insert = "INSERT INTO table_name (a, b, c) VALUES (abc,def,ghi) , (jkl,mno,pqr)";

		String regex = "((?<=(INSERT\\sINTO\\s))[\\w\\d_]+(?=\\s+))|((?<=\\()([\\w\\d_,]+)+(?=\\)))";

		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher m = re.matcher(insert);
		
		while (m.find()) {
			System.out.println(m.group(0));
		}
		
	}
}