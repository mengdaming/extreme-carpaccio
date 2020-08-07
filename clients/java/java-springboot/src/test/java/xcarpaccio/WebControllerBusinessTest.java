package xcarpaccio;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Testing business methods that don't require Spring at all, using assertj
 */
public class WebControllerBusinessTest {

    private static Order buildSimpleOrder() {
        Order order = new Order();
        order.prices = new Double[]{1.0};
        order.quantities = new Long[]{1L};
        order.country = "FR";
        order.reduction = "STANDARD";
        return order;
    }

    private static void accepting_orders_from_country(String country) {
        Order order = buildSimpleOrder();
        order.country = country;
        assertTrue(new WebController().willAcceptOrder(order));
    }

    private void accepting_orders_with_reduction_type(String reduction) {
        Order order = buildSimpleOrder();
        order.reduction = reduction;
        assertTrue(new WebController().willAcceptOrder(order));
    }

    @Test
    public void default_computed_amount_should_be_zero() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{0.0};
        order.quantities = new Long[]{0L};
        assertThat((new WebController()).computeAmount(order)).isEqualTo(0.0);
    }

    @Test
    public void acceptance_test_for_france_and_half_price_reduction() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{1.4, 2.5, 4.0, 3.25};
        order.quantities = new Long[]{12L, 2L, 5L, 8L};
        order.country = "FR";
        order.reduction = "HALF PRICE";
        assertThat((new WebController()).computeAmount(order)).isEqualTo(40.68);
    }

    @Test
    public void acceptance_test_for_united_kingdom_and_standard_reduction() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{1000.0, 200.0, 4.0};
        order.quantities = new Long[]{5L, 5L, 5L};
        order.country = "UK";
        order.reduction = "STANDARD";
        assertThat((new WebController()).computeAmount(order)).isEqualTo(6774.306);
    }

    @Test
    public void acceptance_test_for_italy_and_pay_the_price_reduction() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{1000.0, 200.0, 4.0};
        order.quantities = new Long[]{5L, 5L, 5L};
        order.country = "IT";
        order.reduction = "PAY THE PRICE";
        assertThat((new WebController()).computeAmount(order)).isEqualTo(7525.00);
    }

    @Test
    public void accepting_orders_with_reduction_type_STANDARD() {
        accepting_orders_with_reduction_type("STANDARD");
    }

    @Test
    public void accepting_orders_with_reduction_type_HALF_PRICE() {
        accepting_orders_with_reduction_type("HALF PRICE");
    }

    @Test
    public void accepting_orders_with_reduction_type_PAY_THE_PRICE() {
        accepting_orders_with_reduction_type("PAY THE PRICE");
    }

    @Test
    public void will_not_accept_orders_with_unknown_reduction() {
        Order order = buildSimpleOrder();
        order.reduction = "XXXXXXX";
        assertFalse(new WebController().willAcceptOrder(order));
    }

    @Test
    public void will_not_accept_orders_with_undefined_reduction() {
        Order order = buildSimpleOrder();
        order.reduction = null;
        assertFalse(new WebController().willAcceptOrder(order));
    }

    @Test
    public void will_not_accept_orders_from_an_unknown_country() {
        Order order = buildSimpleOrder();
        order.country = "XX";
        assertFalse(new WebController().willAcceptOrder(order));
    }

    @Test
    public void will_not_accept_orders_from_an_undefined_country() {
        Order order = buildSimpleOrder();
        order.country = null;
        assertFalse(new WebController().willAcceptOrder(order));
    }

    @Test
    public void accepting_orders_from_germany() {
        accepting_orders_from_country("DE");
    }

    @Test
    public void accepting_orders_from_united_kingdom() {
        accepting_orders_from_country("UK");
    }

    @Test
    public void accepting_orders_from_france() {
        accepting_orders_from_country("FR");
    }

    @Test
    public void accepting_orders_from_italy() {
        accepting_orders_from_country("IT");
    }

    @Test
    public void accepting_orders_from_spain() {
        accepting_orders_from_country("ES");
    }

    @Test
    public void accepting_orders_from_poland() {
        accepting_orders_from_country("PL");
    }

    @Test
    public void accepting_orders_from_romania() {
        accepting_orders_from_country("RO");
    }

    @Test
    public void accepting_orders_from_netherlands() {
        accepting_orders_from_country("NL");
    }

    @Test
    public void accepting_orders_from_belgium() {
        accepting_orders_from_country("BE");
    }

    @Test
    public void accepting_orders_from_greece() {
        accepting_orders_from_country("EL");
    }

    @Test
    public void accepting_orders_from_czech_republic() {
        accepting_orders_from_country("CZ");
    }

    @Test
    public void accepting_orders_from_portugal() {
        accepting_orders_from_country("PT");
    }

    @Test
    public void accepting_orders_from_hungary() {
        accepting_orders_from_country("HU");
    }

    @Test
    public void accepting_orders_from_sweden() {
        accepting_orders_from_country("SE");
    }

    @Test
    public void accepting_orders_from_austria() {
        accepting_orders_from_country("AT");
    }

    @Test
    public void accepting_orders_from_bulgaria() {
        accepting_orders_from_country("BG");
    }

    @Test
    public void accepting_orders_from_denmark() {
        accepting_orders_from_country("DK");
    }

    @Test
    public void accepting_orders_from_finland() {
        accepting_orders_from_country("FI");
    }

    @Test
    public void accepting_orders_from_slovakia() {
        accepting_orders_from_country("SK");
    }

    @Test
    public void accepting_orders_from_ireland() {
        accepting_orders_from_country("IE");
    }

    @Test
    public void accepting_orders_from_croatia() {
        accepting_orders_from_country("HR");
    }

    @Test
    public void accepting_orders_from_lithuania() {
        accepting_orders_from_country("LT");
    }

    @Test
    public void accepting_orders_from_slovenia() {
        accepting_orders_from_country("SI");
    }

    @Test
    public void accepting_orders_from_latvia() {
        accepting_orders_from_country("LV");
    }

    @Test
    public void accepting_orders_from_estonia() {
        accepting_orders_from_country("EE");
    }

    @Test
    public void accepting_orders_from_cyprus() {
        accepting_orders_from_country("CY");
    }

    @Test
    public void accepting_orders_from_luxembourg() {
        accepting_orders_from_country("LU");
    }

    @Test
    public void accepting_orders_from_malta() {
        accepting_orders_from_country("MT");
    }
}
