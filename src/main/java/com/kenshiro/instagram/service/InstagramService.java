package com.kenshiro.instagram.service;

import com.google.gson.Gson;
import com.kenshiro.instagram.document.Node;
import com.kenshiro.instagram.document.User;
import com.kenshiro.instagram.json.PageJson;
import com.kenshiro.instagram.json.SharedDataJson;
import com.kenshiro.instagram.repository.NodeRepository;
import com.kenshiro.instagram.repository.UserRepository;
import com.kenshiro.instagram.util.OkHttpHelper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
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
    @Autowired
    private MongoTemplate mongoTemplate;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User createUserProfileFromUrl(String url) throws IOException {
        OkHttpClient client = OkHttpHelper.getClient();
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
        u.setUrl(url);
        return userRepository.save(u);
    }

    public void download(String url) throws IOException {

        SharedDataJson sd = OkHttpHelper.getSharedData(url);
        if (sd.getEntry_data() == null || sd.getEntry_data().getProfilePage() == null) {
            return;
        }
        String username = sd.getEntry_data().getProfilePage().get(0).getUser().getUsername();

        SharedDataJson.EntryData.ProfilePage.User.Media mainPageMedia = OkHttpHelper.getMainPageMedia(url);
        List<SharedDataJson.EntryData.ProfilePage.User.Media.Nodes> nodes = mainPageMedia.getNodes();
        for (SharedDataJson.EntryData.ProfilePage.User.Media.Nodes n : nodes) {
            if (exists(n.getId())) {
                return;
            }
            Node node = new Node();
            node.setTypeName(n.get__typename());
            node.setNodeId(n.getId());
            node.setCode(n.getCode());
            node.setIsVideo(n.getIs_video());
            node.setDate(Long.valueOf(n.getDate()));
            node.setDisplaySrc(n.getDisplay_src());
            node.setMediaPreview(n.getMedia_preview());
            node.setCaption(n.getCaption());
            node.setOwnerId(n.getOwner().getId());
            node.setOwnerName(username);
            nodeRepository.save(node);
        }
        int count = mainPageMedia.getCount() / 5;
        String ownerId = mainPageMedia.getNodes().get(0).getOwner().getId();
        Boolean hasNextPage = mainPageMedia.getPage_info().getHas_next_page();
        String endCursor = mainPageMedia.getPage_info().getEnd_cursor();
        while (hasNextPage) {
            Object[] nextPage = getNextPage(count, ownerId, endCursor, username);
            hasNextPage = (Boolean) nextPage[0];
            endCursor = (String) nextPage[1];
        }
    }

    private Object[] getNextPage(int pageSize, String ownerId, String endCursor, String username) throws IOException {
        String nextQueryUrl = "https://www.instagram.com/graphql/query/" +
                "?query_id=17888483320059182" +
                "&variables={\"id\":\"{ownerId}\",\"first\":{pageSize},\"after\":\"{endCursor}\"}";
        nextQueryUrl = nextQueryUrl.replace("{ownerId}", ownerId)
                .replace("{endCursor}", endCursor)
                .replace("{pageSize}", pageSize+"");
        PageJson pageJson = OkHttpHelper.getPageJson(nextQueryUrl);
        List<PageJson.Data.User.EdgeOwnerToTimelineMedia.EdgesX> edges = pageJson.getData().getUser().getEdge_owner_to_timeline_media().getEdges();
        for (PageJson.Data.User.EdgeOwnerToTimelineMedia.EdgesX e : edges) {
            PageJson.Data.User.EdgeOwnerToTimelineMedia.EdgesX.NodeX n = e.getNode();
            if (exists(n.getId())) {
                return new Object[]{false, ""};
            }
            Node node = new Node();
            node.setTypeName(n.get__typename());
            node.setNodeId(n.getId());
            node.setCode(n.getShortcode());
            node.setIsVideo(n.getIs_video());
            node.setDate(Long.valueOf(n.getTaken_at_timestamp()));
            node.setDisplaySrc(n.getDisplay_url());
//            node.setMediaPreview(n.getMedia_preview());
            if (n.getEdge_media_to_caption().getEdges().size() > 0) {
                node.setCaption(n.getEdge_media_to_caption().getEdges().get(0).getNode().getText());
            }
            node.setOwnerId(n.getOwner().getId());
            node.setOwnerName(username);
            nodeRepository.save(node);
        }
        Boolean hasNextPage = pageJson.getData().getUser().getEdge_owner_to_timeline_media().getPage_info().getHas_next_page();
        String newEndCursor = pageJson.getData().getUser().getEdge_owner_to_timeline_media().getPage_info().getEnd_cursor();
        return new Object[]{hasNextPage, newEndCursor};
    }

    public boolean exists(String nodeId) {
        Long node = nodeRepository.countByNodeId(nodeId);
        return node.compareTo(1L) >= 0;
    }

    public void updateDownloaded(String nodeId) {
        mongoTemplate.updateMulti(Query.query(Criteria.where("nodeId").is(nodeId)), Update.update("downloaded", true), Node.class);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

}
