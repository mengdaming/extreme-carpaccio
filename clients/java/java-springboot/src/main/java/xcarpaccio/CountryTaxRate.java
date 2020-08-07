package xcarpaccio;

public enum CountryTaxRate {
    DE(0.20),
    UK(0.21) {
        protected double retrieveRateFor(double amount) {
                if (amount > 2000)
                    return 0.21;
                else
                    return 0.05;
        }
    },
    FR(0.20),
    IT(0.25),
    ES(0.19),
    PL(0.21),
    RO(0.20),
    NL(0.20),
    BE(0.24),
    EL(0.20),
    CZ(0.19),
    PT(0.23),
    HU(0.27),
    SE(0.23),
    AT(0.22),
    BG(0.21),
    DK(0.21),
    FI(0.17),
    SK(0.18),
    IE(0.21),
    HR(0.23),
    LT(0.23),
    SI(0.24),
    LV(0.20),
    EE(0.22),
    CY(0.21),
    LU(0.25),
    MT(0.20);

    private final double rate;
    final String countryCode;

    CountryTaxRate(double rate) {
        this.countryCode = this.name();
        this.rate = rate;
    }

    public static CountryTaxRate find(String countryCode) {
        return valueOf(countryCode);
    }

    public static boolean isDefined(String countryCode) {
        try {
            find(countryCode);
        } catch (IllegalArgumentException | NullPointerException exception) {
            return false;
        }
        return true;
    }

    protected double retrieveRateFor(double amount) {
        return this.rate;
    }
}
