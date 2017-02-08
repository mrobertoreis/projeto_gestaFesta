package com.gestaofesta.dao;

import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.gestaofesta.bean.GestaoFesta;
import com.gestaofesta.config.ConnectionFactory;
import com.gestaofesta.config.enums.Service;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class GestaoFestaDao {

	private static final String methods = "clientes";
	public static boolean inserir(GestaoFesta festa){
		Response done;
		try {
			done = ConnectionFactory.post(Service.GESTAO,methods+"/inserir", festa);
			Platform.runLater(()->{
				Alert alert = new Alert(done.getStatus() == 201 ? AlertType.INFORMATION : AlertType.ERROR);
				alert.setTitle("Atenção");
				alert.setContentText(done.readEntity(String.class));
				alert.show();
			});
			return done.getStatus() == 201;
		} catch (JSONException e) {
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}



}