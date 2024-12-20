package com.example.cedricrecyclerview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// CharacterAdapter extends from RecyclerView.Adapter
// This class responsible for connecting the data to the view (RecyclerView).
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.MyViewHolder> {
    /* dataSet Holds the data we want to display in the RecyclerView.
    Defined as an ArrayList of CharacterModel objects, which are the characters' information (including name, description and image). */
    private ArrayList<CharacterModel> dataSet; // the array of characters we built
    private ArrayList<CharacterModel> copyDataSet; // copy of the array

    // The constructor receives a list of characters and stores it for use in the Adapter
    public CharacterAdapter(ArrayList<CharacterModel> dataSet) {
        this.copyDataSet = new ArrayList<>(dataSet);  // keep the original characters (For example, if we want to return all characters as soon as the search is deleted)
        this.dataSet = dataSet;
    }

    /* The inner class MyViewHolder
    Role: Holds references to the display elements (TextView, ImageView) that appear in each row of the RecyclerView.
    When creating a new row (new ViewHolder), it looks for view elements by their id.*/
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;
        TextView textViewDescription;
        ImageView imageView;

        // Pulls out all the organs that exist inside the card according to id
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.characterName);
            textViewDescription = itemView.findViewById(R.id.characterDescription);
            imageView = itemView.findViewById(R.id.characterImage);
        }
    }

    /*Lay out the map for us on top of our line.
    The function Creates a new view for each row in the RecyclerView.*/
    public CharacterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_character, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    /* Build one row inside our recyclerView
    The function connects data to a specific row in the RecyclerView.
    According to the position, extracts information from the list (dataSet) and updates the TextView and the image in the ViewHolder.*/
    @Override
    public void onBindViewHolder(@NonNull CharacterAdapter.MyViewHolder holder, int position) {

        holder.textViewName.setText(dataSet.get(position).getName());
        holder.textViewDescription.setText(dataSet.get(position).getDescription());
        holder.imageView.setImageResource(dataSet.get(position).getImageId());

        // Handle clicking on a row- show a message
        holder.itemView.setOnClickListener(v -> {
            String message = "Clicked: " + dataSet.get(position).getName();
            Toast.makeText(v.getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    /*How many members in total will be in our adapter (which is the size of the array it received).
    in this way the adapter knows how many lines to display.*/
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // The function Performs the filtering and updates the list of characters displayed in the RecyclerView.
    public void filterData(String query) {
        ArrayList<CharacterModel> filteredList = new ArrayList<>();

        // if the field is empty- do not search
        if (query.isEmpty()) {
            filteredList.addAll(copyDataSet);
        } else {
            // filter the character by name
            for (CharacterModel character : copyDataSet) {
                if (character.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(character);
                }
            }
        }
        // update the adapter with the new dataset
        dataSet.clear();
        dataSet.addAll(filteredList);
        /* update the RecyclerView
        When we make changes to the data set (dataSet) associated with the Adapter, we must call this function to update the view*/
        notifyDataSetChanged();
    }
}


