package tais.userprofiling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;

import nl.captcha.Captcha;

import atg.core.util.StringUtils;
import atg.droplet.DropletException;
import atg.scenario.userprofiling.ScenarioProfileFormHandler;
import atg.scenario.userprofiling.ScenarioPropertyManager;
import atg.servlet.DynamoHttpServletRequest;
import atg.servlet.DynamoHttpServletResponse;
import atg.servlet.UploadedFile;

public class TaisProfileFormhandler extends ScenarioProfileFormHandler {

	@Override
	protected void preCreateUser(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {
		super.preCreateUser(pRequest, pResponse);

		// Copy over user email to their login field
		ScenarioPropertyManager propertyManager = (ScenarioPropertyManager) getProfileTools()
				.getPropertyManager();
		String emailPropertyName = propertyManager
				.getEmailAddressPropertyName();
		String email = getStringValueProperty(emailPropertyName);

		// Store login in lower case to support case-insensitive logins
		String loginPropertyName = propertyManager.getLoginPropertyName();
		setValueProperty(loginPropertyName, email.toLowerCase());

		if (!email.equals(getConfirmEmail())) {
			addFormException(new DropletException(
					"Your email and confirm email is not same. Please provide same."));

		}
		validateCaptcha(pRequest,pResponse);

	}
	
	

	@Override
	protected void postCreateUser(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws ServletException,
			IOException {
		if(!getValue().get("designation").equals("Project Manager")){
			setValueProperty("accountStatus", "pendingApproval");
		} else {
			setValueProperty("accountStatus", "active");
			setValueProperty("isAdmin", true);
		}
		
		super.postCreateUser(pRequest, pResponse);
	}



	protected void validateCaptcha(DynamoHttpServletRequest pRequest,
			DynamoHttpServletResponse pResponse) throws UnsupportedEncodingException{
		if (StringUtils.isEmpty(getCaptchaString())) {

            addFormException(new DropletException("Please enter captcha."));
        } else {
            Boolean isResponseCorrect = Boolean.FALSE;
            Captcha captcha = (Captcha) pRequest.getSession().getAttribute(
                    Captcha.NAME);
            pRequest.setCharacterEncoding("UTF-8"); // Do this so we can
                                                    // capture non-Latin
                                                    // chars.

            isResponseCorrect = captcha.isCorrect(getCaptchaString());

            if (!isResponseCorrect) {
                addFormException(new DropletException("Wrong Captcha entered. " +
                		"Please enter valida one."));
            }
        }
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

	public List<String> getSupportedFileTypes() {
		return supportedFileTypes;
	}

	public void setSupportedFileTypes(List<String> supportedFileTypes) {
		this.supportedFileTypes = supportedFileTypes;
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
						"/tais/profilephotos/" + clientFilePath);
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
	String mUploadDirectory;

	private String confirmEmail;
	
	private String captchaString;

	public String getCaptchaString() {
		return captchaString;
	}

	public void setCaptchaString(String captchaString) {
		this.captchaString = captchaString;
	}

	List<String> supportedFileTypes;

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
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
