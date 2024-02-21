package com.example.isustain_app;

import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class onBoardingAdapter extends RecyclerView.Adapter<onBoardingAdapter.onBoardingAdapterViewholder> {

    private List<OnboardingItem> onboardingItemList;

    public onBoardingAdapter(List<OnboardingItem> onboardingItemList) {
        this.onboardingItemList = onboardingItemList;
    }

    @NonNull
    @Override
    public onBoardingAdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       return new onBoardingAdapterViewholder(
               LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_onboarding,parent,false)

       );
    }

    @Override
    public void onBindViewHolder(@NonNull onBoardingAdapterViewholder holder, int position) {

        holder.setOnboardingData(onboardingItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return onboardingItemList.size();
    }

    class onBoardingAdapterViewholder extends RecyclerView.ViewHolder{
        private TextView textitle;
        private TextView textDescrip;
        private ImageView imageOnboarding;

        public onBoardingAdapterViewholder(View itemView){
            super(itemView);
            textitle = itemView.findViewById(R.id.textTitle);
            textDescrip = itemView.findViewById(R.id.textDescription);
            imageOnboarding = itemView.findViewById(R.id.imageOnboarding);

        }

        void setOnboardingData(OnboardingItem onboardingData){
            textitle.setText(onboardingData.getTitle());
            textDescrip.setText(onboardingData.getDescrip());
            imageOnboarding.setImageResource(onboardingData.getImage());

        }

    }
}
