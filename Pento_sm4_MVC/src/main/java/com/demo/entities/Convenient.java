package com.demo.entities;
// Generated Feb 23, 2024, 1:06:06 PM by Hibernate Tools 4.3.6.Final

import jakarta.persistence.*;
/**
 * Convenient generated by hbm2java
 */
@Entity
@Table(name = "convenient", catalog = "pento_sm4")
public class Convenient implements java.io.Serializable {

	private Integer id;
	private Branchs branchs;
	private String name;

	public Convenient() {
	}

	public Convenient(Branchs branchs, String name) {
		this.branchs = branchs;
		this.name = name;
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
	@JoinColumn(name = "branch_id", nullable = false)
	public Branchs getBranchs() {
		return this.branchs;
	}

	public void setBranchs(Branchs branchs) {
		this.branchs = branchs;
	}

	@Column(name = "name", nullable = false, length = 250)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
