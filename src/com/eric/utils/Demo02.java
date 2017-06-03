package com.eric.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class Demo02 {//标准表
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
		String sql = "INSERT INTO t_standard (chineseName,englishName,bookId,pubdate,stdNO,issuedDep,belongDep,publishDep,draftPerson,stdState,propose,comment,fileref) values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement ps = null;

				try {
					SAXReader reader = new SAXReader();
					// ͨ��read������ȡһ���ļ� ת����Document����
					Document document = reader.read(file); // 135029-5381
					// ��ȡ��ڵ�Ԫ�ض���
					Element node = document.getRootElement();
					Element Title = node.element("title");
					String etitle = "", pubdate = "", stdstate = "", propose = "";
					if (node.element("etitle") != null) {
						etitle = node.element("etitle").getText();
					}
					Element info = node.element("info");
					Element Bookid = info.element("bookId");
					if (info.element("pubdate") != null) {
						pubdate = info.element("pubdate").getText()+"-01";
					}
					else
						pubdate = "2007-01-01";
					  SimpleDateFormat myFmt1=new SimpleDateFormat("yy-MM-dd"); 
					  Date date=myFmt1.parse(pubdate); 
					Element stdexpand = node.element("StdExpand");
					Element StdNO = stdexpand.element("StdNO");
					Element IssuedDep = stdexpand.element("IssuedDep");
					Element BelongDep = stdexpand.element("BelongDep");
					Element PublishDep = stdexpand.element("PublishDep");
					Element DraftPerson = stdexpand.element("DraftPerson");
					if (stdexpand.element("StdState") != null) {
						stdstate = stdexpand.element("StdState").getText();
					}
					if (stdexpand.element("propose") != null) {
						propose = stdexpand.element("propose").getText();
					}
					Element Scope = stdexpand.element("Scope");
					String para = "";
					Iterator iter = Scope.elementIterator("para");
					while (iter.hasNext()) {
						Element e = (Element) iter.next();
						para = para + e.getTextTrim() + "/";
					}
					String ref = "";// pdf url
					int flag = 0;
					Iterator iter2 = node.elementIterator("attachment");
					while (iter2.hasNext()) {
						Element e = (Element) iter2.next();
						String tmp = e.attribute("type").getValue();
						if ("低精度pdf".equals(tmp)) {
							flag = 1;
							ref = e.attribute("fileref").getValue();
						}
					}
					String bookid = "";
					if (Bookid.getText() != null)
						bookid = Bookid.getText();
					String stdno = "";
					if (StdNO.getText() != null)
						stdno = StdNO.getText();
					String issueddep = "";
					if (IssuedDep.getText() != null)
						issueddep = IssuedDep.getText();
					String belongdep = "";
					if (BelongDep.getText() != null)
						belongdep = BelongDep.getText();
					String publishdep = "";
					if (PublishDep.getText() != null)
						publishdep = PublishDep.getText();
					String draftperson = "";
					if (DraftPerson.getText() != null)
						draftperson = DraftPerson.getText();

					// String result="";//������
					// result=
					// Title.getText()+"*"+etitle+"*"+bookid+"*"+pubdate+
					// "*"+stdno+"*"+issueddep+"*"+belongdep+"*"+
					// publishdep+"*"+draftperson+"*"+stdstate+"*"+
					// propose+"*"+para+"*"+ref+"\n";
					ps = conn.prepareStatement(sql);
					ps.setString(1, Title.getText());
					ps.setString(2, etitle);
					ps.setString(3, bookid);
					Timestamp timeStamp = new Timestamp(date.getTime());

					ps.setTimestamp(4, timeStamp);
					ps.setString(5, stdno);
					ps.setString(6, issueddep);
					ps.setString(7, belongdep);
					ps.setString(8, publishdep);
					ps.setString(9, draftperson);
					ps.setString(10, stdstate);
					ps.setString(11, propose);
					ps.setString(12, para);
					ps.setString(13, ref);
					int j = ps.executeUpdate();
					// System.out.println(result);

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
		conn.close();
		System.out.println("Insert 标准表 succeeded!");
	}
}
