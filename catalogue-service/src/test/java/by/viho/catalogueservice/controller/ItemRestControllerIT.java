/*
package by.viho.catalogueservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ItemRestControllerIT
{
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("/SQL/items.sql")
    void  findProduct_ProductExists_ReturnsProductsList() throws Exception{
        //given
        var requsetBuilder = MockMvcRequestBuilders
                .get("/catalogue-api/item/1")
                .with(jwt().jwt(builder -> builder.claim("scope", "view_catalogue")));
        //when
        this.mockMvc.perform(requsetBuilder)
                //then
                .andExpectAll(status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                content().json("""
                                {
                                    "id": 1,
                                    "title": "Some title",
                                    "details": "Some details"
                                }""")
        );
    }
}
*/
