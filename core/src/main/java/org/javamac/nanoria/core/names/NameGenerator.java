package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.utils.DefaultRandomNumberGenerator;

import java.util.ArrayList;
import java.util.List;

public class NameGenerator {
    private final SymbolSet symbolSet;
    private final DefaultRandomNumberGenerator randomNumberGenerator = new DefaultRandomNumberGenerator();
    private final SymbolChooser symbolChooser = new SymbolChooser(randomNumberGenerator);

    public NameGenerator(SymbolSet symbolSet) {
        this.symbolSet = symbolSet;
    }

    public String generate() {
        return new NameGeneration().getName();
    }

    private class NameGeneration {
        private StringBuilder nameBuilder = new StringBuilder();
        private int syllableCount = 0;
        private boolean firstSyllable;
        private boolean lastSyllable;

        private Symbol previousSymbol = null;

        public NameGeneration() {
            do {
                firstSyllable = checkIfFirstSyllable();
                lastSyllable = checkIfLastSyllable();
                appendRandomSymbol(Role.INSET);
                appendRandomSymbol(Role.NUCLEUS);
                appendRandomSymbol(Role.CODA);
                syllableCount++;
            } while (!lastSyllable);
        }

        public String getName() {
            return nameBuilder.toString();
        }

        private boolean checkIfFirstSyllable() {
            return syllableCount == 0;
        }

        private boolean checkIfLastSyllable() {
            return randomNumberGenerator.generate(1.0) > 1.0 / (syllableCount + 1.0) || syllableCount > 1;
        }

        private void appendRandomSymbol(Role segmentRole) {
            final List<Symbol> matchingSymbols = symbolSet.selectMatchingSymbols(previousSymbol, rolesForSymbol(segmentRole));
            nameBuilder.append(symbolChooser.choose(matchingSymbols).getKey());
        }

        private Role[] rolesForSymbol(Role segmentRole) {
            final List<Role> requiredRoles = new ArrayList<Role>();
            requiredRoles.add(segmentRole);
            addSyllableRoles(requiredRoles);
            return requiredRoles.toArray(new Role[requiredRoles.size()]);
        }

        private void addSyllableRoles(List<Role> requiredRoles) {
            if (firstSyllable) {
                requiredRoles.add(Role.FIRST);
            }
            if (lastSyllable) {
                requiredRoles.add(Role.LAST);
            }
            if (!firstSyllable && !lastSyllable) {
                requiredRoles.add(Role.MIDDLE);
            }
        }
    }
}

