package org.springframework.samples.petclinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Visit;

public class JdbcScheduleRowMapper implements RowMapper<Schedule> {

	@Override
    public Schedule mapRow(ResultSet rs, int row) throws SQLException {
		Schedule schedule = new Schedule();
		schedule.setId(rs.getInt("schedule_id"));
        schedule.setDescription(rs.getString("description"));
        schedule.setStarttime(rs.getInt("start_time"));
        schedule.setStatus(rs.getString("status"));
        Date scheduleDate = rs.getDate("schedule_date");
        schedule.setDate(new Date(scheduleDate.getTime()));
        return schedule;
    }
}
