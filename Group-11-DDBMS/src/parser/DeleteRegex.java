package com.group11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteRegex {
	public static void main(String []args){
		String delete = "DELETE FROM table_name WHERE id=1";


		String regex = "((?<=(DELETE\\sFROM\\s))[\\w\\d_]+(?=\\sWHERE)[\\s\\w\\d_]+(\\s)?(\\W)(\\s)?[\\d])";
		
		Pattern re = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher m = re.matcher(delete);
		
		while (m.find()) {
			System.out.println(m.group(0));
		}
	}

}
