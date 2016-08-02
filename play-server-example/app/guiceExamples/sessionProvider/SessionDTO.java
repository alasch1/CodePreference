package guiceExamples.sessionProvider;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionDTO {
	private static final String EMPTY = "";

	private String uuid=EMPTY;
	private String data=EMPTY;
	private String recentTimestamp=EMPTY;
	
	public SessionDTO() {
	}

	public void start() {
		uuid = java.util.UUID.randomUUID().toString();
		updateTimestamp();
	}
	
	/**
	 * Updates the session timestamp with the current time
	 */
	public void touch() {
		updateTimestamp();
	}
	
	/**
	 * Resets all session id values; such session ID represents an ended session
	 */
	public void reset() {
		init();
	}
	
	public boolean isStarted() {
		return (uuid.compareTo(EMPTY) != 0);
	}
	
	private void init() {
		uuid = EMPTY;
		recentTimestamp = EMPTY;
		data = EMPTY;
	}
	
	private void updateTimestamp() {
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
		recentTimestamp = formatter.format(date.truncatedTo(ChronoUnit.SECONDS));		
	}

	@Override
	public String toString() {
		return String.format(
				"session [uuid=%s, data=%s, recentTimestamp=%s]", uuid,
				data, recentTimestamp);
	}
	
}
