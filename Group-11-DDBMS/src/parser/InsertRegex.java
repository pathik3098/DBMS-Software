package com.group11;

import java.util.regex.*;

public class InsertRegex 
{
	private static final String INSERT_QUERY_REGEX = "^INSERT INTO [A-Z_]* VALUES \\(.*\\)$";
	private String tableName;
	private String[] dataValues;
	
	public String[] getDataValues() {
		return dataValues;
	}

	public void setDataValues(String[] dataValues) {
		this.dataValues = dataValues;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
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

	public void insertQueryParser(String query, String databaseName)
	{
		String string[];
		int indexOfOpenBracket;
		int indexOfCloseBracket;

		if(queryChecker(query.toUpperCase()))
		{
			string = query.split(" ");
			this.setTableName(string[2]);
			System.out.println(this.getTableName());
			FileHandlingOperations file = new FileHandlingOperations(databaseName, tableName.toLowerCase() + ".txt");
			if(file.isFilePresent())
			{
				indexOfOpenBracket = query.indexOf("(");
				indexOfCloseBracket = query.indexOf(")");
				this.setDataValues(query.substring(indexOfOpenBracket + 1, indexOfCloseBracket).split(","));
		        file.writeTableData(getDataValues());
			}
			else
			{
				System.out.println("File Not present");
			}
			
		}
	}

}