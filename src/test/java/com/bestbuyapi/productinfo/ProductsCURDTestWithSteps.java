package com.bestbuyapi.productinfo;

import com.bestbuyapi.productsinfo.ProductsSteps;

import com.bestbuyapi.testbase.TestBase;
import com.bestbuyapi.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;


@RunWith(SerenityRunner.class)

public class ProductsCURDTestWithSteps extends TestBase {

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

    @Steps
    ProductsSteps productsSteps;

    @Title("This will create a new product")
    @Test
    public void  test001(){
        ValidatableResponse response = productsSteps.createproduct( name,  type,price,  upc, shipping,  manufacturer, description,  model,  url,  img);
        response.log().all().statusCode(201);
    }
    @Title("verify if product was created")
    @Test
    public void test002() {
        HashMap<String, Object> promap = productsSteps.getProductInfoByName(name);
        Assert.assertThat(promap, hasValue(name));
        productID = (int) promap.get("id");
        System.out.println(productID);
    }
    @Title("update the product and verify the update information")
    @Test
    public void test003(){
      name = name +"updated";
        productsSteps.updateproduct(productID,name,type,price,  upc, shipping,  manufacturer, description,  model,  url,  img);
        HashMap<String, Object> promap = productsSteps.getProductInfoByName(name);

        Assert.assertThat(promap,hasValue(name));
    }
    @Title("Delete the product and verify if the student is deleted")
    @Test
    public void test004(){
     productsSteps.deleteproduct(productID).log().all().statusCode(200);
     productsSteps.verifyProductDeleted(productID).log().all().statusCode(404);
    }
}
