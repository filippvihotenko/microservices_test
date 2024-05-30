/*

package by.viho.catalogueservice.controller;

import org.hibernate.annotations.processing.SQL;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;




@Transactional
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ItemsRestControllerTestIT
{
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/SQL/items.sql")
    void  findItems_ResturnsItemsList() throws Exception
    {
        //given
       var requestBuilder =   MockMvcRequestBuilders.get("/catalogue-api/items/list").
               with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));
       //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(status().isOk(), content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                content().json("""
                        [
                        {"id" : 1, "title" : "Some title", "details" : "Some details"}
                        ]""")
        );
    }
    @Test
    @Sql("/SQL/newDb.sql")
    void createItem_returnsNewItem() throws Exception
    {
        //given
        var requestBuilder  = MockMvcRequestBuilders.post("/catalogue-api/items/create").contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"title": "Some title", "details": "Some details"}""").with(jwt().jwt(builder -> builder.claim("scope", "edit_catalogue")));

        //when
        this.mockMvc.perform(requestBuilder)
                //then
                .andExpectAll(status().isCreated(), content().contentType(MediaType.APPLICATION_JSON),
                        header().string(HttpHeaders.LOCATION, "http://localhost/catalogue-api/item/1"),
                content().contentType(MediaType.APPLICATION_JSON), content().json("""
    {"id" : 1, "title" : "Some title", "details" :  "Some details"}
"""));
    }
}

*/
