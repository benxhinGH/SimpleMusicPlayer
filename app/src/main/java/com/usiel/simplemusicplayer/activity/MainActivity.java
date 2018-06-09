package com.usiel.simplemusicplayer.activity;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.usiel.simplemusicplayer.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private static final String title="SimpleMusicPlayer";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.btn_local_music)
    Button btnLocalMusic;

    RxPermissions rxPermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        checkPer();
    }

    @OnClick(R.id.btn_local_music)
    void onClickBtnLocalMusic(){
        Intent intent=new Intent(this,LocalMusicActivity.class);
        startActivity(intent);
    }

    private void checkPer(){
        rxPermissions=new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if(aBoolean){
                            Toast.makeText(MainActivity.this, "grant", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "notgrant", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
