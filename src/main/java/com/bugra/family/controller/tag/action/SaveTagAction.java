package com.bugra.family.controller.tag.action;

import javax.persistence.EntityManager;

import com.bugra.family.businessrule.Action;
import com.bugra.family.businessrule.Result;
import com.bugra.family.entity.Tag;
import com.bugra.family.entity.TagDTO;

public class SaveTagAction implements Action {

	private EntityManager entityManager;
	private TagDTO tagDTO;
	
	public SaveTagAction(TagDTO tagDTO, EntityManager entityManager) {
		this.tagDTO = tagDTO;
		this.entityManager = entityManager;
	}
	
	@Override
	public Result execute() {
		try {
			Tag newTag = new Tag();
			
			newTag.setName(tagDTO.getName());
			newTag.setIconText(tagDTO.getIconText());
			newTag.setColor(tagDTO.getColor());
			newTag.setId(tagDTO.getId());
			
			if(tagDTO.getId() != null) {
				entityManager.merge(newTag);
			} else {
				entityManager.persist(newTag);
			}			
		} catch(Exception e) {
			return new Result("HATA: " + e.getMessage(), true);
		}
		return new Result("Etiket basariyla eklendi.", false);
	}

}
