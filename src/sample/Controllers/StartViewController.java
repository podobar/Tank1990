package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class StartViewController {
    private void ValidateInput()
    {
        //check if user chose free key for control
    }

    @FXML
    private void startGame(MouseEvent e){
        sample.Main.root.getChildren().remove(0);
        sample.Main.root.getChildren().add(sample.Main.views.get(0));
    }
}
