package com.bestbuyapi.productsinfo;


import com.bestbuyapi.constants.EndPoints;

import com.bestbuyapi.constants.Path;
import com.bestbuyapi.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;


public class ProductsSteps {
    static  int productID;

    @Step("creating product with name :{0},type: {1},price:{2},upc:{3}, shipping:{4},manufacture :{5} , description :{6},model:{7},url:{8},image:{9}")
    public ValidatableResponse createproduct(String name, String type, int price, String upc, int shipping, String manufacturer, String description, String model, String url, String img) {

        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setUpc(upc);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setDescription(description);
        productsPojo.setModel(model);
        productsPojo.setShipping(shipping);
        productsPojo.setUrl(url);
        productsPojo.setImage(img);
      return   SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);
    }
    @Step ("getting product info by name:{0}")
    public HashMap<String,Object> getProductInfoByName(String name){
        String p1="data.findAll{it.name='";
        String p2="'}.get(0)";
      return     SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1+name+p2);
    }
    @Step("update product with product id :{0}, name :{1},type: {2},price:{3},upc:{4}, shipping:{5},manufacture :{6} , description :{7},model:{8},url:{9},image:{10}")
    public ValidatableResponse updateproduct(int productID,String name, String type, int price, String upc, int shipping, String manufacturer, String description, String model, String url, String img) {


        ProductsPojo productsPojo = new ProductsPojo();
        productsPojo.setName(name);
        productsPojo.setType(type);
        productsPojo.setPrice(price);
        productsPojo.setUpc(upc);
        productsPojo.setManufacturer(manufacturer);
        productsPojo.setDescription(description);
        productsPojo.setModel(model);
        productsPojo.setShipping(shipping);
        productsPojo.setUrl(url);
        productsPojo.setImage(img);
       return SerenityRest. given().log().all()
                .header("Content-Type","application/json; charset=utf-8")
                .pathParam("productID",productID)
                .body(productsPojo)
                .when()
                .put(EndPoints.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then();
    }
    @Step("Delete product with productID :{0} ")
    public ValidatableResponse deleteproduct (int productID){
     return    SerenityRest. given()
                .pathParam("productID",productID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_PRODUCT_BY_ID)
                .then();


    }
    @Step("Verify product has been deleted for productID: {0}")
    public ValidatableResponse verifyProductDeleted(int productID){
        return SerenityRest.given()
                .log().all()
                .pathParam("productID",productID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();

}
}