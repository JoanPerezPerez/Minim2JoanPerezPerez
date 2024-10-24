package com.example.robacobres_androidclient;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Item> items;
    private Context context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTrackSinger;
        public TextView txtTrackName;
        public TextView txtTrackId;

        public View layout;


        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtTrackName = (TextView) v.findViewById(R.id.trackName);
            txtTrackSinger = (TextView) v.findViewById(R.id.trackSinger);
            txtTrackId = (TextView) v.findViewById(R.id.trackId);
        }
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context,List<Item> myDataset) {
        this.context = context;
        items = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Item i = items.get(position);
        //holder.txtTrackId.setText(track.id);
        //holder.txtTrackSinger.setText(track.singer);
        //holder.txtTrackName.setText(track.title);

        //SI VOLEM FER COSES DE ELIMINAR
        holder.txtTrackSinger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                // Crear un Intent para abrir la nueva actividad
                Intent intent = new Intent(context, ActivityInfoSong.class);

                // Pasar los datos del track seleccionado a la nueva actividad
                intent.putExtra("trackId", track.id);
                intent.putExtra("trackSinger", track.singer);
                intent.putExtra("trackName", track.title);

                // Iniciar la nueva actividad
                context.startActivity(intent);
                 */
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return items.size();
    }

}
