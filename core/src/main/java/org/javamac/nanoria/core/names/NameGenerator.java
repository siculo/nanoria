package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.utils.*;

public class NameGenerator implements Generator {
    private SyllableGenerator syllableGenerator = new SyllableGenerator();

    @Override
    public String emit() {
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(syllableGenerator.emit());
        } while (!syllableGenerator.stop());
        return sb.toString();
    }

    @Override
    public boolean stop() {
        return true;
    }

    private static class SyllableGenerator implements Generator {
        private static final String CONSONANTS = "bbbbccccddddfffggghklllmmmnnnpppqqqrrrssstttvvwwxxzz";
        private static final String VOWELS = "aaaaeeeiioouuyj";

        private final CircularQueue<Generator> segmentGenerators;
        private Generator current;
        private boolean noMoreSyllables = false;
        private double continueProbability = 0.9;

        public SyllableGenerator() {
            segmentGenerators = new CircularQueue<Generator>(new OnsetGenerator(), new NucleusGenerator(), new CodaGenerator());
        }

        @Override
        public String emit() {
            current = segmentGenerators.next();
            return current.emit();
        }

        @Override
        public boolean stop() {
            return current.stop();
        }

        private static final String selectRandom(String source) {
            int s = (int) Math.floor(Math.random() * source.length());
            return String.valueOf(source.charAt(s));
        }

        private class OnsetGenerator implements Generator {
            @Override
            public String emit() {
                noMoreSyllables = Math.random() > continueProbability;
                return selectRandom(CONSONANTS);
            }

            @Override
            public boolean stop() {
                return false;
            }
        }

        private class NucleusGenerator implements Generator {
            @Override
            public String emit() {
                return selectRandom(VOWELS);
            }

            @Override
            public boolean stop() {
                return false;
            }
        }

        private class CodaGenerator implements Generator {
            @Override
            public String emit() {
                if (Math.random() > 0.6)
                    return selectRandom(CONSONANTS);
                else
                    return "";
            }

            @Override
            public boolean stop() {
                continueProbability = continueProbability * 0.5;
                return noMoreSyllables;
            }
        }

    }
}
