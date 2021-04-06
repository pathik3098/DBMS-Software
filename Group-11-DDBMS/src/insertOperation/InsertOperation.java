package insertOperation;

import java.util.Map;
import java.util.regex.*;

import fileHandlingOperation.FileHandlingOperations;
import parser.InsertRegex;


public class InsertOperation 
{
	InsertRegex insertRegex = new InsertRegex();
	FileHandlingOperations file;

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


	public void insertQueryParser(String query, String databaseName)
	{
		String string[];
		String dataValues[];
		int indexOfOpenBracket;
		int indexOfCloseBracket;

		if(insertRegex.queryChecker(query.toUpperCase()))
		{
			string = query.split(" ");
			this.setTableName(string[2]);
			file = new FileHandlingOperations(databaseName, tableName.toLowerCase() + ".txt");
			if(file.isFilePresent())
			{
				FileHandlingOperations fileOp = new FileHandlingOperations(databaseName, tableName.toLowerCase() + "meta.txt");
				if(fileOp.isFilePresent())
				{
					Map<Integer, ColumnData> columnData = getTableColumns(databaseName, tableName);
					//System.out.println(columnData.toString());
					indexOfOpenBracket = query.indexOf("(");
					indexOfCloseBracket = query.indexOf(")");
					String[] dataValue = query.substring(indexOfOpenBracket + 1, indexOfCloseBracket).split(",");
					if(validateData(dataValue, columnData))
					{
						this.setDataValues(query.substring(indexOfOpenBracket + 1, indexOfCloseBracket).split(","));
						file = new FileHandlingOperations(databaseName, tableName.toLowerCase() + ".txt");
						if(file.isFilePresent())
						{
							System.out.println(getDataValues());
							file.writeTableData(getDataValues());
						}
						else
						{
							System.out.println("File Not Found");
						}
					}
					else
					{
						System.out.println("Entered data doesn't match the schema");
					}
				}
				else
				{
					System.out.println("Metadata File is not present");
				}
			}
			else
			{
				System.out.println("Table Not present");
			}
		}
	}

	public Map<Integer, ColumnData> getTableColumns(String dbName, String tableName) {
		file = new FileHandlingOperations(dbName, tableName.toLowerCase() + "meta.txt");
		return file.readMetaData();
	}


	public boolean validateData(String[] dataValues, Map<Integer, ColumnData> columnData) 
	{
		//index-name-type => columnData
		boolean flag = true;
		String columnType;
		ColumnData column;
		//System.out.println(columnData);
		for (int i = 0; i < dataValues.length; i++) 
		{
			column = (ColumnData)columnData.get(i);
			columnType = column.getType();

			switch (columnType.toUpperCase())
			{
			case "STRING": 
			{
				String checkStringData = dataValues[i].trim();
				flag = checkStringData.startsWith("'");
				if (!flag) 
				{
					return false;
				}
				flag = checkStringData.endsWith("'");
				if (!flag) {
					return false;
				}
				break;
			}
			case "INT":
			{
				try 
				{
					int checkIntData = Integer.parseInt(dataValues[i].trim());
				} 
				catch (Exception e) 
				{
					return false;
				}
				break;
			}
			case "FLOAT": 
			{
				try 
				{
					float checkFloatData = Float.parseFloat(dataValues[i].trim());
				} catch (Exception e) {
					return false;
				}
				break;
			}
			case "DOUBLE": {
				try {
					double checkDoubleData = Double.parseDouble(dataValues[i].trim());
				} catch (Exception e) {
					return false;
				}
				break;
			}
			case "BOOLEAN": {
				try {
					boolean checkBooleanData = Boolean.parseBoolean(dataValues[i].trim());
				} catch (Exception e) {
					return false;
				}
				break;
			}
			default:
				break;
			}
		}
		return true;
	}


}