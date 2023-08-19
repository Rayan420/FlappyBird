package com.rayan.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;


public class Bird {
    private final long startTime; // Variable to store the start time

    private static final int GRAVITY = -15;
    private Vector3 position;
    private static int MOVEMENT = 100;
    private Vector3 velocity;
    private Rectangle bounds;
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
        startTime = TimeUtils.nanoTime(); // Store the current time when the state is created


    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void update(float dt) {
        birdAnimation.update(dt);

        if (position.y > 0) {
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(dt);

        if (TimeUtils.timeSinceNanos(startTime) <= 90000000000L) { // 90 seconds in nanoseconds
            // Calculate the incremental change per frame
            float increment = (250 - 100) / (90 * 1000000000f) * TimeUtils.timeSinceNanos(startTime);

            MOVEMENT = (int) Math.min(100 + increment, 250); // Ensure MOVEMENT doesn't exceed 250
        }

        position.add(MOVEMENT * dt, velocity.y, 0);

        if (position.y < 0) {
            position.y = 0;
        }

        velocity.scl(1 / dt);
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
        MOVEMENT = 100;
    }
}
