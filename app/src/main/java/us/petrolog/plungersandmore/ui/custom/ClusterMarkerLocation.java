package us.petrolog.plungersandmore.ui.custom;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

import us.petrolog.plungersandmore.model.Well;

/**
 * Created by Vazh on 10/3/2016.
 */
public class ClusterMarkerLocation implements ClusterItem {

    private LatLng position;
    private Well well;
    private String wellKey;

    public ClusterMarkerLocation(LatLng position, Well well, String wellKey) {
        this.position = position;
        this.well = well;
        this.wellKey = wellKey;
    }

    @Override
    public LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    public Well getWell() {
        return well;
    }

    public void setWell(Well well) {
        this.well = well;
    }

    public String getWellKey() {
        return wellKey;
    }

    public void setWellKey(String wellKey) {
        this.wellKey = wellKey;
    }
}