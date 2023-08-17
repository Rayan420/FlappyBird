package com.rayan.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {

    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    private static final int LOWEST_OPENING =120;
    private static final int TUBE_GAP = 100;
    private Texture topTube, bottomTube;
    private Vector2 posTopTube, posBottomTube;
    private Random rand;
    private Rectangle topBounds, bottomBounds;

    public Tube(float x)
    {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        rand = new Random();
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION)+ TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());

        topBounds =  new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        bottomBounds = new Rectangle(posBottomTube.x, posBottomTube.y, bottomTube.getWidth(), bottomTube.getHeight());
    }

    public void reposition(float x)
    {
        posTopTube.set(x, rand.nextInt(FLUCTUATION)+ TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        topBounds.setPosition(posTopTube.x, posTopTube.y);
        bottomBounds.setPosition(posBottomTube.x, posBottomTube.y);
    }
    public Texture getTopTube() {
        return topTube;
    }

    public Texture getBottomTube() {
        return bottomTube;
    }

    public Vector2 getPosTopTube() {
        return posTopTube;
    }

    public Vector2 getPosBottomTube() {
        return posBottomTube;
    }

    public boolean collide(Rectangle player)
    {
        return player.overlaps(topBounds) || player.overlaps(bottomBounds);
    }

    public void dispose()
    {
        topTube.dispose();
        bottomTube.dispose();
    }

}
