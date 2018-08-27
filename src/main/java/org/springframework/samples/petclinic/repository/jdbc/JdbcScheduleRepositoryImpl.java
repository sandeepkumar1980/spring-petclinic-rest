package org.springframework.samples.petclinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.ScheduleRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.repository.jdbc.JdbcVisitRepositoryImpl.JdbcVisitRowMapperExt;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jdbc")
public class JdbcScheduleRepositoryImpl implements ScheduleRepository{

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected SimpleJdbcInsert insertVisit;

    @Autowired
    public JdbcScheduleRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertVisit = new SimpleJdbcInsert(dataSource)
            .withTableName("schedules")
            .usingGeneratedKeyColumns("id");
    }
    
	@Override
	public void save(Schedule schedule) throws DataAccessException {
		if (schedule.isNew()) {
			Number newKey = this.insertVisit.executeAndReturnKey(createScheduleParameterSource(schedule));
			schedule.setId(newKey.intValue());
		} else {
			this.namedParameterJdbcTemplate.update(
					"UPDATE schedules SET schedule_id=:schedule_id,vet_id=:vet_id, description=:description ,schedule_date=:schedule_date WHERE id=:id ",
					createScheduleParameterSource(schedule));
		}
	}
    
    protected MapSqlParameterSource createScheduleParameterSource(Schedule schedule) {
        return new MapSqlParameterSource()
            .addValue("id", schedule.getId())
            .addValue("vet_id", schedule.getVet().getId())   
            .addValue("schedule_date", schedule.getDate()) 
            .addValue("start_time", schedule.getStarttime()) 
            .addValue("status", schedule.getStatus()) 
            .addValue("description", schedule.getDescription()
           
            		);
            
    }
	
	@Override
	public Collection<Schedule> findAll() throws DataAccessException {
		Map<String, Object> params = new HashMap<>();
		return this.namedParameterJdbcTemplate.query(
				"SELECT id as schedule_id description schedule_date FROM schedules",
				params, new JdbcScheduleRowMapperExt());
	}

	protected class JdbcScheduleRowMapperExt implements RowMapper<Schedule> {

		@Override
		public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
			Schedule schedule = new Schedule();

			schedule.setId(rs.getInt("schedule_id"));

			schedule.setDescription(rs.getString("description"));
			schedule.setDate(rs.getDate("schedule_date"));
			schedule.setStarttime(rs.getInt("start_time"));
			schedule.setStatus(rs.getString("status"));
			Vet vet = new Vet();
			
			Map<String, Object> params = new HashMap<>();
			params.put("vet_id", rs.getInt("vet_id"));
			vet = JdbcScheduleRepositoryImpl.this.namedParameterJdbcTemplate.queryForObject(
					"SELECT id, first_name, last_name  FROM vets WHERE id= :vet_id",
					params,
					BeanPropertyRowMapper.newInstance(Vet.class));
			schedule.setVet(vet);
			return schedule;
		}
	}

	@Override
	public List<Schedule> findByVetId(Integer vetId,Date date,String status, int time) {
		// TODO Auto-generated method stub
        Map<String, Object> params = new HashMap<>();
        params.put("id", vetId);
        Vet vet = this.namedParameterJdbcTemplate.queryForObject(
                "SELECT id as vets_id, first_name, last_name FROM vets WHERE id=:id",
                params,
                new JdbcVetRowMapper());

        List<Schedule> schedules = this.namedParameterJdbcTemplate.query(
            "SELECT id as schedule_id, description FROM schedules WHERE vet_id=:id",
            params, new JdbcScheduleRowMapper());

        for (Schedule schedule: schedules) {
        	schedule.setVet(vet);
        }

        return schedules;
		
		
		//return null;
	}

	
}
