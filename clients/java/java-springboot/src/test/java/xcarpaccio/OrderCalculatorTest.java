package xcarpaccio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderCalculatorTest {

    private static Order buildSimpleOrder() {
        Order order = new Order();
        order.prices = new Double[]{1.0};
        order.quantities = new Long[]{1L};
        order.country = "FR";
        order.reduction = "STANDARD";
        return order;
    }

    // Order validity verification
    // -------------------------------------------------------------------------------------

    private static void assertOrderIsValid(Order order, boolean expected) {
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(expected, orderCalculator.isOrderValid());
    }

    @Test
    public void a_null_order_is_invalid() {
        Order order = null;
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_empty_order_is_invalid() {
        Order order = new Order();
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_null_prices_table_is_invalid() {
        Order order = buildSimpleOrder();
        order.prices = null;
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_empty_prices_table_is_invalid() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{};
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_null_quantities_table_is_invalid() {
        Order order = buildSimpleOrder();
        order.quantities = null;
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_empty_quantities_table_is_invalid() {
        Order order = buildSimpleOrder();
        order.quantities = new Long[]{};
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_unmatched_prices_and_quantities_is_invalid() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{2.0, 1.0};
        order.quantities = new Long[]{1L};
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_null_country_is_invalid() {
        Order order = buildSimpleOrder();
        order.country = null;
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_empty_country_is_invalid() {
        Order order = buildSimpleOrder();
        order.country = "";
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_country_with_more_than_2_characters_is_invalid() {
        Order order = buildSimpleOrder();
        order.country = "FRF";
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_null_reduction_is_invalid() {
        Order order = buildSimpleOrder();
        order.reduction = null;
        assertOrderIsValid(order, false);
    }

    @Test
    public void an_order_with_empty_reduction_is_invalid() {
        Order order = buildSimpleOrder();
        order.reduction = "";
        assertOrderIsValid(order, false);
    }

    // Before Tax
    // -------------------------------------------------------------------------------------

    @Test
    public void computed_price_before_tax_for_single_value_should_be_this_value() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{2.0};
        order.quantities = new Long[]{1L};
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(2.0, orderCalculator.computeAmountBeforeTax(), 0.0);
    }

    @Test
    public void computed_price_before_tax_for_multiple_values_should_be_their_sum() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{2.0, 1.0};
        order.quantities = new Long[]{1L, 1L};
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(3.0, orderCalculator.computeAmountBeforeTax(), 0.0);
    }

    @Test
    public void computed_price_before_tax_for_single_value_and_non_zero_quantity_should_be_their_product() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{2.0};
        order.quantities = new Long[]{3L};
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(6.0, orderCalculator.computeAmountBeforeTax(), 0.0);
    }

    @Test
    public void computed_price_before_tax_for_multiple_values_and_various_quantities_should_be_the_sum_of_their_products() {
        Order order = buildSimpleOrder();
        order.prices = new Double[]{2.5, 3.3};
        order.quantities = new Long[]{3L, 4L};
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(20.7, orderCalculator.computeAmountBeforeTax(), 0.0);
    }

    // With Tax
    // -------------------------------------------------------------------------------------

    @Test
    public void computed_price_with_tax_for_france() {
        Order order = buildSimpleOrder();
        order.country = "FR";
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(1.20, orderCalculator.computeAmountWithTax(), 0.0);
    }

    @Test
    public void computed_price_with_tax_for_united_kingdom() {
        Order order = buildSimpleOrder();
        order.country = "UK";
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(1.05, orderCalculator.computeAmountWithTax(), 0.0);
    }

    // Reduction calculation for reduction type STANDARD
    // -------------------------------------------------------------------------------------

    @Test
    public void amount_of_0_00_gets_0_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.00, OrderCalculator.computeReductionRate(0.00, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_999_99_gets_0_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.00, OrderCalculator.computeReductionRate(999.99, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_1000_00_gets_3_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.03, OrderCalculator.computeReductionRate(1000.00, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_4999_99_gets_3_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.03, OrderCalculator.computeReductionRate(4999.99, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_5000_00_gets_5_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.05, OrderCalculator.computeReductionRate(5000.00, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_6999_99_gets_5_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.05, OrderCalculator.computeReductionRate(6999.99, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_7000_00_gets_7_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.07, OrderCalculator.computeReductionRate(7000.00, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_9999_99_gets_7_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.07, OrderCalculator.computeReductionRate(9999.99, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_10000_00_gets_10_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.10, OrderCalculator.computeReductionRate(10000.00, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_49999_99_gets_10_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.10, OrderCalculator.computeReductionRate(49999.99, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_50000_00_gets_15_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.15, OrderCalculator.computeReductionRate(50000.00, "STANDARD"), 0.0);
    }

    @Test
    public void amount_of_100000_00_gets_15_percent_reduction_when_reduction_is_STANDARD() {
        assertEquals(0.15, OrderCalculator.computeReductionRate(100000.00, "STANDARD"), 0.0);
    }

    // Reduction calculation for reduction type HALF PRICE
    // -------------------------------------------------------------------------------------

    @Test
    public void amount_of_0_00_gets_50_percent_reduction_when_reduction_is_HALF_PRICE() {
        assertEquals(0.50, OrderCalculator.computeReductionRate(0.00, "HALF PRICE"), 0.0);
    }

    @Test
    public void amount_of_1000_00_gets_50_percent_reduction_when_reduction_is_HALF_PRICE() {
        assertEquals(0.50, OrderCalculator.computeReductionRate(1000.00, "HALF PRICE"), 0.0);
    }

    @Test
    public void amount_of_100000_00_gets_50_percent_reduction_when_reduction_is_HALF_PRICE() {
        assertEquals(0.50, OrderCalculator.computeReductionRate(100000.00, "HALF PRICE"), 0.0);
    }

    // Reduction calculation for reduction type PAY THE PRICE
    // -------------------------------------------------------------------------------------

    @Test
    public void amount_of_0_00_gets_0_percent_reduction_when_reduction_is_PAY_THE_PRICE() {
        assertEquals(0.00, OrderCalculator.computeReductionRate(0.00, "PAY_THE PRICE"), 0.0);
    }

    @Test
    public void amount_of_1000_00_gets_0_percent_reduction_when_reduction_is_PAY_THE_PRICE() {
        assertEquals(0.00, OrderCalculator.computeReductionRate(1000.00, "PAY_THE PRICE"), 0.0);
    }

    @Test
    public void amount_of_100000_00_gets_0_percent_reduction_when_reduction_is_PAY_THE_PRICE() {
        assertEquals(0.00, OrderCalculator.computeReductionRate(100000.00, "PAY_THE PRICE"), 0.0);
    }

    // Amount with tax and reduction
    // -------------------------------------------------------------------------------------

    @Test
    public void compute_amount_with_tax_and_reduction_STANDARD(){
        Order order = new Order();
        order.prices = new Double[]{5000.00};
        order.quantities = new Long[]{1L};
        order.country = "FR";
        order.reduction = "STANDARD";
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(5700.00, orderCalculator.computeAmount(), 0.0);
    }

    @Test
    public void compute_amount_with_tax_and_reduction_HALF_PRICE(){
        Order order = new Order();
        order.prices = new Double[]{5000.00};
        order.quantities = new Long[]{1L};
        order.country = "FR";
        order.reduction = "HALF PRICE";
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(3000.00, orderCalculator.computeAmount(), 0.0);
    }

    @Test
    public void compute_amount_with_tax_and_reduction_PAY_THE_PRICE(){
        Order order = new Order();
        order.prices = new Double[]{5000.00};
        order.quantities = new Long[]{1L};
        order.country = "FR";
        order.reduction = "PAY THE PRICE";
        OrderCalculator orderCalculator = new OrderCalculator(order);
        assertEquals(6000.00, orderCalculator.computeAmount(), 0.0);
    }

}