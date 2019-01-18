package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.Board;
import sample.Main;


public class StartViewController {

    @FXML
    CheckBox twoPlayersCheckBox;
    @FXML
    ImageView player1TankImage, player2TankImage;
    @FXML
    VBox player1VBox, player2VBox;

    private boolean twoPlayers=true;
    public void initialize()
    {
        player2TankImage.setImage(new Image("/Resources/Tanks/Yellow/down1.png"));
        player1TankImage.setImage(new Image("/Resources/Tanks/Green/down1.png"));
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
        if(twoPlayers==false){
            Board.players[1].setLives(0);
        }
        Main.root.getChildren().remove(0);
        Main.root.getChildren().add(sample.Main.views.get(0));
        GameViewController.timer.start();
    }
}
