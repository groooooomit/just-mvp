package just.mvp;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


public abstract class SimpleFragment extends Fragment {

    // --------------------------------------------------------------------------------------------------------------------------------------------------

    @LayoutRes
    protected abstract int getLayoutResId();

    // --------------------------------------------------------------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutResId = getLayoutResId();
        if (0 != layoutResId) {
            final View rootView = inflater.inflate(layoutResId, container, false);
            rootView.setClickable(true);// 防止点击穿透
            return rootView;
        } else {
            return null;
        }
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------------

    @ColorInt
    protected final int getColour(@ColorRes int colorId) {
        //noinspection ConstantConditions
        return ContextCompat.getColor(getContext(), colorId);
    }

}
