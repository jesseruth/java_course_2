package edu.uw.cp520.scg.util;

import java.util.HashMap;
import java.util.Locale;

/**
 * U. S. Postal state codes.
 *
 * @author Jesse Ruth
 */
public enum StateCode {
  AK("ALASKA"),
  AL("ALABAMA"),
  AR("ARKANSAS"),
  AS("AMERICAN SAMOA"),
  AZ("ARIZONA"),
  CA("CALIFORNIA"),
  CO("COLORADO"),
  CT("CONNECTICUT"),
  DC("DISTRICT OF COLUMBIA"),
  DE("DELAWARE"),
  FL("FLORIDA"),
  FM("FEDERATED STATES OF MICRONESIA"),
  GA("GEORGIA"),
  GU("GUAM"),
  HI("HAWAII"),
  IA("IOWA"),
  ID("IDAHO"),
  IL("ILLINOIS"),
  IN("INDIANA"),
  KS("KANSAS"),
  KY("KENTUCKY"),
  LA("LOUISIANA"),
  MA("MASSACHUSETTS"),
  MD("MARYLAND"),
  ME("MAINE"),
  MH("MARSHALL ISLANDS"),
  MI("MICHIGAN"),
  MN("MINNESOTA"),
  MO("MISSOURI"),
  MP("NORTHERN MARIANA ISLANDS"),
  MS("MISSISSIPPI"),
  MT("MONTANA"),
  NC("NORTH CAROLINA"),
  ND("NEVADA"),
  NE("NEBRASKA"),
  NH("NEW HAMPSHIRE"),
  NJ("NEW JERSEY"),
  NM("NEW MEXICO"),
  NV("NEVADA"),
  NY("NEW YORK"),
  OH("OHIO"),
  OK("OKLAHOMA"),
  OR("OREGON"),
  PA("PENNSYLVANIA"),
  PR("PUERTO RICO"),
  PW("PALAU"),
  RI("RHODE ISLAND"),
  SC("SOUTH CAROLINA"),
  SD("SOUTH DAKOTA"),
  TN("TENNESSEE"),
  TX("TEXAS"),
  UT("UTAH"),
  VA("VIRGINIA"),
  VI("VIRGIN ISLANDS"),
  VT("VERMONT"),
  WA("WASHINGTON"),
  WI("WISCONSIN"),
  WV("WEST VIRGINIA"),
  WY("WYOMING");

  /**
   * Cache states by label for faster lookup.
   */
  private static final HashMap<String, StateCode> BY_LABEL = new HashMap<>();

  static {
    for (StateCode state : values()) {
      BY_LABEL.put(state.getName().toUpperCase(Locale.US), state);
    }
  }

  /**
   * Friendly name for state
   **/
  private final String friendlyName;

  /**
   *
   * @param friendlyName
   */
  private StateCode(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  /**
   * Returns the name of the state.
   *
   * @return the name of the state;
   */
  public String getName() {
    return friendlyName;
  }

  /**
   * Looks up a StateCode by the state name.
   *
   * @param stateName the name of the state to lookup
   * @return the StateCode corresponding the state name, or null if the state name is not recognized.
   */
  public StateCode forName(String stateName) {
    return BY_LABEL.get(stateName);
  }
}
