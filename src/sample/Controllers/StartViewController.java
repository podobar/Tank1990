package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sample.Main;
import sample.PlayerTank;
import sample.Tank;

import java.io.File;



public class StartViewController {
    @FXML
    ImageView player1TankImage, player2TankImage;

    private void ValidateInput()
    {
        //check if user chose free key for control
    }
    public void initialize()
    {
        player1TankImage.setImage(new Image("Tanks/Blue/down1.png"));
        player2TankImage.setImage(new Image("Tanks/Green/down1.png"));
    }
    @FXML
    private void startGame(MouseEvent e){
        Main.root.getChildren().remove(0);
        Main.root.getChildren().add(sample.Main.views.get(0));
    }
}
