package org.javamac.nanoria.core.names;

import org.junit.Test;

import java.io.StringReader;
import java.util.regex.Pattern;

public class SymbolSetReaderTest {
    private static final String TEST01 = "b\\tinset\\t5\\t[aeiou]{1,2}";
    private static final String TEST02 = "b\\tinset\\t5\\t[aeiou]{1,2}\\nbr\\tinset\\t2\\t([aeiou]{1,2})|[y]\\nc\\tinset\\t5\\t[aeiou]{1,2}\\ncr\\tinset\\t2\\t[aeiou]{1,2}\\nd\\tinset\\t5\\t([aeiou]{1,2})|[y]";

    @Test
    public void createSymbolSetReader() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader(""));
    }

    @Test
    public void readASymbol() {
        SymbolSetReader reader = new SymbolSetReader(new StringReader(TEST01));
        // TODO: wip
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

}
