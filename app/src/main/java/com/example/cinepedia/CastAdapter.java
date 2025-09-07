package com.example.cinepedia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cinepedia.R;
import com.example.cinepedia.CastMember;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private List<CastMember> castMembers;
    private Context context;

    public CastAdapter(Context context, List<CastMember> castMembers) {
        this.context = context;
        this.castMembers = castMembers;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        CastMember castMember = castMembers.get(position);

        holder.castName.setText(castMember.getName());
        holder.castRole.setText(castMember.getRole());

        Glide.with(context)
                .load(castMember.getPhotoUrl())
                .centerCrop()
                .into(holder.castPhoto);
    }

    @Override
    public int getItemCount() {
        return castMembers.size();
    }

    static class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView castPhoto;
        TextView castName;
        TextView castRole;

        CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castPhoto = itemView.findViewById(R.id.castPhoto);
            castName = itemView.findViewById(R.id.castName);
            castRole = itemView.findViewById(R.id.castRole);
        }
    }
}
