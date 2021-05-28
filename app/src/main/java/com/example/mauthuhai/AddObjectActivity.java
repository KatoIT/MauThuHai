package com.example.mauthuhai;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddObjectActivity extends AppCompatActivity {
    private EditText editText1, editText2, editText3, editText4, editText5;
    private Button button;
    private HocSinh object;
    private Database database = new Database(AddObjectActivity.this);
    private VarFinal mVarFinal = new VarFinal();
    private boolean isSuccessful = false;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_object);
        this.AnhXa();


        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(mVarFinal.KEY_INTENT);
        int id = bundle.getInt(mVarFinal.KEY_ID);
        boolean isUpdate = bundle.getBoolean(mVarFinal.KEY_IS_UPDATE);

        if (isUpdate) {
            button.setText("Cập nhật");
            getSupportActionBar().setTitle("Chỉnh sửa");
            Cursor cursor = database.GetData("SELECT * FROM " + mVarFinal.TABLENAME + " WHERE Id = " + id + ";");
            cursor.moveToFirst();
            object = new HocSinh(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.parseDouble(cursor.getString(3)),
                    Double.parseDouble(cursor.getString(4)),
                    Double.parseDouble(cursor.getString(5))
            );
            // set Text cho EditText
            editText1.setText(object.getSBD());
            editText2.setText(object.getHoTen());
            editText3.setText(String.valueOf(object.getDToan()));
            editText4.setText(String.valueOf(object.getDLy()));
            editText5.setText(String.valueOf(object.getDHoa()));
        } else {
            button.setText("Thêm");
            getSupportActionBar().setTitle("Thêm mới");
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                object = new HocSinh(
                        id,
                        editText1.getText().toString(),
                        editText2.getText().toString(),
                        Double.parseDouble(editText3.getText().toString()),
                        Double.parseDouble(editText4.getText().toString()),
                        Double.parseDouble(editText5.getText().toString())
                );
                if (isUpdate) {
                    database.Update(object);
                    isSuccessful = true;
                } else {
                    database.Insert(object);
                    isSuccessful = true;
                }
                if (isSuccessful) {
                    finish();
                    Intent intent1 = new Intent(AddObjectActivity.this, MainActivity.class);
                    startActivity(intent1);
                    isSuccessful = false;
                }
            }
        });
    }

    public void AnhXa() {
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);
        editText5 = findViewById(R.id.editText5);
        button = findViewById(R.id.button);
    }


}