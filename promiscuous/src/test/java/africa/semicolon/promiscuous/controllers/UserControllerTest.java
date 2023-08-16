package africa.semicolon.promiscuous.controllers;

import africa.semicolon.promiscuous.dtos.request.FindUserRequest;
import africa.semicolon.promiscuous.dtos.request.RegisterUserRequest;
import africa.semicolon.promiscuous.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserService userService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testRegister(){
        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("bibeni9669@touchend.com");
            request.setPassword("password");
            String json=mapper.writeValueAsString(request);
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/v1/user")
                                    .content(json)
                                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    ).andExpect(MockMvcResultMatchers.status()
                            .is(HttpStatus.CREATED.value()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Test
    void testFindUserById(){
        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4291@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            log.info("request --> {}", json);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            FindUserRequest request = new FindUserRequest();
            request.setId(1L);
            String json = mapper.writeValueAsString(request);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/findById")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FOUND.value()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void testFindAllUsers(){
        buildSixUsers();
        try {
            FindUserRequest request = new FindUserRequest();
            request.setPage(1);
            request.setPageSize(5);
            String json = mapper.writeValueAsString(request);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/user/getAllUsers")
                            .content(json)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().is(HttpStatus.FOUND.value()))
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void buildSixUsers() {
        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4299@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            log.info("request --> {}", json);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4293@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            log.info("request --> {}", json);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4294@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            log.info("request --> {}", json);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4295@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            log.info("request --> {}", json);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4296@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4297@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            RegisterUserRequest request = new RegisterUserRequest();
            request.setEmail("ciwano4298@viperace.com");
            request.setPassword("password");
            String json = mapper.writeValueAsString(request);
            mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/user")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()));
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}