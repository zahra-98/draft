package com.example.isustain_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.util.ArrayList;

import HelperClasses.HelperClasses.BestAdapter;
import HelperClasses.HelperClasses.BestSellingBooks;
import HelperClasses.HelperClasses.MostWatchesDoc;
import HelperClasses.HelperClasses.most_watched_adapter;

public class resources extends AppCompatActivity {

    RecyclerView best_books , most_docs;
    RecyclerView.Adapter adapter, adapter2;
    MediaController mediaController;
    private VideoView videoView;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);

        //hooks

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() { //navigation to go back to the homepage
            @Override
            public void onClick(View view) { onBackPressed();}
        });

        best_books = findViewById(R.id.books_recycler);
        best_books();

        most_docs = findViewById(R.id.most_watched);
        most_docs();

        videoView = findViewById(R.id.videoView);
        String vidPath = "android.resource://"+getPackageName()+"/"+R.raw.how_to_save;
        Uri uri = Uri.parse(vidPath);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i1) {
                        if(mediaController==null){
                            mediaController = new MediaController(resources.this);
                            videoView.setMediaController(mediaController);
                            mediaController.setAnchorView(videoView);
                        }
                    }
                });
            }
        });

    }

    private void most_docs() {

        String[] docsUrl;

        most_docs.setHasFixedSize(true);
        most_docs.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        ArrayList<MostWatchesDoc> mostWatchesDocs = new ArrayList<>();
        mostWatchesDocs.add(new MostWatchesDoc(R.drawable.a_plastic_ocean,"A Plastic Ocean","Craig Leeson discovers a startling\n" +
                "amount of plastic pollution in the\n" +
                "world's oceans."));
        mostWatchesDocs.add(new MostWatchesDoc(R.drawable.life_on_planet,"A Life On Our Planet","Witnessing humanity's impacts on Earth,\n"+
               "Sir Attenborough shares his concerns\n"+
                "about the current state of our Planet"));

        mostWatchesDocs.add(new MostWatchesDoc(R.drawable.crashing_ice,"Chasing Ice","James Balog and his team on the Extreme Ice Survey\n " +
                "assemble a multiyear chronicle of the\n " +
                "planet's rapidly melting glaciers."));
        mostWatchesDocs.add(new MostWatchesDoc(R.drawable.chasing_coral,"Chasing Coral","Divers, photographers and scientist set to\n"+
                "to investigate the disappearance of\n" +
                "Coral Reefs."));
        mostWatchesDocs.add(new MostWatchesDoc(R.drawable.i_am_greta,"I Am Greta","A film about acting to stop climate change\n" +
                "before it's too late."));

        mostWatchesDocs.add(new MostWatchesDoc(R.drawable.beautiful_planet,"A Beautiful Planet","Astronauts aboard the International Space Station\n" +
                "capture breathtaking footage\n " +
                "of the natural wonders of Earth."));

        docsUrl = new String[]{"https://www.amazon.com/Plastic-Ocean-Craig-Leeson/dp/B01NALDB9D/ref=sr_1_2?dchild=" +
                "1&keywords=a+plastic+ocean&qid=1632757172&s=movies-tv&sr=1-2.html",
        "https://www.amazon.com/Chasing-Ice-James-Balog/dp/B00AZMFNX2/ref=sr_1_1?dchild=1&keywords=chasing+ice&qid=1632757255&sr=8-1.html",
        "https://www.amazon.com/Greta-Documentary-NON-USA-Format-Region/dp/B08PWC6ZMY/ref=sr_1_1?dchild=1&keywords=i+am+greta&qid=1632757307&sr=8-1.html",
        "https://www.amazon.com/Beautiful-Planet-Ultra-Enhanced-Blu-ray/dp/B07J35T8H2/ref=sr_1_1?dchild=1&keywords=a+beautiful+planet&qid=1632757338&sr=8-1.html"};

        adapter2 = new most_watched_adapter(resources.this,mostWatchesDocs,docsUrl);
        most_docs.setAdapter(adapter2);



    }



    private void best_books() {
        String[] bookUrls;

        best_books.setHasFixedSize(true);
        best_books.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

        ArrayList<BestSellingBooks> bestSellers = new ArrayList<>();

        bestSellers.add(new BestSellingBooks(R.drawable.a_climate_for_change,"A Climate For Change","By Peter Newell"));
        bestSellers.add(new BestSellingBooks(R.drawable.as_long_as,"As Long As Grass Grows","By Dina Gilio-Whitaker "));
        bestSellers.add(new BestSellingBooks(R.drawable.climate_war,"The New Climate War","By Michael E.Mann"));
        bestSellers.add(new BestSellingBooks(R.drawable.all_we_can_save,"All We Can Save","By Ayana Elizabeth"));
        bestSellers.add(new BestSellingBooks(R.drawable.bio_diversity,"Biodiversity and Climate Change","By Lee Hannah"));
        bestSellers.add(new BestSellingBooks(R.drawable.how_to_giveup,"How To Give Up Plastic","By Will Mccallum"));
        bestSellers.add(new BestSellingBooks(R.drawable.on_fire,"On Fire","By Noami Klein"));
        bestSellers.add(new BestSellingBooks(R.drawable.our_house,"Our House Is On Fire ","By Jeanette Winter"));
        bestSellers.add(new BestSellingBooks(R.drawable.make,"Make It Last","By Raleigh Briggs"));
        bestSellers.add(new BestSellingBooks(R.drawable.this_changes_everything,"This Changes Everything","By Noami Klein"));
        bestSellers.add(new BestSellingBooks(R.drawable.practical,"Practical Sustainability","By Robert Brinkmann"));


        bookUrls = new String[]{"https://www.amazon.com/Climate-Change-Warming-Faith-Based-Decisions/dp/0446549568/ref=sr_1_2?crid=" +
                "ZB85BO9S99FV&dchild=1&keywords=a+climate+for+change+hayhoe&qid=1632731300&sprefix=a+climate+for+change+hay%2Caps%2C552&sr=8-2.html",
        "https://www.amazon.com/Long-Grass-Grows-Environmental-Colonization/dp/0807028363/ref=sr_1_1?crid=11IQOD84OC756&dchild=" +
                "1&keywords=as+long+as+grass+grows&qid=1632739278&sprefix=as+long+as+%2Caps%2C431&sr=8-1.html",
        "https://www.amazon.com/New-Climate-War-Fight-Planet/dp/B08QV8KSGH/ref=sr_1_1?dchild=1&keywords=climate+war&qid=1632739342&sr=8-1.html"};



        adapter = new BestAdapter(resources.this,bestSellers, bookUrls);
        best_books.setAdapter(adapter);


    }
}