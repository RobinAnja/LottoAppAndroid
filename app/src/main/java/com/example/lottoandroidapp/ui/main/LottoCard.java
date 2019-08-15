package com.example.lottoandroidapp.ui.main;


public class LottoCard {
    private LottoNumbers _numbers;
    int cardIndex;

    public LottoCard(int index){
        _numbers = new LottoNumbers(index);
        cardIndex = index;
    }

    public LottoNumbers getNumbers(){
        return _numbers;
    }

    public void setNumbers(LottoNumbers newNumbers){
        _numbers = newNumbers;
    }

}
