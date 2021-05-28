package com.example.mauthuhai;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseAdapterK extends BaseAdapter implements Filterable {
    private Context context; //context
    private ArrayList<HocSinh> arrayListObject; //data source of the list adapter

    private CustomFilter filter;
    private ArrayList<HocSinh> filterList;


    public BaseAdapterK(Context context, ArrayList<HocSinh> arrayListObject) {
        this.context = context;
        this.arrayListObject = arrayListObject;
        this.filterList = arrayListObject;
    }

    @Override
    public int getCount() {
        return arrayListObject.size();
    }

    @Override
    public HocSinh getItem(int position) {
        return arrayListObject.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(ArrayList<HocSinh> lUser) {
        this.arrayListObject = lUser;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.item_list_view, null);
        }
        HocSinh object = (HocSinh) getItem(position);
        if (object != null) {
            // Ánh xạ
            TextView textView1 = (TextView) view.findViewById(R.id.textView1);
            TextView textView2 = (TextView) view.findViewById(R.id.textView2);
            TextView textView3 = (TextView) view.findViewById(R.id.textView3);
            // Xử lý dữ liệu
            Double sum = object.getDToan() + object.getDLy() + object.getDHoa();
            textView1.setText(object.getSBD());
            textView2.setText(object.getHoTen());
            textView3.setText(String.valueOf(sum));
        }
        return view;
    }

    public String convertGia(int value) {
        String val = "";
        while (value >= 1000) {
            int var = (value % 1000);
            if (var == 0) {
                val = ".000" + val;
            } else {
                if (var < 10) {
                    val = "." + String.valueOf(100 * var) + val;
                } else {
                    if (var < 100) {
                        val = "." + String.valueOf(10 * var) + val;
                    } else {
                        val = "." + String.valueOf(var) + val;
                    }
                }
            }
            value = (int) value / 1000;
        }
        val = String.valueOf(value) + val;
        return val;
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }


    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<HocSinh> arrayList = new ArrayList<>();
                for (int i = 0; i < filterList.size(); i++) {
                    // search theo filterList.get(i).getTenSP() -------------------------------
                    if (filterList.get(i).getSBD().toUpperCase().contains(constraint) || filterList.get(i).getHoTen().toUpperCase().contains(constraint) ) {
                        // chú ý -----------------------------------------
                        HocSinh object = new HocSinh(filterList.get(i).getId(), filterList.get(i).getSBD(), filterList.get(i).getHoTen(), filterList.get(i).getDToan(), filterList.get(i).getDLy(), filterList.get(i).getDHoa());
                        //
                        arrayList.add(object);
                    }
                }
                results.count = arrayList.size();
                results.values = arrayList;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            arrayListObject = (ArrayList<HocSinh>) results.values;
            notifyDataSetChanged();
        }

    }

    public void updateResults(ArrayList<HocSinh> results) {
        arrayListObject = results;
        notifyDataSetChanged();
    }

}
