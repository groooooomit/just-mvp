package just.mvp.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Fragment 的简单封装，设置布局的两种方式：
 * 1、重写 {@link SimpleDialogFragment#getLayoutResId()} 方法
 * 2、在类上增加 {@link LayoutResId} 注解
 */
public class SimpleDialogFragment extends DialogFragment {

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
        // 去掉标题
        final Dialog dialog = getDialog();
        if (null != dialog) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        final int layoutResId = getLayoutResId();
        if (0 != layoutResId) {
            final View rootView = inflater.inflate(layoutResId, container, false);
            rootView.setClickable(true);// 防止点击穿透
            return rootView;
        } else {
            return null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // 在 onStart 后对 window 修改宽高才能够生效
        final Dialog dialog = getDialog();
        if (dialog != null) {
            final Window window = dialog.getWindow();
            if (null != window) {
                onWindowPrepare(window);
            }
        }
    }

    /**
     * 可以在此重置 window 的 size
     */
    public void onWindowPrepare(@NonNull Window window) {
    }

}
