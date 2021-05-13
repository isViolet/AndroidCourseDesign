package com.example.networkrequest;

import java.util.Date;
import java.util.List;

public class Entity {

    private boolean success;
    private int code;
    private String message;
    private Data data;
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    public void setData(Data data) {
        this.data = data;
    }
    public Data getData() {
        return data;
    }

    public static class Data {

        private List<Items> items;
        public void setItems(List<Items> items) {
            this.items = items;
        }
        public List<Items> getItems() {
            return items;
        }

    }

    public static class Items {

        private String id;
        private String recommendId;
        private String title;
        private String authorId;
        private String cover;
        private int viewCounts;
        private int comments;
        private String duration;
        private boolean isDeleted;
        private Date gmtCreate;
        private Date gmtModified;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setRecommendId(String recommendId) {
            this.recommendId = recommendId;
        }
        public String getRecommendId() {
            return recommendId;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public String getTitle() {
            return title;
        }

        public void setAuthorId(String authorId) {
            this.authorId = authorId;
        }
        public String getAuthorId() {
            return authorId;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
        public String getCover() {
            return cover;
        }

        public void setViewCounts(int viewCounts) {
            this.viewCounts = viewCounts;
        }
        public int getViewCounts() {
            return viewCounts;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }
        public int getComments() {
            return comments;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }
        public String getDuration() {
            return duration;
        }

        public void setIsDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
        }
        public boolean getIsDeleted() {
            return isDeleted;
        }

        public void setGmtCreate(Date gmtCreate) {
            this.gmtCreate = gmtCreate;
        }
        public Date getGmtCreate() {
            return gmtCreate;
        }

        public void setGmtModified(Date gmtModified) {
            this.gmtModified = gmtModified;
        }
        public Date getGmtModified() {
            return gmtModified;
        }

    }

}
