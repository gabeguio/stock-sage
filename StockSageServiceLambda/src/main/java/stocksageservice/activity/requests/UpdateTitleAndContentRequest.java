package stocksageservice.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = UpdateTitleAndContentRequest.Builder.class)
public class UpdateTitleAndContentRequest {
    private final String username;
    private final String queryId;
    private final String title;
    private final String content;

    public UpdateTitleAndContentRequest(String username, String queryId, String title, String content) {
        this.username = username;
        this.queryId = queryId;
        this.title = title;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public String getQueryId() {
        return queryId;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "UpdateTitleAndContentRequest{" +
                "username='" + username + '\'' +
                ", queryId='" + queryId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String username;
        private String queryId;
        private String title;
        private String content;

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder withQueryId(String queryId) {
            this.queryId = queryId;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public UpdateTitleAndContentRequest build() {
            return new UpdateTitleAndContentRequest(username, queryId, title, content);
        }
    }
}

