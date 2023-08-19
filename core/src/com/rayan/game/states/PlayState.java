package com.rayan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rayan.game.FlappyBird;
import com.rayan.game.sprites.Animation;
import com.rayan.game.sprites.Bird;
import com.rayan.game.sprites.Tube;
import com.badlogic.gdx.audio.Sound;

public class PlayState extends State{
    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -30;
    private Bird bird;
    private Texture bg, coin;
    private Array<Tube> tubes;
    private BitmapFont font;
    private Sound gameOverEffect;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Animation coinAnime;

    protected PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,300);
        coin = new Texture("coin_gold.png");
        coinAnime = new Animation(new TextureRegion(coin), 8, 0.5f);
        bg = new Texture("bg.png");
        camera.setToOrtho(false, FlappyBird.WIDTH/2, FlappyBird.HEIGHT/2);
        tubes = new Array<Tube>();
        gameOverEffect = Gdx.audio.newSound(Gdx.files.internal("game_over.mp3"));
        font = new BitmapFont(); // Load your font file
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
                gameOverEffect.play(1f);
                dispose();
                gsm.set(new GameOver(gsm));
            }
            if (tube.coinCollide(bird.getBounds())) {
                if (tube.isCoinVisible() && tube.isCoinCollected()) {
                    tube.setCoinCollected(true); // Set the coin as collected
                    tube.hideCoin();
                }
                bird.setCoins();
                System.out.println(bird.coins);
            }
            tube.update(dt);
        }
        if(bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET)
        {
            gameOverEffect.play(1f);
            dispose();
            gsm.set(new GameOver(gsm));
        }
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
        float coinPlaceholderX = camera.position.x + camera.viewportWidth / 2 - coinAnime.getFrame().getRegionWidth() - 10;

        float coinPlaceholderY = camera.viewportHeight - coinAnime.getFrame().getRegionHeight() - 10;
        sb.draw(coinAnime.getFrame(), coinPlaceholderX, coinPlaceholderY);

        // Calculate the x-coordinate to center the text relative to the coin
        float coinTextX = coinPlaceholderX + coinAnime.getFrame().getRegionWidth() - 5;

        // Adjust the font position for the collected coins
        font.draw(sb, bird.getCoins(), coinTextX, coinPlaceholderY  + coinAnime.getFrame().getRegionHeight() - font.getLineHeight()/2 );
        font.setColor(255,215,0, 1);

        for(Tube tube : tubes)
        {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
            if (tube.isCoinVisible()) {
                sb.draw(tube.getCoin(), tube.getPosCoin().x, tube.getPosCoin().y);
            }

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
