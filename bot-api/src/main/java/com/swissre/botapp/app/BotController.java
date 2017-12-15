package com.swissre.botapp.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.swissre.botapp.api.BotMessageSender;
import com.swissre.botapp.common.AppConstants;
import com.swissre.botapp.common.NlpOperations;
import com.swissre.botapp.dto.activity.Activity;
import com.swissre.botapp.dto.app.UserIntent;

public class BotController implements AppConstants {

	NlpOperations nlpStuff = new NlpOperations();

	public void controlRequests(Activity activity) {

		String userInputText = activity.getText();

		String cleanedText = nlpStuff.cleanText(userInputText);

		UserIntent intent = new UserIntent();

		SingleResponseController singleResponseController = new SingleResponseController();

		BotMessageSender sendMessage = new BotMessageSender();

		if (cleanedText.equals(null) || cleanedText.isEmpty() || cleanedText.equals(" ")) {
			// System.out.println("Please send me some message :(");
			sendMessage.replyToUser(activity, SEND_ME_A_REPLY);
			return;
		} else {
			intent = nlpStuff.findIntent(cleanedText);
		}

		if (intent.getintentType() == SINGLE_RESPONSE_TYPE) {

			if (intent.getIntent().equals("welcome")) {

				List<String> messagesToUser = new ArrayList<>();
				String initialResponse = singleResponseController.buildResponse(intent);

				messagesToUser.add(initialResponse);

				List<String> botTasks = fetchBotTasks();

				messagesToUser.addAll(botTasks);

				sendMessage.replyToUser(activity, messagesToUser);

			} else {
				String messageToUser = singleResponseController.buildResponse(intent);
				sendMessage.replyToUser(activity, messageToUser);
			}
		} else if (intent.getintentType() == WORKFLOW_TYPE) {
			// Implement Workflow type!
		} else {
			System.out.println("Intent Type is Wrong!");
			// Throw an exception here!
		}
	}

	private List<String> fetchBotTasks() {

		String result = "";
		List<String> responseLines = new ArrayList<String>();
		ClassLoader classLoader = getClass().getClassLoader();

		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(BOT_TASKS_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}

		responseLines = Arrays.asList(result.split("\\r?\\n"));

		return responseLines;

	}
}
