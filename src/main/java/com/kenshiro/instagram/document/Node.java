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
    private String __typename;
    private String nodeId;
    private String ownerId;
    private String media_preview;
    private Boolean is_video;
    private String code;
    private Long date;
    private String display_src;
    private String caption;
}
