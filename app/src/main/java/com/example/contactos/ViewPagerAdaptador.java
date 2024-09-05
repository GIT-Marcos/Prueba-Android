package com.example.contactos;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.contactos.fragments.AyudaCrear;
import com.example.contactos.fragments.AyudaEditar;
import com.example.contactos.fragments.AyudaMostrar;

public class ViewPagerAdaptador extends FragmentStateAdapter {
    public ViewPagerAdaptador(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragmento =new Fragment();
        switch (position){
            case 0: fragmento = new AyudaMostrar();
            break;
            case 1:fragmento= new AyudaCrear();
            break;
            case 2:fragmento= new AyudaEditar();
            break;
        }
        return fragmento;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
