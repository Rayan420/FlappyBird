package com.rayan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rayan.game.FlappyBird;
import com.rayan.game.sprites.Bird;
import com.rayan.game.sprites.Tube;

public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -30;
    private Bird bird;
    private Texture bg;
    private Array<Tube> tubes;

    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        bg = new Texture("bg.png");
        camera.setToOrtho(false, FlappyBird.WIDTH/2, FlappyBird.HEIGHT/2);
        tubes = new Array<Tube>();
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(camera.position.x - camera.viewportWidth /2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2((camera.position.x - camera.viewportWidth /2) + ground.getWidth(), GROUND_Y_OFFSET);

        for(int i = 1; i<=TUBE_COUNT;i++)
        {
            tubes.add(new Tube(i * (TUBE_SPACING + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched())
            bird.jump();
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);
        camera.position.x = bird.getPosition().x + 80;

        for(int i = 0; i<tubes.size; i++)
        {
            Tube tube = tubes.get(i);
            if(camera.position.x - (camera.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth())
            {
                tube.reposition(tube.getPosTopTube().x + ((tube.TUBE_WIDTH+TUBE_SPACING)* TUBE_COUNT));
            }
            if(tube.collide(bird.getBounds()))
            {
                gsm.set(new PlayState(gsm));
            }
        }
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
            gsm.set(new PlayState(gsm));
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(bg, camera.position.x - (camera.viewportWidth/2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);

        for(Tube tube : tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);

        }
        sb.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        bird.dispose();
        ground.dispose();
        for(Tube tube :tubes)
            tube.dispose();
        System.out.println("play state disposed");
    }
    private void updateGround()
    {
        if(camera.position.x - (camera.viewportWidth/2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth()*2, 0);
        if(camera.position.x - (camera.viewportWidth/2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth()*2, 0);

    }
}
