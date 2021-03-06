package NewsApp.fragment;

import android.content.Context;
import android.graphics.Color;

import com.example.s1908114.newsapp.MyItem;
import com.example.s1908114.newsapp.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by Amir on 05.01.2016.
 */
//CLASS TO OVERRIDE THE DEFAAULT CLUSTER CREATER  IN city VIEW
class MyclusterRenderer2 extends DefaultClusterRenderer<MyItem> {
    private Context cntxt;

    public MyclusterRenderer2(Context context, GoogleMap map,
                              ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);

        this.cntxt = context;
    }

    @Override
    // set the snippet and title for items
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));

        super.onBeforeClusterItemRendered(item, markerOptions);
        markerOptions.title(item.getTitle());
        markerOptions.snippet(item.getSnippet());

    }

    @Override
    //override the before renderer of the clusters to get the popup text of city name on top
    protected void onBeforeClusterRendered(Cluster<MyItem> cluster, MarkerOptions markerOptions) {

        super.onBeforeClusterRendered(cluster, markerOptions);
        for (MyItem item : cluster.getItems()) {
            IconGenerator iconFactorya = new IconGenerator(cntxt);
            addIcon(iconFactorya, item.getPlace(), item.getPosition());
        }
    }
    // city name infowindows shown as marker
    private void addIcon(IconGenerator iconFactory, String text, LatLng position) {
        MarkerOptions markerOptions = new MarkerOptions().
                icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(text))).
                position(position).
                anchor(0.5f, 2f);

        MainFragment.mMap.addMarker(markerOptions);
    }

    @Override
    protected void onClusterItemRendered(MyItem clusterItem, Marker marker) {
        super.onClusterItemRendered(clusterItem, marker);

        //here you have access to the marker itself
    }

    @Override
    protected void onClusterRendered(Cluster<MyItem> cluster, Marker marker) {
        super.onClusterRendered(cluster, marker);
         //here you have access to the marker itself
    }

    @Override
    // render all as clusters even with 1 item
    protected boolean shouldRenderAsCluster(Cluster<MyItem> cluster) {
        //start clustering if at least 2 items overlap cluster.getSize() > 1 && MainFragment.zoom <5
        return true;
    }

    @Override
    //setting the color for the marker clusters based on there relevance
    protected int getColor(int clusterSize) {
        final float hueRange = 20;
        final float sizeRange = 300;
        final float size = Math.min(clusterSize, sizeRange);
        final float hue = (sizeRange - size) * (sizeRange - size) / (sizeRange * sizeRange) * hueRange;
        return Color.HSVToColor(new float[]{
                hue, 1f, .6f
        });
    }

}
