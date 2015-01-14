package tais.droplet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import tais.admin.users.UserList;
import tais.admin.users.UserStatusManager;
import atg.nucleus.naming.ParameterName;
import atg.repository.RepositoryException;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;

/**
 * The Class UserStatusDroplet.
 */

public class UserStatusDroplet extends DynamoServlet {

    /** The Constant RESULT. */
    private static final String RESULT = "result";

    /** The Constant USER_EMAIL. */
    private static final String USER_EMAIL = "email";

    /** The Constant ACCOUNT_STATUS. */
    private static final String ACCOUNT_STATUS = "accountStatus";

    /** The Constant STARTINDEX. */
    private static final String STARTINDEX = "startIndex";

    /** The Constant NUMOFRECORDS. */
    private static final String NUMOFRECORDS = "numOfRecords";

    /** The Constant TOTALCOUNT. */
    private static final String TOTALCOUNT = "totalCount";

    /** The Constant OUTPUT. */
    private static final ParameterName OUTPUT = ParameterName
            .getParameterName("output");

    private static final ParameterName EMPTY = ParameterName
            .getParameterName("empty");
    /** The user status manager. */
    private UserStatusManager userStatusManager;

    /**
     * Gets the user status manager.
     * @return the user status manager
     */
    public final UserStatusManager getUserStatusManager() {
        return userStatusManager;
    }

    /**
     * Sets the user status manager.
     * @param manager
     *        the new user status manager
     */
    public final void setUserStatusManager(final UserStatusManager manager) {
        this.userStatusManager = manager;
    }

    /** The user list. */
    private List < UserList > userList = null;

    /**
     * Service.
     * @param req
     *        the req
     * @param res
     *        the res
     * @throws ServletException
     *         the servlet exception
     * @throws IOException
     *         Signals that an I/O exception has occurred.
     */
    @Override
    public final void service(final DynamoHttpServletRequest req,
            final DynamoHttpServletResponse res) throws ServletException,
            IOException {
        final String email = req.getParameter(USER_EMAIL);
        final String status = req.getParameter(ACCOUNT_STATUS);
        final String startIndex = "0";
        final String numOfRecords = "100";
        vlogDebug("Accountstatus:" + status);
        vlogDebug("Email:" + email);
        try {

            userList = userStatusManager.displayUsers(status, email,
                    startIndex, numOfRecords);
            vlogDebug("UserStatusDroplet:Fetching users");
        } catch (RepositoryException e) {
            final String err = "cannot find repository" + e.toString();
            vlogError(err);
        }
        int totalCount = 0;
        
        if (userList == null && userList.size() == 0) {
        	req.serviceLocalParameter(EMPTY, req, res);
        }
        if (userList != null && userList.size() > 0) {
            totalCount = userList.get(0).getTotalCount();
        }
        req.setParameter(TOTALCOUNT, totalCount);
        req.setParameter(RESULT, userList);
        req.serviceLocalParameter(OUTPUT, req, res);
    }
}
