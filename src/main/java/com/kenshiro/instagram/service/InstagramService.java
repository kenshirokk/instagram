package com.kenshiro.instagram.service;

import com.google.gson.Gson;
import com.kenshiro.instagram.document.Node;
import com.kenshiro.instagram.document.User;
import com.kenshiro.instagram.json.SharedDataJson;
import com.kenshiro.instagram.repository.NodeRepository;
import com.kenshiro.instagram.repository.UserRepository;
import com.kenshiro.instagram.util.OkHttpHelper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class InstagramService {

    private Pattern pattern = Pattern.compile("<script type=\"text/javascript\">window._sharedData = (.*);</script>");
    private Gson gson = new Gson();
    private String picRegex = "/\\w\\d*x\\d*";

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NodeRepository nodeRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createUserProfileFromUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().proxy(new Proxy(Proxy.Type.SOCKS,
                new InetSocketAddress(8081))).build();
        Request request = new Request.Builder().url(url).build();
        String html;
        try (Response response = client.newCall(request).execute()) {
            html = response.body().string();
        }
        Matcher matcher = pattern.matcher(html);
        matcher.find();
        SharedDataJson sharedDataJson = gson.fromJson(matcher.group(1), SharedDataJson.class);
        SharedDataJson.EntryData.ProfilePage.User jsonUser = sharedDataJson.getEntry_data().getProfilePage().get(0).getUser();
        User u = new User();
        u.setUserId(jsonUser.getId());
        u.setUsername(jsonUser.getUsername());
        u.setFullname(jsonUser.getFull_name());
        u.setBiography(jsonUser.getBiography());
        u.setProfilePicUrl(jsonUser.getProfile_pic_url().replaceAll(picRegex, ""));
        return userRepository.save(u);
    }

    public void download(String url) throws IOException {
        SharedDataJson.EntryData.ProfilePage.User.Media mainPageMedia = OkHttpHelper.getMainPageMedia(url);
        List<SharedDataJson.EntryData.ProfilePage.User.Media.Nodes> nodes = mainPageMedia.getNodes();
        nodes.forEach(n -> {
            Node node = new Node();
            node.set__typename(n.get__typename());
            node.setNodeId(n.getId());
            node.setCode(n.getCode());
            node.setIs_video(n.getIs_video());
            node.setDate(Long.valueOf(n.getDate()));
            node.setDisplay_src(n.getDisplay_src());
            node.setMedia_preview(n.getMedia_preview());
            node.setCaption(n.getCaption());
            node.setOwnerId(n.getOwner().getId());
            nodeRepository.save(node);

        });
        int count = mainPageMedia.getCount() / 5;
        Boolean has_next_page = mainPageMedia.getPage_info().getHas_next_page();
        while (has_next_page) {

        }
    }
}
