package com.eric.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.eric.dto.SearchInfo;
import com.eric.model.HotWord;
import com.eric.model.Item;
import com.eric.model.Standard;
import com.eric.service.ItemService;
import com.eric.utils.HighLightUtil;
import com.eric.utils.JudgeEnOrZh;
import com.eric.utils.SearchInfoCompator_Ch;
import com.eric.utils.SearchInfoCompator_En;
import com.eric.utils.SplitWord;
import com.opensymphony.xwork2.ActionSupport;

@Component("itemAction")
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/item")
public class ItemAction extends ActionSupport implements ServletRequestAware,
		ServletResponseAware {
	private ItemService itemService;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String searchItem;
	// 是根据术语查还是根据标准查
	private String mode;
	// 根据术语查询是模糊查询还是精确查询
	private String queryMode;
	// 新的标准查询(no)，还是跳转到标准页面(yes)
	private String isToStd;
	// 需要跳转的标准号
	private String stdno;
	// 年份查询
	private String year;
	// 来源查询
	private String source;

	@Action(value = "itemAction", results = {
			@Result(name = "listInfo", location = "/WEB-INF/page/listInfo.jsp"),
			@Result(name = "noResult", location = "/WEB-INF/page/listInfoNoResult.jsp"),
			@Result(name = "listInfoStandard", location = "/WEB-INF/page/listInfo_standard.jsp"),
			@Result(name = "standard_jsp", location = "/WEB-INF/page/standard_detail.jsp") })
	
	//查询统一入口
	public String itemSearch() throws ServletException, IOException {
		if ("shuyu".equals(mode)) {
			if(!"年份".equals(year)&&"来源(全部)".equals(source)){
				System.err.println("按照年份查询");
				return searchItemFromItem_Dic_ByYear();
			}
			if("年份".equals(year)&&!"来源(全部)".equals(source)){
				System.out.println("按照来源查询");
				return searchItemFromItem_Dic_BySource();
			}
			if(!"年份".equals(year)&&!"来源(全部)".equals(source)){
				System.out.println("年份与来源叠加查询");
				return searchItemFromItem_Dic_ByYear_BySource();
			}
			// 术语和词典的查询结果显示在一起
			return searchItemFromItem_Dic();
		}else if("biaozhun".equals(mode)){
			return itemSearchByBiaoZhun();
		}else{
			return itemSearchByCiDian();
		} 
	}

	


	private String searchItemFromItem_Dic_ByYear_BySource() throws IOException {
		searchItem = searchItem.trim();
		
		
		// 搜索热词
		String[] hot = itemService.searchHotWord();
		long start = System.currentTimeMillis();
		List<SearchInfo> queryResult = HighLightUtil
				.highLightProcess(itemService.searchItemFromItem_Dic_ByYear_BySource(
						searchItem,year, source), searchItem, true);
		long end = System.currentTimeMillis();
		float time = (float) ((end - start) * 1.0 / 1000);
		start = System.currentTimeMillis();
		List<SearchInfo> queryResultLike = HighLightUtil.highLightProcess(
				itemService.searchItemFromItem_Dic_ByYear_BySource_Like(searchItem,
						year,source), searchItem, true);
		end = System.currentTimeMillis();
		float timeLike = (float) ((end - start) * 1.0 / 1000);

		String userName = (String) request.getSession()
				.getAttribute("userName");
		// 判断对否为登录状态
		boolean isLoginOn = (userName != null) ? true : false;
		if (queryResultLike != null && queryResultLike.size() > 0) {
			if (queryMode.equals("jingque")) {
				if (queryResult != null && queryResult.size() > 0) {
					if (JudgeEnOrZh.isEnglish(searchItem)) {
						Collections.sort(queryResult,
								new SearchInfoCompator_En());
					} else {
						Collections.sort(queryResult,
								new SearchInfoCompator_Ch());
					}
				} else {
					queryResult = new ArrayList<SearchInfo>();
				}

				request.setAttribute("queryResult", queryResult);
				request.setAttribute("useTime", time);
				request.setAttribute("itemNum", queryResult.size());
			} else {
				if (JudgeEnOrZh.isEnglish(searchItem)) {
					Collections.sort(queryResultLike,
							new SearchInfoCompator_En());
				} else {
					Collections.sort(queryResultLike,
							new SearchInfoCompator_Ch());
				}
				request.setAttribute("queryResult", queryResultLike);
				request.setAttribute("useTime", timeLike);
				request.setAttribute("itemNum", queryResultLike.size());
			}
			if (isLoginOn) {
				itemService.addOrUpdateHotWord(searchItem);
			}
			request.setAttribute("source", source);
			request.setAttribute("hotResult", hot);
			request.setAttribute("mode", mode);
			return "listInfo";
		} else {
			// 如果是中文，并且未找到结果，则进行分词
			if (!JudgeEnOrZh.isEnglish(searchItem)) {
				List<String> split = SplitWord.split(searchItem);
				start = System.currentTimeMillis();
				queryResult = HighLightUtil.highLightProcess(itemService
						.searchItemFromItem_Dic_Split_BySource(split, source),
						split, true);
				end = System.currentTimeMillis();
				time = (float) ((end - start) * 1.0 / 1000);
				start = System.currentTimeMillis();
				queryResultLike = HighLightUtil.highLightProcess(itemService
						.searchItemFromItem_Dic_Split_BySource_Like(split,
								source), split, true);
				end = System.currentTimeMillis();
				time = (float) ((end - start) * 1.0 / 1000);
				if (queryResultLike != null && queryResultLike.size() > 0) {
					if (queryMode.equals("jingque")) {
						if (queryResult != null && queryResult.size() > 0) {
							Collections.sort(queryResult,
									new SearchInfoCompator_Ch());
						} else {
							queryResult = new ArrayList<SearchInfo>();
						}
						request.setAttribute("queryResult", queryResult);
						request.setAttribute("useTime", time);
						request.setAttribute("itemNum", queryResult.size());
						request.setAttribute("hotResult", hot);
						return "listInfo";
					} else {
						Collections.sort(queryResultLike,
								new SearchInfoCompator_Ch());
						request.setAttribute("queryResult", queryResultLike);
						request.setAttribute("useTime", timeLike);
						request.setAttribute("itemNum", queryResultLike.size());
						request.setAttribute("hotResult", hot);
						return "listInfo";
					}
				} else {
					request.setAttribute("mode", mode);
					request.setAttribute("hotResult", hot);
					return "listInfo";
				}
			}
			request.setAttribute("mode", mode);
			request.setAttribute("hotResult", hot);
			return "listInfo";// noResult
		}
	}




	// 根据标准进行查询
	private String itemSearchByBiaoZhun() {
		searchItem = searchItem.trim();
		if (stdno != null) {
			stdno = stdno.trim();
		}
		Map<String,String> fill = itemService.fillTreeView_shuyu_biaozhun(searchItem,year);
		request.getSession().setAttribute("fill", fill);
		
		//搜索热词
		String[] hot = itemService.searchHotWord();

		List<Standard> queryResult = null;
		float time = 0f;
		if ("no".equals(isToStd)) {
			long start = System.currentTimeMillis();
			queryResult = HighLightUtil.highLightProcess(
					itemService.SearchByBiaoZhun(searchItem,year), searchItem, 1);
			long end = System.currentTimeMillis();
			time = (float) ((end - start) * 1.0 / 1000);
		} else {
			long start = System.currentTimeMillis();
			queryResult = itemService.SearchToBiaoZhun(stdno);
			long end = System.currentTimeMillis();
			time = (float) ((end - start) * 1.0 / 1000);
		}

		String userName = (String) request.getSession()
				.getAttribute("userName");
		// 判断对否为登录状态
		boolean isLoginOn = (userName != null) ? true : false;
		if (queryResult != null && queryResult.size() > 0) {
			request.setAttribute("queryResult", queryResult);
			request.setAttribute("useTime", time);
			request.setAttribute("itemNum", queryResult.size());
			request.setAttribute("mode", mode);
			request.setAttribute("hotResult", hot);
			return "listInfoStandard";
		}
		request.setAttribute("year", year);
		request.setAttribute("mode", mode);
		request.setAttribute("hotResult", hot);
		return "noResult";
	}

	//跳转到标准列表
	public String toStandardList() {
		searchItem = searchItem.trim();
		stdno = stdno.trim();
		List<Standard> queryResult = null;
		float time = 0f;
		if ("yes".equals(isToStd)) {
			long start = System.currentTimeMillis();
			queryResult = HighLightUtil.highLightProcess(
					itemService.SearchByBiaoZhun(searchItem, year), searchItem, 1);
			long end = System.currentTimeMillis();
			time = (float) ((end - start) * 1.0 / 1000);
		} else {
			long start = System.currentTimeMillis();
			queryResult = itemService.SearchToBiaoZhun(stdno);
			long end = System.currentTimeMillis();
			time = (float) ((end - start) * 1.0 / 1000);
		}

		String userName = (String) request.getSession()
				.getAttribute("userName");
		// 判断对否为登录状态
		boolean isLoginOn = (userName != null) ? true : false;
		if (queryResult != null && queryResult.size() > 0) {
			request.setAttribute("queryResult", queryResult);
			request.setAttribute("useTime", time);
			request.setAttribute("itemNum", queryResult.size());
			request.setAttribute("mode", mode);
			//request.setAttribute("isToStd", isToStd);
			return "listInfoStandard";
		}
		return "noResult";
	}

	
	//跳转到标准列表(byYear)
		public String toStandardListByYear() {
			searchItem = searchItem.trim();
			String year = (String)request.getParameter("year");
			//stdno = stdno.trim();
			List<Standard> queryResult = null;
			String[] hot = itemService.searchHotWord();
			float time = 0f;
			//if ("yes".equals(isToStd)) {
				long start = System.currentTimeMillis();
				queryResult = HighLightUtil.highLightProcess(
						itemService.SearchByBiaoZhunByYear(searchItem,year), searchItem, 1);
				long end = System.currentTimeMillis();
				time = (float) ((end - start) * 1.0 / 1000);
			//} else {
//				long start = System.currentTimeMillis();
//				queryResult = itemService.SearchToBiaoZhun(stdno);
//				long end = System.currentTimeMillis();
//				time = (float) ((end - start) * 1.0 / 1000);
			//}

			String userName = (String) request.getSession()
					.getAttribute("userName");
			// 判断对否为登录状态
			boolean isLoginOn = (userName != null) ? true : false;
			if (queryResult != null && queryResult.size() > 0) {
				request.setAttribute("queryResult", queryResult);
				request.setAttribute("useTime", time);
				request.setAttribute("itemNum", queryResult.size());
				request.setAttribute("mode", mode);
				request.setAttribute("hotResult", hot);
				return "listInfoStandard";
			}
			return "noResult";
		}

	// 查询词典(已与术语合并查询)
	private String itemSearchByCiDian() {
		// TODO Auto-generated method stub
		return null;
	}

	// 根据搜索词进行查询
	public String itemSearchByItemName() {
		long start = System.currentTimeMillis();
		List<Item> queryResult = HighLightUtil.highLightProcess(
				itemService.searchItemByItemName(searchItem), searchItem);
		long end = System.currentTimeMillis();
		float time = (float) ((end - start) * 1.0 / 1000);
		start = System.currentTimeMillis();
		List<Item> queryResultLike = HighLightUtil.highLightProcess(
				itemService.searchItemByItemNameLike(searchItem), searchItem);
		end = System.currentTimeMillis();
		float timeLike = (float) ((end - start) * 1.0 / 1000);
		String userName = (String) request.getSession()
				.getAttribute("userName");
		// 是否为登录状态
		boolean isLoginOn = (userName != null) ? true : false;
		if (queryResultLike.size() > 0) {
			if (isLoginOn) {
				if (queryMode.equals("jingque")) {
					request.setAttribute("queryResult", queryResult);
					request.setAttribute("useTime", time);
					request.setAttribute("itemNum", queryResult.size());
				} else {
					request.setAttribute("queryResult", queryResultLike);
					request.setAttribute("useTime", timeLike);
					request.setAttribute("itemNum", queryResultLike.size());
				}
				itemService.addOrUpdateHotWord(searchItem);
			} else {
				if (queryMode.equals("jingque")) {
					request.setAttribute("queryResult", queryResult);
					request.setAttribute("useTime", time);
					request.setAttribute("itemNum", queryResult.size());
				} else {
					request.setAttribute("queryResult", queryResultLike);
					request.setAttribute("useTime", timeLike);
					request.setAttribute("itemNum", queryResultLike.size());
				}
			}
			return "listInfo";
		} else {
			if (isLoginOn) {
				itemService.addOrUpdateNewWord(searchItem, userName);
			}
			return "noResult";
		}
	}

	//从术语和词典表中查询数据
	public String searchItemFromItem_Dic() throws ServletException, IOException {
		searchItem = searchItem.trim();
		//搜索热词
		String[] hot = itemService.searchHotWord();
		Map<String,String> fill = itemService.fillTreeView_shuyu_all(searchItem,queryMode);
		request.getSession().setAttribute("fill", fill);
		long start = System.currentTimeMillis();
		List<SearchInfo> queryResult = HighLightUtil.highLightProcess(
				itemService.searchItemFromItem_Dic(searchItem), searchItem,
				true);
		long end = System.currentTimeMillis();
		float time = (float) ((end - start) * 1.0 / 1000);
		start = System.currentTimeMillis();
		List<SearchInfo> queryResultLike = HighLightUtil.highLightProcess(
				itemService.searchItemFromItem_Dic_Like(searchItem),
				searchItem, true);
		end = System.currentTimeMillis();
		float timeLike = (float) ((end - start) * 1.0 / 1000);

		String userName = (String) request.getSession()
				.getAttribute("userName");
		// 判断对否为登录状态
		boolean isLoginOn = (userName != null) ? true : false;
		if (queryResultLike != null && queryResultLike.size() > 0) {
			if (queryMode.equals("jingque")) {
				if (queryResult != null && queryResult.size() > 0) {
					if (JudgeEnOrZh.isEnglish(searchItem)) {
						Collections.sort(queryResult,
								new SearchInfoCompator_En());
					} else {
						Collections.sort(queryResult,
								new SearchInfoCompator_Ch());
					}
				} else {
					queryResult = new ArrayList<SearchInfo>();
				}
				
				request.setAttribute("queryResult", queryResult);
				request.setAttribute("useTime", time);
				request.setAttribute("itemNum", queryResult.size());
			} else {
				if (JudgeEnOrZh.isEnglish(searchItem)) {
					Collections.sort(queryResultLike,
							new SearchInfoCompator_En());
				} else {
					Collections.sort(queryResultLike,
							new SearchInfoCompator_Ch());
				}
				request.setAttribute("queryResult", queryResultLike);
				request.setAttribute("useTime", timeLike);
				request.setAttribute("itemNum", queryResultLike.size());
			}
			if (isLoginOn) {
				itemService.addOrUpdateHotWord(searchItem);
			}
			request.setAttribute("source", "来源(全部)");
			request.setAttribute("year", "年份");
			request.setAttribute("hotResult", hot);
			request.setAttribute("mode", mode);
			return "listInfo";
		} else {
			// 如果是中文，并且未找到结果，则进行分词
			if (!JudgeEnOrZh.isEnglish(searchItem)) {
				List<String> split = SplitWord.split(searchItem);
				start = System.currentTimeMillis();
				queryResult = HighLightUtil.highLightProcess(
						itemService.searchItemFromItem_Dic_Split(split),
						split, true);
				end = System.currentTimeMillis();
				time = (float) ((end - start) * 1.0 / 1000);
				start = System.currentTimeMillis();
				queryResultLike = HighLightUtil.highLightProcess(
						itemService.searchItemFromItem_Dic_Split_Like(split),
						split, true);
				end = System.currentTimeMillis();
				time = (float) ((end - start) * 1.0 / 1000);
				if (queryResultLike != null && queryResultLike.size() > 0) {
					if (queryMode.equals("jingque")) {
						if (queryResult != null && queryResult.size() > 0) {
							Collections.sort(queryResult,
									new SearchInfoCompator_Ch());
						} else {
							queryResult = new ArrayList<SearchInfo>();
						}
						request.setAttribute("queryResult", queryResult);
						request.setAttribute("useTime", time);
						request.setAttribute("itemNum", queryResult.size());
						request.setAttribute("hotResult", hot);
						return "listInfo";
					} else {
						Collections.sort(queryResultLike,
								new SearchInfoCompator_Ch());
						request.setAttribute("queryResult", queryResultLike);
						request.setAttribute("useTime", timeLike);
						request.setAttribute("itemNum", queryResultLike.size());
						request.setAttribute("hotResult", hot);
						return "listInfo";
					}
				} else {
					if (isLoginOn) {
						itemService.addOrUpdateNewWord(searchItem, userName);
					}
					request.setAttribute("mode", mode);
					request.setAttribute("hotResult", hot);
					return "noResult";
				}
			}
			request.setAttribute("mode", mode);
			request.setAttribute("hotResult", hot);
			return "noResult";
		}
	}

	
	//从术语和词典表中查询数据(按年份查询)
		public String searchItemFromItem_Dic_ByYear() throws ServletException, IOException {
			searchItem = searchItem.trim();
			Map<String,String> fill = itemService.fillTreeView_shuyu_year(searchItem, queryMode, year);
			request.getSession().setAttribute("fill", fill);
			//搜索热词
			String[] hot = itemService.searchHotWord();
			long start = System.currentTimeMillis();
			List<SearchInfo> queryResult = HighLightUtil.highLightProcess(
					itemService.searchItemFromItem_Dic_ByYear(searchItem,year), searchItem,
					true);
			long end = System.currentTimeMillis();
			float time = (float) ((end - start) * 1.0 / 1000);
			start = System.currentTimeMillis();
			List<SearchInfo> queryResultLike = HighLightUtil.highLightProcess(
					itemService.searchItemFromItem_Dic_ByYear_Like(searchItem,year),
					searchItem, true);
			end = System.currentTimeMillis();
			float timeLike = (float) ((end - start) * 1.0 / 1000);

			String userName = (String) request.getSession()
					.getAttribute("userName");
			// 判断对否为登录状态
			boolean isLoginOn = (userName != null) ? true : false;
			if (queryResultLike != null && queryResultLike.size() > 0) {
				if (queryMode.equals("jingque")) {
					if (queryResult != null && queryResult.size() > 0) {
						if (JudgeEnOrZh.isEnglish(searchItem)) {
							Collections.sort(queryResult,
									new SearchInfoCompator_En());
						} else {
							Collections.sort(queryResult,
									new SearchInfoCompator_Ch());
						}
					} else {
						queryResult = new ArrayList<SearchInfo>();
					}
					request.setAttribute("queryResult", queryResult);
					request.setAttribute("useTime", time);
					request.setAttribute("itemNum", queryResult.size());
				} else {
					if (JudgeEnOrZh.isEnglish(searchItem)) {
						Collections.sort(queryResultLike,
								new SearchInfoCompator_En());
					} else {
						Collections.sort(queryResultLike,
								new SearchInfoCompator_Ch());
					}
					request.setAttribute("queryResult", queryResultLike);
					request.setAttribute("useTime", timeLike);
					request.setAttribute("itemNum", queryResultLike.size());
				}
				if (isLoginOn) {
					itemService.addOrUpdateHotWord(searchItem);
				}
				request.setAttribute("year", year);
				request.setAttribute("hotResult", hot);
				request.setAttribute("mode", mode);
				return "listInfo";
			} else {
				// 如果是中文，并且未找到结果，则进行分词
				if (!JudgeEnOrZh.isEnglish(searchItem)) {
					List<String> split = SplitWord.split(searchItem);
					start = System.currentTimeMillis();
					queryResult = HighLightUtil.highLightProcess(
							itemService.searchItemFromItem_Dic_Split_ByYear(split,year),
							split, true);
					end = System.currentTimeMillis();
					time = (float) ((end - start) * 1.0 / 1000);
					start = System.currentTimeMillis();
					queryResultLike = HighLightUtil.highLightProcess(
							itemService.searchItemFromItem_Dic_Split_ByYear_Like(split,year),
							split, true);
					end = System.currentTimeMillis();
					time = (float) ((end - start) * 1.0 / 1000);
					if (queryResultLike != null && queryResultLike.size() > 0) {
						if (queryMode.equals("jingque")) {
							if (queryResult != null && queryResult.size() > 0) {
								Collections.sort(queryResult,
										new SearchInfoCompator_Ch());
							} else {
								queryResult = new ArrayList<SearchInfo>();
							}
							request.setAttribute("queryResult", queryResult);
							request.setAttribute("useTime", time);
							request.setAttribute("itemNum", queryResult.size());
							request.setAttribute("hotResult", hot);
							return "listInfo";
						} else {
							Collections.sort(queryResultLike,
									new SearchInfoCompator_Ch());
							request.setAttribute("queryResult", queryResultLike);
							request.setAttribute("useTime", timeLike);
							request.setAttribute("itemNum", queryResultLike.size());
							request.setAttribute("hotResult", hot);
							return "listInfo";
						}
					} else {
						request.setAttribute("mode", mode);
						request.setAttribute("hotResult", hot);
						return "listInfo";
					}
				}
				request.setAttribute("mode", mode);
				request.setAttribute("hotResult", hot);
				return "listInfo";//noResult
			}
		}
	
		
		//从术语和词典表中查询数据(按年份查询)
	public String searchItemFromItem_Dic_BySource() throws ServletException,
			IOException {
		searchItem = searchItem.trim();
		Map<String,String> fill = itemService.fillTreeView_shuyu_source(searchItem, queryMode, source);
		request.getSession().setAttribute("fill", fill);
		// 搜索热词
		String[] hot = itemService.searchHotWord();
		long start = System.currentTimeMillis();
		List<SearchInfo> queryResult = HighLightUtil
				.highLightProcess(itemService.searchItemFromItem_Dic_BySource(
						searchItem, source), searchItem, true);
		long end = System.currentTimeMillis();
		float time = (float) ((end - start) * 1.0 / 1000);
		start = System.currentTimeMillis();
		List<SearchInfo> queryResultLike = HighLightUtil.highLightProcess(
				itemService.searchItemFromItem_Dic_BySource_Like(searchItem,
						source), searchItem, true);
		end = System.currentTimeMillis();
		float timeLike = (float) ((end - start) * 1.0 / 1000);

		String userName = (String) request.getSession()
				.getAttribute("userName");
		// 判断对否为登录状态
		boolean isLoginOn = (userName != null) ? true : false;
		if (queryResultLike != null && queryResultLike.size() > 0) {
			if (queryMode.equals("jingque")) {
				if (queryResult != null && queryResult.size() > 0) {
					if (JudgeEnOrZh.isEnglish(searchItem)) {
						Collections.sort(queryResult,
								new SearchInfoCompator_En());
					} else {
						Collections.sort(queryResult,
								new SearchInfoCompator_Ch());
					}
				} else {
					queryResult = new ArrayList<SearchInfo>();
				}

				request.setAttribute("queryResult", queryResult);
				request.setAttribute("useTime", time);
				request.setAttribute("itemNum", queryResult.size());
			} else {
				if (JudgeEnOrZh.isEnglish(searchItem)) {
					Collections.sort(queryResultLike,
							new SearchInfoCompator_En());
				} else {
					Collections.sort(queryResultLike,
							new SearchInfoCompator_Ch());
				}
				request.setAttribute("queryResult", queryResultLike);
				request.setAttribute("useTime", timeLike);
				request.setAttribute("itemNum", queryResultLike.size());
			}
			if (isLoginOn) {
				itemService.addOrUpdateHotWord(searchItem);
			}
			request.setAttribute("source", source);
			request.setAttribute("hotResult", hot);
			request.setAttribute("mode", mode);
			return "listInfo";
		} else {
			// 如果是中文，并且未找到结果，则进行分词
			if (!JudgeEnOrZh.isEnglish(searchItem)) {
				List<String> split = SplitWord.split(searchItem);
				start = System.currentTimeMillis();
				queryResult = HighLightUtil.highLightProcess(itemService
						.searchItemFromItem_Dic_Split_BySource(split, source),
						split, true);
				end = System.currentTimeMillis();
				time = (float) ((end - start) * 1.0 / 1000);
				start = System.currentTimeMillis();
				queryResultLike = HighLightUtil.highLightProcess(itemService
						.searchItemFromItem_Dic_Split_BySource_Like(split,
								source), split, true);
				end = System.currentTimeMillis();
				time = (float) ((end - start) * 1.0 / 1000);
				if (queryResultLike != null && queryResultLike.size() > 0) {
					if (queryMode.equals("jingque")) {
						if (queryResult != null && queryResult.size() > 0) {
							Collections.sort(queryResult,
									new SearchInfoCompator_Ch());
						} else {
							queryResult = new ArrayList<SearchInfo>();
						}
						request.setAttribute("queryResult", queryResult);
						request.setAttribute("useTime", time);
						request.setAttribute("itemNum", queryResult.size());
						request.setAttribute("hotResult", hot);
						return "listInfo";
					} else {
						Collections.sort(queryResultLike,
								new SearchInfoCompator_Ch());
						request.setAttribute("queryResult", queryResultLike);
						request.setAttribute("useTime", timeLike);
						request.setAttribute("itemNum", queryResultLike.size());
						request.setAttribute("hotResult", hot);
						return "listInfo";
					}
				} else {
					request.setAttribute("mode", mode);
					request.setAttribute("hotResult", hot);
					return "listInfo";
				}
			}
			request.setAttribute("mode", mode);
			request.setAttribute("hotResult", hot);
			return "listInfo";// noResult
		}
	}
		
		
	//跳转到标准详情页
	public String toStandard() throws UnsupportedEncodingException {
		String searchStd = ((String) request.getParameter("searchStd")).trim();
		//searchStd = new String(searchStd.getBytes("iso-8859-1"), "UTF-8").trim();
		System.out.println(searchStd);
		Standard s = itemService.searchStd(searchStd);
		request.setAttribute("stdResult", s);
		return "standard_jsp";
	}

	
	
	
	
	
	
	
	
	
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		this.request = request;
	}

	public String getSearchItem() {
		return searchItem;
	}

	public void setSearchItem(String searchItem) {
		this.searchItem = searchItem;
	}

	public ItemService getItemService() {
		return itemService;
	}

	public void setItemService(ItemService itemService) {
		this.itemService = itemService;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getQueryMode() {
		return queryMode;
	}

	public void setQueryMode(String queryMode) {
		this.queryMode = queryMode;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;
	}

	public String getIsToStd() {
		return isToStd;
	}

	public void setIsToStd(String isToStd) {
		this.isToStd = isToStd;
	}

	public String getStdno() {
		return stdno;
	}

	public void setStdno(String stdno) {
		this.stdno = stdno;
	}




	public String getYear() {
		return year;
	}




	public void setYear(String year) {
		this.year = year;
	}




	public String getSource() {
		return source;
	}




	public void setSource(String source) {
		this.source = source;
	}
}
