package org.javamac.nanoria.core;

import playn.core.Canvas;
import playn.core.Texture;
import playn.core.Tile;
import playn.scene.GroupLayer;
import playn.scene.ImageLayer;
import playn.scene.Layer;
import pythagoras.f.IDimension;
import react.RMap;

import java.util.HashMap;
import java.util.Map;

public class GameView extends GroupLayer {
    private final Nanoria game;
    private final MapView bview;
    private final GroupLayer pgroup = new GroupLayer();
    private final Tile[] ptiles;
    private final Map<Coord, ImageLayer> pviews = new HashMap<>();

    public GameView(Nanoria game, IDimension viewSize) {
        this.game = game;
        bview = new MapView(game, viewSize);
        addCenterAt(bview, viewSize.width() / 2, viewSize.height() / 2);
        addAt(pgroup, bview.tx(), bview.ty());

        // draw a black piece and white piece into a single canvas image
        final float size = bview.cellSize - 2, hsize = size / 2;
        final Canvas canvas = game.plat.graphics().createCanvas(2 * size, size);
        canvas.setFillColor(0xFF000000).fillCircle(hsize, hsize, hsize).
                setStrokeColor(0xFFFFFFFF).setStrokeWidth(2).strokeCircle(hsize, hsize, hsize - 1);
        canvas.setFillColor(0xFFFFFFFF).fillCircle(size + hsize, hsize, hsize).
                setStrokeColor(0xFF000000).setStrokeWidth(2).strokeCircle(size + hsize, hsize, hsize - 1);

        // convert the image to a texture and extract a texture region (tile) for each piece
        final Texture ptex = canvas.toTexture(Texture.Config.UNMANAGED);
        ptiles = new Tile[Piece.values().length];
        ptiles[Piece.BLACK.ordinal()] = ptex.tile(0, 0, size, size);
        ptiles[Piece.WHITE.ordinal()] = ptex.tile(size, 0, size, size);

        // dispose our pieces texture when this layer is disposed
        onDisposed(ptex.disposeSlot());

        game.pieces.connect(new RMap.Listener<Coord, Piece>() {
            @Override
            public void onPut(Coord coord, Piece piece) {
                setPiece(coord, piece);
            }

            @Override
            public void onRemove(Coord coord) {
                clearPiece(coord);
            }
        });

    }

    @Override
    public void close() {
        super.close();
        ptiles[0].texture().close(); // both ptiles reference the same texture
    }

    private ImageLayer addPiece(Coord at, Piece piece) {
        ImageLayer pview = new ImageLayer(ptiles[piece.ordinal()]);
        pview.setOrigin(Layer.Origin.CENTER);
        pgroup.addAt(pview, bview.cell(at.x), bview.cell(at.y));
        return pview;
    }

    private void setPiece(Coord at, Piece piece) {
        ImageLayer pview = pviews.get(at);
        if (pview == null) {
            pviews.put(at, addPiece(at, piece));
        } else {
            pview.setTile(ptiles[piece.ordinal()]);
        }
    }

    private void clearPiece(Coord at) {
        ImageLayer pview = pviews.remove(at);
        if (pview != null) pview.close();
    }
}
