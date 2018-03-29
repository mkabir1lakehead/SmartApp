package com.longfeizeng;

import java.io.Serializable;

public class CounterBean implements Serializable {
    int count = 0;
    public CounterBean(){

    }
    public int getCount(){
        count++;
        return count;
    }
    public void setCount(int c){
        count = c;
    }
}
