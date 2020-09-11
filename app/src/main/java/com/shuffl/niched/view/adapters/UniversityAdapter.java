package com.shuffl.niched.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.shuffl.niched.R;
import com.shuffl.niched.databinding.ItemUniversityBinding;
import com.shuffl.niched.listeners.UniversityClickListener;
import com.shuffl.niched.model.University;

import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder> {

    List<University> unis;
    UniversityClickListener listener;

    public UniversityAdapter(List<University> unis, UniversityClickListener listener) {
        this.unis = unis;
        this.listener = listener;
    }

    public void updateUniList(List<University> unis){
        if (unis != null && !unis.isEmpty()) {
            this.unis.clear();
            this.unis.addAll(unis);
            notifyDataSetChanged();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUniversityBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_university, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.uniName.setText(unis.get(position).name);
        holder.binding.mainUniLayout.setOnClickListener(view -> listener.universityClicked(unis.get(position)));
    }

    @Override
    public int getItemCount() {
        return unis.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemUniversityBinding binding;

        public ViewHolder(@NonNull ItemUniversityBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
