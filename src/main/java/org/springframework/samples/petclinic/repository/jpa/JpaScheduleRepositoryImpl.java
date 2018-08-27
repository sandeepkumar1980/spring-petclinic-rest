package org.springframework.samples.petclinic.repository.jpa;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.ScheduleRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public class JpaScheduleRepositoryImpl implements ScheduleRepository {

    @PersistenceContext
    private EntityManager em;
    
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Schedule> findAll() throws DataAccessException {
		return  this.em.createQuery("SELECT s FROM Schedule s").getResultList();
	}
	
    @Override
    public void save(Schedule schedule) {
        if (schedule.getId() == null) {
            this.em.persist(schedule);
        } else {
            this.em.merge(schedule);
        }
    }

	@Override
	public List<Schedule> findByVetId(Integer vetId,Date date,String status, int time) {
        Query query = this.em.createQuery("SELECT s FROM Schedule s where s.id= :id AND s.status=:status AND s.start_time=:time AND s.schedule_date=:date");
        query.setParameter("id", vetId);
        query.setParameter("start_time", time);
        query.setParameter("status", status);
        query.setParameter("schedule_date", date);
        return query.getResultList();
	}

}
