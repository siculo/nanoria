package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.NotEmptyStringMatcher;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class NameGeneratorTest {
    private RandomNumberGenerator randomNumberGenerator = new RandomNumberGenerator() {
        @Override
        public double generate(double max) {
            return Math.random() * max;
        }
    };

    @Test
    public void generateNamesFromASymbolSet() {
        SymbolSet symbolSet = new SymbolSet(randomNumberGenerator);
        NameGenerator generator = new NameGenerator(symbolSet);
    }

    @Ignore
    @Test
    public void generateNames() {
        NameGenerator generator = new NameGenerator(null);
        String name = generator.generate();

        System.out.println(name);

        Assert.assertThat(name, NotEmptyStringMatcher.INSTANCE);
    }
}
