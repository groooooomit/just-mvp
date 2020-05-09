package just.mvp.view;


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

/**
 * Fragment 的简单封装，设置布局的两种方式：
 * 1、重写 {@link SimpleFragment#getLayoutResId()} 方法
 * 2、在类上增加 {@link LayoutResId} 注解
 */
// @LayoutResId(R.layout.fragment_xxx)
public class SimpleFragment extends Fragment {

    // --------------------------------------------------------------------------------------------------------------------------------------------------

    @LayoutRes
    protected int getLayoutResId() {
        final LayoutResId layoutResId = this.getClass().getAnnotation(LayoutResId.class);
        return null == layoutResId ? 0 : layoutResId.value();
    }

    // --------------------------------------------------------------------------------------------------------------------------------------------------

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final int layoutResId = getLayoutResId();
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
