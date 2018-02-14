package com.aspha.toyota.mMessages;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.aspha.toyota.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;


import java.util.Random;




/**
 * Created by kinsley kajiva on 6/28/2016.Zvaganzirwa nakinsley kajiva musiwa 6/28/2016
 */
public class NifftyDialogs {

    private Context context;

    public NifftyDialogs(Context context) {
        this.context = context;
    }
    public  void justMessage(String txt){
        NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(context);

        dialogBuilder
                .withTitle("Welcome..")
                .withTitleColor("#FFFFFF")
                .withDialogColor( Build.VERSION.SDK_INT>21? ContextCompat.getColor(context, R.color.colorPrimary): context.getResources().getColor( R.color.colorPrimary))
                .withMessage(txt)
                .show();
    }
    public void  messageOk(String Title,String message){
        final NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle(Title)
                .withDialogColor(Build.VERSION.SDK_INT>21? ContextCompat.getColor(context, R.color.colorAccent): context.getResources().getColor(R.color.colorAccent))
                .withTitleColor("#FFFFFF")
                .withMessage(message)
                .withButton1Text("Ok").isCancelableOnTouchOutside(false).withEffect(stylepop_up())
                /*.withIcon(R.drawable.ic_info_white_24dp)*/
                .withDuration(700)
                .setButton1Click( v -> dialogBuilder.dismiss() )
                .show();
    }
    public void  messageOkError(String Title,String message){
        final NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle(Title)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#727272")
                .withMessage(message)
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#FFE74C3C")
                .withButton1Text("Ok")
                .isCancelableOnTouchOutside(false)
                .withEffect(stylepop_up())
                .withDuration(700)
                .setButton1Click( v -> dialogBuilder.dismiss() )
                .show();
    }
    public void  messageCloseError(String Title,String message){
        final NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(context);
        dialogBuilder.withTitle(Title)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#727272")
                .withMessage(message)
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#FFE74C3C")
                .withButton1Text("Close")
                .isCancelableOnTouchOutside(false)
                .withEffect(stylepop_up())
                .withDuration(700)
                .setButton1Click( v -> dialogBuilder.dismiss() )
                .show();
    }
    public void  messageOk(String message){
        final NiftyDialogBuilder dialogBuilder= NiftyDialogBuilder.getInstance(context);

        dialogBuilder.withTitle("Infomation")
                .withTitleColor("#FFFFFF")
                .withDialogColor(Build.VERSION.SDK_INT>21? ContextCompat.getColor(context, R.color.colorAccent): context.getResources().getColor(R.color.colorAccent))
                .withMessage(message)
                .withButton1Text("Ok").isCancelableOnTouchOutside(false).withEffect(stylepop_up())
                /*.withIcon(R.drawable.ic_info_white_24dp)*/
                .withDuration(700)
                .setButton1Click( v -> dialogBuilder.dismiss() )
                .show();
    }
    public boolean messageOkCancel(String Title,String message){
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(context);
        final boolean[] value = new boolean[1];
        dialogBuilder
                .withTitle(Title)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#727272")
                /*.withIcon(R.drawable.ic_info_white_24dp)*/
                .withMessage(message)
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#FFE74C3C")
                .isCancelableOnTouchOutside(false)
                .withDuration(700)
                .withEffect(stylepop_up())
                .withButton1Text("OK")
                .withButton2Text("Cancel")
                .isCancelableOnTouchOutside(true)

                .setButton1Click( v -> {

                    value[0] =true;
                    dialogBuilder.dismiss();
                } )
                .setButton2Click( v -> {
                    value[0] =false;
                    dialogBuilder.dismiss();
                } )
                .show();
        return value[0];
    }
    public boolean messageOkBool(String Title,String message){
        final NiftyDialogBuilder dialogBuilder=NiftyDialogBuilder.getInstance(context);
        final boolean[] value = new boolean[1];
        dialogBuilder
                .withTitle(Title)
                .withTitleColor("#FFFFFF")
                .withDividerColor("#727272")
                /*.withIcon(R.drawable.ic_info_white_24dp)*/
                .withMessage(message)
                .withMessageColor("#FFFFFFFF")
                .withDialogColor("#FFE74C3C")
                .isCancelableOnTouchOutside(false)
                .withDuration(700)
                .withEffect(stylepop_up())
                .withButton1Text("OK")

                .isCancelableOnTouchOutside(true)

                .setButton1Click( v -> {

                    value[0] =false;

                    dialogBuilder.dismiss();
                } )

                .show();
        return value[0];
    }
    public Effectstype stylepop_up(){
        final int maximum = 14;
        final int minimum = 1;
        final int randomNum = new Random().nextInt(maximum - minimum + 1) + minimum;

        switch (randomNum){
            case 1: return Effectstype.Fadein;
            case 2: return Effectstype.Slideleft;
            case 3: return Effectstype.Slidetop;
            case 4: return Effectstype.SlideBottom;
            case 5: return Effectstype.Slideright;
            case 6: return Effectstype.Fall;
            case 7: return Effectstype.Newspager;
            case 8: return Effectstype.Fliph;
            case 9: return Effectstype.Flipv;
            case 10: return Effectstype.RotateBottom;
            case 11: return Effectstype.RotateLeft;
            case 12: return Effectstype.Slit;
            case 13: return Effectstype.Shake;
            case 14: return Effectstype.Sidefill;
            default:
                return Effectstype.Fadein;
        }
    }
}
