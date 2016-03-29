package org.javamac.nanoria.java;

import playn.java.LWJGLPlatform;

import org.javamac.nanoria.core.Nanoria;

public class NanoriaJava {
    public static void main(String[] args) {
        LWJGLPlatform.Config config = new LWJGLPlatform.Config();
        // use config to customize the Java platform, if needed
        LWJGLPlatform plat = new LWJGLPlatform(config);
        new Nanoria(plat);
        plat.start();
    }
}
