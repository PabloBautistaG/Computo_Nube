package com.mycompany.mylogin;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;

public class Scene1Controller {
    @FXML
    TextField nameTextField;
    @FXML
    TextField passwordTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Cliente cliente;
    
    public void login(ActionEvent e){
        try{
            cliente = new Cliente();
            String username = nameTextField.getText();
            String password = passwordTextField.getText();
            
            Cliente.username = username;
            Cliente.password = password;
            
            Cliente.initializeClient();
            
            /*if(Cliente.username != "" && Cliente.password != ""){
                    Cliente.initializeClient();   
                }else{
                    nameTextField.setText("");
                    passwordTextField.setText("");
                }*/
            
            Cliente.initializeChat(Cliente.dis, Cliente.dos);
            
            stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            
            this.moveTank(stage);

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public void moveTank(Stage stage) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        root = loader.load();
        
        Scene2Controller scene2Controller = loader.getController();
        
        Cliente.scene2 = scene2Controller;
        
        scene2Controller.getPosition(cliente);
                
        //scene2Controller.displayName(username, Cliente.dis, Cliente.dos);

        scene = new Scene(root);
        scene.setOnKeyPressed(ee -> {
            switch (ee.getCode()) {
                case W:
                    scene2Controller.moveUp();
                    break;
                case S:
                    scene2Controller.moveDown();
                    break;
                case D:
                    scene2Controller.moveRight();
                    break;
                case A:
                    scene2Controller.moveLeft();
                    break;
                case RIGHT:
                    scene2Controller.rotateRight();
                    break;
                case LEFT:
                    scene2Controller.rotateLeft();
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
