package fpoly.edu.ungdungbantrasua.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import fpoly.edu.ungdungbantrasua.Adapter.TopAdapter;
import fpoly.edu.ungdungbantrasua.DAO.ThongKeDAO;
import fpoly.edu.ungdungbantrasua.DTO.Top;
import fpoly.edu.ungdungbantrasua.R;
public class TopFragment extends Fragment {
    View view;
    ListView listView;
    ThongKeDAO thongKeDao;

    TopAdapter topAdapter;
    ArrayList<Top> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_top, container, false);
        listView = view.findViewById(R.id.ListView_Top10);
        thongKeDao = new ThongKeDAO(getContext());
        list = thongKeDao.getTop();
        topAdapter = new TopAdapter(getContext(), list);
        listView.setAdapter(topAdapter);
        return view;
    }
}