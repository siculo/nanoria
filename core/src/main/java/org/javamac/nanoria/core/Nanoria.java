package org.javamac.nanoria.core;

import playn.core.*;
import playn.scene.*;
import playn.scene.Mouse;
import playn.scene.Pointer;
import pythagoras.f.IDimension;
import react.RMap;
import react.Slot;
import react.Value;
import tripleplay.anim.Animator;

import java.util.ArrayList;
import java.util.List;

public class Nanoria extends SceneGame {

    public final int boardSize = 8;

    public final RMap<Coord, Piece> pieces = RMap.create();
    public final Value<Piece> turn = Value.create(null);
    public final Logic logic = new Logic(boardSize);
    public final Pointer pointer;
    public final Animator anim;

    public Nanoria(Platform plat) {
        super(plat, 33);

        final IDimension size = plat.graphics().viewSize;

        rootLayer.add(new Layer() {
            @Override
            protected void paintImpl(Surface surface) {
                surface.setFillColor(0xFFCCCCCC).fillRect(0, 0, size.width(), size.height());
            }
        });

        // create an animator for some zip zing
        anim = new Animator(paint);

        final GameView gameView = new GameView(this, size);
        rootLayer.add(gameView);

        // wire up a turn handler
        turn.connect(new Slot<Piece>() {
            private boolean lastPlayerPassed = false;

            @Override
            public void onEmit(Piece color) {
                List<Coord> plays = logic.legalPlays(pieces, color);
                if (!plays.isEmpty()) {
                    lastPlayerPassed = false;
                    gameView.showPlays(plays, color);
                } else if (lastPlayerPassed) {
                    endGame();
                } else {
                    lastPlayerPassed = true;
                    turn.update(color.next());
                }
            }
        });

        reset();

        // wire up pointer and mouse event dispatch
        pointer = new Pointer(plat, rootLayer, false);
        plat.input().mouseEvents.connect(new Mouse.Dispatcher(rootLayer, false));
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

    private void endGame() {
        // count up the pieces for each color
        Piece[] ps = Piece.values();
        int[] count = new int[ps.length];
        for (Piece p : pieces.values()) count[p.ordinal()]++;

        // figure out who won
        List<Piece> winners = new ArrayList<>();
        int highScore = 0;
        for (int ii = 0; ii < count.length; ii++) {
            int score = count[ii];
            if (score == highScore) winners.add(ps[ii]);
            else if (score > highScore) {
                winners.clear();
                winners.add(ps[ii]);
                highScore = score;
            }
        }

        // if we have only one winner, they win; otherwise it's a tie
        StringBuilder msg = new StringBuilder();
        if (winners.size() == 1) msg.append(winners.get(0)).append(" wins!");
        else {
            for (Piece p : winners) {
                if (msg.length() > 0) msg.append(" and ");
                msg.append(p);
            }
            msg.append(" tie.");
        }
        msg.append("\nClick to play again.");

        // render the game over message and display it in a layer
        IDimension vsize = plat.graphics().viewSize;
        TextBlock block = new TextBlock(plat.graphics().layoutText(
                msg.toString(), new TextFormat(new Font("Helvetica", Font.Style.BOLD, 48)),
                new TextWrap(vsize.width() - 20)));
        Canvas canvas = plat.graphics().createCanvas(block.bounds.width() + 4, block.bounds.height() + 4);
        canvas.setFillColor(0xFF0000FF).setStrokeColor(0xFFFFFFFF).setStrokeWidth(4f);
        block.stroke(canvas, TextBlock.Align.CENTER, 2, 2);
        block.fill(canvas, TextBlock.Align.CENTER, 2, 2);
        final ImageLayer layer = new ImageLayer(canvas.toTexture());
        rootLayer.addFloorAt(layer, (vsize.width() - canvas.width) / 2, (vsize.height() - canvas.height) / 2);

        // when the player clicks anywhere, restart the game
        pointer.events.connect(new Slot<Pointer.Event>() {
            @Override
            public void onEmit(Pointer.Event event) {
                if (event.kind.isStart) {
                    layer.close();
                    reset();
                    pointer.events.disconnect(this);
                }
            }
        });
    }
}
