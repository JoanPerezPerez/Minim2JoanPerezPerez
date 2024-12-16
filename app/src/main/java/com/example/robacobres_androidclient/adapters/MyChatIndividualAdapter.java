package com.example.robacobres_androidclient.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.robacobres_androidclient.R;
import com.example.robacobres_androidclient.callbacks.ForumCallback;
import com.example.robacobres_androidclient.callbacks.PrivateCallback;
import com.example.robacobres_androidclient.models.ChatIndividual;
import com.example.robacobres_androidclient.models.Forum;

import java.util.List;

public class MyChatIndividualAdapter extends RecyclerView.Adapter<MyChatIndividualAdapter.ViewHolder> {

    private Context context;      // Context de l'activitat
    private PrivateCallback callback; // Callback per gestionar esdeveniments
    private List<ChatIndividual> chatIndividual;
    // Constructor de l'adaptador
    public MyChatIndividualAdapter(Context context, PrivateCallback callback, List<ChatIndividual> chatIndividual) {
        this.context = context;
        this.callback = callback;
        this.chatIndividual = chatIndividual;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar el layout de cada fila
        View view = LayoutInflater.from(context).inflate(R.layout.row_forum_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChatIndividual chatIndividual1 = chatIndividual.get(position); // Obté l'element en funció de la posició
        holder.userName.setText(chatIndividual1.getNameFrom());
        holder.comentario.setText(chatIndividual1.getComentario());
    }

    @Override
    public int getItemCount() {
        return chatIndividual.size();
    }

    // Classe ViewHolder per vincular els components visuals
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView comentario;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Associa els components visuals amb els seus IDs
            userName = itemView.findViewById(R.id.userName);
            comentario = itemView.findViewById(R.id.comentario);
            icon = itemView.findViewById(R.id.icon);
        }
    }

    public void setPrivateMessages(List<ChatIndividual> newMessages) {
        this.chatIndividual = newMessages; // Actualitza la llista
        notifyDataSetChanged(); // Notifica al RecyclerView que actualitzi les dades
    }
}
