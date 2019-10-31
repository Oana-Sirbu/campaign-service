package de.rakuten.campaign.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

import static de.rakuten.campaign.commons.Constants.DATE_TIME_FORMAT;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
  private LocalDateTime timestamp;

  private int status;
  private String message;
}
