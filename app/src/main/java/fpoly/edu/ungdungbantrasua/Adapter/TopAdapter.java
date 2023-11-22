package fpoly.edu.ungdungbantrasua.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.DTO.Top;
import fpoly.edu.ungdungbantrasua.R;

public class TopAdapter extends BaseAdapter {
    Context context;
    ArrayList<Top> list;

    public TopAdapter(Context context, ArrayList<Top> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class TopViewHolder {
        TextView txt_tenSanPham, txt_soLuong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TopViewHolder top10ViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_top,parent,false);
            top10ViewHolder = new TopViewHolder();
            top10ViewHolder.txt_tenSanPham = convertView.findViewById(R.id.txt_tenSanPham_Top);
            top10ViewHolder.txt_soLuong = convertView.findViewById(R.id.txt_soLuong_Top);
            convertView.setTag(top10ViewHolder);
        } else {
            top10ViewHolder = (TopViewHolder) convertView.getTag();
        }
        Top top = list.get(position);
        if (top != null) {
            top10ViewHolder.txt_tenSanPham.setText("Tên Sản phẩm :" + list.get(position).tenTraSua);
            top10ViewHolder.txt_soLuong.setText("Số lượng :" + list.get(position).soLuong);
        }
        return convertView;
    }
}
