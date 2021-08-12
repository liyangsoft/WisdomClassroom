package com.example.service.smartControl.entity;

import java.util.Map;

public class LightControl {

	private int code;
	private String id;
	private int ep;
	private int pid;
	private int did;
	private int serial;
	private Map<String, Object> control;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getSerial() {
		return serial;
	}

	public void setSerial(int serial) {
		this.serial = serial;
	}

	public Map<String, Object> getControl() {
		return control;
	}

	public void setControl(Map<String, Object> control) {
		this.control = control;
	}
}
