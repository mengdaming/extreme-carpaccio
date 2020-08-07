package xcarpaccio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

/**
 * Testing web API, starting the server
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebControllerServerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;


    private static Order buildSimpleOrder() {
        Order order = new Order();
        order.prices = new Double[]{1.0};
        order.quantities = new Long[]{1L};
        order.country = "FR";
        order.reduction = "STANDARD";
        return order;
    }

    @Test
    public void order_with_empty_price_list_should_return_bad_request() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        Order order = buildSimpleOrder();
        order.prices = new Double[]{};

        ResponseEntity<Amount> response = testRestTemplate.
                postForEntity("http://localhost:" + this.port + "/order", order, Amount.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void a_nominal_test() {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        Order order = buildSimpleOrder();
        order.prices = new Double[]{0.0};
        order.quantities = new Long[]{0L};

        ResponseEntity<Amount> response = testRestTemplate.
                postForEntity("http://localhost:" + this.port + "/order", order, Amount.class);
        assertEquals(Double.valueOf(0), response.getBody().total);
    }

    @Test
    public void accepting_orders_can_be_turned_off_and_on() throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        Order order = buildSimpleOrder();

        ResponseEntity<Amount> response_disable = testRestTemplate.
                postForEntity("http://localhost:" + this.port + "/disable", null, null);
        assertEquals(HttpStatus.OK, response_disable.getStatusCode());

        ResponseEntity<Amount> response_order_disabled = testRestTemplate.
                postForEntity("http://localhost:" + this.port + "/order", order, Amount.class);
        assertEquals(HttpStatus.NOT_FOUND, response_order_disabled.getStatusCode());

        ResponseEntity<Amount> response_enable = testRestTemplate.
                postForEntity("http://localhost:" + this.port + "/enable", null, null);
        assertEquals(HttpStatus.OK, response_enable.getStatusCode());

        ResponseEntity<Amount> response_order_enabled = testRestTemplate.
                postForEntity("http://localhost:" + this.port + "/order", order, Amount.class);
        assertEquals(Double.valueOf(1.2), response_order_enabled.getBody().total);
    }
}
