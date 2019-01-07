package sample;

import javafx.scene.image.Image;

public abstract class MovingTile extends Tile
{
    protected boolean IsMoving;
    protected double X;
    protected double Y;
    protected String Direction;

    protected double speed; // 1 means that tank (object) moves about 1 tile per second


    protected int TextureChangeCounter; // To think about

    protected Image[] TextureUp;
    protected Image[] TextureDown;
    protected Image[] TextureLeft;
    protected Image[] TextureRight;

    public double getSpeed() {
        return speed;
    }

    public int getTextureChangeCounter() {
        return TextureChangeCounter;
    }

    public void setTextureChangeCounter(int TextureChangeCounter) {
        this.TextureChangeCounter = TextureChangeCounter;
    }

    public void ChangeTexture(){
        switch (Direction){
            case "UP":
                texture = TextureUp[ChooseNextTexture(TextureUp)];
                break;

            case "DOWN":
                texture = TextureDown[ChooseNextTexture(TextureDown)];
                break;

            case "LEFT":
                texture = TextureLeft[ChooseNextTexture(TextureLeft)];
                break;

            case "RIGHT":
                texture = TextureRight[ChooseNextTexture(TextureRight)];
                break;

                default:
                    break;
        }
    }

    private int ChooseNextTexture(Image[] textures){
        int iNow = 0;
        for(int i = 0; i < textures.length; i++)
            if(texture == textures[i]) {
                iNow = i;
                break;
            }
            return iNow == textures.length - 1? 0 : iNow + 1;
    }
}
