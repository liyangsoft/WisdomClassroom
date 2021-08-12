package com.example.service.smartControl.entity;

import java.util.Map;

public class Device {
	private String id;
	private Boolean ol;
	private int ep;
	private int pid;
	private int did;
	private String dn;
	private int dtype;
	private int ztype;
	private String fac;
	private String dsp;
	private String swid;
	private Map<String, Object> st;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getOl() {
		return ol;
	}

	public void setOl(Boolean ol) {
		this.ol = ol;
	}

	public int getEp() {
		return ep;
	}

	public void setEp(int ep) {
		this.ep = ep;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public int getDtype() {
		return dtype;
	}

	public void setDtype(int dtype) {
		this.dtype = dtype;
	}

	public int getZtype() {
		return ztype;
	}

	public void setZtype(int ztype) {
		this.ztype = ztype;
	}

	public String getFac() {
		return fac;
	}

	public void setFac(String fac) {
		this.fac = fac;
	}

	public String getDsp() {
		return dsp;
	}

	public void setDsp(String dsp) {
		this.dsp = dsp;
	}

	public String getSwid() {
		return swid;
	}

	public void setSwid(String swid) {
		this.swid = swid;
	}

	public Map<String, Object> getSt() {
		return st;
	}

	public void setSt(Map<String, Object> st) {
		this.st = st;
	}
}
