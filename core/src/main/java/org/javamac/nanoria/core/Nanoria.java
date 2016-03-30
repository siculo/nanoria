package org.javamac.nanoria.core;

import playn.core.Platform;
import playn.core.Surface;
import playn.scene.Layer;
import playn.scene.SceneGame;
import pythagoras.f.IDimension;
import react.RMap;
import react.Value;

public class Nanoria extends SceneGame {

    public final int boardSize = 8;

    public final RMap<Coord, Piece> pieces = RMap.create();
    public final Value<Piece> turn = Value.create(null);

    public Nanoria(Platform plat) {
        super(plat, 33);

        final IDimension size = plat.graphics().viewSize;

        rootLayer.add(new Layer() {
            @Override
            protected void paintImpl(Surface surface) {
                surface.setFillColor(0xFFCCCCCC).fillRect(0, 0, size.width(), size.height());
            }
        });

        rootLayer.add(new GameView(this, size));

        reset();
    }

    /**
     * Clears the board and sets the 2x2 set of starting pieces in the middle.
     */
    private void reset() {
        pieces.clear();
        int half = boardSize / 2;
        pieces.put(new Coord(half - 1, half - 1), Piece.WHITE);
        pieces.put(new Coord(half, half - 1), Piece.BLACK);
        pieces.put(new Coord(half - 1, half), Piece.BLACK);
        pieces.put(new Coord(half, half), Piece.WHITE);
        turn.updateForce(Piece.BLACK);
    }

}
