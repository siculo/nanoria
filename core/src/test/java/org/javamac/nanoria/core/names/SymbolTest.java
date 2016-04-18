package org.javamac.nanoria.core.names;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.regex.PatternSyntaxException;

public class SymbolTest {
    @Test
    public void simpleRoleMatching() throws InvalidSymbolException {
        Symbol symbol = new Symbol("c", 5.0, null, Role.INSET);
        boolean matching = symbol.matchRoles(Role.INSET);
        Assert.assertTrue(matching);
    }

    @Test
    public void multipleRolesMatching() throws InvalidSymbolException {
        Symbol symbol = new Symbol("c", 5.0, null, Role.CODA);
        boolean matching = symbol.matchRoles(Role.CODA, Role.FIRST);
        Assert.assertTrue(matching);
    }

    @Test
    public void multipleRolesMatching2() throws InvalidSymbolException {
        Symbol symbol = new Symbol("c", 5.0, null, Role.INSET, Role.MIDDLE);
        boolean matching = symbol.matchRoles(Role.INSET, Role.MIDDLE);
        Assert.assertTrue(matching);
    }

    @Test
    public void notMatchingRoles() throws InvalidSymbolException {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        boolean matching = symbol.matchRoles(Role.NUCLEUS, Role.LAST);
        Assert.assertFalse(matching);
    }

    @Test
    public void compareEqualsSymbol() throws InvalidSymbolException {
        Comparable<Symbol> a = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol b = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);

        Assert.assertEquals(0, a.compareTo(b));
    }

    @Test
    public void compareSymbol1() throws InvalidSymbolException {
        Comparable<Symbol> a = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol b = new Symbol("c", 5.0, null, Role.NUCLEUS);

        Assert.assertEquals(1, a.compareTo(b));
    }

    @Test
    public void compareSymbol2() throws InvalidSymbolException {
        Comparable<Symbol> a = new Symbol("c", 5.0, null, Role.NUCLEUS);
        Symbol b = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);

        Assert.assertEquals(-1, a.compareTo(b));
    }

    @Test
    public void allowedByNull() throws InvalidSymbolException {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(null));
    }

    @Test
    public void allowedBySymbolWithNullRule() throws InvalidSymbolException {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol previousSymbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(previousSymbol));
    }

    @Test
    public void allowedBySymbolWithEmptyRule() throws InvalidSymbolException {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol previousSymbol = new Symbol("c", 5.0, "", Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(previousSymbol));
    }

    @Test
    public void allowedByRule() throws InvalidSymbolException {
        Symbol previousSymbol = new Symbol("c", 5.0, "[bcd].*", Role.NUCLEUS, Role.MIDDLE);
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(previousSymbol));
    }

    @Test
    public void notAllowedByRule() throws InvalidSymbolException {
        Symbol previousSymbol = new Symbol("c", 5.0, "[bcd].*", Role.NUCLEUS, Role.MIDDLE);
        Symbol symbol = new Symbol("a", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertFalse(symbol.allowedBy(previousSymbol));
    }

    @Test
    public void emptyKeyIsValid() throws InvalidSymbolException {
        new Symbol("", 5.0, "[bcd].*", Role.NUCLEUS, Role.MIDDLE);
    }

    @Test(expected = InvalidSymbolException.class)
    public void weightMustBeValid() throws InvalidSymbolException {
        new Symbol("a", -33.563, "[bcd].*", Role.NUCLEUS, Role.MIDDLE);
    }

    @Test(expected = InvalidSymbolException.class)
    public void allowsMustBeAValidPattern() throws InvalidSymbolException {
        new Symbol("c", 5.0, "**", Role.NUCLEUS, Role.MIDDLE);
    }
}