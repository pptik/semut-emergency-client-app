package id.pptik.semut.emergencyreport.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import id.pptik.semut.emergencyreport.R;
import id.pptik.semut.emergencyreport.models.Emergency;


public class EmergencyDetailFragment extends Fragment {

    private Emergency emergency;
    private ImageView mImageProfile;
    private TextView mTextEmail, mTextPhone;
    private Button mButtonCall;
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
        mImageProfile = (ImageView)view.findViewById(R.id.image_profile);


        mDetailLayout.setOnClickListener(view1 -> mDetailLayout.setVisibility(View.GONE));
        return view;
    }
}
