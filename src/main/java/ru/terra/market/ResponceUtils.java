package ru.terra.market;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponceUtils
{
	public static ResponseEntity<String> makeResponce(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, headers, HttpStatus.OK);
	}

	public static ResponseEntity<String> makeErrorResponce(String json)
	{
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=utf-8");
		return new ResponseEntity<String>(json, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
