package tais.userprofiling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

import javax.servlet.ServletException;

import atg.droplet.DropletException;
import atg.repository.MutableRepository;
import atg.repository.MutableRepositoryItem;
import atg.repository.RepositoryException;
import atg.repository.RepositoryItem;
import atg.scenario.userprofiling.ScenarioProfileFormHandler;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.UploadedFile;
import atg.userprofiling.Profile;

public class TaisFoodExpenseFormHandler extends ScenarioProfileFormHandler {

	private String addExpSuccessUrl;

	private String addExpErrorUrl;

	public boolean handleAddExpense(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {
		if (getFormError()) {
			return checkFormRedirect(addExpSuccessUrl, addExpErrorUrl,
					pRequest, pResponse);
		}
		try {
			createExpenseItem(getValue(), getProfile());
		} catch (RepositoryException e) {
			addFormException(new DropletException("Found repository exception."));
		} catch (ParseException e) {
			addFormException(new DropletException(
					"Found ParseException exception."));
		}
		return checkFormRedirect(addExpSuccessUrl, addExpErrorUrl, pRequest,
				pResponse);

	}

	public String getAddExpSuccessUrl() {
		return addExpSuccessUrl;
	}

	public void setAddExpSuccessUrl(String addExpSuccessUrl) {
		this.addExpSuccessUrl = addExpSuccessUrl;
	}

	public String getAddExpErrorUrl() {
		return addExpErrorUrl;
	}

	public void setAddExpErrorUrl(String addExpErrorUrl) {
		this.addExpErrorUrl = addExpErrorUrl;
	}
	
	@SuppressWarnings("rawtypes")
	public void createExpenseItem(Dictionary dictionary,Profile profile) throws RepositoryException, ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("mm/dd/yyyy");
		Date expDate = formatter.parse(dictionary.get("expenseDate").toString());
		Double amount = Double.valueOf(dictionary.get("amount").toString());
		String expenseType = (String) dictionary.get("expenseType").toString();
		String proofUrl = (String) dictionary.get("expenseProofUrl").toString();
		MutableRepository profileRepo = getProfileTools().getProfileRepository();
		MutableRepositoryItem foodExpItem = profileRepo
				.createItem("foodExpenditure");
		foodExpItem.setPropertyValue("expenseDate",	expDate);
		foodExpItem.setPropertyValue("amount", amount );
		foodExpItem.setPropertyValue("expenseType", expenseType );
		foodExpItem.setPropertyValue("expenseProofUrl", proofUrl );
		foodExpItem.setPropertyValue("status", "unsettled");
		foodExpItem.setPropertyValue("descriptioin",
				(String)dictionary.get("descriptioin"));
		profileRepo.addItem(foodExpItem);
		List<RepositoryItem> foodItem = (List<RepositoryItem>) profile.getPropertyValue("foodExpense");
		foodItem.add(foodExpItem);
		profile.setPropertyValue("foodExpense", foodItem);
	}
	
	/**
	 * This method is called when the form above is submitted. This code makes
	 * sure that it has an appropriate object and then pass it along for further
	 * processing.
	 * 
	 * @param Object
	 *            either an UploadedFile or an UploadedFile[]
	 **/
	public void setUploadProperty(Object fileObject) {
		if (fileObject == null) {
			System.err
					.println("**** ERROR: FileUploadDroplet received a NULL file.");
			addFormException(new DropletException(
					"FileUploadDroplet received a NULL file."));
			return;
		}

		if (fileObject != null && fileObject instanceof UploadedFile) {
			UploadedFile file = (UploadedFile) fileObject;
			String fileType = file.getContentType();
			if (!getSupportedFileTypes().contains(fileType)) {
				addFormException(new DropletException(
						"Un Suppported file type..."));
				return;
			}

		}
		if (fileObject instanceof UploadedFile[]) {
			System.out.println("Reading in UploadedFile[]");
			readUpFiles((UploadedFile[]) fileObject);
		} else if (fileObject instanceof UploadedFile) {
			readUpFiles(new UploadedFile[] { (UploadedFile) fileObject });
		} else {
			System.err
					.print("**** ERROR: FileUploadDroplet received an Object which is "
							+ "neither an UploadedFile or an UploadedFile[].");
			addFormException(new DropletException(
					"FileUploadDroplet received an Object which is "
							+ "neither an UploadedFile."));
		}
	}

	
	/**
	 * Returns property UploadProperty
	 **/
	public Object getUploadProperty() {
		// return null since we don't need to maintain a
		// reference to the original uploaded file(s)
		return null;
	}

	// -------------------------------------
	/**
	 * Here you can access the data in the uploaded file(s). You should get the
	 * data from the uploaded file before the request is complete. If the file
	 * is large, it is stored as a temporary file on disk, and this file is
	 * removed when the request is complete.
	 * 
	 * @param UploadedFile
	 *            [] the uploaded file(s)
	 **/
	@SuppressWarnings("unchecked")
	void readUpFiles(UploadedFile[] pFiles) {
		UploadedFile upFile = null;
		String clientFilePath = null;
		String fileName = null;
		File localFile = null;
		FileOutputStream fos = null;
		byte[] fileData = null;

		for (int i = 0; i < pFiles.length; i++) {
			upFile = pFiles[i];
			clientFilePath = upFile.getFilename();

			// Check that file uploaded is not size 0.
			if (upFile.getFileSize() <= 0) {
				System.err
						.println(" FileUploadDroplet Cannot upload - file has length 0: "
								+ clientFilePath);
				return;
			}
			/**
			 * Extract the FilePath, which is the file location provided by the
			 * browser client. Convert the file separator character to use the
			 * one accepted by the web client's Operating system.
			 **/

			String otherSeparator = "/";
			if ("/".equals(File.separator))
				otherSeparator = "\\";
			String convertedClientFilePath = atg.core.util.StringUtils.replace(
					clientFilePath, otherSeparator, File.separator);

			fileName = new String(
					convertedClientFilePath.substring(convertedClientFilePath
							.lastIndexOf(File.separator) + 1));

			// Construct a local file (using the uploaded file directory)
			localFile = new File(mUploadDirectory + File.separator + fileName);

			// You can either get the file as an array of bytes ...
			try {
				fileData = upFile.toByteArray();
				System.out.println(" ** client filename: " + clientFilePath);
				System.out.println(" ** client file is " + upFile.getFileSize()
						+ " bytes long.");
				fos = new FileOutputStream(localFile);
				getValue().put("profilePhotoUrl",
						"/tais/expense_proofs/" +getProfile().getRepositoryId()+clientFilePath);
				fos.write(fileData);
				fos.flush();
			} catch (IOException e) {
				System.err.println("FileUploadDroplet failed");
				e.printStackTrace();
			} finally {
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException exc) {
						exc.printStackTrace();
					}
				}// end try/catch
			}// end finally

			// ... or you can read the data yourself from the input stream.
			/**
			 * try{ InputStream is = upFile.getInputStream(); ... } catch
			 * (IOException e) { }
			 **/
		}// end for
	}// end readUpFiles

	// -------------------------------------
	// property: UploadDirectory
	// where we will put the uploaded file
	private String mUploadDirectory;
	
	private List<String> supportedFileTypes;
	
	public List<String> getSupportedFileTypes() {
		return supportedFileTypes;
	}

	public void setSupportedFileTypes(List<String> supportedFileTypes) {
		this.supportedFileTypes = supportedFileTypes;
	}

	/**
	 * Sets property UploadDirectory
	 **/
	public void setUploadDirectory(String pUploadDirectory) {
		mUploadDirectory = pUploadDirectory;
	}

	/**
	 * Returns property UploadDirectory
	 **/
	public String getUploadDirectory() {
		return mUploadDirectory;
	}

}
