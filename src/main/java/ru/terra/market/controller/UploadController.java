package ru.terra.market.controller;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import ru.terra.market.constants.FilePathConstants;
import ru.terra.market.constants.URLConstants;
import ru.terra.market.dto.UploadDTO;
import ru.terra.market.engine.PhotoEngine;
import ru.terra.market.util.FilePathUtil;
import ru.terra.market.web.security.SessionHelper;

@Controller
public class UploadController
{

	@Inject
	private PhotoEngine pe;
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@RequestMapping(value = URLConstants.Pages.UPLOAD, method = RequestMethod.GET)
	public String getUploadForm(HttpServletRequest request, Model model)
	{
		if (SessionHelper.isUserCurrentAuthorized())
		{
			UploadDTO uploadDTO = new UploadDTO();

			uploadDTO.setTarget("product");
			try
			{
				uploadDTO.setTargetId(Integer.parseInt(request.getParameter("product")));
				logger.info("target id = " + Integer.parseInt(request.getParameter("product")));
			} catch (Exception e)
			{
				uploadDTO.setTargetId(0);
				logger.info("unable to parse target id: ", e);
			}
			model.addAttribute(uploadDTO);
			return URLConstants.Views.UPLOAD;
		}
		else
		{
			return URLConstants.Views.ERROR404;
		}
	}

	@RequestMapping(value = URLConstants.Pages.UPLOAD, method = RequestMethod.POST)
	public String create(HttpServletRequest request, UploadDTO uploadDTO, BindingResult result)
	{
		logger.info("create : targetId = " + request.getParameter("targetId"));
		if (result.hasErrors())
		{
			for (ObjectError error : result.getAllErrors())
			{
				System.err.println("Error: " + error.getCode() + " - " + error.getDefaultMessage());
			}
			return URLConstants.Views.UPLOAD;
		}

		CommonsMultipartFile multipartFile = uploadDTO.getFileData();

		FilePathUtil fpu = FilePathUtil.getInstance();
		fpu.setAbsoluteWebContextPath(request.getSession().getServletContext().getRealPath(""));
		String basePath = fpu.getAbsoluteWebContextPath();
		String subPath = FilePathConstants.PRODUCT_IMAGES_DIR;
		File dir = new File(basePath + subPath);
		if (!dir.exists())
		{
			dir.mkdir();
		}
		String outFileName = multipartFile.getOriginalFilename();
		File localFile = new File(basePath + subPath + "/" + outFileName);
		try
		{
			multipartFile.transferTo(localFile);
		} catch (IllegalStateException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		pe.newPhoto(uploadDTO.getName(), subPath + "/" + outFileName, uploadDTO.getTargetId());

		return "redirect:/";
	}
}