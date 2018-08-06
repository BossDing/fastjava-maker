package com.fastjavaframework.maker.support.util;

import java.util.Set;

/**
 * mapper.xml属性
 *
 * @author wangshuli
 */
public class SqlMapperBean {

	/**
	 * mapperxml文件名
	 */
	private String mapperName;

	/**
	 * 命名空间
	 */
	private String nameSpace;

	/**
	 * 关联的命名空间
	 */
	private String refNameSpace;

	/**
	 * 表明
	 */
	private String tableName;

	/**
	 * 关联表明
	 */
	private Set<String> refTableName;
	
	public String getMapperName() {
		return mapperName;
	}
	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}
	public String getNameSpace() {
		return nameSpace;
	}
	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}
	public String getRefNameSpace() {
		return refNameSpace;
	}
	public void setRefNameSpace(String refNameSpace) {
		this.refNameSpace = refNameSpace;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Set<String> getRefTableName() {
		return refTableName;
	}
	public void setRefTableName(Set<String> refTableName) {
		this.refTableName = refTableName;
	}
	
}
