package tais.admin.users;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class UserAccountStates.
 * @author siva.ku This class sets and gets user account states and modifiable
 *         sates.
 */
public class UserAccountStates {

    /** The user account status. */
    private Map < String, String > userAccountStatus =
            new HashMap < String, String >();

    /** The modifiable user account status. */
    private Map < String, String > modifiableUserAccountStatus =
            new HashMap < String, String >();

    /**
     * Gets the user account status.
     * @return the user account status
     */
    public final Map < String, String > getUserAccountStatus() {
        return userAccountStatus;
    }

    /**
     * Sets the user account status.
     * @param pUserAccountStatus
     *        the user account status
     */
    public final void setUserAccountStatus(
            final Map < String, String > pUserAccountStatus) {
        this.userAccountStatus = pUserAccountStatus;
    }

    /**
     * Gets the modifiable user account status.
     * @return the modifiable user account status
     */
    public final Map < String, String > getModifiableUserAccountStatus() {
        return modifiableUserAccountStatus;
    }

    /**
     * Sets the modifiable user account status.
     * @param pModifiableUserAccountStatus
     *        the modifiable user account status
     */
    public final void setModifiableUserAccountStatus(
            final Map < String, String > pModifiableUserAccountStatus) {
        this.modifiableUserAccountStatus = pModifiableUserAccountStatus;
    }
}
