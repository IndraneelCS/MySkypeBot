package com.swissre.botapp.dto.app;

public class UserIntent {

	private String intent;
	private String intentType;

	public String getIntent() {
		return intent;
	}

	public void setIntent(String intent) {
		this.intent = intent;
	}

	public String getintentType() {
		return intentType;
	}

	public void setintentType(String type) {
		this.intentType = type;
	}

	@Override
	public String toString() {
		return "UserIntent [intent=" + intent + ", intentType=" + intentType + "]";
	}
}
