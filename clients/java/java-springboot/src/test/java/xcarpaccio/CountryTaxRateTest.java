package xcarpaccio;

import org.junit.Test;

import static org.junit.Assert.*;

public class CountryTaxRateTest {

    private static void assertCountryTaxRate(String country, double expectedRate) {
        assertCountryTaxRateForAmount(country, 0.0, expectedRate);
    }

    private static void assertCountryTaxRateForAmount(String country, double amount, double expectedRate) {
        assertEquals(expectedRate, CountryTaxRate.find(country).retrieveRateFor(amount), 0.0);
    }

    @Test
    public void can_retrieve_tax_rate_for_known_country_code() {
        CountryTaxRate countryTaxRate = CountryTaxRate.find("FR");
        assertNotNull(countryTaxRate);
        assertEquals("FR", countryTaxRate.countryCode);
        assertEquals(0.2, countryTaxRate.retrieveRateFor(0.0), 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void retrieving_tax_rate_for_unknown_country_code_returns_null() {
        CountryTaxRate.find("XX");
    }

    @Test(expected = NullPointerException.class)
    public void retrieving_tax_rate_for_null_country_code_returns_null() {
        CountryTaxRate.find(null);
    }

    @Test
    public void checking_known_country_code() {
        assertTrue(CountryTaxRate.isDefined("FR"));
    }

    @Test
    public void checking_unknown_country_code() {
        assertFalse(CountryTaxRate.isDefined("XX"));
    }

    @Test
    public void checking_null_country_code() {
        assertFalse(CountryTaxRate.isDefined(null));
    }

    @Test
    public void can_retrieve_tax_rate_for_germany() {
        assertCountryTaxRate("DE", 0.20);
    }

//    @Ignore
//    @Test
//    public void can_retrieve_tax_rate_for_united_kingdom() {
//        assertCountryTaxRate("UK", 0.21);
//    }

    @Test
    public void can_retrieve_tax_rate_for_united_kingdom() {
        assertCountryTaxRateForAmount("UK", 0.00, 0.05);
        assertCountryTaxRateForAmount("UK", 2000.00, 0.05);
        assertCountryTaxRateForAmount("UK", 2000.01, 0.21);
        assertCountryTaxRateForAmount("UK", 10000.00, 0.21);
    }

    @Test
    public void can_retrieve_tax_rate_for_france() {
        assertCountryTaxRate("FR", 0.20);
    }

    @Test
    public void can_retrieve_tax_rate_for_italy() {
        assertCountryTaxRate("IT", 0.25);
    }

    @Test
    public void can_retrieve_tax_rate_for_spain() {
        assertCountryTaxRate("ES", 0.19);
    }

    @Test
    public void can_retrieve_tax_rate_for_poland() {
        assertCountryTaxRate("PL", 0.21);
    }

    @Test
    public void can_retrieve_tax_rate_for_romania() {
        assertCountryTaxRate("RO", 0.20);
    }

    @Test
    public void can_retrieve_tax_rate_for_netherlands() {
        assertCountryTaxRate("NL", 0.20);
    }

    @Test
    public void can_retrieve_tax_rate_for_belgium() {
        assertCountryTaxRate("BE", 0.24);
    }

    @Test
    public void can_retrieve_tax_rate_for_greece() {
        assertCountryTaxRate("EL", 0.20);
    }

    @Test
    public void can_retrieve_tax_rate_for_czech_republic() {
        assertCountryTaxRate("CZ", 0.19);
    }

    @Test
    public void can_retrieve_tax_rate_for_portugal() {
        assertCountryTaxRate("PT", 0.23);
    }

    @Test
    public void can_retrieve_tax_rate_for_hungary() {
        assertCountryTaxRate("HU", 0.27);
    }

    @Test
    public void can_retrieve_tax_rate_for_sweden() {
        assertCountryTaxRate("SE", 0.23);
    }

    @Test
    public void can_retrieve_tax_rate_for_austria() {
        assertCountryTaxRate("AT", 0.22);
    }

    @Test
    public void can_retrieve_tax_rate_for_bulgaria() {
        assertCountryTaxRate("BG", 0.21);
    }

    @Test
    public void can_retrieve_tax_rate_for_denmark() {
        assertCountryTaxRate("DK", 0.21);
    }

    @Test
    public void can_retrieve_tax_rate_for_finland() {
        assertCountryTaxRate("FI", 0.17);
    }

    @Test
    public void can_retrieve_tax_rate_for_slovakia() {
        assertCountryTaxRate("SK", 0.18);
    }

    @Test
    public void can_retrieve_tax_rate_for_ireland() {
        assertCountryTaxRate("IE", 0.21);
    }

    @Test
    public void can_retrieve_tax_rate_for_croatia() {
        assertCountryTaxRate("HR", 0.23);
    }

    @Test
    public void can_retrieve_tax_rate_for_lithuania() {
        assertCountryTaxRate("LT", 0.23);
    }

    @Test
    public void can_retrieve_tax_rate_for_slovenia() {
        assertCountryTaxRate("SI", 0.24);
    }

    @Test
    public void can_retrieve_tax_rate_for_latvia() {
        assertCountryTaxRate("LV", 0.20);
    }

    @Test
    public void can_retrieve_tax_rate_for_estonia() {
        assertCountryTaxRate("EE", 0.22);
    }

    @Test
    public void can_retrieve_tax_rate_for_cyprus() {
        assertCountryTaxRate("CY", 0.21);
    }

    @Test
    public void can_retrieve_tax_rate_for_luxembourg() {
        assertCountryTaxRate("LU", 0.25);
    }

    @Test
    public void can_retrieve_tax_rate_for_malta() {
        assertCountryTaxRate("MT", 0.20);
    }
}