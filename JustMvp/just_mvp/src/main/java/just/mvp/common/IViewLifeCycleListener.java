package just.mvp.common;

public interface IViewLifeCycleListener {

	default void afterViewCreate() {
	}

	default void afterViewStart() {
	}

	default void afterViewResume() {
	}

	default void beforeViewPause() {
	}

	/**
	 * 在 Activity 的主题是 Dialog 或 Translucent 时，启动的 Activity 的生命周期不会触发 onStop()
	 */
	default void beforeViewStop() {
	}

	default void beforeViewDestroy() {
	}

	default void onViewAny() {
	}
}
