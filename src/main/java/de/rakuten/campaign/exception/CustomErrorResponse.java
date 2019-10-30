package de.rakuten.campaign.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

import static de.rakuten.campaign.commons.Constants.CUSTOM_ERROR_DATE_FORMAT;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CUSTOM_ERROR_DATE_FORMAT)
  private LocalDateTime timestamp;

  private int status;
  private String message;
}
