package id.pptik.semut.emergencyreport;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.semut.emergencyreport.models.Emergency;
import id.pptik.semut.emergencyreport.setup.Constants;
import id.pptik.semut.emergencyreport.ui.CommonAlerts;


public class DetailMapActivity extends AppCompatActivity {

    @BindView(R.id.mapView)
    MapView mMap;


    private Context mContext;
    private final String TAG = this.getClass().getSimpleName();
    private IMapController mapController;
    private Intent mIntent;
    private Emergency mEmergency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        mContext = this;
        mIntent = getIntent();
        String emergencyString = (mIntent.getStringExtra(Constants.INTENT_JSON_EMERGENCY) == null) ? "" : mIntent.getStringExtra(Constants.INTENT_JSON_EMERGENCY);
        if(emergencyString.equals("")){
            // empty
            CommonAlerts.commonError(mContext, "Data Kosong");
        }else {
            mEmergency = new Gson().fromJson(emergencyString, Emergency.class);
            initMap();
        }
    }

    private void initMap(){
        mMap.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMap.setMultiTouchControls(true);
        mapController = mMap.getController();
        mapController.setZoom(25);
        mapController.animateTo(new GeoPoint(mEmergency.getLatitude(), mEmergency.getLongitude()));
        Marker marker = new Marker(mMap);
        marker.setPosition(new GeoPoint(mEmergency.getLatitude(), mEmergency.getLongitude()));
        marker.setTitle(mEmergency.getName());
    }

}
