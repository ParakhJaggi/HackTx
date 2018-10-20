package alloy.util;

/**
 * Created by jlutteringer on 1/15/18.
 */
public interface Identifiable extends Momento<Long> {
	String DEFAULT_FIELD_NAME = "id";

	Long getId();

	default Long getMomento() {
		return this.getId();
	}
}