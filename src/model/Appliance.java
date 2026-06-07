package model;

public class Appliance {

    private int applianceId;
    private String applianceName;
    private double powerRating;
    private String category;

    public Appliance() {

    }

    public Appliance(
            int applianceId,
            String applianceName,
            double powerRating,
            String category
    ) {

        this.applianceId = applianceId;
        this.applianceName = applianceName;
        this.powerRating = powerRating;
        this.category = category;
    }

    public int getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(int applianceId) {
        this.applianceId = applianceId;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(
            String applianceName
    ) {
        this.applianceName = applianceName;
    }

    public double getPowerRating() {
        return powerRating;
    }

    public void setPowerRating(
            double powerRating
    ) {
        this.powerRating = powerRating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(
            String category
    ) {
        this.category = category;
    }

    @Override
    public String toString() {

        return applianceName;
    }
}