package com.gestaofesta.config;

import java.io.IOException;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JSONObjectModule extends  SimpleModule {
	
	private static final long serialVersionUID = 6515376101564607110L;

	public JSONObjectModule(){
		super("JSONObjectModule");
		addSerializer(new Serializer(JSONObject.class));
		addDeserializer(JSONObject.class, new Desserializer(JSONObject.class));
	}

	public static class Desserializer extends StdScalarDeserializer<JSONObject> {

		private static final long serialVersionUID = 510123016741535924L;

		protected Desserializer(Class<JSONObject> vc) {
			super(vc);
		}

		@SuppressWarnings("deprecation")
		@Override
		public JSONObject deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
			JsonToken t = jp.getCurrentToken();
			if (t == JsonToken.VALUE_STRING) {
				String str = jp.getText().trim();
				if (str.length() == 0) {
					return null;
				}
				return new JSONObject(str);
			}else if(t == JsonToken.START_OBJECT){
				JsonNode node = jp.readValueAsTree();
				return new JSONObject(node.toString());
			}
			throw ctxt.mappingException(getValueClass());
		}
	}

	public static class Serializer extends StdSerializer <JSONObject>{
		private static final long serialVersionUID = 8078782858146092360L;

		protected Serializer(Class<JSONObject> t) {
			super(t);
		}

		@Override
		public void serialize(JSONObject value, JsonGenerator jgen, SerializerProvider provider)throws IOException, JsonGenerationException{
			String str = value.toString();
			jgen.writeString(str);
		}
	}
}

