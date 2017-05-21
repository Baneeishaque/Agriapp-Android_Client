package agriapp.studio.ndk.agriappstudio;

/**
 * Created by DK_win10-Asus_3ghz on 31-01-2017.
 */

public class Crop_Modal {
    String id;
    String name;
    String water_availability_low;
    String water_availability_high;
    String Avg_temperature_low;
    String Avg_temperature_high;
    String soil_type;
    String pH_low;
    String pH_high;
    String provider;
    String enigineer_name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWater_availability_low() {
        return water_availability_low;
    }

    public void setWater_availability_low(String water_availability_low) {
        this.water_availability_low = water_availability_low;
    }

    public String getWater_availability_high() {
        return water_availability_high;
    }

    public void setWater_availability_high(String water_availability_high) {
        this.water_availability_high = water_availability_high;
    }

    public String getAvg_temperature_low() {
        return Avg_temperature_low;
    }

    public void setAvg_temperature_low(String avg_temperature_low) {
        Avg_temperature_low = avg_temperature_low;
    }

    public String getAvg_temperature_high() {
        return Avg_temperature_high;
    }

    public void setAvg_temperature_high(String avg_temperature_high) {
        Avg_temperature_high = avg_temperature_high;
    }

    public String getSoil_type() {
        return soil_type;
    }

    public void setSoil_type(String soil_type) {
        this.soil_type = soil_type;
    }

    public String getpH_low() {
        return pH_low;
    }

    public void setpH_low(String pH_low) {
        this.pH_low = pH_low;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpH_high() {
        return pH_high;
    }

    public void setpH_high(String pH_high) {
        this.pH_high = pH_high;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getEnigineer_name() {
        return enigineer_name;
    }

    public void setEnigineer_name(String enigineer_name) {
        this.enigineer_name = enigineer_name;
    }
}
