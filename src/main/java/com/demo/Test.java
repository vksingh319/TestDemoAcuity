package com.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Test {

	public static void main(String[] args) throws JSONException {
		List<String> emailList = new ArrayList<>();
		try {

			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

			Map<String, Object> appoFilter = new HashMap<>();
			appoFilter.put("minDate", new Date().getTime() - 2);

			HttpResponse<JsonNode> jsonResponse1 = Unirest.get("https://acuityscheduling.com/api/v1/appointments")
					.basicAuth("14672051", "25e2f0578975b5c0f567b6511d9fec9a").header("accept", "application/json")
					.queryString(appoFilter).asJson();
			JSONArray js = jsonResponse1.getBody().getArray();
			for (int i = 0; i < js.length(); i++) {
				JSONObject objectInArray = js.getJSONObject(i);
				emailList.add(objectInArray.getString("email"));
			}

			HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api2.autopilothq.com/v1/contacts")
					.header("accept", "application/json").header("autopilotapikey", "e9c57d54251f4105866ae488efe467d6")
					.asJson();
			js = jsonResponse.getBody().getArray();
			for (int i = 0; i < js.length(); i++) {
				JSONObject objectInArray = js.getJSONObject(i);
				JSONArray js1 = objectInArray.getJSONArray("contacts");
				if (js1 != null && js1.length() > 0) {
					for (int j = 0; j < js1.length(); j++) {
						objectInArray = js1.getJSONObject(j);
						if (emailList.contains(objectInArray.getString("Email"))) {
							JSONArray js2 = objectInArray.getJSONArray("custom_fields");
							if (js2 != null && js2.length() > 0) {
								for (int k = 0; k < js2.length(); k++) {
									JSONObject obj = js2.getJSONObject(k);
									if (obj.getString("kind").equals("PaperWork")) {
										if (obj.getString("value") == null || obj.getString("value").equals("")) {
											System.out.println("done");
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
