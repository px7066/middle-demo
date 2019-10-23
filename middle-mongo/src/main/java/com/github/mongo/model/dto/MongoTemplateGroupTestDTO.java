package com.github.mongo.model.dto;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Data
@Document(collection = "test")
public class MongoTemplateGroupTestDTO {

    private String id;

    private String code;

    private List<MongoTemplateGroupSubTestDTO> subs;

}
