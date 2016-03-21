package calle.frostcodingtest;


public class Puff {

    private String url, imageUrl, buttonText;

    public Puff(String url, String imageUrl, String buttonText) {
        this.url = url;
        this.imageUrl = imageUrl;
        this.buttonText = buttonText;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() { return imageUrl; }

    public String getButtonText() {
        return buttonText;
    }
}
