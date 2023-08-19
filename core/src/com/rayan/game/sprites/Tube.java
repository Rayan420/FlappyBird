package com.rayan.game.sprites;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Tube {

    public static final int TUBE_WIDTH = 52;
    private static final int FLUCTUATION = 130;
    private static final int LOWEST_OPENING =120;
    private static final int TUBE_GAP = 100;
    private Texture topTube, bottomTube, coin;
    private Vector2 posTopTube, posBottomTube, posCoin;
    private Random rand;
    private Rectangle topBounds, bottomBounds, coinBounds;
    private boolean coinCollected = false;
    private boolean coinVisible = true;
    private Animation coinAnimation;
    private Sound ding;
    private int coinCount = 0;
    private Animation coinCounter;


    public Tube(float x)
    {
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");
        coin = new Texture("coin_gold.png");
        coinAnimation = new Animation(new TextureRegion(coin), 8, 0.5f);
        coinCounter = new Animation(new TextureRegion(coin), 8, 0.5f);
        rand = new Random();
        posTopTube = new Vector2(x, rand.nextInt(FLUCTUATION)+ TUBE_GAP + LOWEST_OPENING);
        posBottomTube = new Vector2(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        posCoin = new Vector2((float) (x + 8.5),(posTopTube.y + posBottomTube.y + TUBE_GAP + bottomTube.getHeight() - LOWEST_OPENING - coin.getHeight())/2);
        ding = Gdx.audio.newSound(Gdx.files.internal("coin_effect.ogg"));

        topBounds =  new Rectangle(posTopTube.x, posTopTube.y, topTube.getWidth(), topTube.getHeight());
        bottomBounds = new Rectangle(posBottomTube.x, posBottomTube.y, bottomTube.getWidth(), bottomTube.getHeight());
        coinBounds =  new Rectangle(posCoin.x, posCoin.y, coin.getWidth(), coin.getHeight());
    }

    public void reposition(float x)
    {
        posTopTube.set(x, rand.nextInt(FLUCTUATION)+ TUBE_GAP + LOWEST_OPENING);
        posBottomTube.set(x, posTopTube.y - TUBE_GAP - bottomTube.getHeight());
        posCoin.set((float) (x+8.5), (posTopTube.y + posBottomTube.y + TUBE_GAP + bottomTube.getHeight() - LOWEST_OPENING - coin.getHeight())/2);
        topBounds.setPosition(posTopTube.x, posTopTube.y);
        bottomBounds.setPosition(posBottomTube.x, posBottomTube.y);
        coinBounds.setPosition(posCoin.x, posCoin.y);
        this.coinCollected = false;
        this.coinVisible = true;
    }

    public void update(float dt)
    {
        coinAnimation.update(dt);

    }

    public TextureRegion getCoin() {
        return coinAnimation.getFrame();
    }


    public Vector2 getPosCoin() {
        return posCoin;
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

    // Method to check if the coin is visible
    public boolean isCoinVisible() {
        return coinVisible;
    }

    // Method to hide the coin
    public void hideCoin() {
        coinVisible = false;
    }
    public boolean coinCollide(Rectangle player) {
        if (coinVisible && player.overlaps(coinBounds)) {
            ding.play(0.1f);
            coinCount++;
            hideCoin(); // Hide the coin upon collision
            return true;
        }
        return false;
    }

    public boolean isCoinCollected()
    {
        return coinCollected;
    }

    public void setCoinCollected(boolean val)
    {
        coinCollected = val;
    }

    public void dispose()
    {
        topTube.dispose();
        bottomTube.dispose();
        coin.dispose();
        ding.dispose();


    }

}
