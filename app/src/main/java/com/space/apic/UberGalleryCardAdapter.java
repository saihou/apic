package com.space.apic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tankangsoon on 28/2/16.
 */
public class UberGalleryCardAdapter extends RecyclerView.Adapter<UberGalleryCardAdapter.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView caption;
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            caption = (TextView) itemView.findViewById(R.id.caption);
            image = (ImageView) itemView.findViewById(R.id.image);
        }
    }

    private List<ChallengeCardData> data;

    public UberGalleryCardAdapter(List<ChallengeCardData> data) {
        this.data = data;
    }

    @Override
    public UberGalleryCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View uberCard = inflater.inflate(R.layout.uber_gallery_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(uberCard);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UberGalleryCardAdapter.ViewHolder viewHolder, int position) {
        ChallengeCardData cardData = data.get(position);
        TextView textView = viewHolder.caption;
        textView.setText(cardData.getCaption());
        textView.setTextColor(Color.WHITE);
        ImageView cardImage = viewHolder.image;
        try {
            int picture = Integer.parseInt(cardData.getPicture());
            Bitmap bitmap = BitmapFactory.decodeResource(textView.getContext().getResources(),picture);
            int height = bitmap.getHeight();
            int width = bitmap.getWidth();
            int scale = 2;
            if (width > 800) {
                height = height/scale;
                width = width/scale;
            }
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
            cardImage.setImageBitmap(scaledBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount(){
        return data.size();
    }
}
