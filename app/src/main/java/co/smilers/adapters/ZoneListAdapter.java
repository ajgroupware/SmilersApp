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
import android.widget.QuickContactBadge;
import android.widget.TextView;

import java.util.List;

import co.smilers.R;
import co.smilers.StartZoneActivity;
import co.smilers.fragments.SelectCampaignFragment;
import co.smilers.model.Headquarter;
import co.smilers.model.User;
import co.smilers.model.Zone;
import co.smilers.model.data.daos.ParameterDAO;
import co.smilers.model.data.daos.UserDAO;
import co.smilers.ui.DialogConfirmFragment;

public class ZoneListAdapter extends RecyclerView.Adapter<ZoneListAdapter.ViewHolder>{

    private static final String TAG = ZoneListAdapter.class.getSimpleName();

    private List<Zone> mDataset;
    private Context context;

    public ZoneListAdapter(Context mContext, List<Zone> mDataset) {
        Log.d(TAG, "--ZoneListAdapter");
        this.mDataset = mDataset;
        this.context = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.item_zone, null);
        // create ViewHolder
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final Zone zone = mDataset.get(i);
        viewHolder.name.setText(zone.getName() + "/" +zone.getHeadquarter().getName()  );

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogConfirmFragment dialogFragment = DialogConfirmFragment.newInstance(new DialogConfirmFragment.OnButtonClickDialogListener() {
                    @Override
                    public void onAcceptClick(DialogConfirmFragment dialogFragment) {
                        Intent intent = new Intent(context, StartZoneActivity.class);
                        intent.putExtra(SelectCampaignFragment.ARG_HEADQUARTER, zone.getHeadquarter().getCode());
                        intent.putExtra(SelectCampaignFragment.ARG_ZONE, zone.getCode());

                        ParameterDAO parameterDAO = new ParameterDAO(context);
                        UserDAO userDAO = new UserDAO(context);
                        User user = userDAO.getUserLogin();
                        parameterDAO.addCurrentConfig(user.getAccount().getCode(), zone.getHeadquarter().getCode(), zone.getCode()); // Ingresar configuración

                        ((AppCompatActivity)context).startActivity(intent);
                    }

                    @Override
                    public void onCancelClick(DialogConfirmFragment dialogFragment) {

                    }
                });
                //dialogFragment.text.setText("¿Desea iniciar campañas en la Sede " + zone.getHeadquarter().getName() + ", Zona " + zone.getName() +"?");
                dialogFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), DialogConfirmFragment.TAG);
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
