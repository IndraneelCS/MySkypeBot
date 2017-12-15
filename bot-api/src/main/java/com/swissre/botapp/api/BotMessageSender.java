package com.swissre.botapp.api;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.swissre.botapp.dto.activity.Activity;
import com.swissre.botapp.dto.app.ResponseActivity;
import com.swissre.botapp.app.BotAuthenticator;
import com.swissre.botapp.dto.AuthToken;
import com.swissre.botapp.dto.Identification;

public class BotMessageSender {

	RestTemplate restTemplate;

	BotAuthenticator auth;

	public boolean replyToUser(Activity activity, String messageToBeSent) {

		boolean messageReplied = false;

		ResponseActivity responseActivity = prepareResponseActivity(activity);
		responseActivity.getActivity().setText(messageToBeSent);
		messageReplied = sendMessage(responseActivity);

		if (messageReplied) {
			System.out.println("Message sent");
		} else
			System.out.println("Message didn't go -.- ");

		return messageReplied;
	}

	public boolean replyToUser(Activity activity, String[] messagesToBeSent) {
		boolean messageReplied = false;

		for (String str : messagesToBeSent) {
			messageReplied = replyToUser(activity, str);
		}
		return messageReplied;
	}

	public boolean replyToUser(Activity activity, List<String> messagesToBeSent) {

		boolean messageReplied = false;

		for (String str : messagesToBeSent) {
			messageReplied = replyToUser(activity, str);
		}

		return messageReplied;
	}

	private ResponseActivity prepareResponseActivity(Activity activity) {

		ResponseActivity response = new ResponseActivity();

		if (activity.equals(null)) {
			System.out.println("Nothing found in the activity");
			// Add an error here
		}

		String url = activity.getServiceUrl() + "/v3/conversations/" + activity.getConversation().getId()
				+ "/activities/" + activity.getId();

		Activity responseActivity = new Activity();

		responseActivity.setConversation(activity.getConversation());
		responseActivity.setFrom(activity.getFrom());
		responseActivity.setLocale(activity.getLocale());
		responseActivity.setRecipient(activity.getRecipient());
		responseActivity.setType("message");
		responseActivity.setReplyToId(activity.getId());

		response.setActivity(responseActivity);
		response.setUrl(url);

		return response;

	}

	private boolean sendMessage(ResponseActivity responseActivity) {

		boolean messageReplied = false;

		AuthToken token = auth.getAuthToken();

		restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.add("Authorization", String.format("Bearer %s", token.getAccessToken()));
		httpHeaders.add("Accept", "*/*");

		HttpEntity<Activity> requestEntity = new HttpEntity<Activity>(responseActivity.getActivity(), httpHeaders);

		ResponseEntity<Identification> responseEntity = restTemplate.exchange(responseActivity.getUrl(),
				HttpMethod.POST, requestEntity, Identification.class);

		if (responseEntity.getBody().getId() != null || responseEntity.getBody().getId() != "") {
			messageReplied = true;
		}

		return messageReplied;

	}

	/*
	 * private boolean replyToBot(Activity activity) {
	 * 
	 * boolean messageReplied = false;
	 * 
	 * String url = activity.getServiceUrl() + "/v3/conversations/" +
	 * activity.getConversation().getId() + "/activities/" + activity.getId();
	 * 
	 * auth = new BotAuthenticator();
	 * 
	 * AuthToken token = auth.getAuthToken();
	 * 
	 * if (token.equals("") || token.equals(null)) { System.out.println(
	 * "Token is empty!!"); } else {
	 * 
	 * Activity responseActivity = new Activity();
	 * 
	 * responseActivity.setConversation(activity.getConversation());
	 * responseActivity.setFrom(activity.getFrom());
	 * responseActivity.setLocale(activity.getLocale());
	 * responseActivity.setRecipient(activity.getRecipient());
	 * responseActivity.setType("message");
	 * responseActivity.setReplyToId(activity.getId()); if
	 * (activity.getText().equals(null) || activity.getText().equals("") ||
	 * activity.getText().equals(" ")) { responseActivity.setText(
	 * "Please send me some message... :( "); } else { responseActivity.setText(
	 * "Hiii, this is Indraneel ;) "); }
	 * 
	 * System.out.println(responseActivity.getText());
	 * 
	 * restTemplate = new RestTemplate();
	 * 
	 * HttpHeaders httpHeaders = new HttpHeaders();
	 */

	// httpHeaders.add("Authorization", String.format("Bearer %s",
	// token.getAccessToken()));
	// httpHeaders.add("Accept", "*/*");

	/*
	 * HttpEntity<Activity> requestEntity = new
	 * HttpEntity<Activity>(responseActivity, httpHeaders);
	 * 
	 * ResponseEntity<Identification> responseEntity =
	 * restTemplate.exchange(url, HttpMethod.POST, requestEntity,
	 * Identification.class);
	 * 
	 * System.out.println(responseEntity.getBody().getId());
	 * 
	 * if (responseEntity.getBody().getId() != null ||
	 * responseEntity.getBody().getId() != "") { messageReplied = true; }
	 * 
	 * }
	 * 
	 * return messageReplied; }
	 */
}
