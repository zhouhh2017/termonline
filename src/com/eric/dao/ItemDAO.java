package com.eric.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.eric.model.HotWord;
import com.eric.model.Item;
import com.eric.model.NewWord;
import com.eric.model.Standard;
import com.eric.utils.JudgeEnOrZh;

@Component
public class ItemDAO {
	private HibernateTemplate hibernateTemplate;

	// 根据搜索词进行精确查询
	public List<Item> searchItemByItemName(String searchItem) {
		List<Item> item = null;
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			item = (List<Item>) hibernateTemplate.find(
					"from t_item where englishName = ?",
					new String[] { searchItem });
		} else {
			item = (List<Item>) hibernateTemplate.find(
					"from t_item where chineseName = ?",
					new String[] { searchItem });
		}
		return item;
	}

	// 根据搜索词进行模糊查询
	public List<Item> searchItemByItemName_Like(String searchItem) {
		List<Item> item = null;
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			item = (List<Item>) hibernateTemplate
					.find("from t_item where englishName like ? order by englishName",
							new String[] { "%" + searchItem + "%" });
		} else {
			item = (List<Item>) hibernateTemplate
					.find("from t_item where chineseName like ? order by chineseName",
							new String[] { "%" + searchItem + "%" });
		}
		return item;
	}

	@SuppressWarnings("unchecked")
	public List<Item> searchItemByItemName_Like_Limit10(String searchItem) {
		List<Item> item = null;
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			item = (List<Item>) hibernateTemplate
					.find("from t_item where englishName like ? order by englishName",
							new String[] { "%" + searchItem + "%" });
		} else {
			item = (List<Item>) hibernateTemplate
					.find("from t_item where chineseName like ? order by chineseName",
							new String[] { "%" + searchItem + "%" });
		}
		return item;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	// ��ӻ�����ȴ�
	public void addOrUpdateHotWord(String searchItem) {
		// TODO Auto-generated method stub
		List<Item> item = (List<Item>) hibernateTemplate.find(
				"from t_hotword where name = ?", searchItem);
		if (item.size() > 0) {
			hibernateTemplate.getSessionFactory().getCurrentSession()
					.createQuery("update t_hotword set times = times+1 where name=" + "\'" + searchItem + "\'")
					.executeUpdate();
		} else {
			HotWord hotWord = new HotWord();
			hotWord.setName(searchItem);
			hotWord.setTimes(1);
			hibernateTemplate.save(hotWord);
		}
	}

	public void addOrUpdateNewWord(String searchItem, String userName) {
		// TODO Auto-generated method stub
		List<Item> item = (List<Item>) hibernateTemplate.find(
				"from t_newword where name = ? and userName = ?", new String[] {
						searchItem, userName });
		if (item.size() > 0) {
			hibernateTemplate
					.getSessionFactory()
					.getCurrentSession()
					.createQuery(
							"update t_newword set times = times+1 where name = ? and userName = ?")
					.setString(0, searchItem).setString(1, userName)
					.executeUpdate();
		} else {
			NewWord newWord = new NewWord();
			newWord.setName(searchItem);
			newWord.setUserName(userName);
			newWord.setTimes(1);
			hibernateTemplate.save(newWord);
		}
	}

	// 根据搜索词从术语表和词典表中查询
	public List<Object> searchItemFromItem_Dic(String searchItem) {
		List<Object> tmp = new ArrayList<Object>();

		if (JudgeEnOrZh.isEnglish(searchItem)) {
			/*
			 * tmp = hibernateTemplate.getSessionFactory().getCurrentSession().
			 * createSQLQuery("" +
			 * "select chineseName,englishName,author,standard,definitionContent "
			 * + "from t_item where englishName='" + searchItem + "'" +
			 * " union " +
			 * "select chineseName,englishName,author,definitionContent " +
			 * "from t_item where englishName='" + searchItem + "'").list();
			 */

			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where englishName like ? order by englishName",
							new String[] { searchItem }));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where englishName like ? order by englishName",
							new String[] {  searchItem }));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_word where englishName like ? order by englishName",
							new String[] {  searchItem }));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		} else {
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where chineseName like ? order by chineseName",
							new String[] { searchItem }));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where chineseName like ? order by chineseName",
							new String[] { searchItem }));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_word where chineseName like ? order by chineseName",
							new String[] {  searchItem }));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		}
		if (tmp.size() > 0) {
			return tmp;
		} else {
			return null;
		}

	}

	public List<Object> searchItemFromItem_Dic_Like(String searchItem) {
		List<Object> tmp = new ArrayList<Object>();
		
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			/*tmp = hibernateTemplate.getSessionFactory().getCurrentSession().createSQLQuery("" +
					"select chineseName,englishName,author,standard,definitionContent " +
					"from t_item where englishName='" + searchItem + "'" +
							" union " +
					"select chineseName,englishName,author,definitionContent " +
					"from t_item where englishName='" + searchItem + "'").list();*/
			
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_item where englishName like ? order by englishName", new String[]{"%" + searchItem + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_dictionary where englishName like ? order by englishName", new String[]{"%" + searchItem + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_word where englishName like ? order by englishName", new String[]{"%" + searchItem + "%"}));
			
			/*for(Object o: tmp){
				System.out.println(o.getClass());
			}*/
		} else {
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_item where chineseName like ? order by chineseName", new String[]{"%" + searchItem + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_dictionary where chineseName like ? order by chineseName", new String[]{"%" + searchItem + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_word where chineseName like ? order by chineseName", new String[]{"%" + searchItem + "%"}));
			
			/*for(Object o: tmp){
				System.out.println(o.getClass());
			}*/
		}
		if(tmp.size()>0){
			return tmp;
		}else{
			return null;
		}
		
	}

	public List<Standard> SearchByBiaoZhun(String searchItem, String year) {
		List<Standard> tmp = new ArrayList<Standard>();
		if("年份".equals(year)){
			if (JudgeEnOrZh.isEnglish(searchItem)) {
				tmp.addAll((List<Standard>) hibernateTemplate.find("from t_standard where englishName like ? order by englishName", new String[]{"%" + searchItem + "%"}));
			} else {
				tmp.addAll((List<Standard>) hibernateTemplate.find("from t_standard where chineseName like ? order by chineseName", new String[]{"%" + searchItem + "%"}));
			}
		}else{
			if (JudgeEnOrZh.isEnglish(searchItem)) {
				tmp.addAll((List<Standard>) hibernateTemplate.find("from t_standard where englishName like ? and stdNO like ? order by englishName", new String[]{"%" + searchItem + "%","%"+year+"%"}));
			} else {
				tmp.addAll((List<Standard>) hibernateTemplate.find("from t_standard where chineseName like ? and stdNO like ? order by chineseName", new String[]{"%" + searchItem + "%","%"+year+"%"}));
			}
		}
		
		if(tmp.size()>0){
			return tmp;
		}else{
			return null;
		}
	}

	public Standard searchStd(String searchStd) {
		// TODO Auto-generated method stub
		return ((List<Standard>)hibernateTemplate.find("from t_standard where stdNO=?", new String[]{searchStd})).get(0);
	}

	public List<Standard> SearchToBiaoZhun(String stdno) {
		// TODO Auto-generated method stub
		return (List<Standard>)hibernateTemplate.find("from t_standard where stdNO=?", new String[]{stdno});
	}

	

	public List<Object> searchItemFromItem_Dic_Split(List<String> split) {
		// TODO Auto-generated method stub
		List<Object> tmp = new ArrayList<Object>();
		for(String searchItem:split){
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_item where chineseName like ? order by chineseName", new String[]{searchItem}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_dictionary where chineseName like ? order by chineseName", new String[]{searchItem}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_word where chineseName like ? order by chineseName", new String[]{searchItem }));
			
		}
		
		return tmp;
	}

	public List<Object> searchItemFromItem_Dic_Split_Like(List<String> split) {
		// TODO Auto-generated method stub
		List<Object> tmp = new ArrayList<Object>();
		for(String searchItem:split){
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_item where chineseName like ? order by chineseName", new String[]{"%" + searchItem + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_dictionary where chineseName like ? order by chineseName", new String[]{"%" + searchItem + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_word where chineseName like ? order by chineseName", new String[]{"%" + searchItem + "%"}));
			
		}
		
		return tmp;
	}

	public String[] searchHotWord() {
		Session s = hibernateTemplate.getSessionFactory().getCurrentSession();
		// TODO Auto-generated method stub
		List<Object> tmp =  (List<Object>)s.createSQLQuery("select name from t_hotword order by times desc limit 10").list();
		String[] t = new String[10];
		for(int i=0;i<10;i++){
			t[i] = tmp.get(i).toString();
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	public List<Object> searchItemFromItem_Dic_ByYear(String searchItem, String year) {
		// TODO Auto-generated method stub
		List<Object> tmp = new ArrayList<Object>();
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where englishName = ? and standard like ? order by englishName",
							new String[] { searchItem,"%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where englishName = ? and pubdate like ? order by englishName",
							new String[] {  searchItem,"%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_word where englishName = ? and pubdate like ? order by englishName",
							new String[] {  searchItem,"%" + year + "%"}));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		} else {
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where chineseName = ? and standard like ? order by chineseName",
							new String[] { searchItem,"%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where chineseName = ? and pubdate like ? order by chineseName",
							new String[] { searchItem,"%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_word where chineseName = ? and pubdate like ? order by chineseName",
							new String[] { searchItem,"%" + year + "%"}));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		}
		if (tmp.size() > 0) {
			return tmp;
		} else {
			return null;
		}

	}

	public List<Object> searchItemFromItem_Dic_ByYear_Like(String searchItem,
			String year) {
		List<Object> tmp = new ArrayList<Object>();
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where englishName like ? and standard like ? order by englishName",
							new String[] { "%" + searchItem + "%","%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where englishName like ? and pubdate like ? order by englishName",
							new String[] {  "%" + searchItem + "%","%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_word where englishName like ? and pubdate like ? order by englishName",
							new String[] {  "%" + searchItem + "%","%" + year + "%"}));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		} else {
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where chineseName like ? and standard like ? order by chineseName",
							new String[] { "%" + searchItem + "%","%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where chineseName like ? and pubdate like ? order by chineseName",
							new String[] { "%" + searchItem + "%","%" + year + "%"}));
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_word where chineseName like ? and pubdate like ? order by chineseName",
							new String[] { "%" + searchItem + "%","%" + year + "%"}));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		}
		if (tmp.size() > 0) {
			return tmp;
		} else {
			return null;
		}
	}

	public List<Object> searchItemFromItem_Dic_Split_ByYear(List<String> split,
			String year) {
		List<Object> tmp = new ArrayList<Object>();
		for(String searchItem:split){
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_item where chineseName like ? and standard like ? order by chineseName", new String[]{ searchItem ,"%" + year}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_dictionary where chineseName like ? and standard like ? order by chineseName", new String[]{ searchItem ,"%" + year}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_word where chineseName like ? and standard like ? order by chineseName", new String[]{ searchItem ,"%" + year}));
		}
		
		return tmp;
	}

	public List<Object> searchItemFromItem_Dic_Split_ByYear_Like(
			List<String> split, String year) {
		List<Object> tmp = new ArrayList<Object>();
		for(String searchItem:split){
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_item where chineseName like ? and standard like ? order by chineseName", new String[]{"%" +searchItem + "%","%" + year}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_dictionary where chineseName like ? and standard like ? order by chineseName", new String[]{ "%" +searchItem + "%","%" + year}));
			tmp.addAll((List<Object>) hibernateTemplate.find("from t_word where chineseName like ? and standard like ? order by chineseName", new String[]{ "%" +searchItem + "%","%" + year}));
		}
		
		return tmp;
	}

	public List<Object> searchItemFromItem_Dic_BySource(String searchItem,
			String source) {
		// TODO Auto-generated method stub
				List<Object> tmp = new ArrayList<Object>();
				if (JudgeEnOrZh.isEnglish(searchItem)) {
					if ("气象标准术语库".equals(source)){
						tmp.addAll((List<Object>) hibernateTemplate
								.find("from t_item where englishName = ? order by englishName",
										new String[] { searchItem }));
					}
					
					else if ("冰冻圈科学辞典".equals(source)){
						tmp.addAll((List<Object>) hibernateTemplate
								.find("from t_dictionary where englishName = ? order by englishName",
										new String[] {  searchItem }));
					}
					
					else{
						tmp.addAll((List<Object>) hibernateTemplate
								.find("from t_word where englishName = ? order by englishName",
										new String[] {  searchItem }));
					}
						
					for (Object o : tmp) {
						System.out.println(o.getClass());
					}
				} else {
					if ("气象标准术语库".equals(source)){
						tmp.addAll((List<Object>) hibernateTemplate
								.find("from t_item where chineseName = ? order by chineseName",
										new String[] { searchItem }));
					}
					
					else if ("冰冻圈科学辞典".equals(source)){
						tmp.addAll((List<Object>) hibernateTemplate
								.find("from t_dictionary where chineseName = ? order by chineseName",
										new String[] { searchItem }));
					}
					
					else{
						tmp.addAll((List<Object>) hibernateTemplate
								.find("from t_word where chineseName = ? order by chineseName",
										new String[] { searchItem }));
					}
						
					for (Object o : tmp) {
						System.out.println(o.getClass());
					}
				}
				if (tmp.size() > 0) {
					return tmp;
				} else {
					return null;
				}

	}

	public List<Object> searchItemFromItem_Dic_BySource_Like(String searchItem,
			String source) {
		// TODO Auto-generated method stub
		List<Object> tmp = new ArrayList<Object>();
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			if ("气象标准术语库".equals(source))
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where englishName like ? order by englishName",
							new String[] { "%"+ searchItem +"%" }));
			else if ("冰冻圈科学辞典".equals(source))
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where englishName like ? order by englishName",
							new String[] {"%"+searchItem+"%" }));
			else
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_word where englishName like ? order by englishName",
								new String[] {"%"+searchItem+"%" }));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		} else {
			if ("气象标准术语库".equals(source))
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_item where chineseName like ? order by chineseName",
							new String[] { "%"+searchItem +"%"}));
			else if ("冰冻圈科学辞典".equals(source))
			tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_dictionary where chineseName like ? order by chineseName",
							new String[] {"%"+ searchItem +"%"}));
			else
				tmp.addAll((List<Object>) hibernateTemplate
					.find("from t_word where chineseName like ? order by chineseName",
							new String[] { "%"+searchItem +"%"}));
			for (Object o : tmp) {
				System.out.println(o.getClass());
			}
		}
		if (tmp.size() > 0) {
			return tmp;
		} else {
			return null;
		}

	}

	public List<Object> searchItemFromItem_Dic_Split_BySource(
			List<String> split, String source) {
		// TODO Auto-generated method stub
		List<Object> tmp = new ArrayList<Object>();
		for(String searchItem:split){
			if ("气象标准术语库".equals(source))
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_item where chineseName like ? order by chineseName",
								new String[] { searchItem }));
				else if ("冰冻圈科学辞典".equals(source))
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_dictionary where chineseName like ? order by chineseName",
								new String[] { searchItem }));
				else
					tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_word where chineseName like ? order by chineseName",
								new String[] { searchItem }));
		}
		
		return tmp;
	}

	public List<Object> searchItemFromItem_Dic_Split_BySource_Like(
			List<String> split, String source) {
		// TODO Auto-generated method stub
		List<Object> tmp = new ArrayList<Object>();
		for(String searchItem:split){
			if ("气象标准术语库".equals(source))
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_item where chineseName like ? order by chineseName",
								new String[] { "%" + searchItem + "%"}));
				else if ("冰冻圈科学辞典".equals(source))
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_dictionary where chineseName like ? order by chineseName",
								new String[] { "%" + searchItem + "%"}));
				else
					tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_word where chineseName like ? order by chineseName",
								new String[] { "%" + searchItem + "%"}));
		}
		
		return tmp;
	}

	public List<Standard> SearchByBiaoZhunByYear(String searchItem, String year) {
		List<Standard> tmp = new ArrayList<Standard>();
		
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			tmp.addAll((List<Standard>) hibernateTemplate.find("from t_standard where englishName like ? and stdNO like ? order by englishName", new String[]{"%" + searchItem + "%","%" + year}));
			/*for(Object o: tmp){
				System.out.println(o.getClass());
			}*/
		} else {
			tmp.addAll((List<Standard>) hibernateTemplate.find("from t_standard where chineseName like ? and stdNO like ? order by chineseName", new String[]{"%" + searchItem + "%","%" + year}));
			/*for(Object o: tmp){
				System.out.println(o.getClass());
			}*/
		}
		if(tmp.size()>0){
			return tmp;
		}else{
			return null;
		}
	}

	public void add(HotWord h) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(h);
	}

	public void update(HotWord h) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(h);
	}

	public void delHotWord(String ids) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String[] tmp = ids.split(",");
		for (int i = 0; i < tmp.length; i++) {
			session.createSQLQuery(
					"delete from t_hotword where id = " + "\"" + tmp[i] + "\"")
					.executeUpdate();
		}
	}

	// 得到术语列表信息
	public List<Item> getItemList(int page, int rows) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		return session.createQuery("from t_item").setFirstResult((page-1)*rows).setMaxResults(rows).list();
	}
	//得到术语统计量
	public int getItemTotal() {
		// TODO Auto-generated method stub
		return hibernateTemplate.find("from t_item").size();
	}
	//添加一条术语
	public void add(Item item) {
		// TODO Auto-generated method stub
		hibernateTemplate.save(item);
	}
	//修改一条术语
	public void update(Item item) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(item);
	}
	//删除术语
	public void del(String ids) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		String[] tmp = ids.split(",");
		for (int i = 0; i < tmp.length; i++) {
			session.createSQLQuery(
					"delete from t_item where id = " + "\"" + tmp[i] + "\"")
					.executeUpdate();
		}
	}

	
	public List<Object> searchItemFromItem_Dic_ByYear_BySource(
			String searchItem, String year, String source) {
		List<Object> tmp = new ArrayList<Object>();
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			if ("气象标准术语库".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_item where englishName = ? and standard like ? order by englishName",
								new String[] { searchItem, "%" + year + "%" }));
			}
			else if ("冰冻圈科学辞典".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_dictionary where englishName = ? and pubdate like ? order by englishName",
								new String[] { searchItem, "%" + year + "%" }));
			}
			else {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_word where englishName = ? and pubdate like ? order by englishName",
								new String[] { searchItem, "%" + year + "%" }));
			}
		} else {
			if ("气象标准术语库".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_item where chineseName = ? and standard like ? order by chineseName",
								new String[] { searchItem, "%" + year + "%" }));
			}
			else if ("冰冻圈科学辞典".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_dictionary where chineseName = ? and pubdate like ? order by chineseName",
								new String[] { searchItem, "%" + year + "%" }));
			}
			else {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_word where chineseName = ? and pubdate like ? order by chineseName",
								new String[] { searchItem, "%" + year + "%" }));
			}
		}
		if (tmp.size() > 0) {
			return tmp;
		} else {
			return null;
		}
	}

	public List<Object> searchItemFromItem_Dic_ByYear_BySource_Like(
			String searchItem, String year, String source) {
		List<Object> tmp = new ArrayList<Object>();
		if (JudgeEnOrZh.isEnglish(searchItem)) {
			if ("气象标准术语库".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_item where englishName like ? and standard like ? order by englishName",
								new String[] { "%" + searchItem + "%",
										"%" + year + "%" }));
			}
			else if ("冰冻圈科学辞典".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_dictionary where englishName like ? and pubdate like ? order by englishName",
								new String[] { "%" + searchItem + "%",
										"%" + year + "%" }));
			}
			else {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_word where englishName like ? and pubdate like ? order by englishName",
								new String[] { "%" + searchItem + "%",
										"%" + year + "%" }));
			}
		} else {
			if ("气象标准术语库".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_item where chineseName like ? and standard like ? order by chineseName",
								new String[] { "%" + searchItem + "%",
										"%" + year + "%" }));
			}
			else if ("冰冻圈科学辞典".equals(source)) {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_dictionary where chineseName like ? and pubdate like ? order by chineseName",
								new String[] { "%" + searchItem + "%",
										"%" + year + "%" }));
			}
			else {
				tmp.addAll((List<Object>) hibernateTemplate
						.find("from t_word where chineseName like ? and pubdate like ? order by chineseName",
								new String[] { "%" + searchItem + "%",
										"%" + year + "%" }));
			}
		}
		if (tmp.size() > 0) {
			return tmp;
		} else {
			return null;
		}
	}

	
	
	public Map<String, String> fillTreeView_shuyu(String searchItem, String queryMode) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		List<Object[]> shuyu = new ArrayList<Object[]>();
		Object item = new Object();
		Object dic = new Object();
		Object word = new Object();
		if("jingque".equals(queryMode)){
			if(JudgeEnOrZh.isEnglish(searchItem)){
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
							"((select year,count(1) as cnt from " +  
							"(select SUBSTR(standard,-4) as year from t_item where englishName='" + searchItem +"') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_dictionary where englishName='" + searchItem +"') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_word where englishName='" + searchItem +"') as tmp " +
							"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = session.createSQLQuery(
						"select count(1) from t_item where englishName='" + searchItem +"'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where englishName='" + searchItem +"'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where englishName='" + searchItem +"'").list();
			}else{
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(standard,-4) as year from t_item where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
									+ " union all " +
								"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_dictionary where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
									+ " union all " +
								"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_word where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = session.createSQLQuery(
						"select count(1) from t_item where chineseName='" + searchItem +"'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where chineseName='" + searchItem +"'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where chineseName='" + searchItem +"'").list();
			}
		}else{
			if(JudgeEnOrZh.isEnglish(searchItem)){
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
							"((select year,count(1) as cnt from " +  
							"(select SUBSTR(standard,-4) as year from t_item where englishName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_dictionary where englishName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_word where englishName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = session.createSQLQuery(
						"select count(1) from t_item where englishName like '%" + searchItem +"%'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where englishName like '%" + searchItem +"%'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where englishName like '%" + searchItem +"%'").list();
				
			}else{
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
							"((select year,count(1) as cnt from " +  
							"(select SUBSTR(standard,-4) as year from t_item where chineseName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_dictionary where chineseName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_word where chineseName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = session.createSQLQuery(
						"select count(1) from t_item where chineseName like '%" + searchItem +"%'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where chineseName like '%" + searchItem +"%'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where chineseName like '%" + searchItem +"%'").list();
			}
		}
		
		Map<String,String> statistics = new HashMap<String, String>();
		for(Object[] t:shuyu){
			statistics.put(t[0].toString(), t[1].toString());
		}
		statistics.put("item", item.toString());
		statistics.put("dic", dic.toString());
		statistics.put("word", word.toString());
		return statistics;
	}
	
	
	public Map<String, String> fillTreeView_shuyu_year(String searchItem,
			String queryMode, String year) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		List<Object[]> shuyu = new ArrayList<Object[]>();
		Object item = new Object();
		Object dic = new Object();
		Object word = new Object();
		if("jingque".equals(queryMode)){
			if(JudgeEnOrZh.isEnglish(searchItem)){
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
							"((select year,count(1) as cnt from " +  
							"(select SUBSTR(standard,-4) as year from t_item where englishName='" + searchItem +"') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_dictionary where englishName='" + searchItem +"') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_word where englishName='" + searchItem +"') as tmp " +
							"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = session.createSQLQuery(
						"select count(1) from t_item where englishName='" + searchItem +"' and standard like '%" + year + "%'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where englishName='" + searchItem +"' and pubdate like '%" + year + "%'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where englishName='" + searchItem +"' and pubdate like '%" + year + "%'").list();
			}else{
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(standard,-4) as year from t_item where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
									+ " union all " +
								"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_dictionary where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
									+ " union all " +
								"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_word where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = session.createSQLQuery(
						"select count(1) from t_item where chineseName='" + searchItem +"' and standard like '%" + year + "%'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where chineseName='" + searchItem +"' and pubdate like '%" + year + "%'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where chineseName='" + searchItem +"' and pubdate like '%" + year + "%'").list();
			}
		}else{
			if(JudgeEnOrZh.isEnglish(searchItem)){
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
							"((select year,count(1) as cnt from " +  
							"(select SUBSTR(standard,-4) as year from t_item where englishName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_dictionary where englishName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_word where englishName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = session.createSQLQuery(
						"select count(1) from t_item where englishName like '%" + searchItem +"%' and standard like '%" + year + "%'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where englishName like '%" + searchItem +"%' and pubdate like '%" + year + "%'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where englishName like '%" + searchItem +"%' and pubdate like '%" + year + "%'").list();
			}else{
				shuyu = session.createSQLQuery(
						"select year,sum(cnt) from " + 
							"((select year,count(1) as cnt from " +  
							"(select SUBSTR(standard,-4) as year from t_item where chineseName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_dictionary where chineseName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)"
								+ " union all " +
							"(select year,count(1) as cnt from " +  
							"(select SUBSTR(pubdate,-4) as year from t_word where chineseName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year)) t " +
						"group by t.year"
						).list();
				item = (Object)session.createSQLQuery(
						"select count(1) from t_item where chineseName like '%" + searchItem +"%' and standard like '%" + year + "%'").list();
				dic = (Object)session.createSQLQuery(
						"select count(1) from t_dictionary where chineseName like '%" + searchItem +"%' and pubdate like '%" + year + "%'").list();
				word = (Object)session.createSQLQuery(
						"select count(1) from t_word where chineseName like '%" + searchItem +"%' and pubdate like '%" + year + "%'").list();
			}
		}
		
		Map<String,String> statistics = new HashMap<String, String>();
		for(Object[] t:shuyu){
			statistics.put(t[0].toString(), t[1].toString());
		}
			statistics.put("item", item.toString());
			statistics.put("dic", dic.toString());
			statistics.put("word", word.toString());
		
		return statistics;
	}

	public Map<String, String> fillTreeView_shuyu_source(String searchItem,
			String queryMode, String source) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		List<Object[]> shuyu = new ArrayList<Object[]>();
		Object item = new Object();
		Object dic = new Object();
		Object word = new Object();
		if("jingque".equals(queryMode)){
			if(JudgeEnOrZh.isEnglish(searchItem)){
				item = session.createSQLQuery(
						"select count(1) from t_item where englishName='" + searchItem +"'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where englishName='" + searchItem +"'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where englishName='" + searchItem +"'").list();
				if("气象标准术语库".equals(source)){
					shuyu = session.createSQLQuery(
							"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(standard,-4) as year from t_item where englishName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
							).list();
				}else if("冰冻圈科学辞典".equals(source)){
					shuyu = session.createSQLQuery(
							"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_dictionary where englishName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
							).list();
				}else{
					shuyu = session.createSQLQuery(
							"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_word where englishName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
							).list();
				}
			}else{
				item = session.createSQLQuery(
						"select count(1) from t_item where chineseName='" + searchItem +"'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where chineseName='" + searchItem +"'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where chineseName='" + searchItem +"'").list();
				if("气象标准术语库".equals(source)){
					shuyu = session.createSQLQuery(
							"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(standard,-4) as year from t_item where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
							).list();
				}else if("冰冻圈科学辞典".equals(source)){
					shuyu = session.createSQLQuery(
							"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_dictionary where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
							).list();
				}else{
					shuyu = session.createSQLQuery(
							"select year,sum(cnt) from " + 
								"((select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_word where chineseName='" + searchItem +"') as tmp " +
								"group by tmp.year)"
							).list();
				}
			}
		}else{
			if(JudgeEnOrZh.isEnglish(searchItem)){
				item = session.createSQLQuery(
						"select count(1) from t_item where englishName like '%" + searchItem +"%'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where englishName like '%" + searchItem +"%'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where englishName like '%" + searchItem +"%'").list();
				if("气象标准术语库".equals(source)){
					shuyu = session.createSQLQuery(
							"(select year,count(1) as cnt from " +  
								"(select SUBSTR(standard,-4) as year from t_item where englishName like '%" + searchItem +"%') as tmp " +
								"group by tmp.year)"
							).list();
				}else if("冰冻圈科学辞典".equals(source)){
					shuyu = session.createSQLQuery(
							"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_dictionary where englishName like '%" + searchItem +"%') as tmp " +
								"group by tmp.year)"
							).list();
				}else{
					shuyu = session.createSQLQuery(
							"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_word where englishName like '%" + searchItem +"%') as tmp " +
								"group by tmp.year)"
							).list();
				}
				
			}else{
				item = session.createSQLQuery(
						"select count(1) from t_item where chineseName like '%" + searchItem +"%'").list();
				dic = session.createSQLQuery(
						"select count(1) from t_dictionary where chineseName like '%" + searchItem +"%'").list();
				word = session.createSQLQuery(
						"select count(1) from t_word where chineseName like '%" + searchItem +"%'").list();
				if("气象标准术语库".equals(source)){
					shuyu = session.createSQLQuery(
							"(select year,count(1) as cnt from " +  
								"(select SUBSTR(standard,-4) as year from t_item where chineseName like '%" + searchItem +"%') as tmp " +
								"group by tmp.year)"
							).list();
				}else if("冰冻圈科学辞典".equals(source)){
					shuyu = session.createSQLQuery(
							"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_dictionary where chineseName like '%" + searchItem +"%') as tmp " +
								"group by tmp.year)"
							).list();
				}else{
					shuyu = session.createSQLQuery(
							"(select year,count(1) as cnt from " +  
								"(select SUBSTR(pubdate,-4) as year from t_word where chineseName like '%" + searchItem +"%') as tmp " +
								"group by tmp.year)"
							).list();
				}
			}
		}
		
		Map<String,String> statistics = new HashMap<String, String>();
		for(Object[] t:shuyu){
			statistics.put(t[0].toString(), t[1].toString());
		}
		statistics.put("item", item.toString());
		statistics.put("dic", dic.toString());
		statistics.put("word", word.toString());
		return statistics;
	}

	public Map<String, String> fillTreeView_shuyu_biaozhun(String searchItem,
			String year) {
		// TODO Auto-generated method stub
		Session session = hibernateTemplate.getSessionFactory()
				.getCurrentSession();
		List<Object[]> biaozhun = new ArrayList<Object[]>();
			if(JudgeEnOrZh.isEnglish(searchItem)){
				biaozhun = session.createSQLQuery(
							"select year,count(1) as cnt from " +  
							"(select SUBSTR(stdNO,-4) as year from t_standard where englishName like '%" + searchItem +"%') as tmp " +
							"group by tmp.year"
						).list();
			}else{
				biaozhun = session.createSQLQuery(
						"select year,count(1) as cnt from " +  
						"(select SUBSTR(stdNO,-4) as year from t_standard where chineseName like '%" + searchItem +"%') as tmp " +
						"group by tmp.year"
					).list();
			}
		
		
		Map<String,String> statistics = new HashMap<String, String>();
		for(Object[] t:biaozhun){
			statistics.put(t[0].toString(), t[1].toString());
		}
		return statistics;
	}

	public Map<String, String> fillTreeView_shuyu_year_source(
			String searchItem, String queryMode, String year, String source) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
}
