package com.rayan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rayan.game.FlappyBird;

public class GameOver extends  State{

    private Texture background, gameOver;
    private Texture playBtn;
    public GameOver(GameStateManager gsm)
    {
        super(gsm);
        camera.setToOrtho(false, FlappyBird.WIDTH/2, FlappyBird.HEIGHT/2);
        background = new Texture("bg.png");
        playBtn =  new Texture("playbtn.png");
        gameOver =  new Texture("gameover.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
        {
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }



    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(background, camera.position.x - (camera.viewportWidth/2), 0);
        sb.draw(playBtn, camera.position.x - playBtn.getWidth()/2, camera.position.y );
        sb.draw(gameOver, camera.position.x - gameOver.getWidth()/2, camera.position.y + gameOver.getHeight() + playBtn.getHeight() );
        sb.end();

    }

    @Override
    public void dispose() {
        playBtn.dispose();
        gameOver.dispose();
        background.dispose();
    }
}
