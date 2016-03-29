package org.javamac.nanoria.android;

import playn.android.GameActivity;

import org.javamac.nanoria.core.Nanoria;

public class NanoriaActivity extends GameActivity {
    @Override
    public void main() {
        new Nanoria(platform());
    }
}
