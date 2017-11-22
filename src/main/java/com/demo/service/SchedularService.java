package com.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.config.ApplicationConfig;
import com.demo.pojo.AppoinmentsResponse;
import com.demo.pojo.AutoPilotResponse;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class SchedularService {

	private Logger log = LoggerFactory.getLogger(SchedularService.class);

	@Autowired
	private ApplicationConfig config;

	public List<AppoinmentsResponse> getAcuityAppointments(int days) {
		List<AppoinmentsResponse> appoinmentsResponses = new ArrayList<AppoinmentsResponse>();
		AppoinmentsResponse objAppoinmentsResponse;
		try {
			String maxDate =  getTimeInJodaFormat(days);
			
			Map<String, Object> appoFilter = new HashMap<>();
			appoFilter.put("maxDate", maxDate);
			appoFilter.put("appointmentTypeID", config.getAPPOINTMENT_TYPE_ID());

			HttpResponse<JsonNode> response = Unirest.get(config.getAPPOINMENTS_URL())
					.basicAuth(config.getAPPOINMENTS_USER_ID(), config.getAPPOINMENTS_KEY())
					.header("accept", "application/json").queryString(appoFilter).asJson();
			
			JSONArray js = response.getBody().getArray();
			
			if(response.getStatus() == 401){
				log.debug("Unauthorized : getAcuityAppointments : " + js.getString(1));
			}else{
				if (js != null && js.length() > 0) {
					appoinmentsResponses = new ArrayList<>();
					for (int i = 0; i < js.length(); i++) {
						objAppoinmentsResponse = new AppoinmentsResponse();
						JSONObject objectInArray = js.getJSONObject(i);
						objAppoinmentsResponse.setEmail(objectInArray.getString("email"));
						objAppoinmentsResponse.setAppoinmentId(objectInArray.getInt("id"));
						appoinmentsResponses.add(objAppoinmentsResponse);
					}
				}
			}
		} catch (JSONException e) {
			log.error("Error in getAcuityAppointments : JSONException : " + e.getMessage());
		} catch (UnirestException e) {
			log.error("Error in getAcuityAppointments : UnirestException : " + e.getMessage());
		} finally {
		}
		return appoinmentsResponses;
	}

	private String getTimeInJodaFormat(int days) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DATE, days);
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZ");
		return format.format(now.getTime());
	}

	public AutoPilotResponse getAutoPilotContact(String email) {
		AutoPilotResponse autoPilotResp = null;
		
		try {
			HttpResponse<JsonNode> response = Unirest.get(config.getAUTOPILOT_URL() + "v1/contact/" + email)
					.header("accept", "application/json").header("autopilotapikey", config.getAUTOPILOT_KEY()).asJson();
	
			JSONObject respBody = response.getBody().getObject();
			
			if(response.getStatus() == 200){
				autoPilotResp = new AutoPilotResponse();
				autoPilotResp.setEmail(email);
				
				if(respBody.has("custom_fields")){
					JSONArray customFields = respBody.getJSONArray("custom_fields");
					checkCustomFieldPaperWork(autoPilotResp, customFields);
				}
			} else {
				log.debug("Bad request autopilot : getAutoPilotContact " + respBody.getString("message"));
			}
			
		}catch(JSONException e){
			log.error("Error in getAutoPilotContact : JSONException : " + e.getMessage());
		}catch(UnirestException e){
			log.error("Error in getAutoPilotContact : UnirestException : " + e.getMessage());
		}
		
		return autoPilotResp;
	}

	private void checkCustomFieldPaperWork(AutoPilotResponse autoPilotResp, JSONArray customFields)
			throws JSONException {
		for (int k = 0; k < customFields.length(); k++) {
			JSONObject obj = customFields.getJSONObject(k);
			if (obj.getString("kind").equals(config.getCUSTOM_FIELD())) {
				if (obj.getString("value") == null || obj.getString("value").equals("")) {
					autoPilotResp.setIsPaperWorkNull(true);
				} else {
					autoPilotResp.setIsPaperWorkNull(false);
				}
			}
		}
	}

	public void checkCancelAppoinments() {
		List<AppoinmentsResponse> upcomingAppointmentList = this.getAcuityAppointments(config.getCANCEL_DAYS());
		
		if(upcomingAppointmentList.isEmpty()){
			log.debug("No Appointment found for cancel");
		}
		
		for(AppoinmentsResponse appoinments : upcomingAppointmentList){
			AutoPilotResponse autoPilotResponse = getAutoPilotContact(appoinments.getEmail());
			
			if (autoPilotResponse!=null && appoinments.getEmail().equals(autoPilotResponse.getEmail()) && autoPilotResponse.getIsPaperWorkNull()) {
				log.debug("Cancel Appointment start : " + appoinments.getEmail());
				this.cancelAppoinments(appoinments.getAppoinmentId());
				log.debug("Cancel Appointment end : " + appoinments.getEmail());
			}
		}
	}

	private void cancelAppoinments(int id) {
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put(config.getAPPOINMENTS_CANCEL_URL() + id + "/cancel")
					.basicAuth(config.getAPPOINMENTS_USER_ID(), config.getAPPOINMENTS_KEY())
					.header("accept", "application/json").asJson();
			if (jsonResponse.getStatus() == 200) {
				log.debug("Appointment canceled sucessfully for Id : " + id);
			} else {
				JSONObject body = jsonResponse.getBody().getObject();
				log.error("Something went wrong while canceling appointment for Id : " + id);
				try {
					log.error(" Message : " + body.getString("message"));
				} catch (JSONException e) {
					log.error(" Message : Error while reading body message" );
				}
			}
			
		} catch (UnirestException e) {
			log.error("Error in getAutoPilotContact : cancelAppoinments : Id " +id+ " Error : " + e.getMessage());
		} finally {
		}
	}
}
