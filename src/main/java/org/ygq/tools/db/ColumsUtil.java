package org.ygq.tools.db;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ygq.tools.core.StringUtils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class ColumsUtil {

	public static List<String> createTableScriptToJavaFields(String fieldPath) {
		
		Path path = Paths.get(fieldPath);
		
		CCJSqlParserManager sqlParse = new CCJSqlParserManager();

		try { 
			String sql = new String(Files.readAllBytes(path));
			Statement parse = sqlParse.parse(new StringReader(sql));
			if (parse instanceof CreateTable) {
				CreateTable ct = (CreateTable) parse;
				List<ColumnDefinition> columnDefinitions = ct.getColumnDefinitions();
				List<String> columnNameList = new ArrayList<String>();
				for (ColumnDefinition cd : columnDefinitions) {
					StringBuilder sb = new StringBuilder("private ");
					sb.append(converType(cd.getColDataType().getDataType()));
					sb.append(" ");
					String value = cd.getColumnName().replace("`", "");
					sb.append(StringUtils.underline2Camel(value, true));
					sb.append(";");
					List<String> columnSpecStrings = cd.getColumnSpecStrings();
					if (columnSpecStrings != null && !columnSpecStrings.isEmpty()) {
						sb.append(" //");
						sb.append(columnSpecStrings.get(columnSpecStrings.size()-1).replace("'", ""));
					}
					columnNameList.add(sb.toString());
				}
				return columnNameList;
			}
		} catch (JSQLParserException | IOException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	private static String converType(String type) {

		switch (type.toUpperCase()) {
		case "CHAR":
		case "VARCHAR":
			return "String";
		case "DATETIME":
			return "Date";
		case "DATE":
			return "java.sql.Date";
		case "TIME":
			return "Time";
		case "DECIMAL":
			return "BigDecimal";
		case "INT":
			return "Integer";
		default:
			return type;
		}

	}

	
	public static void main(String[] args) {
		List<String> strList = createTableScriptToJavaFields("E:\\\\file2.txt");
		for (int i = 0; i < strList.size(); i++) {
			System.out.println(strList.get(i));
		}
	}
}
