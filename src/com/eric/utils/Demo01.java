package com.eric.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import java.sql.*;

public class Demo01 {//冰冻圈科学辞典 修订版

	@Test
	public static void test(File file) throws Exception {
		String lineSeparator = System.getProperty("line.separator");

		// ����saxReader����
		SAXReader reader = new SAXReader();
		// ͨ��read������ȡһ���ļ� ת����Document����
		//Document document = reader.read(new File("src/9787502964733.xml")); // 135029-5381
		Document document = reader.read(file); // 135029-5381
		// ��ȡ��ڵ�Ԫ�ض���
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
			String sql = "INSERT INTO t_dictionary (chineseName,englishName,author,definitionContent,standard,pictureref,discription) values(?,?,?,?,?,?,?);";
			PreparedStatement ps = null;
			Iterator iter2 = node.elementIterator("entry");
			while (iter2.hasNext()) {
				Element e = (Element) iter2.next();
				String result = "";

				Element title = e.element("title");
				Element etitle = e.element("etitle");
				Iterator iter = e.elementIterator("para");
				String para = "";
				String pictureref = "";
				String picturediscription = "";
				while (iter.hasNext()) {
					Element element = (Element) iter.next();

					if (element.attribute("align") != null) {
						if ("center".equals(element.attribute("align")
								.getValue())) {
							Element n = element.element("mediaobject")
									.element("imageobject")
									.element("imagedata");
							pictureref = n.attribute("fileref").getValue()
									.toString();
							picturediscription = n.element("title").getText();
							// picture=pictureref+"\t"+picturediscription+"\t";
						}
					} else {
						// picture="\t"+"\t";
						para += element.getText();
					}

				}

				String author = "";
				if (e.element("author") != null)
					author = e.element("author").getTextTrim();

				// result=
				// num.getText()+"\t"+title.getText()+"\t"+etitle.getText()+"\t"+para+"\t"+picture+"\t"+"\n";

				ps = conn.prepareStatement(sql);
				// ps.setInt(1, num++);
				// //对占位符设置值，占位符顺序从1开始，第一个参数是占位符的位置，第二个参数是占位符的值。
				ps.setString(1, title.getText());
				ps.setString(2, etitle.getText());
				ps.setString(3, author);
				ps.setString(4, para);
				ps.setString(5, "冰冻圈科学辞典 修订版");
				ps.setString(6, pictureref);
				ps.setString(7, picturediscription);
				int i = ps.executeUpdate();// 很重要不能漏
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
		System.out.println("Insert 冰冻圈科学辞典 修订版 succeeded!");
	}
}
