package just.mvp.base;

import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

import just.mvp.ViewData;

public final class BundleData implements ViewData {

    @NonNull
    private final BundleProvider bundleProvider;

    public BundleData(@NonNull BundleProvider bundleProvider) {
        this.bundleProvider = bundleProvider;
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        final Bundle bundle = bundleProvider.getBundle();
        return null != bundle ? bundle.getString(key) : null;
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        final Bundle bundle = bundleProvider.getBundle();
        return null != bundle ? bundle.getInt(key, defaultValue) : defaultValue;
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        final Bundle bundle = bundleProvider.getBundle();
        return null != bundle ? bundle.getLong(key, defaultValue) : defaultValue;
    }

    @Nullable
    @Override
    public Bundle getBundle(@NonNull String key) {
        final Bundle bundle = bundleProvider.getBundle();
        return null != bundle ? bundle.getBundle(key) : null;
    }

    @Nullable
    @Override
    public <T extends Parcelable> T getParcelable(@NonNull String key) {
        final Bundle bundle = bundleProvider.getBundle();
        return null != bundle ? bundle.getParcelable(key) : null;
    }

    @Nullable
    @Override
    public <T extends Serializable> T getSerializable(@NonNull String key) {
        final Bundle bundle = bundleProvider.getBundle();
        //noinspection unchecked
        return null != bundle ? (T) bundle.getSerializable(key) : null;
    }

    @Nullable
    @Override
    public Bundle getBundle() {
        return bundleProvider.getBundle();
    }

    @FunctionalInterface
    public interface BundleProvider {
        @Nullable
        Bundle getBundle();
    }
}
