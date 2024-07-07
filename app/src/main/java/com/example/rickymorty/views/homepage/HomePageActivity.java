package com.example.rickymorty.views.homepage;

import android.content.Intent;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.example.rickymorty.R;
import com.example.rickymorty.adapter.CharacterListAdapter;
import com.example.rickymorty.api.APIRequest;
import com.example.rickymorty.databinding.ActivityHomePageBinding;
import com.example.rickymorty.model.Character;
import com.example.rickymorty.utils.BaseActivity;
import com.example.rickymorty.utils.Constants;
import com.example.rickymorty.views.detailspage.CharacterDetailsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomePageActivity extends BaseActivity implements CharacterListAdapter.OnItemClickListener {

    ActivityHomePageBinding binding;
    CharacterListAdapter adapter;
    List<Character> characterList = new ArrayList<>();
    String nextUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.listCharacter.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new CharacterListAdapter(this, characterList, this);
        binding.listCharacter.setAdapter(adapter);

        getCharacters(Constants.BASE_URL + "character");

        binding.listCharacter.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == characterList.size() - 1) {
                    if (nextUrl != null) {
                        getCharacters(nextUrl);
                    }
                }
            }
        });
    }

    private void getCharacters(String url) {
        showProgressWheel();
        APIRequest apiRequest = new APIRequest(getApplicationContext());
        apiRequest.setMethod(Request.Method.GET);
        apiRequest.setCustomUrl(url);

        apiRequest.executeRequest(new APIRequest.VolleyCallback() {
            @Override
            public void getSuccessResponse(JSONObject response, String message) {
                hideProgressWheel(false);
                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject characterObject = results.getJSONObject(i);
                        Character character = new Character(
                                characterObject.getString("id"),
                                characterObject.getString("name"),
                                characterObject.getString("status"),
                                characterObject.getString("image"),
                                characterObject.getJSONObject("origin").getString("name"),
                                characterObject.getJSONObject("location").getString("name")
                        );
                        characterList.add(character);
                    }
                    JSONObject info = response.getJSONObject("info");
                    nextUrl = info.optString("next", null);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void getErrorResponse(String error) {
                hideProgressWheel(false);
                // Handle error
            }
        });
    }

    @Override
    public void onItemClick(Character character) {
        Intent intent = new Intent(HomePageActivity.this, CharacterDetailsActivity.class);
        intent.putExtra("character", character);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProgressWheel();
        getCharacters(Constants.BASE_URL + "character");
    }
}
