package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.Main;


public class StartViewController {
    public static boolean twoPlayers=true;
    @FXML
    CheckBox twoPlayersCheckBox;
    @FXML
    ImageView player1TankImage, player2TankImage;
    @FXML
    VBox player1VBox, player2VBox;

    private void ValidateInput()
    {
        //check if user chose free key for control
    }
    public void initialize()
    {
        player2TankImage.setImage(new Image("Tanks/Blue/down1.png"));
        player1TankImage.setImage(new Image("Tanks/Green/down1.png"));
    }
    @FXML
    private void startWithTwoPlayers(MouseEvent e){
        if(twoPlayersCheckBox.isSelected()){
            player2VBox.setVisible(true);
            twoPlayers=true;
        }
        else{
            player2VBox.setVisible(false);
            twoPlayers=false;
        }


    }
    @FXML
    private void startGame(MouseEvent e){
        Main.root.getChildren().remove(0);
        Main.root.getChildren().add(sample.Main.views.get(0));
        GameViewController.timer.start();
    }
}
