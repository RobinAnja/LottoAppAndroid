package com.example.lottoandroidapp.ui.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.example.lottoandroidapp.R;

import java.util.ArrayList;

public class LottoNumbers {
    ArrayList<Integer> tipNumbers;
    int index;
    boolean isFullTip = false;

    public LottoNumbers(int index){
        this.index = index;
        tipNumbers = new ArrayList<Integer>();
        tipNumbers = new ArrayList<Integer>(6);

    }

    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){return this.index;}

    public void setNumbers(int number){
        if(this.tipNumbers.contains(number)){
            this.tipNumbers.remove(number);
        }else{
            if (this.tipNumbers.size() <= 5){
                this.tipNumbers.add(number);
            }
        }
        isFullTip = this.tipNumbers.size() == 6 ? true : false;
    }
    
    public ArrayList<Integer> getNumbers(){
        return this.tipNumbers;
    }
    
    public void setNumbers(ArrayList<Integer> newNumbers){
        this.tipNumbers = newNumbers;
        isFullTip = this.tipNumbers.size() == 6 ? true : false;
    }
    
    public void clear(){
        this.tipNumbers = new ArrayList<Integer>();
        isFullTip = false;
    }


}
