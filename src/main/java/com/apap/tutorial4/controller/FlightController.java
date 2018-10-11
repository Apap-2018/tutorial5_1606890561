package com.apap.tutorial4.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

/*
 * 
 * FlightController*/


@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		List<FlightModel> listFlight = pilot.getPilotFlight();
		listFlight.add(flight);
		
		
		model.addAttribute("flight", flight);
		model.addAttribute("page", "AddFlight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", params= {"addRow"})
	private String addRow(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		model.addAttribute("page", "AddFlight");
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight, Model model) {
		flightService.addFlight(flight);
		model.addAttribute("page", "Add");
		return "add";
	}
	
	@RequestMapping(value= "/flight/delete", method = RequestMethod.POST)
	private String deleteFlight(@ModelAttribute PilotModel pilot, Model model) {
		
		for(FlightModel flight : pilot.getPilotFlight()) {
			flightService.deleteFlight(flight.getId());
		}
		
		model.addAttribute("page", "delete");
		
		return "delete";
	}
	
	@RequestMapping(value = "/flight/update/{id}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable String id, Model model) {
		long id_long = Long.parseLong(id);
		FlightModel flight = flightService.getFlightDetailById(id_long);
		model.addAttribute("flight", flight);
		model.addAttribute("page", "Update Flight");

		return "update-flight";
	}
	
	@RequestMapping(value= "/flight/update", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel flight_update, Model model) {
		flightService.updateFlight(flight_update);
		return "update";
	}
	
	@RequestMapping(value= "/flight/view/{id}", method = RequestMethod.GET)
	private String getPilotByLicense(@PathVariable String id, Model model) {
		long id_long = Long.parseLong(id);
		FlightModel flight = flightService.getFlightDetailById(id_long);
		PilotModel pilot = flight.getPilot();
		model.addAttribute("flight", flight);
		model.addAttribute("pilot", pilot);
		
		return "view-flight";
	}
	
}
