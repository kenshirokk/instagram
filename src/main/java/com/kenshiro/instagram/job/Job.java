package com.kenshiro.instagram.job;

import com.kenshiro.instagram.document.Node;
import com.kenshiro.instagram.document.User;
import com.kenshiro.instagram.json.CardDetailJson;
import com.kenshiro.instagram.json.VideoDetailJson;
import com.kenshiro.instagram.repository.NodeRepository;
import com.kenshiro.instagram.service.InstagramService;
import com.kenshiro.instagram.util.Constant;
import com.kenshiro.instagram.util.OkHttpHelper;
import com.kenshiro.instagram.util.TypeNameEunm;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class Job {

    ExecutorService exe = Executors.newFixedThreadPool(10);

    @Autowired
    private InstagramService instagramService;
    @Autowired
    private NodeRepository nodeRepository;

//    @Scheduled(cron = "0 */10 * * * ?")
    public void fetchToMongo() {
        log.info("fetchToMongo");
        List<User> allUser = instagramService.findAllUser();
        allUser.forEach(u -> {
            String url = u.getUrl();
            exe.execute(() -> {
                try {
                    instagramService.download(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

//    @Scheduled(cron = "* * */1 * * ?")
    public void download() {
        log.info("download");
        List<Node> nodes = nodeRepository.findByDownloadedExistsOrDownloadedIsFalse(false);
        log.info("download: downloaded false size [{}]", nodes.size());
        nodes.forEach(node -> {
            if (TypeNameEunm.GRAPHIMAGE.value().equalsIgnoreCase(node.getTypeName())) {
                String dir = node.getOwnerName() + "/";
                String fileName = node.getCode() + node.getDisplaySrc().substring(node.getDisplaySrc().lastIndexOf("."));
                exe.submit(() -> {
                    try {
                        downloadFile(node.getDisplaySrc(), dir, fileName);
                        instagramService.updateDownloaded(node.getNodeId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            if(TypeNameEunm.GRAPHVIDEO.value().equalsIgnoreCase(node.getTypeName())) {
                String dir = node.getOwnerName() + Constant.VIDEO_FOLDER;
                String code = node.getCode();
                String url = Constant.DETAIL_URL;
                url = url.replace("{code}", code);
                try {
                    VideoDetailJson videoDetailJson = OkHttpHelper.getDetailJson(url);
                    String display_url = videoDetailJson.getGraphql().getShortcode_media().getDisplay_url().replaceAll(Constant.PIC_REGEX, "");
                    String video_url = videoDetailJson.getGraphql().getShortcode_media().getVideo_url();
                    String ext1 = display_url.substring(display_url.lastIndexOf("."));
                    String ext2 = video_url.substring(video_url.lastIndexOf("."));
                    exe.submit(() -> {
                        try {
                            downloadFile(display_url, dir, code + ext1);
                            downloadFile(video_url, dir, code + ext2);
                            instagramService.updateDownloaded(node.getNodeId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (TypeNameEunm.GRAPHSIDECAR.value().equalsIgnoreCase(node.getTypeName())) {
                String dir = node.getOwnerName() + "/";
                String code = node.getCode();
                String url = Constant.DETAIL_URL;
                url = url.replace("{code}", code);
                try {
                    CardDetailJson cardDetailJson = OkHttpHelper.getCardDetailJson(url);
                    String display_url = cardDetailJson.getGraphql().getShortcode_media().getDisplay_url();
                    List<CardDetailJson.Graphql.ShortcodeMedia.EdgeSidecarToChildren.EdgesXXXX> edges = cardDetailJson.getGraphql().getShortcode_media().getEdge_sidecar_to_children().getEdges();
                    edges.forEach(n -> {
                        if (TypeNameEunm.GRAPHIMAGE.value().equalsIgnoreCase(n.getNode().get__typename())) {
                            String shortcode = n.getNode().getShortcode();
                            String display_url1 = n.getNode().getDisplay_url();
                            String s = code + "[" + shortcode + "]";
                            exe.submit(() -> {
                                try {
                                    downloadFile(display_url1, dir, s + display_url1.substring(display_url1.lastIndexOf(".")));
                                    instagramService.updateDownloaded(n.getNode().getId());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        if (TypeNameEunm.GRAPHVIDEO.value().equalsIgnoreCase(n.getNode().get__typename())) {
                            String shortcode = n.getNode().getShortcode();
                            String video_url = n.getNode().getVideo_url();
                            String s = code + "[" + shortcode + "]";
                            exe.submit(() -> {
                                try {
                                    downloadFile(video_url, dir + Constant.VIDEO_FOLDER, s + video_url.substring(video_url.lastIndexOf(".")));
                                    instagramService.updateDownloaded(n.getNode().getId());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    });
                    exe.submit(() -> {
                        try {
                            downloadFile(display_url, dir, code + display_url.substring(display_url.lastIndexOf(".")));
                            instagramService.updateDownloaded(node.getNodeId());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downloadFile(String url, String dir, String fileName) throws IOException {
        OkHttpClient client = OkHttpHelper.getClient();
        File folder = new File(dir);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        File file = new File(dir + fileName);
        Response response = client.newCall(new Request.Builder().url(url).build()).execute();
        try (InputStream is = response.body().byteStream()) {
            byte[] bs = new byte[1024];
            int len;
            try (FileOutputStream fos = new FileOutputStream(file)) {
                while ((len = is.read(bs)) != -1) {
                    fos.write(bs, 0, len);
                }
            }
        }

    }
}
