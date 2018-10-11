package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.PilotService;

@Controller
public class PilotController {
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("page", "home");
		return "home";
	}
	
	@RequestMapping(value = "/pilot/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("pilot", new PilotModel());
		model.addAttribute("page", "AddPilot");
		return "addPilot";
		
	}
	
	@RequestMapping(value= "/pilot/add", method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PilotModel pilot, Model model) {
		pilotService.addPilot(pilot);
		model.addAttribute("page", "Add");
		return "add";
	}
	
	@RequestMapping(value= "/pilot/view", method = RequestMethod.GET)
	private String getPilotByLicense(String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		model.addAttribute("listFlight", pilot.getPilotFlight());
		model.addAttribute("page", "View Pilot");
		return "view-pilot";
	}
	
	@RequestMapping(value= "/pilot/delete", method = RequestMethod.POST)
	private String deletePilotByLicense(String licenseNumber, Model model) {
		pilotService.deletePilot(licenseNumber);
		model.addAttribute("page", "Delete");
		return "delete";
	}
	
	@RequestMapping(value = "/pilot/update/{licenseNumber}", method = RequestMethod.GET)
	private String updatePilot(@PathVariable String licenseNumber, Model model) {
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", pilot);
		model.addAttribute("page", "Update Pilot");
		return "update-pilot";
	}
	
	@RequestMapping(value= "/pilot/update", method = RequestMethod.POST)
	private String updatePilotSubmit(@ModelAttribute PilotModel pilot_update, Model model) {
		pilotService.updatePilot(pilot_update);
		model.addAttribute("page", "Update");
		return "update";
	}
	
	
}
