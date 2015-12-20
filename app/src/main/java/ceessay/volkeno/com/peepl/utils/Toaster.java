package ceessay.volkeno.com.peepl.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by mohamed on 16/12/15.
 */
public class Toaster {


    public static void showLong(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();

    }

    public static void showShort(Context context, String message) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}