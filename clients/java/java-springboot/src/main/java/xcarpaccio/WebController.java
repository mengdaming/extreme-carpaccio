package xcarpaccio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class WebController {

    private boolean takingOrders = true;

    @RequestMapping(value = "/enable", method = RequestMethod.POST)
    public void enableOrderTaking() {
        System.out.println("Accepting order requests");
        this.takingOrders = true;
    }

    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public void disableOrderTaking() {
        System.err.println("Ignoring order requests");
        this.takingOrders = false;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public Amount answerQuote(@RequestBody Order order) {
        System.out.println("Order received: " + order.toString());

        if (isRequestInvalid(order)) {
            // Throw a 400 if request is incorrect
            System.err.println("Order rejected: " + order.toString());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reject request");
        }

        if (willAcceptOrder(order)) {
            Double amount = computeAmount(order);
            System.out.println("Order answered with value: " + amount);
            return new Amount(amount);
        }

        // Throw a 404 if you don't want to respond to an order, without penalty
        System.err.println("Order ignored: " + order.toString());
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cannot answer");
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.POST)
    public void logFeedback(@RequestBody FeedbackMessage message) {
        System.out.println("feedback received: " + message.toString());
    }

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        System.out.println("ping received");
        return "pong";
    }

    Double computeAmount(Order order) {
        return new OrderCalculator(order).computeAmount();
    }

    boolean willAcceptOrder(Order order) {
        return takingOrders && OrderCalculator.canProcessOrder(order);
    }

    private boolean isRequestInvalid(Order order) {
        return !(OrderCalculator.isOrderValid(order));
    }
}