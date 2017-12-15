package com.swissre.botapp.dto.app;

import com.swissre.botapp.dto.activity.Activity;

public class ResponseActivity {

	private Activity activity;
	private String url;
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ResponseActivity [activity=" + activity + ", url=" + url + "]";
	}
}
