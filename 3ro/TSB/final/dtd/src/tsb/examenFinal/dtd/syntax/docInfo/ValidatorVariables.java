package tsb.examenFinal.dtd.syntax.docInfo;

import tsb.examenFinal.util.StrCmp;
import java.util.ArrayList;

/**
 *
 * valida las variables de un docinfo
 */
class ValidatorVariables {

    private String str;
    private ArrayList<String> names;

    public ValidatorVariables() {
        names = new ArrayList<String>();
    }

    public void clear() {
        this.names.clear();
    }

    public boolean validate(String str) {
        boolean ok = true;
        this.str = str;
        try {
            if (StrCmp.hasEqualThan(str, "=", 1, false) == false) {
                ok = false;
            } else if (this.correctNumberOfFields() == false) {
                ok = false;
            } else if (this.fieldsEmpty() == true) {
                ok = false;
            } else if (this.nameFieldOK() == false) {
                ok = false;
            } else if (this.valueFieldOK() == false) {
                ok = false;
            } else if (StrCmp.hasEqualThan(str, "\"", 2, false) != false) {
                ok = false;
            } else if (this.validateUniqueNames(str) == false) {
                ok = false;
            }
        } catch (Exception e) {
            ok = false;
        } finally {
            return ok;
        }
    }

    private boolean validateUniqueNames(String str) {
        boolean ok = true;
        String newName = str.split("=")[0];
        if (this.names.isEmpty() == false) {
            if (names.contains(newName)) {
                ok = false;
            }
        }
        if (ok) {
            names.add(newName);
        }
        return ok;
    }

    private boolean valueFieldOK() {
        boolean ok = true;
        try {
            String value = str.split("=")[1];
            if (StrCmp.isInitEnd(value, "\"", "\"") == false) {
                ok = false;
            }
            if (this.validateEmptyValues(value) == true) {
                ok = false;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ok = false;
        } finally {
            return ok;
        }
    }

    private boolean validateEmptyValues(String value) {
        String aux = value.replaceAll("\"", "").trim();
        return aux.isEmpty();
    }

    private boolean nameFieldOK() {
        boolean ok = true;
        String name = str.split("=")[0];
        if (StrCmp.hasOnlyAlphaNumAndDashes(name) == false) {
            ok = false;
        }
        return ok;
    }

    private boolean fieldsEmpty() {
        boolean ok = true;
        try {
            ok = str.split("=")[0].isEmpty() || str.split("=")[1].isEmpty();
        } catch (Exception e) {
            ok = false;
        } finally {
            return ok;
        }
    }

    private boolean correctNumberOfFields() {
        return str.split("=").length == 2;
    }
}