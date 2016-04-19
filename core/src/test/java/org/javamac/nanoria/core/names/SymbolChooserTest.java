package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.utils.RandomNumberGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SymbolChooserTest {
    private MyRandomNumberGenerator generator;
    private SymbolChooser chooser;

    @Before
    public void newSymbolChooser() {
        generator = new MyRandomNumberGenerator();
        chooser = new SymbolChooser(generator);
    }

    @Test
    public void noSymbolToSelect() throws InvalidSymbolException {
        List<Symbol> symbols = new ArrayList<Symbol>();
        Symbol symbol = chooser.choose(symbols);
        Assert.assertNull(symbol);
    }

    @Test
    public void selectASymbol() throws InvalidSymbolException {
        Symbol expected;
        List<Symbol> symbols = new ArrayList<Symbol>();
        symbols.add(new Symbol("a", 5.0, null, Role.NUCLEUS, Role.MIDDLE));
        symbols.add(new Symbol("e", 2.0, null, Role.NUCLEUS));
        symbols.add(expected = new Symbol("o", 1.0, null, Role.NUCLEUS));

        generator.fakeRandomNumber = 7.88;

        Symbol symbol = chooser.choose(symbols);
        Assert.assertSame(expected, symbol);

        Math.random();
    }

    private static class MyRandomNumberGenerator implements RandomNumberGenerator {
        double fakeRandomNumber = 0.0;

        @Override
        public double generate(double max) {
            return fakeRandomNumber;
        }
    }
}
