package com.demo.entities;
// Generated Feb 23, 2024, 1:06:06 PM by Hibernate Tools 4.3.6.Final
import jakarta.persistence.*;

/**
 * OrderDetail generated by hbm2java
 */
@Entity
@Table(name = "order_detail", catalog = "pento_sm4")
public class OrderDetail implements java.io.Serializable {

	private Integer id;
	private Menu menu;
	private Orders orders;
	private Promotions promotions;
	private int quantity;
	private double price;

	public OrderDetail() {
	}

	public OrderDetail(Promotions promotions, int quantity, double price) {
		this.promotions = promotions;
		this.quantity = quantity;
		this.price = price;
	}

	public OrderDetail(Menu menu, Orders orders, Promotions promotions, int quantity, double price) {
		this.menu = menu;
		this.orders = orders;
		this.promotions = promotions;
		this.quantity = quantity;
		this.price = price;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "menu_id")
	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id")
	public Orders getOrders() {
		return this.orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "promotion_id", nullable = false)
	public Promotions getPromotions() {
		return this.promotions;
	}

	public void setPromotions(Promotions promotions) {
		this.promotions = promotions;
	}

	@Column(name = "quantity", nullable = false)
	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Column(name = "price", nullable = false, precision = 22, scale = 0)
	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
