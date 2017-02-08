package com.gestaofesta.config.enums;

import java.util.HashMap;


public enum Service{
	GESTAO("http://localhost:8080/OrionServer",
			"http://localhost:8080/OrionServer",
			"http://localhost:8080/OrionServer"){

		@Override
		public HashMap<String, String> getHeaders() {
			HashMap<String, String> map = new HashMap<>();
			map.put("HASH", "");
			map.put("version_number", "");
			map.put("version_namer", "");
			return map;
		}
	};


	private String url;
	private String urlDev;
	private String urlLocal;

	Service(String url, String urlDev, String urlLocal){
		this.url = url;
		this.urlDev = urlDev;
		this.urlLocal = urlLocal;
	}

	public HashMap<String, String> getHeaders(){
		return new HashMap<>();
	}

	@Override
	public String toString() {
		return null;
		
	}
}