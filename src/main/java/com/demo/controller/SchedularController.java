package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.demo.service.SchedularService;
import com.demo.util.Util;

@Controller
@RequestMapping(value = "/")
public class SchedularController {

	@Autowired
	private SchedularService schedularService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView view = new ModelAndView("Index");
		view.addObject("isSchedularON", Util.isSchedularON);
		return view;
	}

	@RequestMapping(value = "/schedular", method = RequestMethod.GET)
	public String setSchedular(@RequestParam Boolean isSchedularON) {
		Util.isSchedularON = isSchedularON;

		return "Schedular status is changed";
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void testSchedular() {
		this.schedularService.checkCancelAppoinments();
	}
}
