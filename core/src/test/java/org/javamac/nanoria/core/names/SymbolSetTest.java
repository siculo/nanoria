package org.javamac.nanoria.core.names;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SymbolSetTest {
    private SymbolSet symbols;
    private List<Symbol> definedSymbols;

    @Before
    public void setUp() {
        symbols = new SymbolSet();
        definedSymbols = new ArrayList<Symbol>();
    }

    @Test
    public void selectTheOnlySymbolAdded() throws InvalidSymbolException {
        defineSymbols(
                new Symbol("c", 5.0, null, Role.INSET)
        );

        List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(null, Role.INSET, Role.FIRST);

        Assert.assertThat(matchingSymbols, new SymbolListMatcher(0));
    }

    @Test
    public void selectSymbolsForRole() throws InvalidSymbolException {
        defineSymbols(
                new Symbol("c", 5.0, null, Role.INSET),
                new Symbol("e", 5.0, null, Role.NUCLEUS),
                new Symbol("x", 2.0, null, Role.INSET)
        );

        List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(null, Role.INSET);

        Assert.assertThat(matchingSymbols, new SymbolListMatcher(0, 2));
    }

    @Test
    public void selectSymbolsForRoles() throws InvalidSymbolException {
        defineSymbols(
                new Symbol("c", 5.0, null, Role.INSET, Role.LAST),
                new Symbol("t", 2.0, null, Role.FIRST),
                new Symbol("c", 5.0, null, Role.CODA, Role.FIRST)
        );

        List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(null, Role.INSET, Role.FIRST);

        Assert.assertThat(matchingSymbols, new SymbolListMatcher(1));
    }

    @Test
    public void selectSymbolsForRoles2() throws InvalidSymbolException {
        defineSymbols(
                new Symbol("", 5.0, null, Role.INSET),
                new Symbol("c", 5.0, null, Role.INSET, Role.FIRST),
                new Symbol("d", 5.0, null, Role.CODA)
        );

        List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(null, Role.INSET, Role.FIRST);

        Assert.assertThat(matchingSymbols, new SymbolListMatcher(0, 1));
    }

    @Test
    public void selectAMoreCompellingRandomSymbolForRoles() throws InvalidSymbolException {
        defineSymbols(
                new Symbol("c", 5.0, null, Role.INSET),
                new Symbol("p", 5.0, null, Role.INSET, Role.LAST),
                new Symbol("c", 2.0, null, Role.INSET, Role.LAST),
                new Symbol("c", 5.0, null, Role.CODA, Role.FIRST)
        );

        List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(null, Role.INSET, Role.LAST);

        Assert.assertThat(matchingSymbols, new SymbolListMatcher(1, 2));
    }

    @Test
    public void selectASymbolCompatibleWithPreviousSymbol() throws InvalidSymbolException {
        defineSymbols(
                new Symbol("c", 4.0, "[aeiou].*", Role.INSET),
                new Symbol("y", 2.0, null, Role.NUCLEUS),
                new Symbol("a", 5.0, null, Role.NUCLEUS),
                new Symbol("j", 2.0, null, Role.NUCLEUS),
                new Symbol("k", 3.0, null, Role.CODA, Role.FIRST)
        );

        List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(definedSymbols.get(0), Role.NUCLEUS, Role.LAST);

        Assert.assertThat(matchingSymbols, new SymbolListMatcher(2));
    }

    private void defineSymbols(Symbol... newSymbols) {
        for (Symbol s : newSymbols) {
            symbols.add(s);
            definedSymbols.add(s);
        }
    }

    private class SymbolListMatcher extends BaseMatcher<List<Symbol>> {
        private final List<Symbol> expectedSymbols;

        public SymbolListMatcher(int... expectedSymbolIndexes) {
            this.expectedSymbols = new ArrayList<Symbol>();
            for (int i : expectedSymbolIndexes) {
                expectedSymbols.add(definedSymbols.get(i));
            }
        }

        @Override
        public void describeTo(Description description) {

        }

        @Override
        public boolean matches(Object o) {
            List<Symbol> actual = (List<Symbol>) o;
            if (actual.size() != expectedSymbols.size()) {
                return false;
            }
            for (Symbol s : actual) {
                boolean found = false;
                for (Symbol t : expectedSymbols) {
                    if (t == s) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    return false;
                }
            }
            return true;
        }
    }
}
