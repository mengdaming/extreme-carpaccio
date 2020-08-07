package xcarpaccio;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Testing web API without the cost of starting the server but yet all the layers below
 * and for this using MockMvc
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class WebControllerMockedServerTest {

    private static final String JSON_FOR_SIMPLE_VALID_ORDER = "{\"prices\":[1],\"quantities\":[1],\"country\":\"FR\",\"reduction\":\"STANDARD\"}";
    
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void order_with_empty_price_list_should_return_bad_request() throws Exception {
        this.mockMvc.perform(
                post("/order/")
                        .content("{\"prices\":[],\"quantities\":[],\"country\":\"FR\",\"reduction\":\"STANDARD\"}")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void acceptance_test_for_known_country_and_standard_reduction() throws Exception {
        this.mockMvc.perform(
                post("/order/")
                        .content(JSON_FOR_SIMPLE_VALID_ORDER)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"total\":1.2}"));
    }

    @Test
    public void accepting_orders_can_be_turned_off_and_on() throws Exception {
        this.mockMvc.perform(
                post("/disable/"))
                .andExpect(status().isOk());
        this.mockMvc.perform(
                post("/order/")
                        .content(JSON_FOR_SIMPLE_VALID_ORDER)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(
                post("/enable/"))
                .andExpect(status().isOk());
        this.mockMvc.perform(
                post("/order/")
                        .content(JSON_FOR_SIMPLE_VALID_ORDER)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"total\":1.2}"));
    }
}