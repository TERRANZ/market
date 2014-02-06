package ru.terra.market.dto.group;

import java.util.List;

public class GroupTreeDTO {
	public List<GroupTreeDTO> childs;
	public GroupDTO group;
	private boolean hasChilds = false;

	public GroupTreeDTO(List<GroupTreeDTO> childs, GroupDTO category) {
		this.childs = childs;
		this.group = category;
	}

	public boolean isHasChilds() {
		hasChilds = childs != null && childs.size() > 0;
		return hasChilds;
	}
}
