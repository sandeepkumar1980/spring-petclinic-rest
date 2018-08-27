package org.springframework.samples.petclinic.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.petclinic.rest.JacksonCustomPetDeserializer;
import org.springframework.samples.petclinic.rest.JacksonCustomScheduleDeserializer;
import org.springframework.samples.petclinic.rest.JacksonCustomScheduleSerializer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "Schedules")
@JsonSerialize(using = JacksonCustomScheduleSerializer.class)
@JsonDeserialize(using = JacksonCustomScheduleDeserializer.class)
public class Schedule extends BaseEntity{
	
    /**
     * Holds value of property description.
     */
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "status")
    private String status;
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "start_time")
    private int starttime;
    
    public int getStarttime() {
		return starttime;
	}

	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}

	@Column(name = "schedule_date")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "mm/dd/yyyy")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="mm/dd/yyyy")
    private Date date;
    
    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
     * Holds value of property pet.
     */
    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

	public Vet getVet() {
		return vet;
	}

	public void setVet(Vet vet) {
		this.vet = vet;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
