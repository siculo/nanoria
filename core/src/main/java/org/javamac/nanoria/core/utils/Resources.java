package org.javamac.nanoria.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

public class Resources {
    public static File getFileFromResource(Class<?> resourceClass, String fileName) throws URISyntaxException, FileNotFoundException {
        URL resource = resourceClass.getResource(fileName);
        if (resource == null) throw new FileNotFoundException();
        return new File(resource.toURI());
    }
}
