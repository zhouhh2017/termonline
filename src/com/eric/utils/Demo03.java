package com.eric.utils;

import java.io.BufferedWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import java.sql.*;

public class Demo03 {//英汉冰冻圈科学词汇
	@Test
	public static void test(File file) throws Exception {
		String lineSeparator = System.getProperty("line.separator");

		// ����saxReader����
		SAXReader reader = new SAXReader();
		// ͨ��read������ȡһ���ļ� ת����Document����
		//Document document = reader.read(new File("src/9787502964771.xml")); // 135029-5381
		Document document = reader.read(file);
		
		Element node = document.getRootElement();

		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名termonline_bak
		String url = "jdbc:mysql://localhost:3306/termonline_bak";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "123456";
		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			Connection conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			// 要执行的SQL语句
			String sql = "INSERT INTO t_word (chineseName,englishName,abbreviation,standard) values(?,?,?,?);";
			PreparedStatement ps = null;
			Iterator iter2 = node.elementIterator("entry");
			while (iter2.hasNext()) {
				Element e = (Element) iter2.next();
				String result = "";
				Element title = e;
				String abbreviation = "";
				if (e.element("title") != null) {
					title = e.element("title");
				} else {
					title = e.element("para");
				}
				Element etitle = e.element("etitle");
				if (e.element("abbreviation") != null)
					abbreviation = e.element("abbreviation").getTextTrim();

				// result=
				// num+"\t"+title.getTextTrim()+"\t"+etitle.getTextTrim()+"\t"+abbreviation+"\t"+"\n";
				ps = conn.prepareStatement(sql);
				ps.setString(1, title.getText());
				ps.setString(2, etitle.getText());
				ps.setString(3, abbreviation);
				ps.setString(4, "英汉冰冻圈科学词汇");

				int i = ps.executeUpdate();
				// System.out.println(num);
			}
			conn.close();

		} catch (ClassNotFoundException e) {
			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Insert 英汉冰冻圈科学词汇 succeeded!");
	}
}
