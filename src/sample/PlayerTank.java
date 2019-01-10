package sample;

import javafx.scene.image.Image;

public class PlayerTank extends Tank {

    private String[] Controls; // Keys bound to moves: 0 - up, 1 - down, 2 - left, 3 - right, 4 - shot.
    private int score;
    private int lives;

    public PlayerTank(int iX, int iY, double x, double y, Image[] texturesUp, Image[] texturesDown, Image[] texturesLeft, Image[] texturesRight, String[] controls, int lives) {
        super(iX, iY, x, y, texturesUp, texturesDown, texturesLeft, texturesRight);
        Controls = controls;
        score = 0;
        this.lives = lives;
        attackSpeed = 3;
    }

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addScore(int score) {
        this.score += (score);
    }

    public String getControl(int i) {
        if (i >= 0 && i < Controls.length)
            return Controls[i];

        // Exception to throw?
        return "IndexOutOfBound";
    }
}

