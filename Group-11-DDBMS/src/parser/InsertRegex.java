package parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertRegex {
	
	private static final String INSERT_QUERY_REGEX = "^INSERT INTO [A-Z_]* VALUES \\(.*\\)$";
	
	public boolean queryChecker(String query)
	{
		Pattern pattern = Pattern.compile(INSERT_QUERY_REGEX);
		Matcher matcher = pattern.matcher(query.toUpperCase());
		if (matcher.find()) 
		{
			return !matcher.group(0).isBlank();
		}
		return false;
	}

}
