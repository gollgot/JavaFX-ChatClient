/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author loic.dessaules
 */
public class FXMLDocumentController implements Initializable {
    
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnConnexionAction(ActionEvent event) {
    }

    @FXML
    private void btnDisconnexionAction(ActionEvent event) {
    }
    
}
