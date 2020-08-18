package com.amol.jare.dxminds.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amol.jare.dxminds.R;
import com.amol.jare.dxminds.model.GenericProductModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mDatabaseReference = database.getReference();

    RecyclerView my_recycler_view;
    private StaggeredGridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        my_recycler_view = findViewById(R.id.my_recycler_view);
        if (my_recycler_view != null) {
            my_recycler_view.setHasFixedSize(true);
        }

        mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        my_recycler_view.setLayoutManager(mLayoutManager);


        final FirebaseRecyclerAdapter<GenericProductModel, MovieViewHolder> adapter = new FirebaseRecyclerAdapter<GenericProductModel, MovieViewHolder>(
                GenericProductModel.class,
                R.layout.card_item_list,
                MovieViewHolder.class,
                mDatabaseReference.child("Products").child("Cards").getRef()
        ) {
            @Override
            protected void populateViewHolder(MovieViewHolder movieViewHolder, GenericProductModel model, int i) {
                movieViewHolder.cardname.setText(model.getCardname());
                movieViewHolder.cardprice.setText("₹ " + Float.toString(model.getCardprice()));
                Picasso.with(MainActivity.this).load(model.getCardimage()).into(movieViewHolder.cardimage);

            }
        };
        my_recycler_view.setAdapter(adapter);
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        TextView cardname;
        ImageView cardimage;
        TextView cardprice;

        View mView;

        public MovieViewHolder(View v) {
            super(v);
            mView = v;
            cardname = v.findViewById(R.id.cart_prtitle);
            cardimage = v.findViewById(R.id.image_cartlist);
            cardprice = v.findViewById(R.id.cart_prprice);
        }
    }
}
