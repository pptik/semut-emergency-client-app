package id.pptik.semut.emergencyreport.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import id.pptik.semut.emergencyreport.R;
import id.pptik.semut.emergencyreport.models.Emergency;


public class EmergencyDetailFragment extends Fragment {

    private Emergency emergency;
    private ImageView mImageProfile;
    private TextView mTextEmail, mTextPhone;
    private Button mButtonCall, mButtonRoute;
    private RelativeLayout mDetailLayout;


    public void setData(Emergency emergency, RelativeLayout mDetailLayout){
        this.emergency = emergency;
        this.mDetailLayout = mDetailLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergency_detail, container, false);

        mTextEmail = (TextView)view.findViewById(R.id.text_email);
        mTextPhone = (TextView)view.findViewById(R.id.text_phone);
        mButtonCall = (Button)view.findViewById(R.id.button_phone);
        mImageProfile = (ImageView)view.findViewById(R.id.profile_image);
        mButtonRoute = (Button)view.findViewById(R.id.button_route);

        mImageProfile.setImageDrawable(new IconicsDrawable(getActivity())
        .color(getActivity().getResources().getColor(R.color.colorPrimaryBold))
        .sizeDp(48)
        .icon(GoogleMaterial.Icon.gmd_account_circle));
        mTextEmail.setText(
                Html.fromHtml(
                        (emergency.getEmail() == null) ?
                                "<b>Email : </b> Email Tidak tersedia" :
                                "<b>Email : </b>"+emergency.getEmail()
                )
        );

        mTextPhone.setText(
                Html.fromHtml(
                        (emergency.getPhoneNumber() == null) ?
                                "<b>Phone : </b> Nomor telepon Tidak tersedia" :
                                "<b>Phone : </b>"+emergency.getPhoneNumber()
                )
        );


        if(emergency.getPhoneNumber() == null) mButtonCall.setVisibility(View.GONE);
        else mButtonCall.setOnClickListener( v -> intentcall());

        mButtonRoute.setOnClickListener(v ->{
            Uri gmmIntentUri = Uri.parse("google.navigation:q="+emergency.getLatitude()+","+emergency.getLongitude());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
            }else Toast.makeText(getActivity(), "Perangkat Anda tidak terinstall Google Map", Toast.LENGTH_LONG).show();
        });

        mDetailLayout.setOnClickListener(view1 -> mDetailLayout.setVisibility(View.GONE));
        return view;
    }

    private void intentcall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+emergency.getPhoneNumber()));
        startActivity(intent);
    }
}
