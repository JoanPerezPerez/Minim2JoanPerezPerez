package com.example.robacobres_androidclient.adapters;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.robacobres_androidclient.R;
import com.example.robacobres_androidclient.callbacks.CharacterCallback;
import com.example.robacobres_androidclient.callbacks.ItemCallback;
import com.example.robacobres_androidclient.models.GameCharacter;
import com.example.robacobres_androidclient.models.Item;
import com.example.robacobres_androidclient.services.Service;
import com.example.robacobres_androidclient.services.ServiceBBDD;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Item> items;
    private List<GameCharacter> characters;
    private List<Object> combinedList;
    private Context context;
    private Service service;
    private ServiceBBDD serviceBBDD;
    private String username;
    private boolean isFromDataBase;
    private ItemCallback itemCallback;
    private CharacterCallback characterCallback;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtIdObj;
        public TextView txtNameItem;
        public TextView txtPriceItem;
        public ImageView icon;
        public Button comprar;


        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtIdObj = (TextView) v.findViewById(R.id.itemId);
            txtNameItem = (TextView) v.findViewById(R.id.itemName);
            txtPriceItem = (TextView) v.findViewById(R.id.itemPrice);
            icon=(ImageView) v.findViewById(R.id.icon);
            comprar = (Button) v.findViewById(R.id.Comprar);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Context context,List<Object> combinedList , String username, ItemCallback _itemCallback, CharacterCallback _characterCallback, boolean isFromDataBase) {
        this.context = context;
        this.username = username;
        this.service = Service.getInstance(context);
        this.serviceBBDD = ServiceBBDD.getInstance(context);
        this.itemCallback=_itemCallback;
        this.characterCallback=_characterCallback;
        this.isFromDataBase = isFromDataBase;
        this.combinedList = combinedList;
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
//        // - get element from your dataset at this position
//        // - replace the contents of the view with that element
//        final Item i = items.get(position);
//        holder.txtIdObj.setText(i.getId());
//        holder.txtNameItem.setText(i.getName());
//        holder.txtPriceItem.setText(String.valueOf(i.getCost()));
//        //SI VOLEM FER COSES DE ELIMINAR
//        holder.comprar.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isFromDataBase){
//                    serviceBBDD.userBuys(username, i.getName(),itemCallback); // Llamar al método cuando se presione el botón
//                }
//                else {
//                    service.userBuys(username, i.getName(),itemCallback); // Llamar al método cuando se presione el botón
//                }
//                /* SI VOLGUESSIM FER QUE SOBRIS UNA NOVA PAG AMB INFO
//                // Crear un Intent para abrir la nueva actividad
//                Intent intent = new Intent(context, ActivityInfoSong.class);
//
//                // Pasar los datos del track seleccionado a la nueva actividad
//                intent.putExtra("trackId", track.id);
//                intent.putExtra("trackSinger", track.singer);
//                intent.putExtra("trackName", track.title);
//
//                // Iniciar la nueva actividad
//                context.startActivity(intent);
//                 */
//            }
//        });
//
//        Glide.with(holder.icon.getContext())
//                .load(i.getItem_url())
//                .into(holder.icon);
        Object obj = combinedList.get(position);

        if (obj instanceof Item) {
            Item item = (Item) obj;
            holder.txtIdObj.setText(item.getId());
            holder.txtNameItem.setText(item.getName());
            holder.txtPriceItem.setText(String.valueOf(item.getCost()));

            Glide.with(holder.icon.getContext())
                    .load(item.getItem_url())
                    .into(holder.icon);

            holder.comprar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFromDataBase) {
                        serviceBBDD.userBuysItem(username, item.getName(), itemCallback);
                    } else {
                        service.userBuysItem(username, item.getName(), itemCallback);
                    }
                }
            });

        } else if (obj instanceof GameCharacter) {
            GameCharacter character = (GameCharacter) obj;
            holder.txtIdObj.setText("");
            holder.txtNameItem.setText(character.getName());
            holder.txtPriceItem.setText(String.valueOf(character.getCost()));

            Glide.with(holder.icon.getContext())
                    .load("")
                    .into(holder.icon);

            holder.comprar.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isFromDataBase) {
                        serviceBBDD.userBuysCharacter(username, character.getName(), characterCallback);
                    } else {
                        service.userBuysCharacter(username, character.getName(), characterCallback);
                    }
                }
            });
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (combinedList.get(position) instanceof Item) {
            return 0; // Tipus per a Item
        } else if (combinedList.get(position) instanceof GameCharacter) {
            return 1; // Tipus per a GameCharacter
        }
        return -1;
    }
}
