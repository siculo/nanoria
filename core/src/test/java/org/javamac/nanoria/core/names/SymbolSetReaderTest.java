package org.javamac.nanoria.core.names;

import org.junit.Assert;
import org.junit.Test;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class SymbolSetReaderTest {
    @Test
    public void createSymbolSetReader() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader(""));
    }

    @Test
    public void gotNoSymbolFromAnEmptyStream() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader(""));
        Symbol symbol = reader.getNextSymbol();

        Assert.assertNull(symbol);
    }

    @Test
    public void getASymbol() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader("b\t5\t[aeiou]{1,2}\tinset"));
        Symbol symbol = reader.getNextSymbol();

        Assert.assertNotNull(symbol);
    }

    @Test
    public void skipEmptyLines() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader("\n \n\n\t \nb\t5\t[aeiou]{1,2}\tinset\n"));

        int symbolsRead = readAllSymbols(reader).size();

        Assert.assertEquals(1, symbolsRead);
    }

    @Test
    public void skipComments() {
        SymbolSetReader reader = new SymbolSetReader(
                new StringReader(
                        "b\t5\t[aeiou]{1,2}\tinset\n" +
                                "br\t2\t([aeiou]{1,2})|[y]\tinset\n" +
                                "c\t5\t[aeiou]{1,2}\tinset\n" +
                                "cr\t2\t[aeiou]{1,2}\tinset\n" +
                                "d\t5\t([aeiou]{1,2})|[y]\tinset"
                )
        );

        int symbolsRead = readAllSymbols(reader).size();

        Assert.assertEquals(5, symbolsRead);
    }

    @Test
    public void getASymbolPerLine() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader("\n# ciao ciao\nb\t5\t[aeiou]{1,2}\tinset\n# fine"));

        int symbolsRead = readAllSymbols(reader).size();

        Assert.assertEquals(1, symbolsRead);
    }

    @Test
    public void symbolContentIsRead() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader("b\t5\t[aeiou]{1,2}\tinset,last"));

        Symbol symbol = reader.getNextSymbol();

        Assert.assertEquals("b", symbol.getKey());
        Assert.assertEquals(5.0, symbol.getWeight(), 0);
        Assert.assertEquals("[aeiou]{1,2}", symbol.getAllows());
        Assert.assertArrayEquals(new Role[]{Role.INSET, Role.LAST}, symbol.getRoles());

    }

    // @Test
    public void sampleMatch() {
        testPattern("[aeiou]+", "a");
        testPattern("[aeiou]+", "as");
        testPattern("([aeiou]{1,2})|[y]", "a");
        testPattern("j[aeou]", "j");
        testPattern("(j[aeou])", "ja");
        testPattern("([aeiou]{1,2})|(j[aeou])", "ja");
        testPattern("([aeiou]{1,2})|(j[aeou])", "ao");
        testPattern("([aeiou]{1,2})|(j[aeou])", "j");
        testPattern("([aeiou]{1,2})|(j[aeou])", "uy");
    }

    private void testPattern(String regexp, String input) {
        System.out.println(String.format("%s matches %s = %b", regexp, input, Pattern.matches(regexp, input)));
    }

    private Collection<Symbol> readAllSymbols(SymbolSetReader reader) {
        List<Symbol> symbols = new ArrayList<Symbol>();
        Symbol symbol;
        while ((symbol = reader.getNextSymbol()) != null) {
            symbols.add(symbol);
        }
        return symbols;
    }
}