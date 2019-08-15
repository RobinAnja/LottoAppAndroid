package com.example.lottoandroidapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.lottoandroidapp.ui.main.DrawingDate;
import com.example.lottoandroidapp.ui.main.GameType;
import com.example.lottoandroidapp.ui.main.LottoCard;
import com.example.lottoandroidapp.ui.main.LottoCardsAdapter;
import com.example.lottoandroidapp.ui.main.LottoNumbers;
import com.example.lottoandroidapp.ui.main.LottoTicket;
import com.viewpagerindicator.LinePageIndicator;
import com.example.lottoandroidapp.ui.main.TicketNumberFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_PAGES = 14;

    ArrayList<LottoNumbers> lottoNumbers = new ArrayList<LottoNumbers>();
    int checkedDayButtonId;
    int checkedWeeksButtonId;
    ViewPager viewPager;
    ImageButton randomTicketNbrBtn;
    FragmentTransaction fragmentTransaction;
    Switch spiel77Switch;
    Switch super6Switch;
    Switch gluecksSpiraleSwitch;
    Switch siegerchanceSwitch;
    Button miUndSaButton;
    Button mittwochButton;
    Button samstagButton;
    Button eineWocheButton;
    Button zweiWochenButton;
    Button dreiWochenButton;
    Button vierWochenButton;
    Button fuenfWochenButton;
    Button achtWochenButton;
    LinearLayout bottomSheet;
    LottoTicket lottoTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lottoTicket = LottoTicket.getInstance();
        bottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet);
        randomTicketNbrBtn = (ImageButton) findViewById(R.id.dices_button_ticketnumber);
        spiel77Switch = (Switch) findViewById(R.id.spiel77_switch);
        super6Switch = (Switch) findViewById(R.id.super6_switch);
        gluecksSpiraleSwitch = (Switch) findViewById(R.id.gluecksspirale_switch);
        siegerchanceSwitch = (Switch) findViewById(R.id.siegerchance_switch);
        miUndSaButton = (Button) findViewById(R.id.mi_und_sa_button);
        mittwochButton = (Button) findViewById(R.id.mittwoch_button);
        samstagButton = (Button) findViewById(R.id.samstag_button);
        eineWocheButton = (Button) findViewById(R.id.laufzeit_1_button);
        zweiWochenButton = (Button) findViewById(R.id.laufzeit_2_button);
        dreiWochenButton = (Button) findViewById(R.id.laufzeit_3_button);
        vierWochenButton = (Button) findViewById(R.id.laufzeit_4_button);
        fuenfWochenButton = (Button) findViewById(R.id.laufzeit_5_button);
        achtWochenButton = (Button) findViewById(R.id.laufzeit_8_button);

        checkedDayButtonId = miUndSaButton.getId();
        checkedWeeksButtonId = eineWocheButton.getId();

        eineWocheButton.setBackground(getResources().getDrawable(R.drawable.add_opt_button_clicked, null));
        eineWocheButton.setTextColor(0xFFFFFFFF);

        miUndSaButton.setBackground(getResources().getDrawable(R.drawable.add_opt_button_clicked, null));
        miUndSaButton.setTextColor(0xFFFFFFFF);

        CompoundButton.OnCheckedChangeListener addLottListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                changeAdditionalLotteries((Switch) compoundButton, b);
            }
        };

        View.OnClickListener onDayBtnListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setLotterieDay(view);
            }
        };


        View.OnClickListener onDurationBtnListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setLotterieDuration(view);
            }
        };

        randomTicketNbrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottoTicket.generateTicketNumber();
                updateTicketNumberView();
            }
        });

        spiel77Switch.setOnCheckedChangeListener(addLottListener);
        super6Switch.setOnCheckedChangeListener(addLottListener);
        gluecksSpiraleSwitch.setOnCheckedChangeListener(addLottListener);
        siegerchanceSwitch.setOnCheckedChangeListener(addLottListener);
        miUndSaButton.setOnClickListener(onDayBtnListener);
        samstagButton.setOnClickListener(onDayBtnListener);
        mittwochButton.setOnClickListener(onDayBtnListener);
        eineWocheButton.setOnClickListener(onDurationBtnListener);
        zweiWochenButton.setOnClickListener(onDurationBtnListener);
        dreiWochenButton.setOnClickListener(onDurationBtnListener);
        vierWochenButton.setOnClickListener(onDurationBtnListener);
        fuenfWochenButton.setOnClickListener(onDurationBtnListener);
        achtWochenButton.setOnClickListener(onDurationBtnListener);



        viewPager = (ViewPager) findViewById(R.id.viewPager);

        LottoCardsAdapter lottoCardsAdapter = new LottoCardsAdapter(this);

        for(int i = 0; i < NUM_PAGES; i++){
            lottoCardsAdapter.addCardItem(new LottoCard(i));
        }



        viewPager.setAdapter(lottoCardsAdapter);
        LinePageIndicator pageIndicator = (LinePageIndicator) findViewById(R.id.page_indicator);
        pageIndicator.setViewPager(viewPager);

        final float density = getResources().getDisplayMetrics().density;
        pageIndicator.setSelectedColor(0x88FF0000);
        pageIndicator.setUnselectedColor(0xFFFFFFFF);
        pageIndicator.setStrokeWidth(4 * density);
        pageIndicator.setLineWidth(20 * density);

        updatePriceView();

    }

    @Override
    protected void onResume(){
        super.onResume();
        LinearLayout layout = (LinearLayout) findViewById(R.id.edit_ticket_layout);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        for (int i = 0; i < 7; i++){
            TicketNumberFragment ticketNbrFragment = TicketNumberFragment.newInstance(i , i + 1);
            fragmentTransaction.add(layout.getId(), ticketNbrFragment);
        }
        fragmentTransaction.commit();

    }

    private void updateTicketNumberView(){
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for (int i = 0; i < 7; i++){
            TicketNumberFragment ticketNbrFragment = (TicketNumberFragment) fragments.get(i);
            ticketNbrFragment.update();
        }
    }

    private void changeAdditionalLotteries(Switch button,  boolean checked){
        GameType lottery;
        switch (button.getId()){
            case R.id.super6_switch:
                lottery = GameType.super6;
                break;
            case R.id.gluecksspirale_switch:
                if(!button.isChecked() && siegerchanceSwitch.isChecked()){
                    siegerchanceSwitch.setChecked(false);
                }
                lottery = GameType.glueckSpirale;
                break;
            case R.id.siegerchance_switch:
                if(button.isChecked() && !gluecksSpiraleSwitch.isChecked()){
                    gluecksSpiraleSwitch.setChecked(true);
                }
                lottery = GameType.siegerChance;
                break;
            default:
                lottery = GameType.spiel77;
        }
        lottoTicket.setAdditionalLotteries(lottery, checked);
        updatePriceView();
    }

    private void setLotterieDay(View changedButton){
        if(changedButton.getId() != checkedDayButtonId){
            Button button = (Button) changedButton;
            DrawingDate drawingDate;
            switch (button.getId()){
                case R.id.mi_und_sa_button:
                    drawingDate = DrawingDate.MittwochUndSamstag;
                    break;
                case  R.id.samstag_button:
                    drawingDate = DrawingDate.Samstag;
                    break;
                default:
                    drawingDate = DrawingDate.Mittwoch;
            }
            lottoTicket.setWeekDay(drawingDate);
            updatePriceView();

            button.setBackground(getResources().getDrawable(R.drawable.add_opt_button_clicked, null));
            button.setTextColor(0xFFFFFFFF);

            Button recentlyCheckedBtn = (Button) findViewById(checkedDayButtonId);
            recentlyCheckedBtn.setBackground(getResources().getDrawable(R.drawable.additional_options_button_shape, null));
            recentlyCheckedBtn.setTextColor(0xFFCC0000);
            checkedDayButtonId = button.getId();
        }
    }

    private void setLotterieDuration(View changedButton){
        if(changedButton.getId() != checkedWeeksButtonId){
            Button button = (Button) changedButton;
            int duration;

            switch (button.getId()){
                case R.id.laufzeit_2_button:
                    duration = 2;
                    break;
                case R.id.laufzeit_3_button:
                    duration = 3;
                    break;
                case R.id.laufzeit_4_button:
                    duration = 4;
                    break;
                case R.id.laufzeit_5_button:
                    duration = 5;
                    break;
                case R.id.laufzeit_8_button:
                    duration = 8;
                    break;
                default:
                    duration = 1;
            }
            lottoTicket.setDuration(duration);

            updatePriceView();
            button.setBackground(getResources().getDrawable(R.drawable.add_opt_button_clicked, null));
            button.setTextColor(0xFFFFFFFF);

            Button recentlyCheckedBtn = (Button) findViewById(checkedWeeksButtonId);
            recentlyCheckedBtn.setBackground(getResources().getDrawable(R.drawable.additional_options_button_shape, null));
            recentlyCheckedBtn.setTextColor(0xFFCC0000);
            checkedWeeksButtonId = button.getId();
        }
    }

    public void updatePriceView(){
        TextView priceText = (TextView) bottomSheet.findViewById(R.id.price_text);
        TextView tipFielsCount = (TextView) bottomSheet.findViewById(R.id.tip_fields_count);
        TextView drawingDate = (TextView) bottomSheet.findViewById(R.id.drawing_date_value);
        TextView weekDay = (TextView) bottomSheet.findViewById(R.id.duration_value);
        priceText.setText(lottoTicket.getPrice());
        tipFielsCount.setText(String.valueOf(Integer.parseInt(lottoTicket.getFullTipFieldCount())));
        drawingDate.setText(lottoTicket.getDrawingDateString());
        weekDay.setText(String.valueOf(lottoTicket.getDuration()) + " Woche(n)");
    }
}
