package com.albayrak.api.composite.anime;

public class ServiceAddress {

    private String compositeAddress;
    private String animeAddress;
    private String reviewAddress;
    private String recommendationAddress;

    public ServiceAddress(String compositeAddress, String animeAddress, String reviewAddress, String recommendationAddress) {
        this.compositeAddress = compositeAddress;
        this.animeAddress = animeAddress;
        this.reviewAddress = reviewAddress;
        this.recommendationAddress = recommendationAddress;
    }

    public ServiceAddress(){
        this.compositeAddress = null;
        this.animeAddress = null;
        this.reviewAddress = null;
        this.recommendationAddress = null;
    }

    public void setCompositeAddress(String compositeAddress) {
        this.compositeAddress = compositeAddress;
    }

    public void setAnimeAddress(String animeAddress) {
        this.animeAddress = animeAddress;
    }

    public void setReviewAddress(String reviewAddress) {
        this.reviewAddress = reviewAddress;
    }

    public void setRecommendationAddress(String recommendationAddress) {
        this.recommendationAddress = recommendationAddress;
    }

    public String getCompositeAddress() {
        return this.compositeAddress;
    }

    public String getAnimeAddress() {
        return this.animeAddress;
    }

    public String getReviewAddress() {
        return this.reviewAddress;
    }

    public String getRecommendationAddress() {
        return this.recommendationAddress;
    }
}
