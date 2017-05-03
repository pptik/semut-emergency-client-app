package id.pptik.semut.emergencyreport;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.semut.emergencyreport.fragments.EmergencyDetailFragment;
import id.pptik.semut.emergencyreport.models.Emergency;
import id.pptik.semut.emergencyreport.setup.Constants;
import id.pptik.semut.emergencyreport.ui.CommonAlerts;


public class DetailMapActivity extends AppCompatActivity implements Marker.OnMarkerClickListener {

    @BindView(R.id.mapView)
    MapView mMap;
    @BindView(R.id.detail_layout)
    RelativeLayout mDetailLayout;


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
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryBold));
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
        marker.setIcon(
                new IconicsDrawable(mContext)
                .icon(GoogleMaterial.Icon.gmd_place)
                .sizeDp(34)
                .color(getResources().getColor(R.color.colorPrimaryDark))
        );
        marker.setOnMarkerClickListener(this);
        mMap.getOverlays().add(marker);
        mMap.invalidate();
    }

    @Override
    public boolean onMarkerClick(Marker marker, MapView mapView) {

        mDetailLayout.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EmergencyDetailFragment emergencyDetailFragment = new EmergencyDetailFragment();
        emergencyDetailFragment.setData(mEmergency, mDetailLayout);
        fragmentTransaction.replace(R.id.detail_layout, emergencyDetailFragment);
        fragmentTransaction.commit();

        return false;
    }
}
