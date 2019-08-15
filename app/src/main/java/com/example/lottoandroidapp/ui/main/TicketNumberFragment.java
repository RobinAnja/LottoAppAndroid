package com.example.lottoandroidapp.ui.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.lottoandroidapp.MainActivity;
import com.example.lottoandroidapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link TicketNumberFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketNumberFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private int number;
    private int index;
    private TextView btnNbrText;
    LottoTicket lottoTicket;

    public TicketNumberFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment TicketNumberFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketNumberFragment newInstance(int i, int nbr) {
        TicketNumberFragment fragment = new TicketNumberFragment();
        Bundle args = new Bundle();
        args.putInt("index", i);
        args.putInt("number", nbr);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        index = 0;
        number = 1;

        if (getArguments() != null) {
            index = getArguments().getInt("index");
            number = getArguments().getInt("number");
        }
        lottoTicket = LottoTicket.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ticket_number, container, false);
        btnNbrText = (TextView) rootView.findViewById(R.id.ticket_nbr);
        btnNbrText.setText(String.valueOf(number));
        ImageButton plusBtn = (ImageButton) rootView.findViewById(R.id.plus_button);
        ImageButton minBtn = (ImageButton) rootView.findViewById(R.id.minus_btn);
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPlusButtonPressed();
            }
        });
        minBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMinusButtonPressed();
            }
        });
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onPlusButtonPressed() {
        lottoTicket.changeSingleTicketNumber(index, true);
        btnNbrText.setText(String.valueOf(lottoTicket.get_ticketNumber().get(index)));
    }

    public void onMinusButtonPressed() {
        lottoTicket.changeSingleTicketNumber(index, false);
        btnNbrText.setText(String.valueOf(lottoTicket.get_ticketNumber().get(index)));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void update(){
        btnNbrText.setText(String.valueOf(lottoTicket.get_ticketNumber().get(index)));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
