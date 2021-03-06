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
public class User implements Serializable {

	@Id
	private String id;
	private String userId;
	private String username;
	private String fullname;
	private String biography;
	private String profilePicUrl;
	private String url;
}
