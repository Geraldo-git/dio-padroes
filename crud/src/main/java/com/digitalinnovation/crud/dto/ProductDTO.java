package com.digitalinnovation.crud.dto;

import com.digitalinnovation.crud.entities.Category;

import java.io.Serializable;
import java.time.Instant;

public class ProductDTO implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imgUrl;
    private Instant date;
}
