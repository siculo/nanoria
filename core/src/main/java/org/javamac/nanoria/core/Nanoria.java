package org.javamac.nanoria.core;

import playn.core.Platform;
import playn.core.Surface;
import playn.scene.Layer;
import playn.scene.SceneGame;
import pythagoras.f.IDimension;
import react.RMap;
import react.Value;

public class Nanoria extends SceneGame {
    public static enum Piece {BLACK, WHITE}

    private final MapView.Config config = new MapView.Config(8);

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

        rootLayer.addCenterAt(new MapView(config, size), size.width() / 2, size.height() / 2);
    }
}
