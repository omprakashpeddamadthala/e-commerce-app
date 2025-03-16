package com.ms.dlj.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface OrderRepository extends JpaRepository<Order, Serializable> {
}
