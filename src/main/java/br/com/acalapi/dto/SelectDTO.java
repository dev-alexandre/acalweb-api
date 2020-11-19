package br.com.acalapi.dto;

import lombok.Data;

import java.util.List;

@Data
public class SelectDTO<T> {

    private String title;
    private List<T> values;

}


