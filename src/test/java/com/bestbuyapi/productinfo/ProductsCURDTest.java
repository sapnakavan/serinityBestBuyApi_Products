package com.bestbuyapi.productinfo;


import com.bestbuyapi.constants.EndPoints;
import com.bestbuyapi.model.ProductsPojo;
import com.bestbuyapi.testbase.TestBase;
import com.bestbuyapi.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static io.restassured.RestAssured.given;
import static org.assertj.core.api.BDDAssertions.then;
import static org.hamcrest.Matchers.hasValue;



public class ProductsCURDTest extends TestBase {

    static String name = "sap"+ TestUtils.getRandomValue();
    static String type = "pate"+ TestUtils.getRandomValue();
    static int price = 10;
    static String upc = "shelf"+TestUtils.getRandomValue();
    static int shipping = 500;
    static String manufacturer = "lencom"+TestUtils.getRandomValue();
    static String model = "85-870";
    static String url = "http://img.bbystatic.com/BestBuy_US/images/products/1276/127687_sa.jpg";
    static String img ="http://www.bestbuy.com/site/duracell-aa-batteries-8-pack/127687.p?id=1051384045676&skuId=127687&cmp=RMXCC";
    static String description = "From our expanded online assortment; compatible with select AM vehicles; Metal material";
    static  int productID;
    @Title("This is will get all information of all store")
    @Test
    public void test001(){
       SerenityRest.given().log().all()
                .when().get()
                .then()
                .log().all()
                .statusCode(200);


    }

    @Title("This will create new product")
    @Test
    public void test002(){
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
        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .body(productsPojo)
                .when()
                .post()
                .then().log().all().statusCode(201);

    }

    @Title("verify if product was created")
    @Test
    public void test003(){
       String p1="data.findAll{it.name='";
       String p2="'}.get(0)";
        HashMap<String,Object> promap =SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1+name+p2);
        Assert.assertThat(promap,hasValue(name));
        productID =(int) promap.get("id");
    }
    @Title("update the product and verify the update information")
    @Test
    public void test004(){
        name=name+"updated";

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
       SerenityRest. given().log().all()
                .header("Content-Type","application/json; charset=utf-8")
                .pathParam("productID",productID)
                .body(productsPojo)
                .when()
                .put(EndPoints.UPDATE_SINGLE_PRODUCT_BY_ID)
                .then().log().all().statusCode(200);
        String p1="data.findAll{it.name='";
        String p2="'}.get(0)";
        HashMap<String,Object> promap =SerenityRest.given().log().all()
                .when()
                .get()
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        Assert.assertThat(promap,hasValue(name));

    }
    @Title("Delete the product and verify if the product is deleted")
    @Test
    public void test005(){
        SerenityRest. given()
                .pathParam("productID",productID)
                .when()
                .delete(EndPoints.DELETE_SINGLE_PRODUCT_BY_ID)
                .then()
                .statusCode(200);
        given().log().all()
                .pathParam("productID",productID)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then()
                .statusCode(404);

    }

}
