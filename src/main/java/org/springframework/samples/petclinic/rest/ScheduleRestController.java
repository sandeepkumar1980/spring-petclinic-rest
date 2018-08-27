package org.springframework.samples.petclinic.rest;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/schedules")
public class ScheduleRestController {

	@Autowired
	private ClinicService clinicService;
	
    @PreAuthorize( "hasRole(@roles.ROLE_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Collection<Schedule>> getAllSchedules(){
		Collection<Schedule> schedules = new ArrayList<Schedule>();
		schedules.addAll(this.clinicService.findAllSchedules());
		if (schedules.isEmpty()){
			return new ResponseEntity<Collection<Schedule>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Schedule>>(schedules, HttpStatus.OK);
	}
    
    /*
    @PreAuthorize( "hasRole(@roles.OWNER_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Schedule> addSchedule(@RequestBody @Valid Schedule schedule, BindingResult bindingResult, UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		if(bindingResult.hasErrors() || (schedule == null) ){
			errors.addAllErrors(bindingResult);
			headers.add("errors", errors.toJSON());
			return new ResponseEntity<Schedule>(headers, HttpStatus.BAD_REQUEST);
		}
		this.clinicService.saveSchedule(schedule);
		headers.setLocation(ucBuilder.path("/api/schedules/{id}").buildAndExpand(schedule.getId()).toUri());
		return new ResponseEntity<Schedule>(schedule, headers, HttpStatus.CREATED);
	}*/
    
    @PreAuthorize( "hasRole(@roles.ROLE_ADMIN)" )
	@RequestMapping(value = "/{vetId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Schedule>> getScheduleByVetId(@PathVariable("vetId") int vetId){
    	Collection<Schedule> schedules = new ArrayList<Schedule>();
    	//schedules.addAll(this.clinicService.findSchedulesByVetId(vetId));
		if (schedules.isEmpty()){
			return new ResponseEntity<Collection<Schedule>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Collection<Schedule>>(schedules, HttpStatus.OK);

	}
    
    
    @PreAuthorize( "hasRole(@roles.ROLE_ADMIN)" )
	@RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Schedule> addSchedule(@RequestBody  Schedule schedule,BindingResult bindingResult,UriComponentsBuilder ucBuilder){
		BindingErrorsResponse errors = new BindingErrorsResponse();
		HttpHeaders headers = new HttpHeaders();
		
		int vetId = schedule.getVet().getId();
		int time=schedule.getStarttime();
		Date date= schedule.getDate();
		String status = schedule.getStatus();
		Collection<Schedule> schedules = new ArrayList<Schedule>();
		//schedules.addAll(this.clinicService.findSchedulesByVetId(vetId,date,status,time));

		/*
		if((schedules != null) ){
			errors.addAllErrors(bindingResult);
			headers.add("Please Select a different time", errors.toJSON());
			return new ResponseEntity<Schedule>(headers, HttpStatus.BAD_REQUEST);
		}*/
		this.clinicService.saveSchedule(schedule);
		headers.setLocation(ucBuilder.path("/api/schedules/{id}").buildAndExpand(schedule.getId()).toUri());
		return new ResponseEntity<Schedule>(schedule, headers, HttpStatus.CREATED);
	}
    
	
}
