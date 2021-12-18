package com.thenextbiggeek.fampayextern;

public class CardImage {
    private String image_type, asset_type, image_url;

    public CardImage(String image_type){
        this.image_type = image_type;
    }
    public CardImage(String image_type, String secondaryParam){
        if(image_type.equals("asset")){
            this.image_type = image_type;
            this.asset_type = secondaryParam;
        }else{
            this.image_type = image_type;
            this.image_url = secondaryParam;
        }
    }

    public String getAsset_type() {
        return asset_type;
    }

    public String getImage_type() {
        return image_type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
