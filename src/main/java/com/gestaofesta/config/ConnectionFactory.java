package com.gestaofesta.config;

import java.util.HashMap;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import br.com.orion.config.ObjectMapperContextResolver;
import com.gestaofesta.config.enums.Service;

public class ConnectionFactory {

	public static class QueryParams {
		private HashMap<String, Object> params = new HashMap<String, Object>();

		public static QueryParams open(String key, Object value) {
			return new QueryParams().put(key, value);
		}

		public QueryParams put(String key, Object value) {
			params.put(key, value);
			return this;
		}

		public Object get(String key) {
			return params.get(key);
		}

		public HashMap<String, Object> getParams() {
			return params;
		}
	}

	public static Response get(Service rest, String url) {
		return get(rest, url, new QueryParams());
	}

	public static Response get(Service rest, String uri, QueryParams params) {
		return get(rest, uri, params.getParams());
	}

	public static Response get(Service rest, String uri, HashMap<String, Object> params) {
		WebTarget clientBuilder = ClientBuilder.newClient()
				.register(ObjectMapperContextResolver.class)
				.target(rest.toString())
				.path(uri);

		if (params != null) {
			for (String key : params.keySet()) {
				clientBuilder = clientBuilder.queryParam(key, params.get(key));
			}
		}

		Builder builder = clientBuilder.request(MediaType.APPLICATION_JSON_TYPE);
		for (String key : rest.getHeaders().keySet()) {
			builder = builder.header(key, rest.getHeaders().get(key));
		}
		return builder.get();
	}

	public static <T> Response post(Service rest, String uri, T param) {
		return post(rest, uri, param, new QueryParams());
	}

	public static <T> Response post(Service rest, String uri, T param, QueryParams params) {
		return post(rest, uri, param, params.getParams());
	}

	public static <T> Response post(Service rest, String uri, T param, HashMap<String, Object> params) {

		MediaType mediaType = param instanceof Form ? MediaType.APPLICATION_FORM_URLENCODED_TYPE
				: param instanceof MultiPart ? MediaType.MULTIPART_FORM_DATA_TYPE : MediaType.APPLICATION_JSON_TYPE;

		WebTarget clientBuilder = ClientBuilder.newClient()
				.register(MultiPartFeature.class)
				.register(ObjectMapperContextResolver.class)
				.target(rest.toString())
				.path(uri);

		if (params != null) {
			for (String key : params.keySet()) {
				clientBuilder = clientBuilder.queryParam(key, params.get(key));
			}
		}

		Builder builder = clientBuilder.request();
		for (String key : rest.getHeaders().keySet()) {
			builder = builder.header(key, rest.getHeaders().get(key));
		}
		return builder.post(Entity.entity(param, mediaType));
	}
	
	public static Response download(Service rest, String url) {
		return download(rest, url, new QueryParams());
	}

	public static Response download(Service rest, String uri, QueryParams params) {
		return download(rest, uri, params.getParams());
	}

	public static Response download(Service rest, String uri, HashMap<String, Object> params) {
		WebTarget target = ClientBuilder
				.newClient()
				.property(ClientProperties.REQUEST_ENTITY_PROCESSING, "CHUNKED")
				.register(ObjectMapperContextResolver.class)
				.target(rest.toString())
				.path(uri);

		if (params != null) {
			for (String key : params.keySet()) {
				target = target.queryParam(key, params.get(key));
			}
		}

		Builder builder = target.request();
		for (String key : rest.getHeaders().keySet()) {
			builder = builder.header(key, rest.getHeaders().get(key));
		}
		Response r = builder.get();
		return r;
	}

	// public static Response doMultiPartPostWithResponse(String url, String
	// param, File file){
	// Response response = null;
	// try{
	// if(!file.getName().contains(".")){
	// throw new Exception("Nome de arquivo ilegal");
	// }
	//
	// String str = file.getName().substring(file.getName().lastIndexOf('.')+1);
	//
	// ByteArrayOutputStream bas = new ByteArrayOutputStream();
	// BufferedImage bi = ImageIO.read(file);
	// ImageIO.write(bi, str, bas);
	// byte[] logo = bas.toByteArray();
	//
	// @SuppressWarnings("resource")
	// MultiPart multiPart = new MultiPart().
	// bodyPart(new BodyPart(param, MediaType.APPLICATION_JSON_TYPE)).
	// bodyPart(new BodyPart(logo, MediaType.APPLICATION_OCTET_STREAM_TYPE));
	//
	// response = ClientBuilder
	// .newClient()
	// .register(ObjectMapperContextResolver.class)
	// .target(Defines.URL_DASHBOARD_SERVER)
	// .path(url)
	// .post(Response.class, multiPart);
	//
	// return response;
	// }catch(Exception er){
	// er.printStackTrace();
	// return null;
	// }
	// }

}