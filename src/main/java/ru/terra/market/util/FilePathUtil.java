package ru.terra.market.util;

public class FilePathUtil
{

	private static FilePathUtil filePathUtil = new FilePathUtil();
	// private String webContextRelativePath = "";
	private String webContextAbsolutePath = "";

	private FilePathUtil()
	{
	}

	public static FilePathUtil getInstance()
	{
		return filePathUtil;
	}

	public void setAbsoluteWebContextPath(String path)
	{
		webContextAbsolutePath = path.replace("\\", "/");
	}

	public String getAbsoluteWebContextPath()
	{
		return webContextAbsolutePath;
	}

	public static String concatenateAbsolutePath(String[] pathParts)
	{
		String defaultSeparator = "/";
		StringBuffer resultPath = new StringBuffer();
		for (String path : pathParts)
		{
			path = path.endsWith(defaultSeparator) ? path.substring(0, path.length() - 1) : path;
			path = path.startsWith(defaultSeparator) && resultPath.length() > 0 ? path.substring(1, path.length()) : path;
			resultPath.append(path);
			resultPath.append(defaultSeparator);
		}
		String resultString = resultPath.toString();
		resultString = resultString.endsWith(defaultSeparator) ? resultString.substring(0, resultString.length() - 1) : resultString;
		return resultString;
	}
}