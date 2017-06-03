package com.eric.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.eric.dao.ItemDAO;
import com.eric.model.HotWord;
import com.eric.model.Item;
import com.eric.model.Standard;
@Component
public class ItemService {
	private ItemDAO itemDAO;

	public ItemDAO getItemDAO() {
		return itemDAO;
	}

	@Resource
	public void setItemDAO(ItemDAO itemDAO) {
		this.itemDAO = itemDAO;
	}

	
	

	@Transactional
	public void addOrUpdateHotWord(String searchItem) {
		// TODO Auto-generated method stub
		itemDAO.addOrUpdateHotWord(searchItem);
	}
	@Transactional
	public void addOrUpdateNewWord(String searchItem, String userName) {
		// TODO Auto-generated method stub
		itemDAO.addOrUpdateNewWord(searchItem,userName);
	}

	
	// �����������
	public List<Item> searchItemByItemName(String searchItem) {
			return itemDAO.searchItemByItemName(searchItem);
	}
	public List<Item> searchItemByItemNameLike(String searchItem) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemByItemName_Like(searchItem);
	}

	public List<Item> searchItemByItemNameLikeLimit10(String searchItem) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemByItemName_Like_Limit10(searchItem);
	}

	public List<Object> searchItemFromItem_Dic(String searchItem) {
		return itemDAO.searchItemFromItem_Dic(searchItem);
	}

	public List<Object> searchItemFromItem_Dic_Like(String searchItem) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_Like(searchItem);
	}

	public List<Standard> SearchByBiaoZhun(String searchItem, String year) {
		// TODO Auto-generated method stub
		return itemDAO.SearchByBiaoZhun(searchItem,year);
	}

	public Standard searchStd(String searchStd) {
		// TODO Auto-generated method stub
		return itemDAO.searchStd(searchStd);
	}

	public List<Standard> SearchToBiaoZhun(String stdno) {
		// TODO Auto-generated method stub
		return itemDAO.SearchToBiaoZhun(stdno);
	}

	public List<Object> searchItemFromItem_Dic_Split(List<String> split) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_Split(split);
	}
	
	public List<Object> searchItemFromItem_Dic_Split_Like(List<String> split) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_Split_Like(split);
	}

	
	//查询热词
	public String[] searchHotWord() {
		// TODO Auto-generated method stub
		return itemDAO.searchHotWord();
	}

	public List<Object> searchItemFromItem_Dic_ByYear(String searchItem, String year) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_ByYear(searchItem,year);
	}

	public List<Object> searchItemFromItem_Dic_ByYear_Like(String searchItem,
			String year) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_ByYear_Like(searchItem,year);
	}

	public List<Object> searchItemFromItem_Dic_Split_ByYear(List<String> split,
			String year) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_Split_ByYear(split,year);
	}

	public List<Object> searchItemFromItem_Dic_Split_ByYear_Like(
			List<String> split, String year) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_Split_ByYear_Like(split,year);
	}

	public List<Object> searchItemFromItem_Dic_BySource(String searchItem,
			String source) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_BySource(searchItem,source);
	}

	public List<Object> searchItemFromItem_Dic_BySource_Like(String searchItem,
			String source) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_BySource_Like(searchItem,source);
	}

	public List<Object> searchItemFromItem_Dic_Split_BySource(
			List<String> split, String source) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_Split_BySource(split,source);
	}

	public List<Object> searchItemFromItem_Dic_Split_BySource_Like(
			List<String> split, String source) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_Split_BySource_Like(split,source);
	}

	public List<Standard> SearchByBiaoZhunByYear(String searchItem, String year) {
		// TODO Auto-generated method stub
		return itemDAO.SearchByBiaoZhunByYear(searchItem,year);
	}

	@Transactional
	public void add(HotWord h) {
		// TODO Auto-generated method stub
		itemDAO.add(h);
	}
	@Transactional
	public void update(HotWord h) {
		// TODO Auto-generated method stub
		itemDAO.update(h);
	}
	@Transactional
	public void delHotWord(String ids) {
		// TODO Auto-generated method stub
		itemDAO.delHotWord(ids);
	}

	//得到术语列表信息
	public List<Item> getItemList(int page, int rows) {
		// TODO Auto-generated method stub
		return itemDAO.getItemList(page,rows);
	}
	//得到术语统计量
	public int getItemTotal() {
		// TODO Auto-generated method stub
		return itemDAO.getItemTotal();
	}
	//添加一条术语
	@Transactional
	public void add(Item item) {
		// TODO Auto-generated method stub
		itemDAO.add(item);
	}
	//修改一条术语
	@Transactional
	public void update(Item item) {
		// TODO Auto-generated method stub
		itemDAO.update(item);
	}
	//删除术语
	@Transactional
	public void del(String ids) {
		// TODO Auto-generated method stub
		itemDAO.del(ids);
	}

	

	public List<Object> searchItemFromItem_Dic_ByYear_BySource(
			String searchItem, String year, String source) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_ByYear_BySource(searchItem, year, source);
	}

	public List<Object> searchItemFromItem_Dic_ByYear_BySource_Like(
			String searchItem, String year, String source) {
		// TODO Auto-generated method stub
		return itemDAO.searchItemFromItem_Dic_ByYear_BySource_Like(searchItem, year, source);
	}

	// 填充树形列表
	public Map<String, String> fillTreeView_shuyu_all(String searchItem,
			String queryMode) {
		// TODO Auto-generated method stub
		return itemDAO.fillTreeView_shuyu(searchItem, queryMode);
	}

	public Map<String, String> fillTreeView_shuyu_year(String searchItem,
			String queryMode, String year) {
		// TODO Auto-generated method stub
		return itemDAO.fillTreeView_shuyu_year(searchItem, queryMode,year);
	}

	public Map<String, String> fillTreeView_shuyu_source(String searchItem,
			String queryMode, String source) {
		// TODO Auto-generated method stub
		return itemDAO.fillTreeView_shuyu_source(searchItem, queryMode,source);
	}

	public Map<String, String> fillTreeView_shuyu_biaozhun(String searchItem,
			String year) {
		// TODO Auto-generated method stub
		return itemDAO.fillTreeView_shuyu_biaozhun(searchItem, year);
	}

	public Map<String, String> fillTreeView_shuyu_year_source(
			String searchItem, String queryMode, String year, String source) {
		// TODO Auto-generated method stub
		return itemDAO.fillTreeView_shuyu_year_source(searchItem, queryMode,year,source);
	}
	

	
	
	
}
