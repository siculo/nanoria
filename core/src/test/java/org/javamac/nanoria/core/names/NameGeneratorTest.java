package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.utils.RandomNumberGenerator;
import org.javamac.nanoria.core.utils.Resources;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

public class NameGeneratorTest {
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator() {
        @Override
        public double generate(double max) {
            return Math.random() * max;
        }
    };

    @Test
    public void generateNamesFromASymbolSet() throws FileNotFoundException, URISyntaxException, InvalidSymbolException {
        SymbolSet symbolSet = SymbolSet.readSymbolSet(Resources.getFileFromResource(NameGeneratorTest.class, "/testSymbols.txt"));
        NameGenerator generator = new NameGenerator(symbolSet);

        for (int i = 0; i < 1000; i++) {
            String name = generator.generate();
            System.out.print((i > 0 ? ", ": "") + "\"" + name + "\"");
        }
        System.out.println("");
    }

}
