package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.utils.RandomNumberGenerator;
import org.javamac.nanoria.core.utils.Resources;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

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

        for (int i = 0; i < 15; i++) {
            String name = generator.generate();
            System.out.print((i > 0 ? ", ": "") + "\"" + name + "\"");
        }
        System.out.println("");
    }

    @Test
    public void generateNamesFromASymbolSet2() throws FileNotFoundException, URISyntaxException, InvalidSymbolException {
        SymbolSet symbolSet = SymbolSet.readSymbolSet(Resources.getFileFromResource(NameGeneratorTest.class, "/newSymbols.txt"));

        boolean validSymbolSet = true;

        SymbolSetValidator validator = new SymbolSetValidator(symbolSet);
        List<Symbol> notValidSymbols = validator.getNotValidSymbols();
        validSymbolSet = notValidSymbols.size() == 0;
        if (!validSymbolSet) {
            System.out.println("Not valid symbols:");
            for (Symbol s : notValidSymbols) {
                System.out.println(s);
            }
        }

        if (validSymbolSet) {
            NameGenerator generator = new NameGenerator(symbolSet);
            for (int i = 0; i < 15; i++) {
                String name = generator.generate();
                System.out.print((i > 0 ? ", " : "") + "\"" + name + "\"");
            }
            System.out.println("");
        }
    }

}
