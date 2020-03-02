package com.minwait.randompath;

import com.minwait.randompath.controller.TestController;
import com.minwait.randompath.filter.PathFilter;
import com.minwait.randompath.interceptor.TestInterceptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RandomPathApplicationTests {
    @Autowired
    private WebApplicationContext webAppContext;

    @Test
    void contextLoads() throws Exception {
        PathFilter pathFilter = new PathFilter();
        MockFilterConfig filterConfig = new MockFilterConfig();
        filterConfig.addInitParameter("path.pattern", "/.{1,}_random/.{1,}");
        pathFilter.init(filterConfig);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new TestController())
                .addMappedInterceptors(new String[]{"/forbidden/**"}, new TestInterceptor())
                .addFilter(pathFilter, "/*")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/xxx_random/hello")
                .servletPath("/xxx_random/hello")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "Random Path")
                .session(new MockHttpSession()))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Random Path"))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.post("/xxx_random/forbidden/hello")
                .servletPath("/xxx_random/forbidden/hello")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("name", "Random Path")
                .session(new MockHttpSession()))
                .andExpect(status().isForbidden());
    }

}
