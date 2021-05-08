package com.albayrak.api.composite.anime;

public class ServiceAddress {

    private final String compositeAddress;
    private final String animeAddress;
    private final String reviewAddress;
    private final String recommendationAddress;

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
