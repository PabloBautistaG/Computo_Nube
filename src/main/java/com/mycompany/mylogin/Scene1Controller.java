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
import static javafx.scene.input.KeyCode.DOWN;
import static javafx.scene.input.KeyCode.LEFT;
import static javafx.scene.input.KeyCode.RIGHT;
import static javafx.scene.input.KeyCode.UP;

public class Scene1Controller {
    @FXML
    TextField nameTextField;
    @FXML
    TextField passwordTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    public void login(ActionEvent e){
        try{
            Cliente cliente = new Cliente();
            String username = nameTextField.getText();
            String password = passwordTextField.getText();
            
            Cliente.username = username;
            Cliente.password = password;
            
            //Cliente.initializeClient();
            
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
