package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.NotEmptyStringMatcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SyllableTest {
    private Syllable syllable;

    @Before
    public void setUp() throws Exception {
        syllable = new Syllable();
    }

    @Test
    public void syllableHasOnset() {

        Segment segment = syllable.getOnset();

        Assert.assertNotNull(segment);
    }

    @Test
    public void syllableHasNucleus() {
        Segment segment = syllable.getNucleus();

        Assert.assertNotNull(segment);
    }

    @Test
    public void syllableHasCoda() {
        Segment segment = syllable.getCoda();

        Assert.assertNotNull(segment);
    }

    @Test
    public void syllableTextIsSegmentsTextConcatenation() {
        String text = syllable.toString();

        Assert.assertEquals(syllable.getOnset().toString() + syllable.getNucleus().toString() + syllable.getCoda().toString(), text);
    }

    @Test
    public void syllableTextIsNotEmpty() {
        String text = syllable.toString();

        Assert.assertThat(text, NotEmptyStringMatcher.INSTANCE);
    }
}
