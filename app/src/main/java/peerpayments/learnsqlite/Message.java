package peerpayments.learnsqlite;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sushantc on 3/5/16.
 */
public class Message {
    public static void message(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
