package com.product.entity;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import com.product.utils.JsonObjectConverter;

import jakarta.persistence.*;

@Entity
@Table(name = "user_logs")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiName;
    private String clientIp;
    private String method;

    @Lob
    @Convert(converter = JsonObjectConverter.class)
    private String requestData;

    @Lob
    @Convert(converter = JsonObjectConverter.class)
    private Object responseData;

    private String responseStatus;
    private int httpStatus;
    private String message;
    private LocalDateTime timestamp;
    



    public UserLog(HttpServletRequest request, String apiName, Object requestData) {
        this.apiName = apiName;
        this.clientIp = request.getRemoteAddr();
        this.method = request.getMethod();
        this.requestData = requestData != null ? requestData.toString() : null;
        this.timestamp = LocalDateTime.now();
    }

    // Getters & Setters (or use Lombok @Getter, @Setter)
}
