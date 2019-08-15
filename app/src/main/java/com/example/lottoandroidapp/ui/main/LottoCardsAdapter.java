package com.example.lottoandroidapp.ui.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.Math;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import 	android.content.res.Resources;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.example.lottoandroidapp.MainActivity;
import com.example.lottoandroidapp.R;
import 	android.graphics.drawable.ScaleDrawable;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LottoCardsAdapter extends PagerAdapter {

    String[] data = new String[49];
    private List<CardView> mViews;
    private List<LottoCard> mData;
    private float mBaseElevation;
    private MainActivity activity;
    List<String> ITEM_LIST;
    ArrayAdapter<String> arrayAdapter;
    Resources res;
    Drawable drawable;
    LottoNumbers lottoNumbers;
    int actualCardIndex;
    ViewPager viewPager;
    GridView gridView;
    LottoTicket lottoTicket;

    public LottoCardsAdapter(MainActivity activity) {
        mData = new ArrayList<>();
        mViews = new ArrayList<>();
        this.activity = activity;
        lottoTicket = LottoTicket.getInstance();

        for (int i = 1; i <= 49; i++) {
            data[i-1] = String.valueOf(i);
        }

        res = activity.getApplicationContext().getResources();
        drawable = ResourcesCompat.getDrawable( res, R.drawable.x, null);
        drawable = new ScaleDrawable(drawable, 0, 10, 10).getDrawable();
        drawable.setBounds(0, 0, 10, 10);
    }

    public void addCardItem(LottoCard item) {
        mViews.add(null);
        mData.add(item);
    }

    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object){


    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        viewPager = (ViewPager) container;
        actualCardIndex = viewPager.getCurrentItem();

        View view = LayoutInflater.from(container.getContext())
                .inflate(R.layout.card, container, false);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        gridView = cardView.findViewById(R.id.grid_view);


        final ImageButton garbageBtn = cardView.findViewById(R.id.garbage_button);
        final ImageButton dicesBtn = cardView.findViewById(R.id.dices_button);
        final Button addOneBtn = cardView.findViewById(R.id.plus_1_button);
        final Button addFourBtn = cardView.findViewById(R.id.plus_4_button);
        final Button addEightBtn = cardView.findViewById(R.id.plus_8_button);
        final Button addFourteenBtn = cardView.findViewById(R.id.plus_14_button);

        ITEM_LIST = new ArrayList<String>(Arrays.asList(data));

        arrayAdapter = new ArrayAdapter<String>(activity.getApplicationContext(), R.layout.grid_item, ITEM_LIST);
        gridView.setAdapter(arrayAdapter);

        lottoNumbers = lottoTicket.lottoNumbers.get(actualCardIndex);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                if (i != actualCardIndex){
                    actualCardIndex = i;
                    lottoNumbers = lottoTicket.lottoNumbers.get(actualCardIndex);

                    for(int k =0 ; k < 49; k ++) {
                        TextView child = (TextView) gridView.getChildAt(k);
                        if(lottoNumbers.getNumbers().contains(Integer.parseInt(child.getText().toString()))){

                            child.setBackground(drawable);
                            child.setForeground(ResourcesCompat.getDrawable( res, R.drawable.cell_shape, null));
                            child.setTextColor(Color.WHITE);
                        }else{
                            child.setBackground(ResourcesCompat.getDrawable( res, R.drawable.cell_shape, null));
                            child.setTextColor(Color.BLACK);
                        }

                        // do stuff with child view
                    }
                }
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lottoTicket.actionOnLottoNumber(i, actualCardIndex);
                updateView(view, i);
                //activity.sendLottoNumbersData(lottoNumbers, actualCardIndex);

            }
        });

        garbageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i =0 ; i < 49; i ++) {
                    TextView child = (TextView) gridView.getChildAt(i);
                    child.setBackground(ResourcesCompat.getDrawable( res, R.drawable.cell_shape, null));
                    child.setTextColor(Color.BLACK);
                    // do stuff with child view
                }
                lottoTicket.removeLottoNumbers(actualCardIndex);

                //activity.sendLottoNumbersData(lottoNumbers, actualCardIndex);
            }
        });

        dicesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottoTicket.removeLottoNumbers(actualCardIndex);

                ArrayList<Integer> allPossibleNbrs = new ArrayList<Integer>();
                for(int i = 1; i <= 49; i++){
                    allPossibleNbrs.add(i);
                    TextView child = (TextView) gridView.getChildAt(i-1);
                    child.setBackground(ResourcesCompat.getDrawable( res, R.drawable.cell_shape, null));
                    child.setTextColor(Color.BLACK);
                }
                int rand;
                for (int i = 0; i < 6; i++){
                    rand = ((int)(Math.random() * (allPossibleNbrs.size() - 1))) + 1;
                    int newNumber = allPossibleNbrs.get(rand);
                    lottoTicket.actionOnLottoNumber(newNumber, actualCardIndex);
                    allPossibleNbrs.remove(allPossibleNbrs.indexOf(newNumber));
                    gridView = mViews.get(actualCardIndex).findViewById(R.id.grid_view);
                    TextView child = (TextView) gridView.getChildAt(rand);
                    child.setBackground(drawable);
                    child.setForeground(ResourcesCompat.getDrawable( res, R.drawable.cell_shape, null));
                    child.setTextColor(Color.WHITE);
                }
                activity.updatePriceView();
            }
    });
        if(container.findViewById(view.getId()) == null){
            container.addView(view);
        }


        if (mBaseElevation == 0) {
            mBaseElevation = cardView.getCardElevation();
        }

        mViews.set(position, cardView);
        return view;
    }

    private void updateView(View view, int number){
        TextView text = (TextView) view;
        if (!lottoTicket.lottoNumbers.get(actualCardIndex).tipNumbers.contains(number)){
            text.setBackground(drawable);
            text.setForeground(ResourcesCompat.getDrawable( res, R.drawable.cell_shape, null));
            text.setTextColor(Color.WHITE);
        }else {
            text.setBackground(ResourcesCompat.getDrawable( res, R.drawable.cell_shape, null));
            text.setTextColor(Color.BLACK);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((View) object);
        mViews.set(position, null);
    }

    private View bind(LottoCard item, View view) {

       return view;
    }


}
