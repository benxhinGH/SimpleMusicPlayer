package com.usiel.simplemusicplayer.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.usiel.simplemusicplayer.R;
import com.usiel.simplemusicplayer.entity.Song;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LocalMusicRvAdapter extends RecyclerView.Adapter<LocalMusicRvAdapter.ViewHolder> {

    private Context context;

    private OnItemClickListener onItemClickListener;

    private List<Song> songs;

    public LocalMusicRvAdapter(Context context){
        this.context=context;
    }

    public void setSongs(List<Song> songs){
        this.songs=songs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_rv_local_music,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.tvMusicName.setText(songs.get(position).getName());
        holder.tvSingerName.setText(songs.get(position).getSingerName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(position);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemLongClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs==null?0:songs.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public List<Song> getSongs(){
        return songs;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_music_name)
        TextView tvMusicName;
        @BindView(R.id.tv_singer_name)
        TextView tvSingerName;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
