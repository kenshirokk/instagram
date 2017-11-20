package com.kenshiro.instagram.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Node implements Serializable {
    @Id
    private String id;
    private String typeName;
    private String nodeId;
    private String ownerId;
    private String mediaPreview;
    private Boolean isVideo;
    private String code;
    private Long date;
    private String displaySrc;
    private String caption;
}
