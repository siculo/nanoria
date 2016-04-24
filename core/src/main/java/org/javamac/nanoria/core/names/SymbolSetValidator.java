package org.javamac.nanoria.core.names;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SymbolSetValidator {
    private final boolean valid;

    public SymbolSetValidator(SymbolSet symbols) {
        valid = validate(symbols);
    }

    public boolean isValid() {
        return valid;
    }

    private boolean validate(SymbolSet symbols) {
        List<Symbol> allSymbols = symbols.getAllSymbols();
        for (Symbol symbol : allSymbols) {
            if (!validate(symbol, symbols)) {
                return false;
            }
        }
        return allSymbols.size() > 0;
    }

    private boolean validate(Symbol symbol, SymbolSet symbols) {
        Iterator<Role[]> i = new CompatibleRolesIterator(symbol.getRoles());
        while (i.hasNext()) {
            Role[] compatibleRoles = i.next();
            List<Symbol> matchingSymbols = symbols.selectMatchingSymbols(symbol, compatibleRoles);
            if (matchingSymbols.size() == 0) {
                return false;
            }
        }
        return true;
    }
}
