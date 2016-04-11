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
        private final CircularQueue<Generator> segmentGenerators;
        private Generator current;

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

        private class OnsetGenerator implements Generator {
            @Override
            public String emit() {
                return "c";
            }

            @Override
            public boolean stop() {
                return false;
            }
        }

        private class NucleusGenerator implements Generator {
            @Override
            public String emit() {
                return "a";
            }

            @Override
            public boolean stop() {
                return false;
            }
        }

        private class CodaGenerator implements Generator {
            @Override
            public String emit() {
                return "t";
            }

            @Override
            public boolean stop() {
                return true;
            }
        }

    }
}
