package sample;

import javafx.scene.image.Image;

public class PlayerTank extends Tank{

    private String[] Controls; // Keys bound to moves: 0 - up, 1 - down, 2 - left, 3 - right.

    public PlayerTank(int iX, int iY, double x, double y, Image[] texturesUp, Image[] texturesDown, Image[] texturesLeft, Image[] texturesRight, String[] controls) {
        super(iX, iY, x, y, texturesUp, texturesDown, texturesLeft, texturesRight);
        Controls = controls;
    }

    public String getControl(int i) {
        if (i >= 0 && i < Controls.length)
            return Controls[i];

        // Exception to throw?
        return "IndexOutOfBound";
    }
}

