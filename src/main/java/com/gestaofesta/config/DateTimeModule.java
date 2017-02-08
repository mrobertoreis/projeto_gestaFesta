package com.gestaofesta.config;

import java.io.IOException;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class DateTimeModule extends  SimpleModule {
	
	private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	private static final long serialVersionUID = 6515376101564607110L;

	public DateTimeModule(){
		super("DateTimeModule");
		addSerializer(new Serializer(LocalDateTime.class));
		addDeserializer(LocalDateTime.class, new Desserializer(LocalDateTime.class));
	}

	public static class Desserializer extends StdScalarDeserializer<LocalDateTime> {

		private static final long serialVersionUID = 510123016741535924L;

		protected Desserializer(Class<LocalDateTime> vc) {
			super(vc);
		}

		@SuppressWarnings("deprecation")
		@Override
		public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonToken t = jp.getCurrentToken();
			if (t == JsonToken.VALUE_NUMBER_INT) {
				return new LocalDateTime(jp.getLongValue());
			}
			
			if (t == JsonToken.VALUE_STRING) {
				String str = jp.getText().trim();
				if (str.length() == 0) {
					return null;
				}
				return DateTimeFormat.forPattern(PATTERN).parseLocalDateTime(str);
			}
			throw ctxt.mappingException(getValueClass());
		}
	}

	public static class Serializer extends StdSerializer <LocalDateTime>{

		private static final long serialVersionUID = 8078782858146092360L;

		protected Serializer(Class<LocalDateTime> t) {
			super(t);
		}

		@Override
		public void serialize(LocalDateTime value, JsonGenerator jgen, SerializerProvider provider)throws IOException, JsonGenerationException{
			if (provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)) {
				jgen.writeNumber(value.toDateTime().getMillis());
			} else {
				jgen.writeString(value.toString(PATTERN));
			}
		}

		@Override
		public JsonNode getSchema(SerializerProvider provider, java.lang.reflect.Type typeHint){
			return createSchemaNode(provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)?
					"number" : "string", true);
		}

	}

}


