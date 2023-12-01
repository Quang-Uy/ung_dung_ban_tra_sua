package fpoly.edu.ungdungbantrasua.Fragment.KhachHang;

import android.app.slice.Slice;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

import fpoly.edu.ungdungbantrasua.Adapter.DanhSachAdapter;
import fpoly.edu.ungdungbantrasua.Adapter.SanPhamAdapter;
import fpoly.edu.ungdungbantrasua.DAO.SanPhamDAO;
import fpoly.edu.ungdungbantrasua.DTO.SanPham;
import fpoly.edu.ungdungbantrasua.R;

public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    private RecyclerView RC_Home;
    SanPhamDAO sanPhamDAO;
    private List<SanPham> sanPham = new ArrayList<SanPham>();
    private DanhSachAdapter adapter;
    List<SanPham> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider = v.findViewById(R.id.imageSlider);
        RC_Home = v.findViewById(R.id.RC_Home);

        RC_Home.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        RC_Home.setHasFixedSize(true);
        sanPhamDAO = new SanPhamDAO(getActivity());

        //Image slider
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slider1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider4, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.slider5, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        list = sanPhamDAO.getAll();
        adapter = new DanhSachAdapter(requireActivity(), list);
        RC_Home.setAdapter(adapter);

//        sanPham = (List<SanPham>) sanPhamDAO.getAll();
//        adapter = new DanhSachAdapter(requireActivity(), sanPham);
//        RC_Home.setAdapter(adapter);

        return v;
    }
}