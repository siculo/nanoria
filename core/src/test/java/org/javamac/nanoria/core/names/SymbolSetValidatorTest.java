package org.javamac.nanoria.core.names;

import org.junit.Assert;
import org.junit.Test;

public class SymbolSetValidatorTest {
    @Test
    public void validateAnEmptySymbolSet() {
        SymbolSet symbols = new SymbolSet();

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Assert.assertFalse(validator.isValid());
        Assert.assertNull(validator.getBrokenSymbol());
    }

    @Test
    public void validSymbolSet() throws InvalidSymbolException {
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        symbols.add(new Symbol("a", 1, null, Role.NUCLEUS));
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Assert.assertTrue(validator.isValid());
        Assert.assertNull(validator.getBrokenSymbol());
    }

    @Test
    public void notValidSymbolSet() throws InvalidSymbolException {
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        // no nucleus
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        Assert.assertTrue(validator.isValid());
        Assert.assertNull(validator.getBrokenSymbol());
    }
}
