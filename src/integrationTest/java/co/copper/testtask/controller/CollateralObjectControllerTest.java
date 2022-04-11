package co.copper.testtask.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author olufemi on 4/10/22
 */

@SpringBootTest()
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CollateralObjectControllerTest {
    public static final String COLLATERALS = "/collaterals";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save_collateral_should_return_id() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"123455\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //When
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "1");
        Assertions.assertEquals(mvcResult.getResponse().getStatus(), 200);
    }

    @Test
    public void save_collateral_should_return_id_when_no_id_is_set_in_request_body() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");
        //When
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "1");
    }

    @Test
    public void save_collateral_should_return_id_incrementally_via_generation_type_identity() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"123455\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //When
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "1");


        //Given
        requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"123455\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //When
        mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "2");


        //Given
        requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"123455\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //When
        mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "3");
    }

    @Test
    public void save_collateral_should_return_same_id_when_request_id_matches_increamented_id() throws Exception {

        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"2\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //When
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "1");


        //Given
        requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"2\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //When
        mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "2");


        //Given
        requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"2\", \"name\": \"newName\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //When
        mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "2");
    }

    @Test
    public void save_collateral_should_return_bad_response_and_empty_id_when_request_value_is_wrong() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450\",\"type\": \"asset\"}");

        //When
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "");
        Assertions.assertEquals(mvcResult.getResponse().getStatus(), 400);
    }

    @Test
    public void save_collateral_should_return_bad_response_and_empty_id_when_type_value_is_not_asset() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"collateral\"}");

        //When
        MvcResult mvcResult = this.mockMvc.perform(requestBuilder).andReturn();

        //Then
        Assertions.assertEquals(mvcResult.getResponse().getContentAsString(), "");
        Assertions.assertEquals(mvcResult.getResponse().getStatus(), 400);
    }


    @Test
    public void getInfo_collateral_should_return_collateral() throws Exception {
        //Given
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(COLLATERALS).contentType(MediaType.APPLICATION_JSON)
                .content("{\"currency\": \"usd\", \"id\": \"123455\", \"name\": \"myname\",\"year\": 2019,\"value\": \"223450033\",\"type\": \"asset\"}");

        //Given
        this.mockMvc.perform(requestBuilder).andReturn();

        //When
        requestBuilder = MockMvcRequestBuilders.get("/collaterals/1").contentType(MediaType.APPLICATION_JSON);

        //Then
        this.mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency").value("usd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("myname"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.year").value("2019"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value("223450033"));
    }
}
