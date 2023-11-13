package org.example;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class TestingWebAppTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnNewShop() throws Exception {
        this.mockMvc.perform(get("/create-shop")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Create Shop")));
    }

    @Test
    public void shouldCreateNewShop() throws Exception {
        this.mockMvc.perform(post("/create-shop")
                        .param("shopName", "Jack's Appliances")
                        .param("shopDescription", "Everything For Your Dining Room!")
                        .param("categories", "APPLIANCES")
                        .param("imageURL", "https://images.unsplash.com/photo-1556911220-bff31c812dba?q=80&w=3736&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())  // Expecting a redirection
                .andExpect(redirectedUrl("/miniShopify"));  // Expecting redirection to /miniShopify
    }

    @Test
    public void shouldCreateNewProduct() throws Exception {
        this.mockMvc.perform(post("/create-product")
                        .param("shopId", String.valueOf(1))
                        .param("productName", "Apples")
                        .param("productDescription", "Golden Apples!")
                        .param("inventory", String.valueOf(10)))
                .andDo(print())
                .andExpect(status().is3xxRedirection())  // Expecting a redirection
                .andExpect(redirectedUrl("/shop/1"));  // Expecting redirection to /miniShopify
    }

}
