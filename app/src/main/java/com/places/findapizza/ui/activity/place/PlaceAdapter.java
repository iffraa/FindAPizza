package com.places.findapizza.ui.activity.place;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.places.findapizza.R;
import com.places.findapizza.data.network.model.Location;
import com.places.findapizza.data.network.model.OpeningHours;
import com.places.findapizza.data.network.model.Place;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.places.findapizza.utilities.GPSTracker;
import com.places.findapizza.utilities.LogUtility;
import com.places.findapizza.utilities.MapUtility;
import com.squareup.picasso.Picasso;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder> {

    public interface OnPlaceAdapter {
        void onPlaceClicked(Place place);
    }

    private Context context;
    private List<Place> mItems;
    private OnPlaceAdapter mListener;

    public PlaceAdapter(OnPlaceAdapter listener) {
        mListener = listener;
        mItems = new ArrayList<Place>();
    }

    public void setItems(List<Place> items,Context context) {
        this.context = context;
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder holder, int position) {
        Place item = getItem(position);

        holder.setOnClickListener(item);
        holder.setTitle(item.getName());
        holder.setIcon(item.getIcon());
        holder.setDistance(String.valueOf(item.getDistance()));

        OpeningHours openingHours = item.getOpeningHours();
        if(openingHours != null) {
            holder.setStatusIcon(openingHours.getOpenNow());
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private Place getItem(int position) {
        return mItems.get(position);
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.place_icon)
        ImageView icon;

        @BindView(R.id.iv_status)
        ImageView img_status;

        @BindView(R.id.record_name)
        TextView title;

        @BindView(R.id.record_distance)
        TextView distance;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setTitle(String title) {
            this.title.setText(title);
        }

         public void setIcon(String imageUrl) {
            Picasso.get().load(imageUrl).into(icon);

        }

        public void setDistance(String distance)
        {
            this.distance.setText(distance + "km away");
        }

        public void setStatusIcon(boolean isOpen)
        {
            if(isOpen)
            {
                img_status.setImageResource(R.drawable.online_status);
            }
            else
            {
                img_status.setImageResource(R.drawable.offline_status);
            }
        }


        private void setOnClickListener(Place place) {
            itemView.setTag(place);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onPlaceClicked((Place) view.getTag());
        }
    }
}
