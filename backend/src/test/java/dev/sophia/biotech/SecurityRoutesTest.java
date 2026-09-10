package dev.sophia.biotech;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityRoutesTest extends AbstractPostgresTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void healthIsPublicAndHidesDetails() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"status\":\"UP\"}", true));
    }

    @Test
    void otherActuatorEndpointsAreNotPublic() throws Exception {
        mockMvc.perform(get("/actuator/env")).andExpect(status().isForbidden());
        mockMvc.perform(get("/actuator/beans")).andExpect(status().isForbidden());
        mockMvc.perform(get("/actuator/mappings")).andExpect(status().isForbidden());
    }

    @Test
    void unlistedRoutesAreDenied() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isForbidden());
        mockMvc.perform(get("/internal/anything")).andExpect(status().isForbidden());
    }

    /** A permitted GET under /api reaches the dispatcher. No endpoint exists yet, so it is a 404. */
    @Test
    void apiGetIsAllowedThroughTheFilterChain() throws Exception {
        mockMvc.perform(get("/api/does-not-exist-yet")).andExpect(status().isNotFound());
    }

    @Test
    void writeMethodsAreDenied() throws Exception {
        mockMvc.perform(post("/api/does-not-exist-yet")).andExpect(status().isForbidden());
        mockMvc.perform(put("/api/does-not-exist-yet")).andExpect(status().isForbidden());
        mockMvc.perform(delete("/api/does-not-exist-yet")).andExpect(status().isForbidden());
    }
}
