package co.smilers.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.model.Campaign;
import co.smilers.model.Zone;
import co.smilers.ui.DialogConfirmFragment;

public class CampaignListAdapter extends RecyclerView.Adapter<CampaignListAdapter.ViewHolder>{

    private static final String TAG = CampaignListAdapter.class.getSimpleName();

    private List<Campaign> mDataset;
    private Context context;

    public CampaignListAdapter(Context mContext, List<Campaign> mDataset) {
        Log.d(TAG, "--CampaignListAdapter");
        this.mDataset = mDataset;
        this.context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_campaign, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Campaign campaign = mDataset.get(i);
        viewHolder.name.setText(campaign.getTitle());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView image;
        public ImageView state;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.event_name);
            image = (ImageView) view.findViewById(R.id.event_image);
            state = (ImageView) view.findViewById(R.id.ImageView_state);
        }
    }
}
