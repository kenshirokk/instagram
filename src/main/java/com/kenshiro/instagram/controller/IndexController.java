package com.kenshiro.instagram.controller;

import com.kenshiro.instagram.document.User;
import com.kenshiro.instagram.job.Job;
import com.kenshiro.instagram.service.InstagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	private InstagramService instagramService;
	@Autowired
	private Job job;


	@GetMapping
	public String index(Model model) throws IOException {
		List<User> users = instagramService.findAllUser();
		model.addAttribute("users", users);
		return "index";
	}

	@GetMapping("addUser")
    @ResponseBody
	public void addUser(String user) throws IOException {
		instagramService.createUserProfileFromUrl(user);
	}

	@GetMapping("download")
    @ResponseBody
	public void download() {
		job.download();
	}

	@GetMapping("fetchtomongo")
	@ResponseBody
	public void fetchToMongo() {
		job.fetchToMongo();
	}
}
