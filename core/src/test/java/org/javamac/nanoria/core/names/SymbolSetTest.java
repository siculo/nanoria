package org.javamac.nanoria.core.names;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

public class SymbolSetTest {
    private MyRandomNumberGenerator generator;
    private SymbolSet symbols;

    @Before
    public void setUp() {
        generator = new MyRandomNumberGenerator();
        symbols = new SymbolSet(generator);
    }

    @Test
    public void selectTheOnlySymbolAdded() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.INSET);
        symbols.add(symbol);
        Symbol previousSymbol = null;
        Symbol selected = symbols.select(previousSymbol, Role.INSET, Role.FIRST);
        Assert.assertSame(symbol, selected);
    }

    @Test
    public void selectASymbolForRole() {
        symbols.add(new Symbol("c", 5.0, null, Role.INSET));
        symbols.add(new Symbol("t", 2.0, null, Role.FIRST));
        Symbol selected = symbols.select(null, Role.INSET);
        Assert.assertThat(selected, new BaseMatcher<Symbol>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("<symbols for role " + Role.INSET + ">");
            }

            @Override
            public boolean matches(Object item) {
                Collection<Symbol> roleSymbols = symbols.findSymbols(Role.INSET);
                return roleSymbols.contains(item);
            }
        });
    }

    @Test
    public void selectASymbolForRoles() {
        Symbol expected = new Symbol("t", 2.0, null, Role.FIRST);

        symbols.add(new Symbol("c", 5.0, null, Role.INSET, Role.LAST));
        symbols.add(expected);
        symbols.add(new Symbol("c", 5.0, null, Role.CODA, Role.FIRST));

        Symbol selected = symbols.select(null, Role.INSET, Role.FIRST);

        Assert.assertSame(expected, selected);
    }

    @Test
    public void selectAMoreCompellingSymbolForRoles() {
        Symbol expected = new Symbol("c", 5.0, null, Role.INSET, Role.LAST);

        symbols.add(new Symbol("c", 5.0, null, Role.INSET));
        symbols.add(expected);
        symbols.add(new Symbol("c", 5.0, null, Role.CODA, Role.FIRST));

        Symbol selected = symbols.select(null, Role.INSET, Role.LAST);

        Assert.assertSame(expected, selected);
    }

    @Test
    public void selectAMoreCompellingRandomSymbolForRoles() {
        Symbol expected = new Symbol("c", 2.0, null, Role.INSET, Role.LAST);

        symbols.add(new Symbol("c", 5.0, null, Role.INSET));
        symbols.add(new Symbol("p", 5.0, null, Role.INSET, Role.LAST));
        symbols.add(expected);
        symbols.add(new Symbol("c", 5.0, null, Role.CODA, Role.FIRST));

        generator.fakeRandom = 7.0;

        Symbol selected = symbols.select(null, Role.INSET, Role.LAST);

        Assert.assertSame(expected, selected);
    }

    @Test
    public void selectAMoreCompellingRandomSymbolForRoles2() {
        Symbol expected = new Symbol("c", 5.0, null, Role.INSET, Role.LAST);

        symbols.add(new Symbol("c", 5.0, null, Role.INSET));
        symbols.add(expected);
        symbols.add(new Symbol("p", 2.0, null, Role.INSET, Role.LAST));
        symbols.add(new Symbol("c", 5.0, null, Role.CODA, Role.FIRST));

        generator.fakeRandom = 1.5;

        Symbol selected = symbols.select(null, Role.INSET, Role.LAST);

        Assert.assertSame(expected, selected);
    }

    @Test
    public void selectASymbolCompatibleWithPreviousSymbol() {
        Symbol previous = new Symbol("c", 4.0, "[aeiou].*", Role.INSET);
        Symbol expected = new Symbol("a", 5.0, null, Role.NUCLEUS);

        symbols.add(previous);
        symbols.add(new Symbol("y", 2.0, null, Role.NUCLEUS));
        symbols.add(expected);
        symbols.add(new Symbol("j", 2.0, null, Role.NUCLEUS));
        symbols.add(new Symbol("k", 3.0, null, Role.CODA, Role.FIRST));

        generator.fakeRandom = 0;

        Symbol selected = symbols.select(previous, Role.NUCLEUS, Role.LAST);

        System.out.println(symbols);

        Assert.assertSame(expected, selected);
    }

    private static class MyRandomNumberGenerator implements RandomNumberGenerator {
        double fakeRandom = 0.0;

        @Override
        public double generate(double max) {
            return fakeRandom;
        }
    }
}
