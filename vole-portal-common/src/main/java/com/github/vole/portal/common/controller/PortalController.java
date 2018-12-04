package com.github.vole.portal.common.controller;

import com.github.vole.common.utils.R;
import org.springframework.ui.Model;

import java.util.List;

public interface PortalController<T> {


    String list(Integer pageNumber, Integer pageSize, String search, Model model);

    String add(Model model);

    R<Boolean> doAdd(T entity);

    R<Boolean> delete(String id);

    R<Boolean> deleteBatch(List<String> ids);

    String edit(String id, Model model);

    R<Boolean>  doEdit(T entity, Model model);
}
