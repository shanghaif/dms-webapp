package com.xmomen.module.wx.module.order.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xmomen.module.wx.module.order.model.MyOrderQuery;
import com.xmomen.module.wx.module.order.model.OrderDetailModel;
import com.xmomen.module.wx.module.order.model.OrderModel;
import com.xmomen.module.wx.module.order.service.MyOrderService;

@Controller
public class MyOrderController {

	@Autowired
	MyOrderService myOrderService;

	/**
	 * 
	 * @param memberCode
	 * @param status 0-未支付 1 待收货
	 * @param minCreateTime
	 * @param maxCreateTime
	 * @return
	 */
	@RequestMapping("/myOrder")
	@ResponseBody
	public List<OrderModel> myOrder(@RequestParam(value = "memberCode", required = false) String memberCode,
			@RequestParam(value = "status", required = false) Integer status, 
			@RequestParam(value = "minOrderTime", required = false) Date minCreateTime,
			@RequestParam(value = "maxOrderTime", required = false) Date maxCreateTime) {
		MyOrderQuery myOrderQuery  = new MyOrderQuery();
		myOrderQuery.setMemberCode(memberCode);
		myOrderQuery.setStatus(status);
		myOrderQuery.setMinOrderTime(minCreateTime);
		myOrderQuery.setMaxOrderTime(maxCreateTime);
		return myOrderService.myOrder(myOrderQuery);
	}
	
	@RequestMapping("/myOrder/{orderId}")
	@ResponseBody
	public  OrderDetailModel orderDetail(@PathVariable("orderId") Integer orderId) {
		return myOrderService.getOrderDetail(orderId);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				super.setValue(new Date(Long.valueOf(text)));
			}
			
		});
	}
}