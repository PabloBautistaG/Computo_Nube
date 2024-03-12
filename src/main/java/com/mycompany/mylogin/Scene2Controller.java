package com.mycompany.mylogin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Scene2Controller {
    @FXML
    Label nameLabel;
    public void displayName(String username, DataInputStream dis, DataOutputStream dos){
        nameLabel.setText("Hello " + username);
        Cliente.sendMessages(dis,dos);
    }
}
