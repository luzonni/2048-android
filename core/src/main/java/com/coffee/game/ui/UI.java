package com.coffee.game.ui;

import static com.coffee.shared.DrawRoundedRectKt.drawRoundedRect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Activity;
import com.coffee.Engine;
import com.coffee.game.Game;
import com.coffee.menu.Menu;
import com.coffee.shared.Button;

public class UI implements Activity {

    private final Button backMenu;
    private final Button restoreButton;

    private final SpriteBatch batch;
    private final ShapeRenderer shape;
    private final GlyphLayout layout;
    private final BitmapFont font;
    private final Rectangle bounds;
    private final Color scoreColor;

    public UI() {
        float x = Engine.getWidth();
        float y = Engine.getHeight() - Engine.SIZE();
        this.restoreButton = new Button("Revert", x, y);
        this.restoreButton.setColor(new Color(0xbcaba0ff));
        this.restoreButton.setPosition(x - this.restoreButton.getBounds().width, y - this.restoreButton.getBounds().height);
        this.backMenu = new Button("Menu", x, y);
        this.backMenu.setColor(new Color(0xbcaba0ff));
        this.backMenu.setPosition(0, y - this.restoreButton.getBounds().height);
        this.batch = new SpriteBatch();
        this.shape = new ShapeRenderer();
        this.font = new BitmapFont(Gdx.files.internal("Rowdies/Rowdies.fnt"));
        this.layout = new GlyphLayout();
        this.layout.setText(this.font, "0");
        this.bounds = new Rectangle(x/2 - layout.width/2, y, layout.width, layout.height);
        this.font.setColor(new Color(0x786e64ff));
        this.scoreColor = new Color(0xbbac9fff);
    }

    private void setScore() {
        String text = String.valueOf(Game.getGame().getScore());
        this.layout.setText(this.font, text);
        float padding = Engine.SCALE() * 2;
        this.bounds.setSize(layout.width + padding*2, layout.height + padding*2);
        float x = Engine.getWidth()/2f;
        float y = Game.getGrid().getBounds().y + Game.getGrid().getBounds().height + Engine.SIZE();
        this.bounds.setPosition(x - bounds.width/2, y - bounds.height);
    }

    @Override
    public void tick() {
        setScore();
        if(this.restoreButton.clicked()) {
            Game.getGame().restore();
        }
        if(this.backMenu.clicked()) {
            Engine.setActivity(new Menu());
            return;
        }
        Rectangle bounds = Game.getGrid().getBounds();
    }

    @Override
    public void render() {
        restoreButton.render();
        backMenu.render();
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(this.scoreColor);
        drawRoundedRect(this.shape, bounds.x, bounds.y, bounds.width, bounds.height, Engine.SIZE()*0.1f);
        shape.end();

        String text = String.valueOf(Game.getGame().getScore());
        float wF = layout.width;
        float hF = layout.height;
        batch.begin();
        font.draw(batch, text, bounds.x + bounds.getWidth()/2 - wF/2, bounds.y + bounds.getHeight()/2 + hF/2);
        batch.end();
    }

    @Override
    public void dispose() {
        restoreButton.dispose();
        backMenu.dispose();
        this.shape.dispose();
        this.batch.dispose();
        this.font.dispose();
    }
}
