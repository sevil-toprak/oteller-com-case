//package com.example.hotel.controller;
//
//import com.example.hotel.entity.Hotel;
//import com.example.hotel.model.request.CreateHotelRequest;
//import com.example.hotel.model.response.CreateHotelResponse;
//import com.example.hotel.model.response.GetHotelResponse;
//import com.example.hotel.service.spec.HotelService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(controllers = HotelController.class)
//class HotelControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @MockBean
//    private HotelService hotelService;
//
//    @Test
//    @DisplayName("POST /api/hotels - create hotel")
//    void testCreateHotel() throws Exception {
//        CreateHotelRequest request = new CreateHotelRequest("Hotel Test", "Address Test", 4, List.of());
//        CreateHotelResponse response = new CreateHotelResponse(1L, "Hotel Test", "Address Test", 4, LocalDateTime.now(), LocalDateTime.now());
//
//        when(hotelService.createHotel(any())).thenReturn(response);
//
//        ResultActions result = mockMvc.perform(post("/api/hotels")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(request)));
//
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1L))
//                .andExpect(jsonPath("$.name").value("Hotel Test"));
//    }
//
//    @Test
//    @DisplayName("GET /api/hotels - get all hotels")
//    void testGetHotelList() throws Exception {
//        List<CreateHotelResponse> hotels = List.of(
//                new CreateHotelResponse(1L, "H1", "A1", 3, LocalDateTime.now(), LocalDateTime.now()),
//                new CreateHotelResponse(2L, "H2", "A2", 4, LocalDateTime.now(), LocalDateTime.now()));
//
//        when(hotelService.getHotelList()).thenReturn(hotels);
//
//        ResultActions result = mockMvc.perform(get("/api/hotels"));
//
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.size()").value(2));
//    }
//
//    @Test
//    @DisplayName("GET /api/hotels/{id} - get hotel by id")
//    void testGetHotelById() throws Exception {
//        GetHotelResponse response = new GetHotelResponse(1L, "Hotel", "Addr", 4, LocalDateTime.now(), LocalDateTime.now(), 1L);
//        when(hotelService.getHotelById(1L)).thenReturn(response);
//
//        ResultActions result = mockMvc.perform(get("/api/hotels/1"));
//
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Hotel"));
//    }
//
//    @Test
//    @DisplayName("PUT /api/hotels/{id} - update hotel")
//    void testUpdateHotel() throws Exception {
//        Hotel updatedHotel = new Hotel();
//        updatedHotel.setName("Updated");
//
//        GetHotelResponse response = new GetHotelResponse(1L, "Updated", "Addr", 4, LocalDateTime.now(), LocalDateTime.now(), 1L);
//        when(hotelService.updateHotel(any(), any())).thenReturn(response);
//
//        ResultActions result = mockMvc.perform(put("/api/hotels/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(updatedHotel)));
//
//        result.andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Updated"));
//    }
//
//    @Test
//    @DisplayName("DELETE /api/hotels/{id} - delete hotel")
//    void testDeleteHotel() throws Exception {
//        doNothing().when(hotelService).deleteHotel(1L);
//
//        ResultActions result = mockMvc.perform(delete("/api/hotels/1"));
//
//        result.andExpect(status().isNoContent());
//    }
//}
