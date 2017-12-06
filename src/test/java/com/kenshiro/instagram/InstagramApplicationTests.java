package com.kenshiro.instagram;

import com.kenshiro.instagram.document.Node;
import com.kenshiro.instagram.document.User;
import com.kenshiro.instagram.repository.NodeRepository;
import com.kenshiro.instagram.service.InstagramService;
import com.mongodb.client.result.UpdateResult;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InstagramApplicationTests {

	@Autowired
	private InstagramService instagramService;
	@Autowired
	private NodeRepository nodeRepository;
	@Autowired
	private MongoTemplate mongoTemplate;

//	@Test
	public void testSave() {
		User u = new User();
		u.setUserId("123123");
		u.setUsername("username");
		u.setFullname("fullname");
		u.setProfilePicUrl("heloworld");
		instagramService.save(u);
	}

//	@Test
	public void testCreateUserProfileFromUrl() throws IOException {
		String str1 = "https://www.instagram.com/katherinebaby.hjx/";
		String str2 = "https://www.instagram.com/cuteellyy/";
		instagramService.createUserProfileFromUrl(str1);
		instagramService.createUserProfileFromUrl(str2);
	}

//	@Test
	public void testDownload() throws IOException {
		new Thread(() -> {
            try {
                instagramService.download("https://www.instagram.com/cuteellyy/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					instagramService.download("https://www.instagram.com/katherinebaby.hjx/");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
//		instagramService.download("https://www.instagram.com/cuteellyy/");
//		instagramService.download("https://www.instagram.com/katherinebaby.hjx/");
	}

//	@Test
	public void testNotDownloaded() {
		List<Node> nodes = nodeRepository.findByDownloadedExistsOrDownloadedIsFalse(false);
		System.out.println(nodes.size());
	}

//	@Test
	public void testUpdateDownloaded() {
		UpdateResult updateResult = mongoTemplate.updateMulti(Query.query(Criteria.where("nodeId").is("1650408232416760436")), Update.update("downloaded", true), Node.class);
		System.out.println(updateResult);
	}

//	@Test
	public void testFindAll() {
		List<Node> all = nodeRepository.findAll();
		System.out.println(all.size());
	}

//	@Test
	public void testCountByNodeId() {
		Long aLong = nodeRepository.countByNodeId("1650566563651529270");
		System.out.println(aLong);
	}

//	@Test
	public void testExist() {
		boolean exists = instagramService.exists("1650566563651529270");
		System.out.println(exists);
	}
}
