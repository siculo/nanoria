package org.javamac.nanoria.core.names;

import org.javamac.nanoria.core.utils.RandomNumberGenerator;

import java.util.List;

public class SymbolChooser {
    private final RandomNumberGenerator generator;

    public SymbolChooser(RandomNumberGenerator generator) {
        this.generator = generator;
    }

    public Symbol choose(List<Symbol> symbols) {
        final double randomValue = generator.generate(getTotalWeight(symbols));
        double runningWeight = 0;
        for (Symbol matchingSymbol : symbols) {
            runningWeight += matchingSymbol.getWeight();
            if (randomValue < runningWeight) {
                return matchingSymbol;
            }
        }
        return symbols.size() > 0 ? symbols.get(symbols.size() - 1) : null;
    }

    private double getTotalWeight(List<Symbol> matchingSymbols) {
        double totalWeight = 0;
        for (Symbol matchingSymbol : matchingSymbols) {
            totalWeight += matchingSymbol.getWeight();
        }
        return totalWeight;
    }
}
