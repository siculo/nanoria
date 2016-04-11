package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.NotEmptyStringMatcher;
import org.junit.Assert;
import org.junit.Test;

public class GeneratorTest {
    @Test
    public void generatorThatEmitsStrings() {
        Generator generator = new NameGenerator();
        String emitted = generator.emit();

        System.out.println(emitted);

        Assert.assertThat(emitted, NotEmptyStringMatcher.INSTANCE);
    }
}
