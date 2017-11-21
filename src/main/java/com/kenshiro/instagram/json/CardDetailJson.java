package com.kenshiro.instagram.json;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class CardDetailJson {
    private Graphql graphql;

    @NoArgsConstructor
    @Data
    public static class Graphql {
        private ShortcodeMedia shortcode_media;

        @NoArgsConstructor
        @Data
        public static class ShortcodeMedia {
            private String __typename;
            private String id;
            private String shortcode;
            private Dimensions dimensions;
            private Object gating_info;
            private Object media_preview;
            private String display_url;
            private Boolean is_video;
            private Boolean should_log_client_event;
            private String tracking_token;
            private EdgeMediaToTaggedUser edge_media_to_tagged_user;
            private EdgeMediaToCaption edge_media_to_caption;
            private Boolean caption_is_edited;
            private EdgeMediaToComment edge_media_to_comment;
            private Boolean comments_disabled;
            private int taken_at_timestamp;
            private EdgeMediaPreviewLike edge_media_preview_like;
            private EdgeMediaToSponsorUser edge_media_to_sponsor_user;
            private Object location;
            private Boolean viewer_has_liked;
            private Boolean viewer_has_saved;
            private Boolean viewer_has_saved_to_collection;
            private OwnerX owner;
            private Boolean is_ad;
            private EdgeWebMediaToRelatedMedia edge_web_media_to_related_media;
            private EdgeSidecarToChildren edge_sidecar_to_children;
            private List<DisplayResourcesX> display_resources;

            @NoArgsConstructor
            @Data
            public static class Dimensions {
                private int height;
                private int width;
            }

            @NoArgsConstructor
            @Data
            public static class EdgeMediaToTaggedUser {
                private List<?> edges;
            }

            @NoArgsConstructor
            @Data
            public static class EdgeMediaToCaption {
                private List<Edges> edges;

                @NoArgsConstructor
                @Data
                public static class Edges {
                    private Node node;

                    @NoArgsConstructor
                    @Data
                    public static class Node {
                        private String text;
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class EdgeMediaToComment {
                private int count;
                private PageInfo page_info;
                private List<EdgesX> edges;

                @NoArgsConstructor
                @Data
                public static class PageInfo {
                    private Boolean has_next_page;
                    private String end_cursor;
                }

                @NoArgsConstructor
                @Data
                public static class EdgesX {
                    private NodeX node;

                    @NoArgsConstructor
                    @Data
                    public static class NodeX {
                        private String id;
                        private String text;
                        private int created_at;
                        private Owner owner;

                        @NoArgsConstructor
                        @Data
                        public static class Owner {
                            private String id;
                            private String profile_pic_url;
                            private String username;
                        }
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class EdgeMediaPreviewLike {
                private int count;
                private List<EdgesXX> edges;

                @NoArgsConstructor
                @Data
                public static class EdgesXX {
                    private NodeXX node;

                    @NoArgsConstructor
                    @Data
                    public static class NodeXX {
                        private String id;
                        private String profile_pic_url;
                        private String username;
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class EdgeMediaToSponsorUser {
                private List<?> edges;
            }

            @NoArgsConstructor
            @Data
            public static class OwnerX {
                private String id;
                private String profile_pic_url;
                private String username;
                private Boolean blocked_by_viewer;
                private Boolean followed_by_viewer;
                private String full_name;
                private Boolean has_blocked_viewer;
                private Boolean is_private;
                private Boolean is_unpublished;
                private Boolean is_verified;
                private Boolean requested_by_viewer;
            }

            @NoArgsConstructor
            @Data
            public static class EdgeWebMediaToRelatedMedia {
                private List<EdgesXXX> edges;

                @NoArgsConstructor
                @Data
                public static class EdgesXXX {
                    private NodeXXX node;

                    @NoArgsConstructor
                    @Data
                    public static class NodeXXX {
                        private String shortcode;
                        private String thumbnail_src;
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class EdgeSidecarToChildren {
                private List<EdgesXXXX> edges;

                @NoArgsConstructor
                @Data
                public static class EdgesXXXX {
                    private NodeXXXX node;

                    @NoArgsConstructor
                    @Data
                    public static class NodeXXXX {
                        private String __typename;
                        private String id;
                        private String shortcode;
                        private DimensionsX dimensions;
                        private Object gating_info;
                        private String media_preview;
                        private String display_url;
                        private DashInfo dash_info;
                        private String video_url;
                        private int video_view_count;
                        private Boolean is_video;
                        private Boolean should_log_client_event;
                        private String tracking_token;
                        private EdgeMediaToTaggedUserX edge_media_to_tagged_user;
                        private List<DisplayResources> display_resources;

                        @NoArgsConstructor
                        @Data
                        public static class DimensionsX {
                            private int height;
                            private int width;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class DashInfo {
                            private Boolean is_dash_eligible;
                            private Object video_dash_manifest;
                            private int number_of_qualities;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class EdgeMediaToTaggedUserX {
                            private List<?> edges;
                        }

                        @NoArgsConstructor
                        @Data
                        public static class DisplayResources {
                            private String src;
                            private int config_width;
                            private int config_height;
                        }
                    }
                }
            }

            @NoArgsConstructor
            @Data
            public static class DisplayResourcesX {
                private String src;
                private int config_width;
                private int config_height;
            }
        }
    }
}
