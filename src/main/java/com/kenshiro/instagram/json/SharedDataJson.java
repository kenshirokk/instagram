package com.kenshiro.instagram.json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class SharedDataJson {
    private EntryData entry_data;


    @NoArgsConstructor
    @Data
    public static class EntryData {
        private List<ProfilePage> ProfilePage;

        @NoArgsConstructor
        @Data
        public static class ProfilePage {
            private User user;
            private String logging_page_id;

            @NoArgsConstructor
            @Data
            public static class User {
                private String biography;
                private Boolean blocked_by_viewer;
                private Boolean country_block;
                private Object external_url;
                private Object external_url_linkshimmed;
                private FollowedBy followed_by;
                private Boolean followed_by_viewer;
                private Follows follows;
                private Boolean follows_viewer;
                private String full_name;
                private Boolean has_blocked_viewer;
                private Boolean has_requested_viewer;
                private String id;
                private Boolean is_private;
                private Boolean is_verified;
                private String profile_pic_url;
                private String profile_pic_url_hd;
                private Boolean requested_by_viewer;
                private String username;
                private Object connected_fb_page;
                private Media media;
                private SavedMedia saved_media;

                @NoArgsConstructor
                @Data
                public static class FollowedBy {
                    private int count;
                }

                @NoArgsConstructor
                @Data
                public static class Follows {
                    private int count;
                }

                @NoArgsConstructor
                @Data
                public static class Media {
                    private int count;
                    private PageInfo page_info;
                    private List<Nodes> nodes;

                    @NoArgsConstructor
                    @Data
                    public static class PageInfo {
                        private Boolean has_next_page;
                        private String end_cursor;
                    }

                    @NoArgsConstructor
                    @Data
                    public static class Nodes {
                        private String __typename;
                        private String id;
                        private Boolean comments_disabled;
                        private Dimensions dimensions;
                        private Object gating_info;
                        private String media_preview;
                        private Owner owner;
                        private String thumbnail_src;
                        private Boolean is_video;
                        private String code;
                        private int date;
                        private String display_src;
                        private String caption;
                        private Comments comments;
                        private Likes likes;
                        private List<ThumbnailResources> thumbnail_resources;

                        @NoArgsConstructor
                        @Data
                        public static class Dimensions {
                            private int height;
                            private int width;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class Owner {
                            private String id;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class Comments {
                            private int count;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class Likes {
                            private int count;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class ThumbnailResources {
                            private String src;
                            private int config_width;
                            private int config_height;
                        }
                    }
                }

                @NoArgsConstructor
                @Data
                public static class SavedMedia {
                    private int count;
                    private PageInfoX page_info;
                    private List<?> nodes;

                    @NoArgsConstructor
                    @Data
                    public static class PageInfoX {
                        private Boolean has_next_page;
                        private Object end_cursor;
                    }
                }
            }
        }
    }

}
