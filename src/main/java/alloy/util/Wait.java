package alloy.util;

import java.time.Duration;

/**
 * Created by jlutteringer on 3/16/17.
 */
public class Wait {
	public static final Duration DEFAULT_WAIT = Duration.ofMillis(250);

	public static void await() {
		await(DEFAULT_WAIT);
	}

	public static void await(Duration duration) {
		_Exceptions.propagate(() -> Thread.sleep(duration.toMillis()));
	}
}