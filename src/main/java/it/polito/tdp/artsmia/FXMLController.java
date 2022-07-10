/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxLUN"
    private ChoiceBox<Integer> boxLUN; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcolaComponenteConnessa"
    private Button btnCalcolaComponenteConnessa; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaOggetti"
    private Button btnCercaOggetti; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalizzaOggetti"
    private Button btnAnalizzaOggetti; // Value injected by FXMLLoader

    @FXML // fx:id="txtObjectId"
    private TextField txtObjectId; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    
    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    txtResult.clear();
    txtResult.appendText(model.creaGrafo());
    	this.btnCalcolaComponenteConnessa.setDisable(false);
    	this.boxLUN.setDisable(false);
    	this.btnCercaOggetti.setDisable(false);
    	this.txtObjectId.setDisable(false);
    	
    	
    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	
    	txtResult.clear();
    	Integer id=null;  
    	try {
    		id= Integer.parseInt(this.txtObjectId.getText());
    	}
    	catch(NumberFormatException e){
    		txtResult.appendText("Inserire un ObjectId valido:");
    	}
    	
    	ArtObject vertice= this.model.getOgg(id); 
    	if (vertice==null) {
    		txtResult.appendText("L'oggetto selezionato non esiste");
    		return; //non funziona non esce, invece dovrebbe
    	}
    	
    	int size= model.getAdiacenti(vertice).size();
    	if(size!=1) {
    	txtResult.appendText("Dimensione componente connessa pari a: "+size);
    	}
    	else {
    		txtResult.appendText("Seleziona un ObjectId esistente");
    	}
    	
    }
    @FXML
    void doCercaOggetti(ActionEvent event) {

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnCalcolaComponenteConnessa.setDisable(true);
    	this.boxLUN.setDisable(true);
    	this.btnCercaOggetti.setDisable(true);
    	this.txtObjectId.setDisable(true);
    }
}
