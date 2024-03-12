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
import com.mycompany.mylogin.Cliente;

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
            root = loader.load();
            
            Cliente.username = username;
            Cliente.password = password;
            
            Cliente.initializeClient();
            
            Scene2Controller scene2Controller = loader.getController();
            scene2Controller.displayName(username, Cliente.dis, Cliente.dos);
            

                stage = (Stage)((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}
