package com.alasch1.cdprf.mocking.voidMethod;

public class Cookie {

	private String name;
	private String value;
	private Integer maxAge;
	private String path;
	private String domain;
	private boolean secure;
	private boolean httpOnly;

	public Cookie(String name, String value, Integer maxAge, String path,
			String domain, boolean secure, boolean httpOnly) {
		this.name = name;
		this.value = value;
		this.maxAge = maxAge;
		this.path = path;
		this.domain = domain;
		this.secure = secure;
		this.httpOnly = httpOnly;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public String getPath() {
		return path;
	}

	public String getDomain() {
		return domain;
	}

	public boolean isSecure() {
		return secure;
	}

	public boolean isHttpOnly() {
		return httpOnly;
	}

}
