package co.smilers.adapters;

import android.content.Context;
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
import co.smilers.fragments.HeadquarterFragment;
import co.smilers.fragments.ZoneFragment;
import co.smilers.model.Headquarter;

public class HeadquarterListAdapter extends RecyclerView.Adapter<HeadquarterListAdapter.ViewHolder>{

    private static final String TAG = HeadquarterListAdapter.class.getSimpleName();

    private List<Headquarter> mDataset;
    private Context context;

    public HeadquarterListAdapter(Context mContext, List<Headquarter> mDataset) {
        Log.d(TAG, "--HeadquarterListAdapter");
        this.mDataset = mDataset;
        this.context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_headquarter, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Headquarter headquarter = mDataset.get(i);
        viewHolder.name.setText(headquarter.getName() + "/" + headquarter.getCity().getName());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZoneFragment zoneFragment = ZoneFragment.newInstance(headquarter.getCode());
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, zoneFragment).commit();
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
