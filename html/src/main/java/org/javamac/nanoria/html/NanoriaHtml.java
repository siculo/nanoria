package org.javamac.nanoria.html;

import com.google.gwt.core.client.EntryPoint;
import playn.html.HtmlPlatform;
import org.javamac.nanoria.core.Nanoria;

public class NanoriaHtml implements EntryPoint {

  @Override public void onModuleLoad () {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform plat = new HtmlPlatform(config);
    plat.assets().setPathPrefix("nanoria/");
    new Nanoria(plat);
    plat.start();
  }
}
