package com.swifta.onerecharge.countryinfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by moyinoluwa on 1/30/17.
 */

public class Data {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("flag_url")
    @Expose
    private String flagUrl;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("added_type")
    @Expose
    private String addedType;
    @SerializedName("currency_symbol_before")
    @Expose
    private Boolean currencySymbolBefore;
    @SerializedName("regions")
    @Expose
    private List<String> regions = null;
    @SerializedName("networks")
    @Expose
    private List<String> networks = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public void setFlagUrl(String flagUrl) {
        this.flagUrl = flagUrl;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedType() {
        return addedType;
    }

    public void setAddedType(String addedType) {
        this.addedType = addedType;
    }

    public Boolean getCurrencySymbolBefore() {
        return currencySymbolBefore;
    }

    public void setCurrencySymbolBefore(Boolean currencySymbolBefore) {
        this.currencySymbolBefore = currencySymbolBefore;
    }

    public List<String> getRegions() {
        return regions;
    }

    public void setRegions(List<String> regions) {
        this.regions = regions;
    }

    public List<String> getNetworks() {
        return networks;
    }

    public void setNetworks(List<String> networks) {
        this.networks = networks;
    }

}
