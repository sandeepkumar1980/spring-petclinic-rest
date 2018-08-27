package org.springframework.samples.petclinic.repository.springdatajpa;

import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.User;
import org.springframework.samples.petclinic.repository.ScheduleRepository;
import org.springframework.samples.petclinic.repository.UserRepository;
@Profile("spring-data-jpa")
public interface SpringDataScheduleRepository extends ScheduleRepository, Repository<Schedule, Integer> {

}
