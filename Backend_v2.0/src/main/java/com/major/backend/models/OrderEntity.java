package com.major.backend.models;

import com.major.backend.response.DispatchItenary;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
@TypeAlias("orders")
public class OrderEntity {


	@Id
	private String orderId;

	private Double cost;

	private DispatchItenary items;

	private String orderName;


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Double getCost() { return cost; }

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public DispatchItenary getItems() {
		return items;
	}

	public void setItems(DispatchItenary items) {
		this.items = items;
	}

	public String getOrderName() { return orderName; }

	public void setOrderName(String orderName) { this.orderName = orderName; }
}
