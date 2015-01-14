package tais.droplet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import tais.admin.users.ExpenseInfoBean;
import tais.admin.users.UserList;
import tais.admin.users.UserStatusManager;
import atg.nucleus.naming.ParameterName;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;

/**
 * The Class UserStatusDroplet.
 */

public class TaisFoodExpenseReportDroplet extends DynamoServlet {

    /** The Constant RESULT. */
    private static final String RESULT = "result";

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
       Map<Integer, List<ExpenseInfoBean>> results = null;
    	try {
    		results = getUserStatusManager().getSettledExpenseDetails();
    		
    		 if(results == null || results.isEmpty()){
        	     req.serviceLocalParameter(EMPTY, req, res);
        	 } else {
        		 for(int i=0;i<results.keySet().size();i++){
        			 req.setParameter(RESULT, results.get(i));
            	     req.serviceLocalParameter(OUTPUT, req, res); 
        		 }
        	    
        	 }
       
    	
		} catch (RepositoryException e) {
			e.printStackTrace();
		}
    	
    }
}
