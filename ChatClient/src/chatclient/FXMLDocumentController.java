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
        int port = 23002;
        
        try {
            String serverIp = tfServerIp.getText();
            String username = tfUsername.getText();
            // create socket to the server
            Socket socket = new Socket(serverIp,port);
            
            // We hide the btnConnection, display the btnDisconnection
            // And we disable username and ipServer textField (this way the user cannot change them)
            btnConnection.setVisible(false);
            btnDisconnection.setVisible(true);
            tfUsername.setDisable(true);
            tfServerIp.setDisable(true);
            
            // If the connection is establish, the server is waiting our username
            // so, we send it 
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(username);
            out.flush();
        } catch (IOException ex) {
            // The connection is not etablish
            taContent.setText(taContent.getText()+"Connexion au serveur impossible ...\n");
            goToTheEndOfTheTextAreaContent();
        }
        
    }

    @FXML
    private void btnDisconnexionAction(ActionEvent event) {
      
    }
    
}
