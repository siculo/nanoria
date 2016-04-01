package org.javamac.nanoria.core;

import playn.core.Canvas;
import playn.core.Sound;
import playn.core.Texture;
import playn.core.Tile;
import playn.scene.*;
import pythagoras.f.IDimension;
import react.RMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameView extends GroupLayer {
    private final Nanoria game;
    private final MapView bview;
    private final GroupLayer pgroup = new GroupLayer();
    private final Tile[] ptiles;
    private final Map<Coord, ImageLayer> pviews = new HashMap<>();
    private final Sound click;

    public GameView(Nanoria game, IDimension viewSize) {
        this.game = game;

        bview = new MapView(game, viewSize);
        addCenterAt(bview, viewSize.width() / 2, viewSize.height() / 2);
        addAt(pgroup, bview.tx(), bview.ty());

        this.click = game.plat.assets().getSound("sounds/click");

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

    public void showPlays(List<Coord> coords, final Piece color) {
        final List<ImageLayer> plays = new ArrayList<>();
        for (final Coord coord : coords) {
            ImageLayer pview = addPiece(coord, color);
            pview.setAlpha(0.3f);
            // when the player clicks on a potential play, commit that play as their move
            pview.events().connect(new Pointer.Listener() {
                @Override
                public void onStart(Pointer.Interaction iact) {
                    // clear out the potential plays layers
                    for (ImageLayer play : plays) play.close();
                    // apply this play to the game state
                    game.logic.applyPlay(game.pieces, color, coord);
                    click.play();
                    // and move to the next player's turn
                    game.turn.update(color.next());
                }
            });
            // when the player hovers over a potential play, highlight it
            pview.events().connect(new Mouse.Listener() {
                @Override
                public void onHover(Mouse.HoverEvent event, Mouse.Interaction iact) {
                    iact.hitLayer.setAlpha(event.inside ? 0.6f : 0.3f);
                }
            });
            plays.add(pview);
        }
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
