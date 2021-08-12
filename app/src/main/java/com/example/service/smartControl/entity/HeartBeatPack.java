package com.example.service.smartControl.entity;

import java.util.List;
import java.util.Map;

public class HeartBeatPack {
	private int code;
	private long timestamp;
	private Map<String, Object> gw;
	private List<Device> device;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public Map<String, Object> getGw() {
		return gw;
	}

	public void setGw(Map<String, Object> gw) {
		this.gw = gw;
	}

	public List<Device> getDevice() {
		return device;
	}

	public void setDevice(List<Device> device) {
		this.device = device;
	}
}
