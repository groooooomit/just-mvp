package just.mvp.widget;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Fragment 的简单封装，设置布局的两种方式：
 * 1、重写 {@link SimpleFragment#getLayoutResId()} 方法
 * 2、在类上增加 {@link LayoutResId} 注解
 */
public class SimpleFragment extends Fragment {

    /**
     * 如果不想使用注解来声明布局也可以重写此方法直接返回布局资源 ID
     */
    @LayoutRes
    protected int getLayoutResId() {
        final LayoutResId layoutResId = this.getClass().getAnnotation(LayoutResId.class);
        return null == layoutResId ? 0 : layoutResId.value();
    }

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

}
