package org.springframework.samples.petclinic.rest;

import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JacksonCustomScheduleSerializer extends StdSerializer<Schedule>{

	public JacksonCustomScheduleSerializer() {
		this(null);
	}

	protected JacksonCustomScheduleSerializer(Class<Schedule> t) {
		super(t);
	}
	@Override
	public void serialize(Schedule schedule, JsonGenerator jgen, SerializerProvider provider) throws IOException {
		if ((schedule == null) || schedule.getVet() == null ) {
			throw new IOException("Cannot serialize schedule object - schedule or schedule.vet is null");
		}
		Format formatter = new SimpleDateFormat("mm/dd/yyyy");
		jgen.writeStartObject(); // schedule
		if (schedule.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", schedule.getId());
		}
		jgen.writeStringField("description", schedule.getDescription());
		jgen.writeNumberField("starttime", schedule.getStarttime());
		jgen.writeStringField("status", schedule.getStatus());
		jgen.writeStringField("date", formatter.format(schedule.getDate()));
		Vet vet = schedule.getVet();
		jgen.writeObjectFieldStart("vet");
		if (vet.getId() == null) {
			jgen.writeNullField("id");
		} else {
			jgen.writeNumberField("id", vet.getId());
		}
		
		jgen.writeStringField("firstName",vet.getFirstName());
		jgen.writeStringField("lastName",vet.getLastName());
		//jgen.writeNumberField("name",vet.getNrOfSpecialties());
		//jgen.write("name",vet.getSpecialties());

		jgen.writeEndObject();// vets
		jgen.writeEndObject(); // schedule
	}

}
