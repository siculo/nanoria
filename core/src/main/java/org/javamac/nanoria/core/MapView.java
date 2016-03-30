package org.javamac.nanoria.core;

import playn.core.Surface;
import playn.scene.Layer;
import pythagoras.f.IDimension;

public class MapView extends Layer {
    private static final float LINE_WIDTH = 2;

    private final Nanoria game;

    public final float cellSize;

    public MapView(Nanoria game, IDimension viewSize) {
        this.game = game;
        float maxBoardSize = Math.min(viewSize.width(), viewSize.height()) - 20;
        this.cellSize = (float) Math.floor(maxBoardSize / game.boardSize);
    }

    @Override
    public float width() {
        return cellSize * game.boardSize + LINE_WIDTH;
    }

    @Override
    public float height() {
        return width();
    }

    @Override
    protected void paintImpl(Surface surface) {
        surface.setFillColor(0xFF000000);

        float top = 0, bot = height(), left = 0, right = width();

        for (int yy = 0; yy <= game.boardSize; yy++) {
            float ypos = yy * cellSize + 1;
            surface.drawLine(left, ypos, right, ypos, LINE_WIDTH);
        }

        for (int xx = 0; xx <= game.boardSize; xx++) {
            float xpos = xx * cellSize + 1;
            surface.drawLine(xpos, bot, xpos, top, LINE_WIDTH);
        }
    }

    /**
     * Returns the offset to the center of cell {@code cc} (in x or y).
     */
    public float cell(int cc) {
        // cc*cellSize is upper left corner, then cellSize/2 to center,
        // then 1 to account for our 2 pixel line width
        return cc * cellSize + cellSize / 2 + 1;
    }

}
