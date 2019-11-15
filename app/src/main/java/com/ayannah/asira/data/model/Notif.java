package com.ayannah.asira.data.model;

import com.google.gson.annotations.SerializedName;
import com.mobsandgeeks.saripaar.annotation.AssertFalse;

import java.util.List;

public class Notif {

    @SerializedName("total_data")
    private int total_data;

    @SerializedName("rows")
    private int rows;

    @SerializedName("current_page")
    private int current_page;

    @SerializedName("last_page")
    private int last_page;

    @SerializedName("from")
    private int from;

    @SerializedName("to")
    private int to;

    @SerializedName("data")
    private List<Data> data;

    public int getTotal_data() {
        return total_data;
    }

    public int getRows() {
        return rows;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data{

        @SerializedName("client_id")
        private int clientId;

        @SerializedName("created_time")
        private String createdTime;

        @SerializedName("firebase_token")
        private String firebase_token;

        @SerializedName("id")
        private String id;

        @SerializedName("message_body")
        private String message_body;

        @SerializedName("recipient_id")
        private String recipient_id;

        @SerializedName("response")
        private String response;

        @SerializedName("send_time")
        private String send_time;

        @SerializedName("title")
        private String title;

        @SerializedName("topic")
        private String topic;

        @SerializedName("updated_time")
        private String updated_time;

        public int getClientId() {
            return clientId;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public String getFirebase_token() {
            return firebase_token;
        }

        public String getId() {
            return id;
        }

        public String getMessage_body() {
            return message_body;
        }

        public String getRecipient_id() {
            return recipient_id;
        }

        public String getResponse() {
            return response;
        }

        public String getSend_time() {
            return send_time;
        }

        public String getTitle() {
            return title;
        }

        public String getTopic() {
            return topic;
        }

        public String getUpdated_time() {
            return updated_time;
        }
    }

}
