package peerpayments.learnsqlite;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    DbAdapter dbHelper;
    EditText username, password, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper=new DbAdapter(this);

        name= (EditText) findViewById(R.id.nameforpwd);
        username=(EditText) findViewById(R.id.usernameval);
        password=(EditText) findViewById(R.id.passwordval);
    }


    public void addUser (View view)
    {
        String user=username.getText().toString();
        String pass=password.getText().toString();

        long id = dbHelper.insertData(user, pass);
        if(id<0)
        {
            Message.message(this, "unsuccessful");
        }
        else
        {
            Message.message(this, "successful");
        }
    }

    public void viewDetails(View view)
    {
        String data=dbHelper.getAllData();
        Message.message(this, data);
    }

    public void getDetails(View view)
    {
        String s1=name.getText().toString();
        String s2=dbHelper.getData(s1);
        Message.message(this, s2);
    }

    public void update(View view)
    {
        int count = dbHelper.updateName("sri","ram");
        Message.message(this, "rows updated: " + count);
    }

    public void delete(View view)
    {
        int count = dbHelper.deleteRow("sush");
        Message.message(this, "rows deleted: "+count);
    }
}
