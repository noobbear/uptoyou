package cn.fision.uptoyou.util;

import java.io.Serializable;
import java.util.List;

/**
 * MongoDB分页查询结果集
 * desc PageResult.java
 * @author 广东技术师范大学 吴飞雄 
 * @project nitrogen-core-pojo
 * @email fx@fision.cn
 * @date 2019年1月3日-下午5:28:51
 * @version 1.0
 */
public class PageResult implements Serializable {

	private int pageNumber;
	private long totalpage;
	private long totalcount;
	private int pageSize;
	private List<?> list;

	
	
	public PageResult(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	public PageResult() {
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}
	public long getTotalpage() {
		return totalpage;
	}
	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
	}
	public long getTotalcount() {
		return totalcount;
	}
	public void setTotalcount(long totalcount) {
		this.totalcount = totalcount;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "PageResult [pageNumber=" + pageNumber + ", totalpage=" + totalpage + ", totalcount=" + totalcount
				+ ", pageSize=" + pageSize + ", list=" + list  + "]";
	}
	
}
