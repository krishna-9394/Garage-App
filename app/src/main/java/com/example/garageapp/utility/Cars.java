package com.example.garageapp.utility;

public class Cars {
    final String MakeName;
    final String ModelName;
    final Integer MakeId;
    final Integer ModelId;
    byte [] Image;

    public Cars(String makeName, String modelName, Integer makeId, Integer modelId,byte [] image) {
        MakeName = makeName;
        ModelName = modelName;
        MakeId = makeId;
        ModelId = modelId;
        Image = image;
    }

    public String getMakeName() {
        return MakeName;
    }

    public void setImage(byte[] image) {
        Image = image;
    }

    public String getModelName() {
        return ModelName;
    }

    public Integer getMakeId() {
        return MakeId;
    }

    public Integer getModelId() {
        return ModelId;
    }
}
