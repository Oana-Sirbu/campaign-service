package de.rakuten.campaign.commons;

public class Constants {
  public static final String BAD_INPUT_ERROR_MESSAGE = "Bad input parameter";
  public static final String NOT_FOUND_ERROR_MESSAGE = "Not found";
  public static final String CAMPAIGN_EXIST_ERROR_MESSAGE = "Campaign already exists";

  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  public static final String RECEIVED_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  public static final String DATE_REGEX_PATTERN =
      "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$";
  public static final String UUID_REGEX_PATTERN =
      "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";
}
