package just.mvp.viewdata;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

public interface ViewData {

    @Nullable
    String getString(@NonNull String key);

    int getInt(@NonNull String key, int defaultValue);

    long getLong(@NonNull String key, long defaultValue);

    @Nullable
    Bundle getBundle(@NonNull String key);

    @Nullable
    <T extends Parcelable> T getParcelable(@NonNull String key);

    @Nullable
    <T extends Serializable> T getSerializable(@NonNull String key);

    @Nullable
    Bundle getBundle();
}
