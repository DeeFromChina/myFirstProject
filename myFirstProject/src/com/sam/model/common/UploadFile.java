package com.sam.model.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="upload_file")
public class UploadFile {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
    private Integer id;

	@Column(name = "file_name")
    private String fileName;

	@Column(name = "real_file_name")
    private String realFileName;
	
	@Column(name = "path")
	private String path;
	
	@Column(name = "table_name")
	private String tableName;
	
	@Column(name = "data_id")
	private Integer dataId;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "create_time")
	private Date createTime;
	
	@Column(name = "create_user")
	private Integer create_user;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRealFileName() {
		return realFileName;
	}

	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getDataId() {
		return dataId;
	}

	public void setDataId(Integer dataId) {
		this.dataId = dataId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreate_user() {
		return create_user;
	}

	public void setCreate_user(Integer create_user) {
		this.create_user = create_user;
	}
}
