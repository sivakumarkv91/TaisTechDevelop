package tais.droplet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import atg.repository.RepositoryItem;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.DynamoServlet;
import atg.userprofiling.Profile;

public class TaisFoodExpenseItemDroplet extends DynamoServlet {

	public static final String OUT_PUT = "output";

	private Profile mProfile;

	/**
	 * @return Profile object
	 */
	public Profile getProfile() {
		return mProfile;
	}

	/**
	 * @param pProfile
	 *            Profile object
	 */
	public void setProfile(Profile pProfile) {
		mProfile = pProfile;
	}

	@SuppressWarnings("unchecked")
	public void service(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {
		List<RepositoryItem> foodExpItemList = (List<RepositoryItem>) getProfile()
				.getPropertyValue("foodExpense");
		pRequest.setParameter("foodExpenseItemList", foodExpItemList);
		pRequest.serviceLocalParameter(OUT_PUT, pRequest, pResponse);
	}
}
