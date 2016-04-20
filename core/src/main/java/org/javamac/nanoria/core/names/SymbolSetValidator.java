package org.javamac.nanoria.core.names;

import java.util.List;

public class SymbolSetValidator {
    private boolean valid = false;
    private Symbol brokenSymbol = null;

    public SymbolSetValidator(SymbolSet symbols) {
        validate(symbols);
    }

    public boolean isValid() {
        return valid;
    }

    public Symbol getBrokenSymbol() {
        return brokenSymbol;
    }

    private void validate(SymbolSet symbols) {
        List<Symbol> allSymbols = symbols.getAllSymbols();
        for (Symbol s : allSymbols) {
            List<Symbol> compatibleSymbols = symbols.selectMatchingSymbols(s, allCompatibleRoles(s));
            if (compatibleSymbols.size() == 0) {
                valid = false;
                brokenSymbol = s;
                return;
            }
        }
        valid = allSymbols.size() > 0;
    }

    private Role[] allCompatibleRoles(Symbol symbol) {
        return new Role[0];
    }
}
