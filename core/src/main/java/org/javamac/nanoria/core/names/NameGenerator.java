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
        StringBuilder sb = new StringBuilder();

        int syllableCount = 0;

        Symbol selectedSymbol = null;

        boolean firstSyllable;
        boolean lastSyllable;

        do {
            firstSyllable = syllableCount == 0;
            lastSyllable = determineLastSyllable(syllableCount);

            selectedSymbol = selectRandomSymbol(selectedSymbol, Role.INSET, firstSyllable, lastSyllable);
            sb.append(selectedSymbol.getKey());

            selectedSymbol = selectRandomSymbol(selectedSymbol, Role.NUCLEUS, firstSyllable, lastSyllable);
            sb.append(selectedSymbol.getKey());

            selectedSymbol = selectRandomSymbol(selectedSymbol, Role.CODA, firstSyllable, lastSyllable);
            sb.append(selectedSymbol.getKey());

            syllableCount++;
        } while (!lastSyllable);

        return sb.toString();
    }

    private boolean determineLastSyllable(int syllableCount) {
        return randomNumberGenerator.generate(1.0) > 1.0 / (syllableCount + 1.0) || syllableCount > 1;
    }

    private Symbol selectRandomSymbol(Symbol previousSymbol, Role segmentRole, boolean firstSyllable, boolean lastSyllable) {
        List<Role> requiredRoles = new ArrayList<Role>();
        requiredRoles.add(segmentRole);
        if (firstSyllable) {
            requiredRoles.add(Role.FIRST);
        }
        if (lastSyllable) {
            requiredRoles.add(Role.LAST);
        }
        if (!firstSyllable && !lastSyllable) {
            requiredRoles.add(Role.MIDDLE);
        }
        List<Symbol> matchingSymbols = symbolSet.selectMatchingSymbols(previousSymbol, requiredRoles.toArray(new Role[requiredRoles.size()]));
        return symbolChooser.choose(matchingSymbols);
    }
}

