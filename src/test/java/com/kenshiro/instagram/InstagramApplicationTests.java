package com.kenshiro.instagram;

import com.kenshiro.instagram.document.User;
import com.kenshiro.instagram.service.InstagramService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstagramApplicationTests {

	@Autowired
	private InstagramService instagramService;

	@Test
	public void testSave() {
		User u = new User();
		u.setUserId("123123");
		u.setUsername("username");
		u.setFullname("fullname");
		u.setProfilePicUrl("heloworld");
		instagramService.save(u);
	}

	@Test
	public void testCreateUserProfileFromUrl() throws IOException {
		String str1 = "https://www.instagram.com/katherinebaby.hjx/";
		String str2 = "https://www.instagram.com/cuteellyy/";
		instagramService.createUserProfileFromUrl(str1);
		instagramService.createUserProfileFromUrl(str2);
	}

	@Test
	public void testDownload() throws IOException {
		instagramService.download("https://www.instagram.com/cuteellyy/");
	}

}
