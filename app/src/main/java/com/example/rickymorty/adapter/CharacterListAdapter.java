package com.example.rickymorty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickymorty.R;
import com.example.rickymorty.model.Character;
import com.example.rickymorty.utils.FavoriteManager;

import java.util.List;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder> {

    private final Context context;
    private List<Character> characterList;
    private final OnItemClickListener onItemClickListener;
    private FavoriteManager favoriteManager;

    public interface OnItemClickListener {
        void onItemClick(Character character);
    }

    public CharacterListAdapter(Context context, List<Character> characterList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.characterList = characterList;
        this.onItemClickListener = onItemClickListener;
        this.favoriteManager = new FavoriteManager(context);
    }


    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.character_list, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characterList.get(position);

        if (favoriteManager.isFavorite(character.getId())) {
            holder.favoriteIcon.setImageResource(R.drawable.baseline_favorite_red_24);
        } else {
            holder.favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24);
        }

        if (character.getImageUrl() != null && !character.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(character.getImageUrl())
                    .error(R.drawable.img) // Fallback image resource
                    .into(holder.characterImage);
        } else {
            holder.characterImage.setImageResource(R.drawable.img); // Set default image if URL is null or empty
        }

        holder.characterName.setText(character.getName());
        holder.characterStatus.setText(character.getStatus());

        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(character));
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class CharacterViewHolder extends RecyclerView.ViewHolder {
        final ImageView characterImage;
        final ImageView favoriteIcon;
        final TextView characterName;
        final TextView characterStatus;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            characterImage = itemView.findViewById(R.id.character_img);
            characterName = itemView.findViewById(R.id.character_name);
            characterStatus = itemView.findViewById(R.id.character_status);
            favoriteIcon = itemView.findViewById(R.id.favorite_icon);
        }
    }
}
