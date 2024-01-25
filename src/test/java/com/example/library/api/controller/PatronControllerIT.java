package com.example.library.api.controller;

import com.example.library.repository.dao.PatronRepository;
import com.example.library.repository.entity.PatronData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PatronControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatronRepository patronRepository;

    @Test
    @DisplayName("GIVEN valid patron data WHEN call add patron api THEN patron is added successfully")
    void addPatronSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patron")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jane",
                                    "contactInfo": "jane@gmail.com"
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(content().json("""
                            {
                                 "name": "Jane",
                                 "contactInfo": "jane@gmail.com"
                             }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid patron data with duplicated contact infoWHEN call add patron api THEN error is returned")
    void addPatronAlreadyExists() throws Exception {
        patronRepository.save(PatronData.builder()
                        .id(20L)
                        .name("Carter")
                        .contactInformation("carter@gmail.com")
                .build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patron")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jane",
                                    "contactInfo": "carter@gmail.com"
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "Contact info carter@gmail.com already exists"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid patron data WHEN call update patron api THEN patron is updated successfully")
    void updatePatronSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patron/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Charles",
                                    "contactInfo": "charles@gmail.com"
                                }
                                """)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            {
                                "id": 3,
                                "name": "Charles",
                                "contactInfo": "charles@gmail.com"
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid patron data with duplicated contact infoWHEN call update patron api THEN error is returned")
    void updatePatronAlreadyExists() throws Exception {
        patronRepository.save(PatronData.builder()
                .id(21L)
                .name("Kane")
                .contactInformation("kane@gmail.com")
                .build());
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Alicia",
                                    "contactInfo": "kane@gmail.com"
                                }
                                """)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message":  "Contact info kane@gmail.com already exists"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid patron id WHEN call delete patron api THEN patron is deleted successfully")
    void deletePatronSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patron/4")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("GIVEN invalid patron id WHEN call delete patron api THEN error is returned")
    void deletePatronNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patron/100")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "Patron doesn't exist for id: 100"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN valid patron id WHEN call get patron api THEN patron is returned successfully")
    void getPatron() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patron/1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            {
                                "id": 1,
                                "name": "Alice Johnson",
                                "contactInfo": "alice@example.com"
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN invalid patron id WHEN call get patron api THEN error is returned")
    void getPatronNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patron/100")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                            {
                                "errors": [
                                    {
                                        "code": "400 BAD_REQUEST",
                                        "message": "Patron doesn't exist for id: 100"
                                    }
                                ]
                            }
                        """));
    }

    @Test
    @DisplayName("GIVEN page and size WHEN call get all patrons api THEN patrons for the given size is returned successfully")
    void getAllPatrons() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patron?size=2&page=0")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("""
                            {
                                 "content": [
                                         {
                                            "id": 1,
                                            "name": "Alice Johnson",
                                            "contactInfo": "alice@example.com"
                                         },
                                         {
                                             "id": 2,
                                             "name": "Bob Smith",
                                             "contactInfo": "bob@example.com"
                                         }
                                     ],
                                 "pageable": {
                                     "pageNumber": 0,
                                     "pageSize": 2,
                                     "sort": {
                                         "empty": true,
                                         "unsorted": true,
                                         "sorted": false
                                     },
                                     "offset": 0,
                                     "paged": true,
                                     "unpaged": false
                                 },
                                 "last": false,
                                 "first": true,
                                 "size": 2,
                                 "number": 0,
                                 "sort": {
                                     "empty": true,
                                     "unsorted": true,
                                     "sorted": false
                                 },
                                 "empty": false
                             }
                        """, false));

    }
}