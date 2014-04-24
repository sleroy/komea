/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.komea.product.wicket.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.komea.product.backend.service.generic.IGenericService;
import org.komea.product.database.api.IHasKey;

/**
 *
 * @author rgalerme
 */
public class DataListSelectDialogBuilder implements Serializable {

    private MarkupContainer page;
    private String idList;
    private String idDialog;
    private String idBtnAdd;
    private String idBtnDel;
    private String nameFieldResult;
    private String displayDialogMessage;
    private List<IHasKey> currentEntityList;
    private List<IHasKey> choiceEntityList;
    private List<IHasKey> selectDialogList;
    private IGenericService _service;
    private final List<CustomUpdater> updaters;
    private final List<ICustomFilter> filters;
    private ListMultipleChoice<IHasKey> listEntite = null;

    public DataListSelectDialogBuilder() {
        this.updaters = new ArrayList<CustomUpdater>();
        this.filters = new ArrayList<ICustomFilter>();
    }

    public MarkupContainer getPage() {
        return page;
    }

    public void setPage(MarkupContainer page) {
        this.page = page;
    }

    public List<ICustomFilter> getFilters() {
        return filters;
    }

    public void setListEntite(ListMultipleChoice<IHasKey> listEntite) {
        this.listEntite = listEntite;
    }

    public ListMultipleChoice<IHasKey> getListEntite() {
        return listEntite;
    }

    public void addFilter(ICustomFilter filter) {
        this.filters.add(filter);
    }

    public List<CustomUpdater> getUpdaters() {
        return updaters;
    }

    public void addUpdater(CustomUpdater updater) {
        this.updaters.add(updater);
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getIdDialog() {
        return idDialog;
    }

    public void setIdDialog(String idDialog) {
        this.idDialog = idDialog;
    }

    public String getIdBtnAdd() {
        return idBtnAdd;
    }

    public void setIdBtnAdd(String idBtnAdd) {
        this.idBtnAdd = idBtnAdd;
    }

    public String getIdBtnDel() {
        return idBtnDel;
    }

    public void setIdBtnDel(String idBtnDel) {
        this.idBtnDel = idBtnDel;
    }

    public String getNameFieldResult() {
        return nameFieldResult;
    }

    public void setNameFieldResult(String nameFieldResult) {
        this.nameFieldResult = nameFieldResult;
    }

    public String getDisplayDialogMessage() {
        return displayDialogMessage;
    }

    public void setDisplayDialogMessage(String displayDialogMessage) {
        this.displayDialogMessage = displayDialogMessage;
    }

    public List<IHasKey> getCurrentEntityList() {
        return currentEntityList;
    }

    public void setCurrentEntityList(List<IHasKey> currentEntityList) {
        this.currentEntityList = currentEntityList;
    }

    public List<IHasKey> getChoiceEntityList() {
        return choiceEntityList;
    }

    public void setChoiceEntityList(List<IHasKey> choiceEntityList) {
        this.choiceEntityList = choiceEntityList;
    }

    public List<IHasKey> getSelectDialogList() {
        return selectDialogList;
    }

    public void setSelectDialogList(List<IHasKey> selectDialogList) {
        this.selectDialogList = selectDialogList;
    }

    public IGenericService getService() {
        return _service;
    }

    public void setService(IGenericService _service) {
        this._service = _service;
    }

}
