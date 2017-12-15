package com.swissre.botapp;

import com.swissre.botapp.common.NlpOperations;

public class TestMain {

	public static void main(String[] args){
		
		NlpOperations nlp = new NlpOperations();
		
		nlp.findIntent("H");
		System.out.println(nlp.cleanText("Hi Heloo how are you "));
	}
	
}
