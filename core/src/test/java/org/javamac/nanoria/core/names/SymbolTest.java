package org.javamac.nanoria.core.names;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.regex.PatternSyntaxException;

public class SymbolTest {
    @Test
    public void simpleRoleMatching() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.INSET);
        boolean matching = symbol.matchRoles(Role.INSET);
        Assert.assertTrue(matching);
    }

    @Test
    public void multipleRolesMatching() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.CODA);
        boolean matching = symbol.matchRoles(Role.CODA, Role.FIRST);
        Assert.assertTrue(matching);
    }

    @Test
    public void multipleRolesMatching2() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.INSET, Role.MIDDLE);
        boolean matching = symbol.matchRoles(Role.INSET, Role.MIDDLE);
        Assert.assertTrue(matching);
    }

    @Test
    public void notMatchingRoles() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        boolean matching = symbol.matchRoles(Role.NUCLEUS, Role.LAST);
        Assert.assertFalse(matching);
    }

    @Test
    public void compareEqualsSymbol() {
        Comparable<Symbol> a = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol b = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);

        Assert.assertEquals(0, a.compareTo(b));
    }

    @Test
    public void compareSymbol1() {
        Comparable<Symbol> a = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol b = new Symbol("c", 5.0, null, Role.NUCLEUS);

        Assert.assertEquals(1, a.compareTo(b));
    }

    @Test
    public void compareSymbol2() {
        Comparable<Symbol> a = new Symbol("c", 5.0, null, Role.NUCLEUS);
        Symbol b = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);

        Assert.assertEquals(-1, a.compareTo(b));
    }

    @Test
    public void allowedByNull() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(null));
    }

    @Test
    public void allowedBySymbolWithNullRule() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol previousSymbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(previousSymbol));
    }

    @Test
    public void allowedBySymbolWithEmptyRule() {
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Symbol previousSymbol = new Symbol("c", 5.0, "", Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(previousSymbol));
    }

    @Test(expected = PatternSyntaxException.class)
    public void ruleMustBeAValidPattern() {
        new Symbol("c", 5.0, "**", Role.NUCLEUS, Role.MIDDLE);
    }

    @Test
    public void allowedByRule() {
        Symbol previousSymbol = new Symbol("c", 5.0, "[bcd].*", Role.NUCLEUS, Role.MIDDLE);
        Symbol symbol = new Symbol("c", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertTrue(symbol.allowedBy(previousSymbol));
    }

    @Test
    public void notAllowedByRule() {
        Symbol previousSymbol = new Symbol("c", 5.0, "[bcd].*", Role.NUCLEUS, Role.MIDDLE);
        Symbol symbol = new Symbol("a", 5.0, null, Role.NUCLEUS, Role.MIDDLE);
        Assert.assertFalse(symbol.allowedBy(previousSymbol));
    }
}