package com.gestaofesta.view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gestaofesta.bean.GestaoFesta;
import com.gestaofesta.dao.GestaoFestaDao;
import com.gestaofesta.interfaces.DaoCallback;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FormGestaoFesta extends Application implements Initializable, DaoCallback<List<GestaoFesta>>{
	
	private Stage window;
	public TextField txtNome;
	public TextField txtAcompanhante;
	public ComboBox<String> cbVallet;
	public TableView<GestaoFesta> tableView;
	public Button btnAdicionar;
	
	private GestaoFesta gestao;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	public void start(Stage primaryStage){

		try{
			BorderPane root = FXMLLoader.load(getClass().getResource("FormGestaoFesta.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/FormGestaoFesta.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
//			primaryStage.getIcons().add(new Image(getClass().getResource("/logo/96.png").toExternalForm()));
			primaryStage.show();
			primaryStage.setOnCloseRequest(e->{
				System.exit(0);
			});
			window = primaryStage;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		telaPrincipal();
		setupTable(tableView);
	}


	@SuppressWarnings("unchecked")
	private void setupTable(TableView<GestaoFesta> table) {
		

		
		table.getItems().addListener(
				(ListChangeListener.Change<? extends GestaoFesta> c) ->setSaldo2());
		
		mostraTabela(table.getSelectionModel().getSelectedItem());
		
		TableColumn<GestaoFesta, String> colNome = new TableColumn<>("Nome");
		colNome.setStyle("-fx-alignment: CENTER-LEFT;");
		colNome.setCellValueFactory(f-> new ReadOnlyStringWrapper(f.getValue().getNome()));
		colNome.setPrefWidth(90);

		TableColumn<GestaoFesta, String> colAcompanhante = new TableColumn<>("Acompanhante");
		colAcompanhante.setStyle("-fx-alignment: CENTER-LEFT;");
		colAcompanhante.setCellValueFactory(f-> new ReadOnlyStringWrapper(f.getValue().getAcompanhante()));
		colAcompanhante.setPrefWidth(90);
		
		TableColumn<GestaoFesta, String> colVallet = new TableColumn<>("Utilizou Vallet");
		colVallet.setStyle("-fx-alignment: CENTER-LEFT;");
		colVallet.setCellValueFactory(f-> new ReadOnlyStringWrapper(f.getValue().getOpcaoVallet()));
		colVallet.setPrefWidth(90);
		
		table.getColumns().addAll(colNome, colAcompanhante, colVallet);
			
		
		
	}

	private void mostraTabela(GestaoFesta selectedItem) {
		// TODO Auto-generated method stub
		
	}

	private Object setSaldo2() {
		// TODO Auto-generated method stub
		return null;
	}

	private void telaPrincipal() {
		btnAdicionar.setOnAction(e->{
			inserir();
		});
		
	}

	private boolean validar() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void inserir() {
		if(validar()){
			ExecutorService ex = Executors.newSingleThreadExecutor();
			ex.submit(inserir);
			ex.shutdown();
		}
		
	}
	
	private Runnable inserir = new Runnable() {
		
		@Override
		public void run() {
			try{
				gestao.setNome(txtNome.getText());
				gestao.setAcompanhante(txtAcompanhante.getText());
				gestao.setOpcaoVallet(cbVallet.getValue());
				
				GestaoFestaDao.inserir(gestao);
				
			}catch(Exception e){
				
				e.printStackTrace();
			}
		}
	};



	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	public void onResult(List<GestaoFesta> result, int status) {
		// TODO Auto-generated method stub
		
	}

	public void onError(Exception error) {
		// TODO Auto-generated method stub
		
	}
		
	
}
