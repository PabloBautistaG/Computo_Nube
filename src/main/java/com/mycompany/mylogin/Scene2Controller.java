package com.mycompany.mylogin;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import java.io.FileNotFoundException;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.animation.TranslateTransition;

public class Scene2Controller {
    @FXML
    Label nameLabel;
    @FXML
    Rectangle rect1;
    @FXML
    ImageView tank;
    //File file = new File("tank.png");
    //Image img = new Image(file.toURI().toString());
    //ImageView tank = new ImageView(img);
    //Image img = new Image(getClass().getResourceAsStream("/img/tank.png"));
    //ImageView tank = new ImageView(img);
    int dir=0;
    
    public Scene2Controller() throws FileNotFoundException {

    }
    
    public void displayName(String username, DataInputStream dis, DataOutputStream dos){
        nameLabel.setText("Hello " + username);
        Cliente.sendMessages(dis,dos);
    }

    public void moveUp(){
        TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank);
        TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);
        
        
        if(dir == 3 || dir == -1 || dir == 1 || dir == -3)
        {
            //animacion de movimiento del tanque
            //tank.setY(tank.getY() -10);

            translateT.setByY(-50f);
            translateT.setCycleCount(1);
            translateT.setAutoReverse(true);
            translateT.play();

            //animacion del movimiento del disparo
            //rect1.setY(rect1.getY() - 10);
            translateR.setByY(-50f);
            translateR.setCycleCount(1);
            translateR.setAutoReverse(true);
            translateR.play();
        }
                
    }
    public void moveDown(){
        
        TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank);
        TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);
        
        if(dir == 1 || dir == -3 || dir == 3 || dir == -1)
        {
           //animacion del tanque
           //tank.setY(tank.getY() +10);
           translateT.setByY(50f);
           translateT.setCycleCount(1);
           translateT.setAutoReverse(true);
           translateT.play();

           //animacion del disparo
           //rect1.setY(rect1.getY() + 10);
           translateR.setByY(50f);
           translateR.setCycleCount(1);
           translateR.setAutoReverse(true);
           translateR.play();
        }
       
    }
    public void moveRight(){
        //
        TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank);
        TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);
        
        
        if(dir == 0 || dir ==2 || dir == -2)
        {
            //animacion del tanque
            //tank.setX(tank.getX() +10);
            translateT.setByX(50f);
            translateT.setCycleCount(1);
            translateT.setAutoReverse(true);
            translateT.play();

            //animacion del disparo
            //rect1.setX(rect1.getX() + 10);
            translateR.setByX(50f);
            translateR.setCycleCount(1);
            translateR.setAutoReverse(true);
            translateR.play();
        }

    }
    public void moveLeft(){
        //
        TranslateTransition translateT = new TranslateTransition(Duration.millis(500),tank);
        TranslateTransition translateR = new TranslateTransition(Duration.millis(500),rect1);
        
        
        if(dir == 2 || dir == -2 || dir == 0)
        {
            //animacion del tanque
            //tank.setX(tank.getX() -10);
            translateT.setByX(-50f);
            translateT.setCycleCount(1);
            translateT.setAutoReverse(true);
            translateT.play();

            //animacion del disparo
            //rect1.setX(rect1.getX() - 10);
            translateR.setByX(-50f);
            translateR.setCycleCount(1);
            translateR.setAutoReverse(true);
            translateR.play();
        }
        
    }
    public void rotateRight(){
        //giro normal sin animacion
        //rect1.setRotate(rect1.getRotate() + 90.0);
        
        
        //rotacion con animacion
        dir+=1;
        //validacion de vuelta completa
        resetPosition();
        
        //
        RotateTransition rotacion = new RotateTransition();
        
        //animacion del giro del tanque
        rotacion.setDuration(Duration.millis(1000)); 
        //rotacion.setNode(rect1);
        rotacion.setNode(tank);
        rotacion.setByAngle(90);
        rotacion.setCycleCount(1);
        rotacion.setAutoReverse(false);
        rotacion.play();
    }
    public void rotateLeft(){
        //giro normal
        //rect1.setRotate(rect1.getRotate() - 90.0);
        
        
        //rotacion con animacion
        dir-=1;
        //validacion de la vuelta completa
        resetPosition();
                
        //
        RotateTransition rotacion = new RotateTransition();
        
        //animacion del giro del tanque
        rotacion.setDuration(Duration.millis(1000));
        //rotacion.setNode(rect1);
        rotacion.setNode(tank);
        rotacion.setByAngle(-90);
        rotacion.setCycleCount(1);
        rotacion.setAutoReverse(false);
        rotacion.play();
    }
    
    public void shot(){
        TranslateTransition tt = new TranslateTransition(Duration.millis(1000),rect1);
        
        switch(dir)
        {
            case 0:
                
                    //rect1.setX(rect1.getX() + 20);
                    tt.setByX(100f);
                    tt.setCycleCount(1);
                    tt.setAutoReverse(true);
                    tt.play();
                    
                    
                    
                    //verificamos si sale de la pantalla
                    if(rect1.getX() + 100f >= 700){
                        rect1=null;
                    }
                
                break;
                
            case 1:
                //rect1.setY(rect1.getY() + 20);
               
                tt.setByY(100f);
                tt.setCycleCount(1);
                tt.setAutoReverse(true);
                tt.play();
                break;
                
            case 2:
                //rect1.setX(rect1.getX() - 20);
               
                tt.setByX(-100f);
                tt.setCycleCount(1);
                tt.setAutoReverse(true);
                tt.play();
                break;
                
            case 3:
                //rect1.setY(rect1.getY() - 20);
               
                tt.setByY(-100f);
                tt.setCycleCount(1);
                tt.setAutoReverse(true);
                tt.play();
                break;
                
            case -1:
                //rect1.setY(rect1.getY() - 20);
               
                tt.setByY(-100f);
                tt.setCycleCount(1);
                tt.setAutoReverse(true);
                tt.play();
                break;
                
            case -2:
                //rect1.setX(rect1.getX() - 20);
               
                tt.setByX(-100f);
                tt.setCycleCount(1);
                tt.setAutoReverse(true);
                tt.play();
                break;
                
            case -3:
                //rect1.setY(rect1.getY() + 20);
              
                tt.setByY(100f);
                tt.setCycleCount(1);
                tt.setAutoReverse(true);
                tt.play();
                break;       
        }
    }
    
    void resetPosition(){
        if(dir ==4 || dir== -4)
        {
            dir=0;
        }
    }
}
