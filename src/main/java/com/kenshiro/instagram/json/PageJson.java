package com.kenshiro.instagram.json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class PageJson {
    private Data data;
    private String status;

    @NoArgsConstructor
    @lombok.Data
    public static class Data {
        private User user;

        @NoArgsConstructor
        @lombok.Data
        public static class User {
            private EdgeOwnerToTimelineMedia edge_owner_to_timeline_media;

            @NoArgsConstructor
            @lombok.Data
            public static class EdgeOwnerToTimelineMedia {
                private int count;
                private PageInfo page_info;
                private List<EdgesX> edges;

                @NoArgsConstructor
                @lombok.Data
                public static class PageInfo {
                    private Boolean has_next_page;
                    private String end_cursor;
                }

                @NoArgsConstructor
                @lombok.Data
                public static class EdgesX {
                    private NodeX node;

                    @NoArgsConstructor
                    @lombok.Data
                    public static class NodeX {
                        private String id;
                        private String __typename;
                        private EdgeMediaToCaption edge_media_to_caption;
                        private String shortcode;
                        private EdgeMediaToComment edge_media_to_comment;
                        private Boolean comments_disabled;
                        private int taken_at_timestamp;
                        private Dimensions dimensions;
                        private String display_url;
                        private EdgeMediaPreviewLike edge_media_preview_like;
                        private Owner owner;
                        private String thumbnail_src;
                        private Boolean is_video;
                        private List<ThumbnailResources> thumbnail_resources;

                        @NoArgsConstructor
                        @lombok.Data
                        public static class EdgeMediaToCaption {
                            private List<Edges> edges;

                            @NoArgsConstructor
                            @lombok.Data
                            public static class Edges {
                                private Node node;

                                @NoArgsConstructor
                                @lombok.Data
                                public static class Node {
                                    private String text;
                                }
                            }
                        }

                        @NoArgsConstructor
                        @lombok.Data
                        public static class EdgeMediaToComment {
                            private int count;
                        }

                        @NoArgsConstructor
                        @lombok.Data
                        public static class Dimensions {
                            private int height;
                            private int width;
                        }

                        @NoArgsConstructor
                        @lombok.Data
                        public static class EdgeMediaPreviewLike {
                            private int count;
                        }

                        @NoArgsConstructor
                        @lombok.Data
                        public static class Owner {
                            private String id;
                        }

                        @NoArgsConstructor
                        @lombok.Data
                        public static class ThumbnailResources {
                            private String src;
                            private int config_width;
                            private int config_height;
                        }
                    }
                }
            }
        }
    }
}
