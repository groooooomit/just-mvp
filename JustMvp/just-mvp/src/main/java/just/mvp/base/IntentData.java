package just.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;

import just.mvp.ViewData;

public final class IntentData implements ViewData {

    @NonNull
    private final IntentProvider intentProvider;

    public IntentData(@NonNull IntentProvider intentProvider) {
        this.intentProvider = intentProvider;
    }

    @Nullable
    @Override
    public String getString(@NonNull String key) {
        final Intent intent = intentProvider.getIntent();
        return null != intent ? intent.getStringExtra(key) : null;
    }

    @Override
    public int getInt(@NonNull String key, int defaultValue) {
        final Intent intent = intentProvider.getIntent();
        return null != intent ? intent.getIntExtra(key, defaultValue) : defaultValue;
    }

    @Override
    public long getLong(@NonNull String key, long defaultValue) {
        final Intent intent = intentProvider.getIntent();
        return null != intent ? intent.getLongExtra(key, defaultValue) : defaultValue;
    }

    @Nullable
    @Override
    public Bundle getBundle(@NonNull String key) {
        final Intent intent = intentProvider.getIntent();
        return null != intent ? intent.getBundleExtra(key) : null;
    }

    @Nullable
    @Override
    public <T extends Parcelable> T getParcelable(@NonNull String key) {
        final Intent intent = intentProvider.getIntent();
        return null != intent ? intent.getParcelableExtra(key) : null;
    }

    @Nullable
    @Override
    public <T extends Serializable> T getSerializable(@NonNull String key) {
        final Intent intent = intentProvider.getIntent();
        //noinspection unchecked
        return null != intent ? (T) intent.getSerializableExtra(key) : null;
    }

    @Nullable
    @Override
    public Bundle getBundle() {
        final Intent intent = intentProvider.getIntent();
        return null != intent ? intent.getExtras() : null;
    }

    @FunctionalInterface
    public interface IntentProvider {
        @Nullable
        Intent getIntent();
    }
}
