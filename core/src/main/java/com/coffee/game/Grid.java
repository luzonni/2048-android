package com.coffee.game;

import static com.coffee.shared.DrawRoundedRectKt.drawRoundedRect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.coffee.Engine;
import com.coffee.input.TypeSlide;

import java.util.ArrayList;
import java.util.List;

public class Grid {

    private final ShapeRenderer shape;
    private final Color color;
    private final Rectangle bounds;

    private final int width, height;
    private final int padding;
    private final Slot[] grid;
    private boolean slided;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        this.padding = 2*Engine.SCALE();
        this.shape = new ShapeRenderer();
        this.color = new Color(0xbdada0ff);
        this.bounds = new Rectangle(0, 0, width*(Engine.SIZE()+padding)+padding, height*(Engine.SIZE()+padding)+padding);
        this.grid = buildGrid();
        spawn();
    }

    private Slot[] buildGrid() {
        Slot[] slots = new Slot[width*height];
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                slots[x+y*width] = new Slot(padding + (padding+Engine.SIZE())*x, padding + (padding+Engine.SIZE())*y, Engine.SIZE());
            }
        }
        return slots;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    private void spawn() {
        List<Slot> emptySlots = new ArrayList<>();
        for(int i = 0; i < grid.length; i++) {
            if(grid[i].isEmpty()) {
                emptySlots.add(grid[i]);
            }
        }
        if(!emptySlots.isEmpty())
            emptySlots.get(Engine.rand.nextInt(emptySlots.size())).putNew();
    }

    public Slot getSlot(int x, int y) {
        return grid[x+y*width];
    }

    private boolean allDone() {
        for (Slot slot : grid) {
            if (!slot.done()) {
                return false;
            }
        }
        return true;
    }

    private void setOnCenterScreen() {
        this.getBounds().setPosition(Engine.getWidth()/2f - getBounds().width/2, Engine.getHeight()/2f - getBounds().height/2);
    }

    public Slot[] getGrid() {
        return this.grid;
    }



    public void setGrid(Integer[] values) {
        for(int i = 0; i < grid.length; i++) {
            Slot slot = grid[i];
            slot.pop();
            if(values[i] != 0) {
                slot.putNew(values[i]);
            }
        }
    }

    public void tick() {
        setOnCenterScreen();
        for(Slot slot : grid) {
            if(slot != null)
                slot.tick();
        }
        if(allDone()) {
            TypeSlide slide = Engine.getInputs().getSlide();
            int score = 0;
            if (!slide.equals(TypeSlide.Idle)) {
                Game.getGame().stackGrid();
                score = slide(slide);
                slided = true;
            }
            if(slided && allDone()) {
                slided = false;
                spawn();
            }
            Game.getGame().plusScore(score);
        }
    }

    private int slide(TypeSlide slide) {
        int score = 0;
        List<Slot> joined = new ArrayList<>();
        if(slide.equals(TypeSlide.Down)) {
            for(int y = height-2; y >= 0; y--) {
                for(int x = 0; x < width; x++) {
                    score += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Up)) {
            for(int y = 1; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    score += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Left)) {
            for(int y = 0; y < height; y++) {
                for(int x = 1; x < width; x++) {
                    score += swap(joined, slide, x, y);
                }
            }
        }else if(slide.equals(TypeSlide.Right)) {
            for(int y = 0; y < height; y++) {
                for(int x = width - 2; x >= 0; x--) {
                    score += swap(joined, slide, x, y);
                }
            }
        }
        return score;
    }

    private int swap(List<Slot> joined, TypeSlide dir, int x, int y) {
        Slot curSlot = getSlot(x, y);
        if(curSlot.isEmpty())
            return 0;
        int nextX = x + dir.getNx();
        int nextY = y + dir.getNy();
        while((nextX >= 0 && nextX < width) && (nextY >= 0 && nextY < height)) {
            Slot nextSlot = getSlot(nextX, nextY);
            if(nextSlot.isEmpty()) {
                nextX += dir.getNx();
                nextY += dir.getNy();
            }else if(nextSlot.compareTo(curSlot) && !joined.contains(nextSlot)) {
                nextSlot.moveTo(curSlot);
                joined.add(nextSlot);
                return nextSlot.content().getValue();
            }else {
                break;
            }
        }
        if((nextX-dir.getNx() != x || nextY-dir.getNy() != y) && getSlot(nextX-dir.getNx(), nextY-dir.getNy()).isEmpty()) {
            getSlot(nextX - dir.getNx(), nextY - dir.getNy()).moveTo(curSlot);
            return 0;
        }
        return 0;
    }

    public void render() {
        shape.begin(ShapeRenderer.ShapeType.Filled);
        shape.setColor(color);
        drawRoundedRect(shape, bounds.x, bounds.y, bounds.width, bounds.height, (bounds.width/width)*0.1f);
        shape.end();
        for(Slot slot : grid) {
            slot.render();
        }
        for(Slot slot : grid) {
            if(!slot.isEmpty())
                slot.content().render();
        }
    }

    public void dispose() {
        for(Slot slot : grid) {
            if(!slot.isEmpty())
                slot.content().dispose();
            slot.dispose();
        }
        shape.dispose();
    }
}
