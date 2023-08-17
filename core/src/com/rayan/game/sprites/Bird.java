package com.rayan.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {
    private static final int GRAVITY = -15;
    private Vector3 position;
    private static final int MOVEMENT = 100;
    private Vector3 velocity;
    private Rectangle bounds;
    private Texture bird;
    private Texture texture;
    private Animation birdAnimation;
    private Sound flap;
    public Bird(int x, int y)
    {
        position = new Vector3(x,y,0);
        velocity = new Vector3(0,0,0);
        texture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(texture), 3, 0.5f);
        bounds = new Rectangle(x,y, texture.getWidth()/3, texture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));

    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void update(float dt)
    {
        birdAnimation.update(dt);
        if(position.y > 0)
            velocity.add(0, GRAVITY,0);
        velocity.scl(dt);
        position.add(MOVEMENT*dt, velocity.y, 0);

        if(position.y < 0 )
            position.y = 0;
        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);

    }

    public Rectangle getBounds()
    {
        return bounds;
    }
    public void jump()
    {
        velocity.y = 250;
        flap.play(0.7f);

    }

    public void dispose()
    {
        texture.dispose();
        flap.dispose();
    }
}
