package com.example.adminvansales.model;

import org.json.JSONException;
import org.json.JSONObject;

public class TargetDetalis {
   String SalManNo;
   String SalManName;
   String Date;
   int TargetType;
   String TargetNetSale;
   String OrignalNetSale;
   String ItemNo;
   String ItemName;

   double ItemTarget;
   String  PERC;

   public String getPERC() {
      return PERC;
   }

   public void setPERC(String PERC) {
      this.PERC = PERC;
   }

   public String getTargetNetSale() {
      return TargetNetSale;
   }

   public void setTargetNetSale(String targetNetSale) {
      TargetNetSale = targetNetSale;
   }

   public String getOrignalNetSale() {
      return OrignalNetSale;
   }

   public void setOrignalNetSale(String orignalNetSale) {
      OrignalNetSale = orignalNetSale;
   }

   public double getItemTarget() {
      return ItemTarget;
   }

   public void setItemTarget(double itemTarget) {
      ItemTarget = itemTarget;
   }

   public String getSalManNo() {
      return SalManNo;
   }

   public void setSalManNo(String salManNo) {
      SalManNo = salManNo;
   }

   public String getSalManName() {
      return SalManName;
   }

   public void setSalManName(String salManName) {
      SalManName = salManName;
   }

   public String getDate() {
      return Date;
   }

   public void setDate(String date) {
      Date = date;
   }

   public int getTargetType() {
      return TargetType;
   }

   public void setTargetType(int targetType) {
      TargetType = targetType;
   }



   public String getItemNo() {
      return ItemNo;
   }

   public void setItemNo(String itemNo) {
      ItemNo = itemNo;
   }

   public String getItemName() {
      return ItemName;
   }

   public void setItemName(String itemName) {
      ItemName = itemName;
   }



   public JSONObject getJsonObject(){

      JSONObject jsonObject=new JSONObject();

      try {

         jsonObject.put("SALE_MAN_NUMBER", SalManNo);
         jsonObject.put("SALE_MAN_NAME", SalManName);
         jsonObject.put("SMONTH", Date);
         jsonObject.put("TARGET_TYPE", TargetType);
         jsonObject.put("TARGET_NET_SALE", TargetNetSale);


      } catch (JSONException e) {
         e.printStackTrace();
      }

      return jsonObject;
   }
   public JSONObject getJsonObject2(){

      JSONObject jsonObject=new JSONObject();

      try {

         jsonObject.put("SALE_MAN_NUMBER", SalManNo);
         jsonObject.put("SALE_MAN_NAME", SalManName);

         jsonObject.put("ITEMOCODE", ItemNo);
         jsonObject.put("ITEMNAME", ItemName);
         jsonObject.put("SMONTH", Date);
         jsonObject.put("TARGET_TYPE", TargetType);
         jsonObject.put("ITEMTARGET", ItemTarget);

      } catch (JSONException e) {
         e.printStackTrace();
      }

      return jsonObject;
   }

}
