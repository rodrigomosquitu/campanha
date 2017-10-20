package com.campanha.domain;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class LogCampanha{
	
	@Id
	int id;
	
	String dataFinalOld;
	String dataFinalNew;
	String sysDate;

	public LogCampanha() {
		super();
	}
	
	public LogCampanha(int i, Date dataFinalOld, Date dataFinalNew) {
		super();
		this.id = i;
		this.dataFinalOld = dataFinalOld.toString();
		this.dataFinalNew = dataFinalNew.toString();
		this.sysDate = new Date().toString();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDataFinalOld() {
		return dataFinalOld;
	}

	public void setDataFinalOld(String dataFinalOld) {
		this.dataFinalOld = dataFinalOld;
	}

	public String getDataFinalNew() {
		return dataFinalNew;
	}

	public void setDataFinalNew(String dataFinalNew) {
		this.dataFinalNew = dataFinalNew;
	}

	public String getSysDate() {
		return sysDate;
	}

	public void setSysDate(String sysDate) {
		this.sysDate = sysDate;
	}

	

}