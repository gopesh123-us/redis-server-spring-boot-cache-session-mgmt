package live.learnjava.cachingsession.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSessionModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sessionId;
	private String username;
	private String role;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate lastAccessed;

}
