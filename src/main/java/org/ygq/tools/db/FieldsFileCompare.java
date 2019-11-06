package org.ygq.tools.db;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ygq.tools.core.StringUtils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class FieldsFileCompare {

	public static String PATTERN_FIELD = "private\\s+[a-zA-Z]+\\s+(\\w+)\\s*;";
	
	public static void main(String[] args) throws IOException {
		String file1 = "E:\\file1.txt";
		String file2 = "E:\\file2.txt";
		FieldsFileCompare compare = new FieldsFileCompare();
		compare.compare(file1, file2);
//		compare.compareField(file1, file2);
		
	}
	
	/**
	 * 读取文件过滤Java属性，提取属性list
	 * @param path
	 * @return
	 * @throws IOException
	 */
	private List<String> readAndfilterFields(Path path) throws IOException {
		
		List<String> valueList = new ArrayList<String>();
		List<String> fields = Files.readAllLines(path);

		for (int i = 0; i < fields.size(); i++) {
			String value = fields.get(i);
			if (value == null || value.trim().isEmpty()) {
				fields.remove(i--);
				continue;
			}

			value = value.trim();
			Pattern compile = Pattern.compile(PATTERN_FIELD);
			Matcher matcher = compile.matcher(value);
			if (matcher.find()) {
				String field = matcher.group(1);
				valueList.add(field);
			}
		}
		return valueList;
	}
	
	/**
	 * 读取数据库字段，提取字段名
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public List<String> readAndfilterColumns(Path path) throws IOException {
		
		CCJSqlParserManager sqlParse = new CCJSqlParserManager();
		
		String sql = new String(Files.readAllBytes(path));
		
		try {
			Statement parse = sqlParse.parse(new StringReader(sql));
			if (parse instanceof CreateTable) {
				CreateTable ct = (CreateTable)parse;
				List<ColumnDefinition> columnDefinitions = ct.getColumnDefinitions();
				List<String> columnNameList = new ArrayList<String>();
				for (ColumnDefinition cd : columnDefinitions) {
					System.out.println(Arrays.toString(cd.getColumnSpecStrings().toArray()));
					columnNameList.add(cd.getColumnName().replace("`", ""));
				}
				return columnNameList;
			}
		} catch (JSQLParserException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
		
	}
	
	/**
	 * 比较属性和字段信息
	 * @param fieldPath
	 * @param columnPath
	 * @throws IOException
	 */
	public void compare(String fieldPath, String columnPath) throws IOException {
		
		List<String> fieldList = readAndfilterFields(Paths.get(fieldPath));
		
		List<String> columnsList = readAndfilterColumns(Paths.get(columnPath));

		for (int i = 0; i < fieldList.size(); i++) {
			String value = fieldList.get(i);
			String underLine = StringUtils.camel2Underline(value).toLowerCase();
			for (int j = 0; j < columnsList.size(); j++) {
				String column = columnsList.get(j).toLowerCase();
				if (underLine.contentEquals(column)) {
					fieldList.remove(i--);
					columnsList.remove(j--);
					break;
				}
			}
		}
		System.out.println(Arrays.toString(fieldList.toArray()));
		System.out.println(Arrays.toString(columnsList.toArray()));
	}
	
	/**
	 * 对比属性名
	 * @param fieldPath
	 * @param fieldPath2
	 * @throws IOException
	 */
	public void compareField(String fieldPath, String fieldPath2) throws IOException {
		
		List<String> fieldList = readAndfilterFields(Paths.get(fieldPath));
		
		List<String> columnsList = readAndfilterFields(Paths.get(fieldPath2));
		
		for (int i = 0; i < fieldList.size(); i++) {
			String value = fieldList.get(i);
			String underLine = value.toLowerCase();//StringUtils.camel2Underline(value).toLowerCase();
			for (int j = 0; j < columnsList.size(); j++) {
				String column = columnsList.get(j).toLowerCase();
				if (underLine.contentEquals(column)) {
					fieldList.remove(i--);
					columnsList.remove(j--);
					break;
				}
			}
		}
		System.out.println(Arrays.toString(fieldList.toArray()));
		System.out.println(Arrays.toString(columnsList.toArray()));
	}
	
}
