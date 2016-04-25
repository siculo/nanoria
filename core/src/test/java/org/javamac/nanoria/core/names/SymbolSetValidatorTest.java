package org.javamac.nanoria.core.names;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SymbolSetValidatorTest {
    @Test
    public void validateAnEmptySymbolSet() {
        SymbolSet symbols = new SymbolSet();

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Assert.assertFalse(validator.isValid());
    }

    @Test
    public void validSymbolSet() throws InvalidSymbolException {
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        symbols.add(new Symbol("a", 1, null, Role.NUCLEUS));
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Assert.assertTrue(validator.isValid());
    }

    @Test
    public void notValidSymbolSet() throws InvalidSymbolException {
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        // no nucleus
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Assert.assertFalse(validator.isValid());
    }

    @Test
    public void notValidSymbolSet2() throws InvalidSymbolException {
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        symbols.add(new Symbol("a", 1, "([aeiou]{1,2})|[y]", Role.NUCLEUS));
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Assert.assertFalse(validator.isValid());
    }

    @Test
    public void notValidSymbolIsNull() throws InvalidSymbolException {
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        symbols.add(new Symbol("a", 1, null, Role.NUCLEUS));
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Symbol currentNotValid = validator.getNotValidSymbol();
        Assert.assertNull(currentNotValid);
    }

    @Test
    public void getNotValidSymbol() throws InvalidSymbolException {
        Symbol expectedNotValid;
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        symbols.add(expectedNotValid = new Symbol("a", 1, "([aeiou]{1,2})|[y]", Role.NUCLEUS));
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Symbol currentNotValid = validator.getNotValidSymbol();
        Assert.assertSame(expectedNotValid, currentNotValid);
    }
}
