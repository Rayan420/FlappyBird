package com.rayan.game.states;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/*
created by Ahmed Rayan on 8/16/2023 at 4:11 am
this is an abstract state class that will be extended to create and control,
the satate and camera throughout the game

 */
public abstract class State {
    // class attributes
    protected OrthographicCamera camera; // camera attribute
    protected Vector3 mouse; // xyz coordinates of mouse
    protected GameStateManager gsm; // state manage to manage and stack states

    // constructor
    protected State(GameStateManager gsm)
    {
        this.gsm = gsm;
        camera = new OrthographicCamera();
        mouse = new Vector3();
    }

    protected abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render(SpriteBatch sb);

    public abstract void dispose();




}
