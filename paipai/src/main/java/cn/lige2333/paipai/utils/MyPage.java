package cn.lige2333.paipai.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.lige2333.paipai.dao.ProductMapper;
import cn.lige2333.paipai.entity.Bid;
import cn.lige2333.paipai.entity.Product;
import cn.lige2333.paipai.query.ProductQueryObject;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

public class MyPage<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	public MyPage(int count, int pagesize, List<T> list) {
		this.count = count;
		this.list = list;
		// 计算pagecount
		this.pagecount = count % pagesize == 0 ? (count / pagesize) : (int) (count / pagesize) + 1;
	}

	private int count;
	private int pagecount;
	private int currentPage;
	private List<T> list;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPagecount() {
		return pagecount;
	}

	public void setPagecount(int pagecount) {
		this.pagecount = pagecount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	// select分页
	public static <T> MyPage<T> selectByPage(Mapper<T> mapper, T t, int pagenum, int pagesize) {
		// 参数计算
		int count = mapper.selectCount(t);
		// 分页拦截
		PageHelper.startPage(pagenum, pagesize);
		List<T> list = mapper.select(t);
		MyPage<T> page = new MyPage<T>(count, pagesize, list);
		page.setCurrentPage(pagenum);
		return page;
	}

	// selectAll分页
	public static <T> MyPage<T> selectAllByPage(Mapper<T> mapper, int pagenum, int pagesize) {
		// 参数计算
		int count = mapper.selectCount(null);
		// 分页拦截
		PageHelper.startPage(pagenum, pagesize);
		List<T> list = mapper.selectAll();
		MyPage<T> page = new MyPage<T>(count, pagesize, list);
		page.setCurrentPage(pagenum);
		return page;
	}
	// selectAll分页
	public static  MyPage<Product> selectKeywordsByPage(ProductMapper mapper, ProductQueryObject qo) {
		try {
			// 参数计算
			int count = mapper.selectCountByConditions(qo);
			// 分页拦截
			List<Product> list = mapper.selectByConditions(qo);
			for (Product pro:list){
				if(pro.getBids()==null){
					pro.setBids(new ArrayList<Bid>());
				}
			}
			MyPage<Product> page = new MyPage<Product>(count, qo.getPageSize(), list);
			page.setCurrentPage(qo.getCurrentPage());
			return page;
		}catch (Exception e){
			MyPage<Product> page = new MyPage<Product>(0, qo.getPageSize(), new ArrayList<>());
			return page;
		}

	}

	// selectByExample分页
	public static <T> MyPage<T> selectByExampleAndPage(Mapper<T> mapper, Example exm, int pagenum, int pagesize) {
		// 参数计算
		int count = mapper.selectCountByExample(exm);
		// 分页拦截
		PageHelper.startPage(pagenum, pagesize);
		List<T> list = mapper.selectByExample(exm);
		MyPage<T> page = new MyPage<T>(count, pagesize, list);
		page.setCurrentPage(pagenum);
		return page;
	}

	//
}
