package com.swissre.botapp.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.IOUtils;

import com.swissre.botapp.common.AppConstants;
import com.swissre.botapp.dto.app.UserIntent;

public class SingleResponseController implements AppConstants {

	public String buildResponse(UserIntent intent) {

		String messageToUser = "";

		String fileName = intent.getIntent() + ".txt";

		List<String> responseLines = new ArrayList<String>();
		ClassLoader classLoader = getClass().getClassLoader();
		Random r = new Random();
		try {
			String result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
			responseLines = Arrays.asList(result.split("\\r?\\n"));
			messageToUser = responseLines.get(r.nextInt(responseLines.size()));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return messageToUser;
	}

}