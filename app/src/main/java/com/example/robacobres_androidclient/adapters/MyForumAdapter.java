package com.example.robacobres_androidclient.adapters;
//
//import android.content.Context;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//
//import com.example.robacobres_androidclient.callbacks.ForumCallback;
//import com.example.robacobres_androidclient.models.Forum;
//import com.example.robacobres_androidclient.services.ServiceBBDD;
//
//import java.util.List;
//
//public class MyForumAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
//    private List<Forum> messages;
//    private Context context;
//    private ServiceBBDD service;
//    private String username;
//    private ForumCallback characterCallback;
//
//    public MyForumAdapter(Context context, List<Forum> messages, String username, ForumCallback callback) {
//        this.context = context;
//        this.messages = messages;
//        this.username = username;
//        this.characterCallback = callback;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        // Declara els components visuals del layout
//        TextView forumTitle;
//        TextView forumMessage;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            // Associa els components visuals amb els IDs del layout `row_forum_layout`
//            forumTitle = itemView.findViewById(R.id.forum_title); // Exemple d'ID
//            forumMessage = itemView.findViewById(R.id.forum_message); // Exemple d'ID
//        }
//    }
//
//    @Override
//    public MyForumAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // Infla el layout de la fila
//        View view = LayoutInflater.from(context).inflate(R.layout.row_forum_layout, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(MyForumAdapter.ViewHolder holder, int position) {
//        // Obté l'objecte actual de la llista
//        Forum forum = messages.get(position);
//
//        // Assigna els valors a les vistes
//        holder.forumTitle.setText(forum.getTitle());
//        holder.forumMessage.setText(forum.getMessage());
//
//        // Exemples: Interacció amb clics
//        holder.itemView.setOnClickListener(v -> {
//            if (characterCallback != null) {
//                characterCallback.onForumSelected(forum); // Crida el callback
//            }
//        });
//    }
//}

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
import com.example.robacobres_androidclient.models.Forum;

import java.util.List;

public class MyForumAdapter extends RecyclerView.Adapter<MyForumAdapter.ViewHolder> {

    private List<Forum> messages; // Llista de missatges del fòrum
    private Context context;      // Context de l'activitat
    private ForumCallback callback; // Callback per gestionar esdeveniments

    // Constructor de l'adaptador
    public MyForumAdapter(Context context, List<Forum> messages, ForumCallback callback) {
        this.context = context;
        this.messages = messages;
        this.callback = callback;
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
        Forum forum = messages.get(position); // Obté l'element en funció de la posició
        holder.userName.setText(forum.getName());
        holder.comentario.setText(forum.getComentario());
    }

    @Override
    public int getItemCount() {
        return messages.size(); // Nombre d'elements a la llista
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

    public void setForumMessages(List<Forum> newMessages) {
        this.messages = newMessages; // Actualitza la llista
        notifyDataSetChanged(); // Notifica al RecyclerView que actualitzi les dades
    }

}
