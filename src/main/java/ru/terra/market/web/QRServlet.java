package ru.terra.market.web;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.terra.market.constants.URLConstants;
import ru.terra.market.helper.QRHelper;

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
		ByteArrayOutputStream out = QRHelper.gen(qrtext);
		response.setContentLength(out.size());

		OutputStream outStream = response.getOutputStream();

		outStream.write(out.toByteArray());

		outStream.flush();
		outStream.close();
	}
}