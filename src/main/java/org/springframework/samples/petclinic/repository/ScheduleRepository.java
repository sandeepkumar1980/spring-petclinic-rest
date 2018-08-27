package org.springframework.samples.petclinic.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Visit;

public interface ScheduleRepository {

	void save(Schedule schedule) throws DataAccessException;
	
	Collection<Schedule> findAll() throws DataAccessException;
	
	List<Schedule> findByVetId(Integer vetId,Date date,String status, int time);
}
