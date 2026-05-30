// package com.example.demo.config;

// import com.fasterxml.jackson.annotation.JsonInclude;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.databind.SerializationFeature;
// import com.fasterxml.jackson.datatype.hibernate7.Hibernate7Module;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
// import jakarta.annotation.PostConstruct;
// import org.springframework.context.annotation.Configuration;

// import java.util.TimeZone;

// @Configuration
// public class JacksonConfig {

//     private final ObjectMapper objectMapper;

//     public JacksonConfig(ObjectMapper objectMapper) {
//         this.objectMapper = objectMapper;
//     }

//     @PostConstruct
//     public void setup() {

//         // Hỗ trợ LocalDateTime, LocalDate,...
//         objectMapper.registerModule(new JavaTimeModule());

//         // Hỗ trợ Hibernate Lazy Loading
//         objectMapper.registerModule(new Hibernate7Module());

//         // Không trả timestamp dạng số
//         objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

//         // Timezone mặc định
//         objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

//         // Bỏ field null khỏi JSON response
//         objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
//     }
// }