package com.swissre.botapp.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.google.common.base.CharMatcher;
import com.swissre.botapp.dto.app.UserIntent;

public class NlpOperations implements AppConstants {

	HashSet<String> singleResponseIntents = new HashSet<>();
	HashSet<String> workflowIntents = new HashSet<>();

	HashMap<String, String> intentsMap;

	public UserIntent findIntent(String input) {

		UserIntent intent = new UserIntent();

		intentsMap = loadIntents();
		responseIntents();

		// implement removing stop words

		String[] inputStringElements = input.split("\\s+");

		for (String str : inputStringElements) {

			for (Map.Entry<String, String> entry : intentsMap.entrySet()) {
				if (str.equals(entry.getValue())) {
					intent.setIntent(entry.getKey());
					intent.setintentType(getIntentType(entry.getKey()));
				}
			}
		}
		return intent;
	}

	public String cleanText(String input) {

		String output = input.toLowerCase().trim().replaceAll("\t", " ");
		while (output.contains("  ")) {
			output.replaceAll("  ", " ");
		}

		return output;
	}

	public String guavaTextCleaner(String input) {

		String output = CharMatcher.whitespace().trimAndCollapseFrom(input, ' ');
		return output;
	}

/*	public UserIntent getIntent(String input) {

		UserIntent intent = new UserIntent();

		singleResponseIntents = new HashSet<String>();
		singleResponseIntents.add("joke");
		singleResponseIntents.add("welcome");

		for (String str : singleResponseIntents) {

			if (input.matches(".*\\b" + str + "\\b.*")) {
				intent.setIntent(str);
				intent.setintentType(SINGLE_RESPONSE_TYPE);
			}
		}
		return intent;
	}*/

	private HashMap<String, String> loadIntents() {

		Properties prop = new Properties();
		ClassLoader classLoader = getClass().getClassLoader();

		try {
			prop.load(classLoader.getResourceAsStream(INTENTS_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		intentsMap = new HashMap<String, String>((Map) prop);

		// System.out.println(intentsMap);

		return intentsMap;
	}

	private void responseIntents() {
		singleResponseIntents.add("joke");
		singleResponseIntents.add("welcome");
	}

	private String getIntentType(String intent) {

		String intentType = "";

		boolean isSingleResponseIntent = false;

		for (String str : singleResponseIntents) {
			if (str.equals(intent)) {
				intentType = SINGLE_RESPONSE_TYPE;
				isSingleResponseIntent = true;
			}
		}

		if (!isSingleResponseIntent) {
			for (String str : workflowIntents) {
				if (str.equals(intent)) {
					intentType = WORKFLOW_TYPE;
				}
			}
		}
		return intentType;
	}
}
