package id.pptik.semut.emergencyreport.adapters;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;

import id.pptik.semut.emergencyreport.DetailMapActivity;
import id.pptik.semut.emergencyreport.R;
import id.pptik.semut.emergencyreport.models.Emergency;
import id.pptik.semut.emergencyreport.setup.Constants;

public class EmergencyAdapter extends RecyclerView.Adapter<EmergencyAdapter.ViewHolder> {

    private ArrayList<Emergency> emergencies =  new ArrayList<Emergency>();

    public EmergencyAdapter(ArrayList<Emergency> emergencies){
        this.emergencies = emergencies;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_emergency, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Emergency emergency = emergencies.get(position);
        String tmp = "<b>Nama : </b>"+emergency.getName();
        tmp += "<br /><b>Jenis : </b>"+emergency.getEmergencyType();
        tmp += "<br /><b>Tanggal : </b>"+emergency.getDate();
        holder.mTextDetail.setText(Html.fromHtml(tmp));
        if(emergency.getPhoneNumber() == null) holder.mButtonPhone.setVisibility(View.GONE);
        holder.mImageProfile.setImageDrawable(new
                IconicsDrawable(holder.mImageProfile.getContext())
        .color(holder.mImageProfile.getResources().getColor(R.color.colorPrimaryDark))
        .icon(GoogleMaterial.Icon.gmd_info_outline)
        .sizeDp(50));
        holder.mButtonMap.setOnClickListener(view -> {
            Intent intent = new Intent(holder.mButtonMap.getContext(), DetailMapActivity.class);
            intent.putExtra(Constants.INTENT_JSON_EMERGENCY, new Gson().toJson(emergency));
            holder.mButtonMap.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return emergencies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageProfile;
        public TextView mTextDetail;
        public Button mButtonPhone, mButtonMap;

        public ViewHolder(View v) {
            super(v);
            mImageProfile = (ImageView) v.findViewById(R.id.image_profile);
            mTextDetail = (TextView)v.findViewById(R.id.text_detail);
            mButtonPhone = (Button)v.findViewById(R.id.button_telepon);
            mButtonMap = (Button)v.findViewById(R.id.button_map);


        }
    }
}
