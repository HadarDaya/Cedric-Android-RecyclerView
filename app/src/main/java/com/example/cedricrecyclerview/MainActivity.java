package com.example.cedricrecyclerview;

import android.os.Bundle;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<CharacterModel> dataSet;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private CharacterAdapter adapter;
    private CharacterAdapter adapterChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        /* dataSet is a new ArrayList object that will contain all the characters we want to display in the RecyclerView.
        Each object in the dataset will be a CharacterModel object that includes the character's name, description, and nickname.*/
        dataSet = new ArrayList<>();
        recyclerView =  findViewById(R.id.recyclerViewResult); // initialization to recyclerView
        layoutManager = new LinearLayoutManager(this); // LinearLayoutManager responsible for arranging the items in the RecyclerView in a linear fashion (by vertical or horizontal order).
        recyclerView.setLayoutManager(layoutManager); //  setLayoutManager determines how the items will be displayed in the RecyclerView.

        /* setItemAnimator determines the animation to be performed when items in the RecyclerView are added or removed.
        DefaultItemAnimator specifies to use default animations, such as adding and removing items.*/
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Adding characters to the dataSet
        for ( int i =0 ; i < CharactersData.namesArray.length ; i++){
            dataSet.add(new CharacterModel(
                    CharactersData.namesArray[i],
                    CharactersData.descriptionsArray[i],
                    CharactersData.charactersImageArray[i]
            ));
        }

        // Setting up the Adapter of the RecyclerView
        adapter = new CharacterAdapter(dataSet);
        recyclerView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchCharacter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // when we writing for search and click enter- do filtering
                adapter.filterData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // when we  change the value for search - do filtering
                adapter.filterData(newText);
                return false;
            }
        });
    }
}