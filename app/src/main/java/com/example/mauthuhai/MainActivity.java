package com.example.mauthuhai;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    public Database db = new Database(MainActivity.this);
    public ListView listView;
    public EditText editTextFilter;
    public FloatingActionButton actionButtonAdd;
    public BaseAdapterK adapter;
    public ArrayList<HocSinh> arrayListObject = new ArrayList<>();
    public long backPressTime;
    public Toast toast;
    private VarFinal mVarFinal = new VarFinal();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Trang chủ");
        // Ánh xạ
        listView = findViewById(R.id.listView);
        editTextFilter = findViewById(R.id.editTextSearch);
        actionButtonAdd = findViewById(R.id.floatingActionButtonAdd);
        // Insert data
        if (db.GetData("SELECT * FROM " + mVarFinal.TABLENAME).getCount() == 0) {
            db.Insert(new HocSinh(0,"181203458", "Nguyễn Văn An", 9.0,9.0,9.0));
            db.Insert(new HocSinh(1,"181212011", "Bùi Tiến Bắc", 8.5,8.0,10.0));
            db.Insert(new HocSinh(2,"181202577", "Đỗ Xuân Cảnh", 9.5,8.0,8.5));
            db.Insert(new HocSinh(3,"181201867", "Nguyễn Đức Phú", 8.0,7.0,7.0));
            db.Insert(new HocSinh(4,"181203460", "Lê Quang Duy", 8.5,10.0,9.0));
            db.Insert(new HocSinh(5,"181202289", "Lê Quang Thọ", 9.5,8.5,9.5));
        }
        // End Insert data
        arrayListObject = selectAll(mVarFinal.TABLENAME);
        // Sort arrayList theo Giá (đảo vị trí (t1,t2) để đảo chiều sort)
        Collections.sort(arrayListObject, (t1, t2) -> t1.getSBD().compareTo(t2.getSBD()));
        // set Adapter
        adapter = new BaseAdapterK(MainActivity.this, arrayListObject);
        listView.setAdapter(adapter);
        // Đăng ký contextMenu
        registerForContextMenu(listView);
        // Filter
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        actionButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentView(0, AddObjectActivity.class, mVarFinal.KEY_INTENT, false);
            }
        });

    }

    public ArrayList<HocSinh> selectAll(String tableName) {
        // Select data SQLite
        ArrayList<HocSinh> arrayList = new ArrayList<>();
        Cursor cursor = db.GetData("SELECT * FROM " + tableName + "");
        while (cursor.moveToNext()) {
            String col1 = cursor.getString(1);
            arrayList.add(new HocSinh(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    Double.parseDouble(cursor.getString(3)),
                    Double.parseDouble(cursor.getString(4)),
                    Double.parseDouble(cursor.getString(5))
            ));
        }
        return arrayList;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // Khởi tạo Context Menu
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // Sự kiện click Item Contect Menu
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_item_xoa:
                // chọn item 'Xóa'
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Thông báo");
                builder.setMessage("Bạn đồng ý xóa!");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Sự kiện click nút 'Có'
                        db.Delete(new HocSinh(arrayListObject.get(info.position).getId()));
                        arrayListObject.remove(info.position);
                        adapter.updateResults(arrayListObject);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // sự kiện nút 'Không'
                    }
                });
                builder.create();// tạo dialog
                builder.show(); // show dialog
                return true;
            case R.id.menu_item_sua:
                // chọn item 'Sửa'
                intentView(arrayListObject.get(info.position).getId(), AddObjectActivity.class, mVarFinal.KEY_INTENT, true);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void intentView(int val, Class cls, String str, Boolean isUpdate) {
        // truyền dữ liệu giữa các activity
        Intent intent = new Intent(MainActivity.this, cls);
        Bundle bundle = new Bundle();
        bundle.putInt(mVarFinal.KEY_ID, val);
        bundle.putBoolean(mVarFinal.KEY_IS_UPDATE, isUpdate);
        intent.putExtra(str, bundle);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        // click 'Back'
        if (backPressTime + 2000 > System.currentTimeMillis()) {
            toast.cancel();
            this.finishAffinity();
//            super.onBackPressed();
            return;
        } else {
            toast = Toast.makeText(MainActivity.this, "Nhấp Back 1 lần nữa để thoát", Toast.LENGTH_SHORT);
            toast.show();
        }
        backPressTime = System.currentTimeMillis();
    }
}