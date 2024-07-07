package com.example.rickymorty.views.detailspage;

import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.example.rickymorty.R;
import com.example.rickymorty.api.APIRequest;
import com.example.rickymorty.databinding.ActivityCharacterDetailsBinding;
import com.example.rickymorty.model.Character;
import com.example.rickymorty.utils.BaseActivity;
import com.example.rickymorty.utils.FavoriteManager;

import org.json.JSONException;
import org.json.JSONObject;

public class CharacterDetailsActivity extends BaseActivity {
    ActivityCharacterDetailsBinding binding;
    private FavoriteManager favoriteManager;
    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCharacterDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize favoriteManager
        favoriteManager = new FavoriteManager(getApplicationContext());

        // Retrieve the Character object from the Intent
        character = getIntent().getParcelableExtra("character");

        if (character != null) {
            // Check if the character is a favorite and update the UI accordingly
            updateFavoriteIcon();
            fetchCharacterDetails(character.getId());

            // Set click listener for the favorite icon
            binding.favoriteIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favoriteManager.isFavorite(character.getId())) {
                        favoriteManager.removeFavorite(character);
                        character.setFavorite(false);
                        binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24); // Unfavorite icon
                    } else {
                        favoriteManager.addFavorite(character);
                        character.setFavorite(true);
                        binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_red_24); // Favorite icon
                    }
                }
            });
        }
    }

    private void updateFavoriteIcon() {
        if (favoriteManager.isFavorite(character.getId())) {
            binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_red_24); // Favorite icon
        } else {
            binding.favoriteIcon.setImageResource(R.drawable.baseline_favorite_border_24); // Unfavorite icon
        }
    }

    private void fetchCharacterDetails(String characterId) {
        APIRequest apiRequest = new APIRequest(getApplicationContext());
        apiRequest.setMethod(Request.Method.GET);
        apiRequest.setPath("/" + characterId);
        apiRequest.executeRequest(new APIRequest.VolleyCallback() {
            @Override
            public void getSuccessResponse(JSONObject response, String message) {
                try {
                    String name = response.getString("name");
                    String species = response.getString("species");
                    String type = response.getString("type");
                    String gender = response.getString("gender");
                    String origin = response.getJSONObject("origin").getString("name");
                    String location = response.getJSONObject("location").getString("name");
                    String imageUrl = response.getString("image");

                    binding.characterName.setText(name);
                    binding.species.setText(species);
                    binding.type.setText(type);
                    binding.gender.setText(gender);
                    binding.origin.setText(origin);
                    binding.address.setText(location);
                    Glide.with(CharacterDetailsActivity.this)
                            .load(imageUrl)
                            .error(R.drawable.img) // Placeholder image
                            .into(binding.profileImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getErrorResponse(String error) {
                // Handle the error
            }
        });
    }
}
