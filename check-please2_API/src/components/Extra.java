/** @author Ryan Gaffney */

package components;

import components.Table.Customers;

public final class Extra
{
    public static final String NONTABLE_FIELD_RADIUS_IN_MILES = "radius_in_miles";
    public static final String NONTABLE_FIELD_OM_SLEEP_TIME_IN_MILLISECONDS = "om_sleep_time_in_milliseconds";
    public static final String NONTABLE_FIELD_NEW_EMAIL = "new_" + Customers.email.name();
    public static final String NONTABLE_FIELD_NEW_USERNAME = "new_" + Customers.username.name();
    public static final String NONTABLE_FIELD_NEW_PASSWORD = "new_" + Customers.password.name();
    public static final String NONTABLE_FIELD_NEW_FIRST_NAME = "new_" + Customers.firstName.name();
    public static final String NONTABLE_FIELD_NEW_LAST_NAME = "new_" + Customers.lastName.name();
    public static final String NONTABLE_FIELD_NEW_BALANCE = "new_" + Customers.balance.name();
}
