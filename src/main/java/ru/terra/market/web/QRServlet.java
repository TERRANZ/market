package ru.terra.market.web;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import ru.terra.market.constants.FilePathConstants;
import ru.terra.market.constants.URLConstants;
import ru.terra.market.helper.QRHelper;
import ru.terra.market.util.FilePathUtil;

public class QRServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3095083436034592947L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String qrtext = request.getParameter("text");
		String prodId = request.getParameter("product");
		if (prodId != null) {
			qrtext = URLConstants.SERVER_URL + URLConstants.Pages.PRODUCT + "?id=" + prodId;
		}
		response.setContentType("image/png");
		ByteArrayOutputStream out = getFromCache(request, qrtext);
		if (out == null) {
			out = QRHelper.gen(qrtext);
			writeToCache(request, out, qrtext);
		}

		response.sendRedirect("/market" + FilePathConstants.QR_DIR + "/" + String.valueOf(qrtext.hashCode()) + ".png");
		// response.setContentLength(out.size());
		// OutputStream outStream = response.getOutputStream();
		// outStream.write(out.toByteArray());
		// outStream.flush();
		// outStream.close();
	}

	private ByteArrayOutputStream getFromCache(HttpServletRequest request, String qrtext) {
		FilePathUtil fpu = FilePathUtil.getInstance();
		fpu.setAbsoluteWebContextPath(request.getSession().getServletContext().getRealPath(""));
		String basePath = fpu.getAbsoluteWebContextPath();
		String subPath = FilePathConstants.QR_DIR;
		File dir = new File(basePath + subPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String outFileName = String.valueOf(qrtext.hashCode()) + ".png";
		File localFile = new File(basePath + subPath + "/" + outFileName);
		if (localFile.exists()) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			try {
				baos.write(FileUtils.readFileToByteArray(localFile));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return baos;
		}
		return null;
	}

	private void writeToCache(HttpServletRequest request, ByteArrayOutputStream baos, String qrtext) {
		FilePathUtil fpu = FilePathUtil.getInstance();
		fpu.setAbsoluteWebContextPath(request.getSession().getServletContext().getRealPath(""));
		String basePath = fpu.getAbsoluteWebContextPath();
		String subPath = FilePathConstants.QR_DIR;
		File dir = new File(basePath + subPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String outFileName = String.valueOf(qrtext.hashCode()) + ".png";
		File localFile = new File(basePath + subPath + "/" + outFileName);
		try {
			FileUtils.writeByteArrayToFile(localFile, baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}