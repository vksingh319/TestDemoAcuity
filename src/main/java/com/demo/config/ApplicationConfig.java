package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:application.properties" })
public class ApplicationConfig {

	private static String APPOINMENTS_URL;

	private static String APPOINMENTS_USER_ID;

	private static String APPOINMENTS_KEY;

	private static String AUTOPILOT_URL;

	private static String AUTOPILOT_KEY;
	
	private static String APPOINMENTS_CANCEL_URL;
	
	private static String CUSTOM_FIELD;
	
	private static String APPOINTMENT_TYPE_ID;
	
	private static int CANCEL_DAYS;
	
	
	public String getAPPOINTMENT_TYPE_ID() {
		return APPOINTMENT_TYPE_ID;
	}

	@Value("${appointment_type_id}")
	public void setAPPOINTMENT_TYPE_ID(String aPPOINTMENT_TYPE_ID) {
		APPOINTMENT_TYPE_ID = aPPOINTMENT_TYPE_ID;
	}

	public String getCUSTOM_FIELD() {
		return CUSTOM_FIELD;
	}

	@Value("${custom_field}")
	public void setCUSTOM_FIELD(String cUSTOM_FIELD) {
		CUSTOM_FIELD = cUSTOM_FIELD;
	}

	public String getAPPOINMENTS_URL() {
		return APPOINMENTS_URL;
	}

	@Value("${appointments.url}")
	public void setAPPOINMENTS_URL(String aPPOINMENTS_URL) {
		APPOINMENTS_URL = aPPOINMENTS_URL;
	}

	public String getAPPOINMENTS_USER_ID() {
		return APPOINMENTS_USER_ID;
	}

	@Value("${appointments.userId}")
	public void setAPPOINMENTS_USER_ID(String aPPOINMENTS_USER_ID) {
		APPOINMENTS_USER_ID = aPPOINMENTS_USER_ID;
	}

	public String getAPPOINMENTS_KEY() {
		return APPOINMENTS_KEY;
	}

	@Value("${appointments.key}")
	public void setAPPOINMENTS_KEY(String aPPOINMENTS_KEY) {
		APPOINMENTS_KEY = aPPOINMENTS_KEY;
	}

	public String getAUTOPILOT_URL() {
		return AUTOPILOT_URL;
	}

	@Value("${autopilothq.url}")
	public void setAUTOPILOT_URL(String aUTOPILOT_URL) {
		AUTOPILOT_URL = aUTOPILOT_URL;
	}

	public String getAUTOPILOT_KEY() {
		return AUTOPILOT_KEY;
	}

	@Value("${autopilothq.autopilotapikey}")
	public void setAUTOPILOT_KEY(String aUTOPILOT_KEY) {
		AUTOPILOT_KEY = aUTOPILOT_KEY;
	}

	public String getAPPOINMENTS_CANCEL_URL() {
		return APPOINMENTS_CANCEL_URL;
	}

	@Value("${appointments.cancel.url}")
	public void setAPPOINMENTS_CANCEL_URL(String aPPOINMENTS_CANCEL_URL) {
		APPOINMENTS_CANCEL_URL = aPPOINMENTS_CANCEL_URL;
	}

	
	public int getCANCEL_DAYS() {
		return CANCEL_DAYS;
	}

	@Value("${cancel.days}")
	public void setCANCEL_DAYS(int cANCEL_DAYS) {
		CANCEL_DAYS = cANCEL_DAYS;
	}
	
	

}
