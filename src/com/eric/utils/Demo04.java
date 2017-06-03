package com.eric.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class Demo04 {//术语表
	@Test
	public static void test(File file) throws Exception {
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";

		// URL指向要访问的数据库名termonline_bak
		String url = "jdbc:mysql://localhost:3306/termonline_bak";

		// MySQL配置时的用户名
		String user = "root";

		// MySQL配置时的密码
		String password = "123456";

		// 加载驱动程序
		Class.forName(driver);

		// 连续数据库
		Connection conn = DriverManager.getConnection(url, user, password);

		if (!conn.isClosed())
			System.out.println("Succeeded connecting to the Database!");

		// 要执行的SQL语句
		String sql = "INSERT INTO t_item (chineseName,englishName,englishAbbr,definitionContent,bookId,Standard,definitionNum,entryTime,author,state) values(?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement ps = null;
		
				try {
					SAXReader reader = new SAXReader();
					Document document = reader.read(file); // 135029-5381
					Element node = document.getRootElement();
					// Element Title =node.element("title");
					String etitle = "", pubdate = "", stdstate = "";
					// if(node.element("etitle")!=null){etitle
					// =node.element("etitle").getText();}
					Element info = node.element("info");
					Element Bookid = info.element("bookId");
					if (info.element("pubdate") != null) {
						pubdate = info.element("pubdate").getText();
					}
					Element stdexpand = node.element("StdExpand");
					Element StdNO = stdexpand.element("StdNO");
					Element DraftPerson = stdexpand.element("DraftPerson");
					if (stdexpand.element("StdState") != null) {
						stdstate = stdexpand.element("StdState").getText();
					}
					String bookid = "";
					if (Bookid.getText() != null)
						bookid = Bookid.getText();
					String stdno = "";
					if (StdNO.getText() != null)
						stdno = StdNO.getText();
					String draftperson = "";
					if (DraftPerson.getText() != null)
						draftperson = DraftPerson.getTextTrim();
					Element gloss = node.element("info");

					if (node.element("glossdiv") != null) {
						gloss = node.element("glossdiv");
					} else {
						// System.out.println(bookid);
						Iterator iter = node.elementIterator("chapter");
						while (iter.hasNext()) {
							Element chapter = (Element) iter.next();
							if (chapter.elementTextTrim("title").charAt(0) == 3) {
								gloss = chapter.element("glossdiv");
							}
						}
					}

					Iterator iter = gloss.elementIterator("glossentry");
					while (iter.hasNext()) {
						String t = "", f = "", s = "", y = "";
						Element e = (Element) iter.next();
						Element term = e.element("glossterm");
						if (term.element("innernum") != null) {
							y = term.element("innernum").getStringValue();
						}
						if (term.element("title") != null) {
							t = term.element("title").getStringValue();
							t = t.replaceAll("[' ']+", " ");
							t = t.replaceAll("\n", " ");
							t = t.trim();
						}
						if (term.element("foreignphrase") != null) {
							f = term.element("foreignphrase").getStringValue();
							f = f.replaceAll("[' ']+", " ");
							f = f.replaceAll("\n", " ");
							f = f.trim();
						}
						if (term.element("EtitleAbbrev") != null) {
							s = term.element("EtitleAbbrev").getStringValue();
							s = s.replaceAll("[' ']+", " ");
							s = s.replaceAll("\n", " ");
							s = s.trim();
						}

						String para = "";
						if (term.element("annotation") != null) {
							Iterator iter2 = term.element("annotation")
									.elementIterator("para");

							while (iter2.hasNext()) {
								Element p = (Element) iter2.next();
								if (p.getText() != null) {
									if (p.element("italic") != null)
										para = para + p.elementText("italic");

									para = para + p.getStringValue();
									para = para.replaceAll(" ", "");
									para = para.replaceAll("\n", "");
								}
							}
						}
						ps = conn.prepareStatement(sql);
						ps.setString(1, t);
						ps.setString(2, f);
						ps.setString(3, s);
						ps.setString(4, para);
						ps.setString(5, bookid);
						ps.setString(6, stdno);
						ps.setString(7, y);
						ps.setString(8, pubdate);
						ps.setString(9, draftperson);
						ps.setString(10, stdstate);
						int j = ps.executeUpdate();
						// else{System.out.println(bookid);}
						// String result="";
						// result=num+"\t"+t+"\t"+f+"\t"+s+"\t"+para+"\t"+bookid+"\t"+stdno+"\t"+y+"\t"+pubdate+"\t"+draftperson+"\t"+stdstate+"\n";
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
		
		conn.close();
		System.out.println("Insert 术语表 succeeded!");
	}
}