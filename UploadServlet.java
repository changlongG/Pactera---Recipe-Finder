package recipesfinder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "upload";

	private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50; // 50MB

	public static String CSVfilePath;
	public static String JsonfilePath;
	RecipesFinder rf = new RecipesFinder();

	public String getCSVfilePath() {
		return CSVfilePath;
	}

	public String getJsonfilePath() {
		return JsonfilePath;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!ServletFileUpload.isMultipartContent(request)) {
			PrintWriter writer = response.getWriter();
			writer.println("Error: no enctype=multipart/form-data");
			writer.flush();
			return;
		}

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MEMORY_THRESHOLD);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setFileSizeMax(MAX_FILE_SIZE);

		upload.setSizeMax(MAX_REQUEST_SIZE);

		upload.setHeaderEncoding("UTF-8");

		String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;

		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			@SuppressWarnings("unchecked")
			List<FileItem> formItems = upload.parseRequest(request);

			if (formItems != null && formItems.size() > 1) {
				for (FileItem item : formItems) {
					if (!item.isFormField()) {
						String fileName = new File(item.getName()).getName();
						if (fileName.contains("csv")) {
							CSVfilePath = uploadPath + File.separator + fileName;
						}
						if (fileName.contains("json")) {
							JsonfilePath = uploadPath + File.separator + fileName;
						}
						String filePath = uploadPath + File.separator + fileName;
						File storeFile = new File(filePath);
						// System.out.println(filePath);
						item.write(storeFile);

					}
				}
				// System.out.println(CSVfilePath);
				// System.out.println(JsonfilePath);
				request.setAttribute("message", rf.execute());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			request.setAttribute("message", "error: " + ex.getMessage());
		}

		getServletContext().getRequestDispatcher("/test/message.jsp").forward(request, response);
	}
}