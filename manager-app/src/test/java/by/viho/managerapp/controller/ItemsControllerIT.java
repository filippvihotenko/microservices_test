package by.viho.managerapp.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class ItemsControllerIT
{
    @Autowired
    MockMvc mockMvc;

    @Test
    void getNewItemPage_ReturnsItemsPage() throws Exception
    {
        //given
        var requestBuilder =  MockMvcRequestBuilders.get("/catalogue/items/create").with(user("j.dewar").roles("MANAGER"));
        //when
        this.mockMvc.perform(requestBuilder).andExpectAll(status().isOk(), view().name("catalogue/items/newItem"));
        //then
    }
}
