/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.Scanner;
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
    
    Socket socket;
    
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
        tfMessage.setDisable(true);
    }    

    @FXML
    private void btnConnexionAction(ActionEvent event){
        int port = 23002;
        
        try {
            String serverIp = tfServerIp.getText();
            String username = tfUsername.getText();
            // create socket to the server
            socket = new Socket(serverIp,port);
            
            // We hide the btnConnection, display the btnDisconnection
            // And we disable username and ipServer textField (this way the user cannot change them)
            // And enable the TF message ans btn send
            btnConnection.setVisible(false);
            btnDisconnection.setVisible(true);
            tfUsername.setDisable(true);
            tfServerIp.setDisable(true);
            btnSend.setDisable(false);
            tfMessage.setDisable(false);
            
            // If the connection is establish, the server is waiting our username
            // so, we send it 
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(username);
            out.flush();
            taContent.setText(taContent.getText()+getTimeFormated()+"Vous êtes connecté au serveur\n");
            goToTheEndOfTheTextAreaContent();
            readMessage();
        } catch (IOException ex) {
            // The connection is not etablish
            taContent.setText(taContent.getText()+getTimeFormated()+"Connexion au serveur impossible ...\n");
            goToTheEndOfTheTextAreaContent();
        }
        
    }

    @FXML
    private void btnDisconnexionAction(ActionEvent event) {
        disconnect();
    }

    @FXML
    private void btnSendAction(ActionEvent event) {
  
        try {
            // Send a message to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String messageToSend = tfMessage.getText();
            if(messageToSend.equals("/quit")){
                disconnect();
            }else{
                out.println(messageToSend);
                out.flush();
                taContent.setText(taContent.getText()+getTimeFormated()+"Vous : "+messageToSend+"\n");
                goToTheEndOfTheTextAreaContent();
            }
            
        } catch (IOException ex) {
            System.out.println("Error on 'btnSendAction' method. EX: "+ex.getMessage().toString());
        }
        
    }
    
    // get the time when you call the method
    private String getTimeFormated(){
        String time = "";
        int hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minutes = Calendar.getInstance().get(Calendar.MINUTE);
        int seconds = Calendar.getInstance().get(Calendar.SECOND);
        if(seconds < 10){
            time = hours+":"+minutes+":0"+seconds+" ";
        }else{
            time = hours+":"+minutes+":"+seconds+" ";
        }

        return time;
    }
    
    // send /quit to the server and close all socket, return to the initial state
    private void disconnect(){
        PrintWriter out = null;
        try {
            // Send message /quit to the server (like that he knows we quit and he
            // can close properly all socket
            out = new PrintWriter(socket.getOutputStream());
            String messageToSend = "/quit";
            out.println(messageToSend);
            out.flush();
            // Put all buttons etc.. on the init state
            socket.close();
            taContent.setText("");
            taConnectedUsers.setText("");
            btnDisconnection.setVisible(false);
            btnConnection.setVisible(true);
            tfUsername.setDisable(false);
            tfServerIp.setDisable(false);
            btnSend.setDisable(true);
            tfMessage.setText("");
            tfMessage.setDisable(true);       
        } catch (IOException ex) {
            System.out.println("Error on 'disconnect' method. EX: "+ex.getMessage().toString());
        } finally {
            out.close();
        }
    }
    
    private void readMessage(){
        Thread readMessageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                BufferedReader in = null;
                String receivedMessage;
                while(!socket.isClosed()){
                    try {
                        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        receivedMessage = in.readLine();
                        if(receivedMessage.contains("[OnlineUsers];")){
                            String[] onlineUsers = receivedMessage.split(";");
                            String toDisplay = "";
                            for (int i = 1; i < onlineUsers.length; i++) {
                                toDisplay += onlineUsers[i]+"\n";
                            }
                            taConnectedUsers.setText("");
                            taConnectedUsers.setText(toDisplay);
                        }else if(receivedMessage.contains("[ServerDisconnected]")){
                            disconnect();
                            taContent.setText(taContent.getText()+getTimeFormated()+"Serveur déconnecté...\n");
                            goToTheEndOfTheTextAreaContent();
                        }
                    } catch (IOException ex) {
                        try {
                            // When the user Disconnected, the socket is closed but
                            // the execution is blocked in "receivedMessage = in.readLine();" line
                            // so, we close the socket properly
                            System.out.println("ERROR in method (Thread) : readMessage ex = "+ex.getMessage().toString());
                            socket.close();
                        } catch (IOException ex1) {
                            System.out.println("ERROR in method (Thread) : readMessage ex1 = "+ex.getMessage().toString());
                        }
                    }
                }
            }
        });
        
        readMessageThread.start();
        
    }
    
}
