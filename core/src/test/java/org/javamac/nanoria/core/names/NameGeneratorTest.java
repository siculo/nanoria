package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.NotEmptyStringMatcher;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.net.URL;

public class NameGeneratorTest {
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator() {
        @Override
        public double generate(double max) {
            return Math.random() * max;
        }
    };

    @Test
    public void generateNamesFromASymbolSet() throws FileNotFoundException, URISyntaxException {
        File f = getFileFromResource(NameGeneratorTest.class, "testSymbols.txt");
    }

    private File getFileFromResource(Class<?> resourceClass, String fileName) throws URISyntaxException, FileNotFoundException {
        URL resource = resourceClass.getClassLoader().getResource(fileName);
        if (resource == null) throw new FileNotFoundException();
        return new File(resource.toURI());
    }

    private static void readLanguageSymbols(SymbolSet symbolSet, File languageFile) throws FileNotFoundException, InvalidSymbolException {
        FileReader reader = new FileReader(languageFile);
        SymbolReader symbolReader = new SymbolReader(reader);
        Symbol symbol;
        while ((symbol = symbolReader.getNextSymbol()) != null) {
            symbolSet.add(symbol);
        }
    }
}
