/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

/**
 *
 * @author loic.dessaules
 */
public class FXMLDocumentController implements Initializable {
    
    
    
    
    @FXML
    private TextArea taContent;
    @FXML
    private TextField tfMessage;
    @FXML
    private TextField tfUsername;
    @FXML
    private TextField tfServerIp;
    @FXML
    private Button btnConnection;
    @FXML
    private Button btnDisconnection;
    @FXML
    private TextArea taConnectedUsers;
    @FXML
    private Button btnSend;
    
    // Place the "cursor" of the text area at the botom of it
    private void goToTheEndOfTheTextAreaContent(){
        taContent.positionCaret(taContent.getLength());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){
        btnDisconnection.setVisible(false);
        btnSend.setDisable(true);
    }    

    @FXML
    private void btnConnexionAction(ActionEvent event){
    }

    @FXML
    private void btnDisconnexionAction(ActionEvent event) {
      
    }
    
}
