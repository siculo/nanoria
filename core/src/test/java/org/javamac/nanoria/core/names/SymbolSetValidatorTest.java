package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.utils.Resources;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.List;

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

        List<Symbol> notValidSymbols = validator.getNotValidSymbols();
        Assert.assertEquals(0, notValidSymbols.size());
    }

    @Test
    public void getNotValidSymbol() throws InvalidSymbolException {
        Symbol expectedNotValid;
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        symbols.add(expectedNotValid = new Symbol("a", 1, "([aeiou]{1,2})|[y]", Role.NUCLEUS));
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        List<Symbol> notValidSymbols = validator.getNotValidSymbols();
        Assert.assertSame(expectedNotValid, notValidSymbols.get(0));
    }

    @Test
    public void getNotValidSymbols() throws InvalidSymbolException {
        Symbol expectedNotValid;
        SymbolSet symbols = new SymbolSet();
        symbols.add(new Symbol("c", 1, null, Role.INSET));
        symbols.add(new Symbol("a", 1, "([aeiou]{1,2})|[y]", Role.NUCLEUS));
        symbols.add(new Symbol("r", 1, "([aeiou]{1,2})|[y]", Role.CODA));
        symbols.add(new Symbol("t", 1, null, Role.CODA));

        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        List<Symbol> notValidSymbols = validator.getNotValidSymbols();

        Assert.assertEquals(2, notValidSymbols.size());

        for (Symbol s: notValidSymbols) {
            System.out.println(s);
        }
    }

    @Test
    public void notValidSymbolSetReadFromReources() throws InvalidSymbolException, FileNotFoundException, URISyntaxException {
        SymbolSet symbols = SymbolSet.readSymbolSet(Resources.getFileFromResource(NameGeneratorTest.class, "/wrongSymbolSet.txt"));
        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        List<Symbol> notValidSymbols = validator.getNotValidSymbols();

        Assert.assertNotEquals(0, notValidSymbols.size());

        for (Symbol s: notValidSymbols) {
            System.out.println(s);
        }
    }

    @Test
    public void notValidSymbolSetReadFromReources2() throws InvalidSymbolException, FileNotFoundException, URISyntaxException {
        SymbolSet symbols = SymbolSet.readSymbolSet(Resources.getFileFromResource(NameGeneratorTest.class, "/testSymbols.txt"));
        SymbolSetValidator validator = new SymbolSetValidator(symbols);

        List<Symbol> notValidSymbols = validator.getNotValidSymbols();
        for (Symbol s: notValidSymbols) {
            System.out.println(s);
        }

        Assert.assertTrue(validator.isValid());
    }
}
