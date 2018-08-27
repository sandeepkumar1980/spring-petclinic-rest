package org.springframework.samples.petclinic.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.samples.petclinic.model.Schedule;
import org.springframework.samples.petclinic.model.Vet;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class JacksonCustomScheduleDeserializer extends StdDeserializer<Schedule> {

	public JacksonCustomScheduleDeserializer() {
		this(null);
	}

	protected JacksonCustomScheduleDeserializer(Class<Schedule> t) {
		super(t);
	}
	
	@Override
	public Schedule deserialize(JsonParser parser, DeserializationContext context)	throws IOException, JsonProcessingException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		Schedule schedule = new Schedule();
		Vet vet = new Vet();
		Date date = null;
		JsonNode node = parser.getCodec().readTree(parser);
		ObjectMapper mapper = new ObjectMapper();
		int scheduleId = node.get("id").asInt();
		String scheduleDateStr = node.get("date").asText(null);
		JsonNode vet_node = node.get("vet");
		vet = mapper.treeToValue(vet_node, Vet.class);
		
		
		String description = node.get("description").asText(null);
		int starttime = node.get("starttime").asInt();
		String status = node.get("status").asText(null);
		if (!(scheduleId == 0)) {
			schedule.setId(scheduleId);
		}
		
		try {
			date = formatter.parse(scheduleDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		schedule.setDescription(description);
		schedule.setStatus(status);
		schedule.setStarttime(starttime);
		schedule.setVet(vet);
		schedule.setDate(date);
		return schedule;
	}

		
}
