package com.aspha.toyota.mMessages;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by kinsley kajiva on 8/6/2016.Zvaganzirwa nakinsley kajiva musiwa 8/6/2016
 */
public class SeeTastyToast  {
    private Context context;


    public SeeTastyToast(Context context) {
        this.context = context;
    }


  public   void ToastWarning(String msg){
      TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.WARNING);
    }
    public   void ToastError(String msg){
        TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.ERROR);
    }
    public   void ToastSuccess(String msg){
        TastyToast.makeText(context,msg, TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
    }
    public   void ToastDafault(String msg){
        TastyToast.makeText(context, msg, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
    }
}
