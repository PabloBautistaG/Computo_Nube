package com.mycompany.mylogin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;


public class Scene2Controller {
    @FXML
    Label nameLabel;
    @FXML
    Rectangle rect1;
    @FXML
    ImageView tank;

    public Scene2Controller() throws FileNotFoundException {

    }
    
    public void displayName(String username, DataInputStream dis, DataOutputStream dos){
        nameLabel.setText("Hello " + username);
        Cliente.sendMessages(dis,dos);
    }

    public void moveUp(){
        rect1.setY(rect1.getY() - 10);
    }
    public void moveDown(){
        rect1.setY(rect1.getY() + 10);
    }
    public void moveRight(){
        rect1.setX(rect1.getX() + 10);
    }
    public void moveLeft(){
        rect1.setX(rect1.getX() - 10);
    }
    public void rotateLeft(){
        rect1.setRotate(rect1.getRotate() + 90.0);
    }
    public void rotateRight(){
        rect1.setRotate(rect1.getRotate() - 90.0);
    }
}
