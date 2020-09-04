package pl.surf.web.demo.model.responses;

import org.springframework.stereotype.Component;

@Component
public class Image {

    private String id;
    private String webContentLink;
    private String webViewLink;

    public String getId() {
        return id;
    }

    public Image setId(String id) {
        this.id = id;
        return this;
    }

    public String getWebContentLink() {
        return webContentLink;
    }

    public Image setWebContentLink(String webContentLink) {
        this.webContentLink = webContentLink;
        return this;
    }

    public String getWebViewLink() {
        return webViewLink;
    }

    public Image setWebViewLink(String webViewLink) {
        this.webViewLink = webViewLink;
        return this;
    }
}
