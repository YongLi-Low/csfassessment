package ibf2022.batch2.csf.backend.models;

import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Archive {
    private String bundleId;
    private String date;
    private String name;
    private String title;
    private String comments;
    private List<String> urls;

    public String getBundleId() {
        return bundleId;
    }
    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> url) {
        this.urls = url;
    }

    public JsonObject toJSON() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        
        for (String url: getUrls()) {
            arrBuilder.add(url);
        }

        JsonObject obj = Json.createObjectBuilder()
                        .add("bundleId", getBundleId())
                        .add("date", getDate())
                        .add("name", getName())
                        .add("title", getTitle())
                        .add("comments", getComments())
                        .add("urls", arrBuilder)
                        .build();

        return obj;
    }
    
}
