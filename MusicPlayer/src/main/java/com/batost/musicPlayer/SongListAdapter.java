package com.batost.musicPlayer;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;


public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder>{

    private final ArrayList<AudioModel> songsList;
    private final Context context;
    private final ContentResolver contentResolver;

    public SongListAdapter(ArrayList<AudioModel> songsList, Context context) {
        this.songsList = songsList;
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_song_item, parent, false);
        return new SongListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AudioModel aSong = songsList.get(position);
        holder.titleTextView.setText(aSong.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SongPlayer.getInstance().reset();
                SongPlayer.currentIndex = position;

                Intent intent = new Intent(context, SongPlayerActivity.class);
                intent.putExtra("SONGS", songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });

        holder.optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu optionsMenu = new PopupMenu(context, v);
                optionsMenu.getMenuInflater().inflate(R.menu.options_menu, optionsMenu.getMenu());
                optionsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_option:

//                              ActivityResultLauncher<IntentSenderRequest>
                                String filePath2 = songsList.get(position).getPath();
                                ArrayList<Uri> uri2 = new ArrayList<>();
                                uri2.add(FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(filePath2)));

                                try {
                                    contentResolver.delete(uri2.get(0), null, null);
                                } catch (SecurityException e) {
                                    Toast.makeText(context, "File Could Not Deleted", Toast.LENGTH_SHORT).show();
                                }
//                               try {
//                                    context.getContentResolver().delete(uri2.get(0), null, null);
//                                } catch (SecurityException e) {
//                                    IntentSender intentSender;
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                                        MediaStore.createDeleteRequest(context.getContentResolver(), uri2);
//                                    }
//                                }

//                                try {
//                                    File file = new File(songsList.get(position).getPath());
//                                    if(file.exists())
//                                        file.delete();
//                                }
//                                catch (Exception e) {
//                                    Log.e("App", "Exception while deleting file " + e.getMessage());
//                                }
//                                File f = new File(songsList.get(position).getPath());
//                                if (!f.delete() && f.exists()) {
//                                    try {
//                                        throw new IOException("failed to delete " + f);
//                                    } catch (IOException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                                boolean deleted = f.delete();
//
//                                if (deleted) {
//                                    Toast.makeText(context, "File Deleted Successfully", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(context, "File Could Not Deleted", Toast.LENGTH_SHORT).show();
//                                }
//                                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.share_option:
                                String filePath = songsList.get(position).getPath();
                                Uri uri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(filePath));
//                                Uri uri = Uri.fromFile(new File(filePath));
                                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                shareIntent.setType("audio/*");
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
//                                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                shareIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                context.startActivity(Intent.createChooser(shareIntent, "Share Music"));
                                context.startActivity(shareIntent);
                                Toast.makeText(context, "Share", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
                optionsMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        ImageView iconImage;
        ImageButton optionsButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.music_name_text);
            iconImage = itemView.findViewById(R.id.music_icon);
            optionsButton = itemView.findViewById(R.id.options_icon);
        }
    }
}
