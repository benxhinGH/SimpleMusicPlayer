package com.usiel.simplemusicplayer.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.usiel.simplemusicplayer.R;
import com.usiel.simplemusicplayer.adapter.LocalMusicRvAdapter;
import com.usiel.simplemusicplayer.adapter.OnItemClickListener;
import com.usiel.simplemusicplayer.engine.PlayControlCenter;
import com.usiel.simplemusicplayer.entity.PlayList;
import com.usiel.simplemusicplayer.entity.Song;
import com.usiel.simplemusicplayer.tools.MusicScanner;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LocalMusicActivity extends AppCompatActivity {

    private final String title="localmusic";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_local_music)
    RecyclerView rvLocalMusic;

    private LocalMusicRvAdapter adapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_music);
        ButterKnife.bind(this);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initView();
        initData();

    }

    private void initView(){
        rvLocalMusic.setLayoutManager(new LinearLayoutManager(this));
        rvLocalMusic.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        adapter=new LocalMusicRvAdapter(LocalMusicActivity.this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                PlayList playList=new PlayList("123",123l,adapter.getSongs());
                PlayControlCenter.getInstance().setPlayList(playList);
                Intent intent=new Intent(LocalMusicActivity.this,PlayActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {

            }
        });
    }

    private void initData(){
        Observer<List<Song>> observer=new Observer<List<Song>>() {
            @Override
            public void onSubscribe(Disposable d) {
                showProgressDialog();
            }

            @Override
            public void onNext(List<Song> songs) {

                adapter.setSongs(songs);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rvLocalMusic.setAdapter(adapter);
                    }
                });

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(LocalMusicActivity.this, "error", Toast.LENGTH_SHORT).show();
                closeProgressDialog();
            }

            @Override
            public void onComplete() {
                closeProgressDialog();
            }
        };

        Observable<List<Song>> observable=Observable.create(new ObservableOnSubscribe<List<Song>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Song>> emitter) throws Exception {
                MusicScanner scanner=new MusicScanner();
                List<Song> songs=scanner.scanSongs();
                emitter.onNext(songs);
                emitter.onComplete();
            }
        });
        observable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void showProgressDialog(){
        if(progressDialog==null){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("scanning...");
        }
        progressDialog.show();
    }

    private void closeProgressDialog(){
        progressDialog.cancel();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_local_music,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_search:
                Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_scan:
                Toast.makeText(this, "scan", Toast.LENGTH_SHORT).show();
                break;
                default:
                    break;
        }
        return true;
    }
}
