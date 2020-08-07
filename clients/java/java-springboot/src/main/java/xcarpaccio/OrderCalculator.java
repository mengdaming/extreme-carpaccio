package xcarpaccio;

import java.util.Arrays;
import java.util.List;

class OrderCalculator {

    private static final List<String> RECOGNIZED_REDUCTIONS = Arrays.asList("STANDARD", "HALF PRICE", "PAY THE PRICE");

    private final Order order;

    OrderCalculator(Order order) {
        this.order = order;
    }

    private static double computeAmountBeforeTax(Order order) {
        double amount = 0.0;

        if (isOrderValid(order)) {
            for (int i = 0; i < order.prices.length; i++) {
                Long quantity = (order.quantities != null && order.quantities.length >= i) ?
                        order.quantities[i] : 1L;
                amount += order.prices[i] * quantity;
            }
        }
        return amount;
    }

    private static double computeAmountWithTax(Order order) {
        double beforeTax = computeAmountBeforeTax(order);
        double taxRate = retrieveTaxRate(order.country, beforeTax);
        return beforeTax * (1 + taxRate);
    }

    private static double retrieveTaxRate(String country, double amount) {
        if (CountryTaxRate.isDefined(country))
            return CountryTaxRate.find(country).retrieveRateFor(amount);
        else
            return 0.0;
    }

    static double computeReductionRate(double amount, String reduction) {
        switch (reduction) {
            case "STANDARD":
                return computeReductionRateForStandard(amount);
            case "HALF PRICE":
                return computeReductionRateForHalfPrice();
            case "PAY THE PRICE":
                return computeReductionRateForPayThePrice();
            default:
                return 0.00;
        }
    }

    private static double computeReductionRateForHalfPrice() {
        return 0.50;
    }

    private static double computeReductionRateForPayThePrice() {
        return 0.00;
    }

    private static double computeReductionRateForStandard(double amount) {
        if (amount >= 50000) return 0.15;
        if (amount >= 10000) return 0.10;
        if (amount >= 7000) return 0.07;
        if (amount >= 5000) return 0.05;
        if (amount >= 1000) return 0.03;
        return 0.00;
    }

    static boolean canProcessOrder(Order order) {
        return new OrderCalculator(order).canProcessOrder();
    }

    static boolean isOrderValid(Order order) {
        return (order != null)
                && (arePricesValid(order))
                && (areQuantitiesValid(order))
                && (doPricesAndQuantitiesMatch(order))
                && (isCountryValid(order))
                && (isReductionValid(order));
    }

    private static boolean isReductionValid(Order order) {
        return order.reduction != null && order.reduction.length() > 0;
    }

    private static boolean isCountryValid(Order order) {
        return order.country != null && order.country.length() >= 1 && order.country.length() <= 2;
    }

    private static boolean doPricesAndQuantitiesMatch(Order order) {
        return order.prices.length == order.quantities.length;
    }

    private static boolean areQuantitiesValid(Order order) {
        return order.quantities != null && order.quantities.length > 0;
    }

    private static boolean arePricesValid(Order order) {
        return order.prices != null && order.prices.length > 0;
    }

    Double computeAmount() {
        double amount = computeAmountWithTax(order);
        double reductionRate = computeReductionRate(amount, order.reduction);
        return amount * (1 - reductionRate);
    }

    double computeAmountBeforeTax() {
        return computeAmountBeforeTax(this.order);
    }

    double computeAmountWithTax() {
        return computeAmountWithTax(this.order);
    }

    private boolean canProcessOrder() {
        return this.isOrderValid() && this.canProcessTax() && this.canProcessReduction();
    }

    boolean isOrderValid() {
        return isOrderValid(this.order);
    }

    private boolean canProcessReduction() {
        return RECOGNIZED_REDUCTIONS.contains(order.reduction);
    }

    private boolean canProcessTax() {
        return CountryTaxRate.isDefined(order.country);
    }
}
