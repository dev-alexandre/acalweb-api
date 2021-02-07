package br.com.acalapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class AE {

    @Id
    private String id;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyyHHmmss")
    public LocalDateTime createdDate;

    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "ddMMyyyyHHmmss")
    public LocalDateTime lastModifiedDate;

    private Boolean ativo;

}