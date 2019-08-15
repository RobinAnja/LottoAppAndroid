package com.example.lottoandroidapp.ui.main;

import java.util.ArrayList;
import java.util.Random;

public class LottoTicket {
    private static final LottoTicket lottoTicket = new LottoTicket();
    public ArrayList<LottoNumbers> lottoNumbers;
    double _servicePrice = 0.2;
    double _price = 0.0;
    ArrayList<Integer> _ticketNumber;
    double _fullTipFieldsCount = 0.0;
    Boolean _spiel77Selected = false;
    Boolean _super6Selected = false;
    Boolean _glueckSpiraleSelected = false;
    Boolean _siegerChanceSelected = false;
    DrawingDate _weekDay = DrawingDate.MittwochUndSamstag;
    int _duration = 1;

    private LottoTicket(){
        lottoNumbers = new ArrayList<LottoNumbers>(14);
        for (int i = 0; i < 14; i++){
            lottoNumbers.add(new LottoNumbers(i));
        }

        _ticketNumber = new ArrayList<Integer>();
        for (int i = 1; i < 8; i++) {
            _ticketNumber.add(i);
        }
    }

    public static LottoTicket getInstance() {
        return lottoTicket;
    }

    public void updatePrice() {
        double tmpPrice = 0.0;
        _fullTipFieldsCount = 0.0;

        for (LottoNumbers lottoNumbers : lottoNumbers){
            if(lottoNumbers.isFullTip){
                _fullTipFieldsCount += 1;
            }
        }

        if (_fullTipFieldsCount > 0) {
            tmpPrice = _fullTipFieldsCount;
            tmpPrice = tmpPrice * _duration;
            tmpPrice =
                    tmpPrice * (_weekDay == DrawingDate.MittwochUndSamstag ? 2 : 1);

            if (_spiel77Selected) {
                tmpPrice += 5.0;
            }
            if (_super6Selected) {
                tmpPrice += 2.5;
            }
            if (_glueckSpiraleSelected) {
                tmpPrice += 5.0;
            }
            if (_siegerChanceSelected) {
                tmpPrice += 3.0;
            }

            tmpPrice += _servicePrice;
        }
        _price = tmpPrice;
    }

    public void actionOnLottoNumber(int number, int index) {
        if (lottoNumbers.get(index).tipNumbers.contains(number)) {
            lottoNumbers.get(index).tipNumbers.remove(number);
        } else {
            if (lottoNumbers.get(index).tipNumbers.size() < 6) {
                lottoNumbers.get(index).tipNumbers.add(number);
            }
        }
        lottoNumbers.get(index).isFullTip =
                lottoNumbers.get(index).tipNumbers.size() == 6;
        updatePrice();
    }

    public void removeLottoNumbers(int tipFieldIndex) {
        lottoNumbers.get(tipFieldIndex).tipNumbers.clear();
        lottoNumbers.get(tipFieldIndex).isFullTip = false;
        updatePrice();
    }

    public void generateRandomLottoNumbers(int cardCount) {
        Random rnd = new Random();
        ArrayList<Integer> tmpNumbers = new ArrayList<Integer>();

        for (int y = 0; y < cardCount; y++) {
            for(int i = 1; i<50; i++){
                tmpNumbers.add(i);
            }

            lottoNumbers.get(y).tipNumbers.clear();

            for (int i = 0; i < 6; i++) {
                int nbr = rnd.nextInt(tmpNumbers.size());
                lottoNumbers.get(y).tipNumbers.add(tmpNumbers.get(nbr));
                tmpNumbers.remove(tmpNumbers.get(nbr));
            }
            lottoNumbers.get(y).isFullTip = true;
        }
        updatePrice();
    }

    public void generateTicketNumber() {
        Random rnd = new Random();

        for (int i = 0; i < _ticketNumber.size(); i++) {
            _ticketNumber.set(i, rnd.nextInt(8) + 1);
        }
    }

    public void changeSingleTicketNumber(int index, Boolean increase) {
        int number = _ticketNumber.get(index);
        number = increase ? number + 1 : number - 1;
        if (number < 1) {
            number = 9;
        } else if (number > 9) {
            number = 1;
        }
        _ticketNumber.set(index, number);
    }

    public ArrayList<LottoNumbers> getTipFields() {
        return lottoNumbers;
    }

    public void setAdditionalLotteries(GameType game, Boolean selected) {

        switch (game) {
            case spiel77:
                _spiel77Selected = selected;
                break;
            case super6:
                _super6Selected = selected;
                break;
            case glueckSpirale:
                _glueckSpiraleSelected = selected;
                if (!selected && _siegerChanceSelected) {
                    _siegerChanceSelected = false;
                }
                break;
            case siegerChance:
                _siegerChanceSelected = selected;
                if (selected && !_glueckSpiraleSelected) {
                    _glueckSpiraleSelected = true;
                }
                break;
        }
        updatePrice();
    }

    public void setWeekDay(DrawingDate day) {
        _weekDay = day;
        updatePrice();
    }

    public void setDuration(int duration) {
        _duration = duration;
        updatePrice();
    }

    public String getPrice() {
        String tmpPrice = String.valueOf(_price).replace(".", ",");
        tmpPrice += "0 €";
        return tmpPrice;
    }

    public Boolean getSpiel77Selected() {
        return _spiel77Selected;
    }

    public Boolean getSuper6Selected() {
        return _super6Selected;
    }

    public Boolean getGlueckSpiraleSelected() {
        return _glueckSpiraleSelected;
    }

    public Boolean getSiegerChanceSelected() {
        return _siegerChanceSelected;
    }

    public DrawingDate getDrawingDate() {
        return _weekDay;
    }

    public ArrayList<Integer> get_ticketNumber(){
        return _ticketNumber;
    }

    public String getDrawingDateString() {
        String tmpWeekDay = "Mi. und Sa.";
        if (_weekDay == DrawingDate.Mittwoch) {
            tmpWeekDay = "Mittwoch";
        } else if (_weekDay == DrawingDate.Samstag) {
            tmpWeekDay = "Samstag";
        }
        return tmpWeekDay;
    }

    public int getDuration() {
        return _duration;
    }

    public String getFullTipFieldCount() {
        return String.valueOf(_fullTipFieldsCount);
    }
}
