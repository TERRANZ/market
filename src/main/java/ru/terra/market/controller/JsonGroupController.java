package ru.terra.market.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.terra.market.constants.URLConstants;
import ru.terra.market.db.entity.Group;
import ru.terra.market.dto.category.GroupDTO;
import ru.terra.market.dto.category.GroupListDTO;
import ru.terra.market.dto.category.GroupTreeDTO;
import ru.terra.market.engine.GroupEngine;
import ru.terra.market.util.ResponceUtils;
import flexjson.JSONSerializer;

@Controller
public class JsonGroupController {
	@Inject
	private GroupEngine ce;

	@RequestMapping(value = URLConstants.DoJson.Group.GET_GROUP_TREE, method = { RequestMethod.GET, RequestMethod.POST })
	private ResponseEntity<String> getCategoryTree(HttpServletRequest request) {
		String json = new JSONSerializer().deepSerialize(getGroupsRecursive(1));
		return ResponceUtils.makeResponce(json);
	}

	@RequestMapping(value = URLConstants.DoJson.Group.GET_BY_PARENT, method = { RequestMethod.GET, RequestMethod.POST })
	private ResponseEntity<String> getCategoryByParent(HttpServletRequest request) {
		String parentId = request.getParameter(URLConstants.DoJson.Group.GROUP_PARAM_ID);
		GroupListDTO ret = new GroupListDTO();
		if (parentId == null)
			parentId = "-1";
		try {
			Integer pid = Integer.parseInt(parentId);
			for (Group cat : ce.getCategoriesByParent(pid)) {
				ret.data.add(new GroupDTO(cat));
			}
			ret.size = ret.data.size();
			String json = new JSONSerializer().deepSerialize(ret);
			return ResponceUtils.makeResponce(json);
		} catch (NumberFormatException e) {
			return ResponceUtils.makeErrorResponce("");
		}
	}

	private GroupTreeDTO getGroupsRecursive(Integer parent) {
		List<GroupTreeDTO> childs = new ArrayList<GroupTreeDTO>();
		for (Group child : ce.getCategoriesByParent(parent)) {
			childs.add(getGroupsRecursive(child.getId()));
		}
		return new GroupTreeDTO(childs, new GroupDTO(ce.getBean(parent)));
	}

	@RequestMapping(value = URLConstants.DoJson.Group.GET_GROUPS, method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<String> getCategories(HttpServletRequest request) {
		GroupListDTO ret = new GroupListDTO();
		for (Group cat : ce.listBeans(true, -1, -1))
			ret.data.add(new GroupDTO(cat));
		ret.size = ret.data.size();
		String json = new JSONSerializer().deepSerialize(ret);
		return ResponceUtils.makeResponce(json);
	}
}
