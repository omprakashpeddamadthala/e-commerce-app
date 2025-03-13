package com.ms.dlj.repository;

import com.ms.dlj.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface OrderRepository extends JpaRepository<Order, Serializable> {
}
